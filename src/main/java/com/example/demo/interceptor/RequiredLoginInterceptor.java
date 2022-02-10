package com.example.demo.interceptor;

import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.bean.EndPointNotFoundException;

@Component
@Aspect
public class RequiredLoginInterceptor {
	
	@Autowired
	private HttpSession session;
	
	/**
	 * api.appパッケージ内で動くControllerに対して割り込むInterceptor。
	 * HTTP Statusをしっかり返したいけどなんか返してくれないから200 OK固定。つらい。
	 * 2022/02/10 勝った！！！！ ちゃんとStatusCode返すいい子になったよ！！！！！
	 */
	@Around("execution(* com.example.demo.controller.api.app.*.*(..)) && @annotation(requestMapping)")
	public Object around(
            ProceedingJoinPoint pjp,
			RequestMapping requestMapping) throws Throwable {
		boolean isOK = true;
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Connection", "Keep-Alive");
		HashMap<String, Object> out = new HashMap<>();
		Object data = null;
		Object obj = session.getAttribute("userData");
		if(obj == null) {
			isOK = false;
		} else {
			try {
				data = pjp.proceed();
			} catch( EndPointNotFoundException e ) {
				response.setStatus(404);
				return new HashMap<String, Object>() {
					{
						put("error", "指定されたエンドポイントは存在しません。");
						put("statusCode", 404);
					}
				};
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		HttpStatus status = HttpStatus.OK;
		if (!isOK) {
			out.put("message", "認証に失敗しました。");
			status = HttpStatus.UNAUTHORIZED;
		} else if (data != null) {
			if(data instanceof HashMap && ((HashMap<String, Object>)data).get("statusCode") != null) {
				status = (HttpStatus) ((HashMap<String, Object>)data).get("statusCode");
				((HashMap<String, Object>)data).remove("statusCode");
			}
			if(status.equals(HttpStatus.OK)) {
				out.put("message", "ok");
			} else {
				out.put("message", "error");
			}
			out.put("data", data);
		} else {
			out.put("message", "入力が不正です。");
			status = HttpStatus.BAD_REQUEST;
		}
		out.put("statusCode", status.value());
		response.setStatus(status.value());
		// 死闘の記録
//		if(status == HttpStatus.OK) 
		return out;
		// *痛み*
		// ResponseEntityを返せばSpring君がよしなにやってくれるんじゃと思ったけど甘かった。
//		return new ResponseEntity<HashMap<String, Object>>(out, headers, status);
		// 勝った！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！１
		// requestMappingのresponseに直接ぶち込んで解決！
	}
}
