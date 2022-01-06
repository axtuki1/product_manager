package com.example.demo.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.entity.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Aspect
public class RequiredLoginInterceptor {
	
	@Autowired
	private HttpSession session;
	
	/**
	 * api.appパッケージ内で動くControllerに対して割り込むInterceptor。
	 * HTTP Statusをしっかり返したいけどなんか返してくれないから200 OK固定。つらい。
	 */
	@Around("execution(* com.example.demo.controller.api.app.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		boolean isOK = true;
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
			} catch ( Exception e ) {
				e.printStackTrace();
				isOK = false;
			}
		}
		HttpStatus status = HttpStatus.OK;
		if (!isOK) {
			out.put("message", "認証に失敗しました。");
			status = HttpStatus.UNAUTHORIZED;
		} else if (data != null) {
			out.put("data", data);
			out.put("message", "ok");
		} else {
			out.put("message", "入力が不正です。");
			status = HttpStatus.BAD_REQUEST;
		}
		out.put("statusCode", status.value());
//		if(status == HttpStatus.OK) 
			return out;
		// *痛み*
		// ResponseEntityを返せばSpring君がよしなにやってくれるんじゃと思ったけど甘かった。
//		return new ResponseEntity<HashMap<String, Object>>(out, headers, status);
	}
}
