package com.example.demo.controller.api.login;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.UserData;
import com.example.demo.entity.User;
import com.example.demo.form.LoginForm;
import com.example.demo.repository.UserRepository;

@Controller
public class LogInOutApiController {
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping(path = "/api/v1/auth/login", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<HashMap<String, Object>> viewPage(HttpServletRequest request, ModelAndView mav, HttpSession session) {
		boolean isOK = true;
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Connection", "Keep-Alive");
		HashMap<String, Object> out = new HashMap<>();
		
		try {
			String origin = request.getHeader("Authorization"),
					authorizationHeader = new String(
					Base64.getDecoder().decode(
							origin
					));
			
			String regex = "^(.*?):(.*?)$";
			Pattern p = Pattern.compile(regex);
		    Matcher m = p.matcher(authorizationHeader);
			String user_name = "", password = "";
			if(m.find()) {
				user_name = m.group(1);
				password = m.group(2);
			} else {
				isOK = false;
			}
			if(user_name.equals("") || password.equals("")) {
				isOK = false;
			}
			
			// チェック
			List<User> userList = repository.findByUserNameAndPassword(
				user_name, DigestUtils.sha256Hex( password ).toUpperCase()
			);
			
			if(userList.size() == 0) {
				isOK = false;
			} else {
				UserData user = new UserData();
				user.setUser_id(userList.get(0).getUserId());
				user.setUser_name(userList.get(0).getUserName());
				session.setAttribute("userData", user);
			}
		} catch (Exception e) {
			isOK = false;
		}
		
		HttpStatus status = HttpStatus.OK;
		if(isOK) {
			out.put("statusCode", 200);
			out.put("status", "ok");
		} else {
			out.put("statusCode", 401);
			out.put("message", "認証に失敗しました。");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<HashMap<String, Object>>(out, headers, status);
	}
	
	
	@RequestMapping(path = "/api/v1/auth", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<HashMap<String, Object>> checkLogin(HttpServletRequest request, ModelAndView mav, HttpSession session) {
		boolean isOK = true;
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Connection", "Keep-Alive");
		HashMap<String, Object> out = new HashMap<>();
		
		Object obj = session.getAttribute("userData");
		if(obj == null) {
			isOK = false;
		}
		
		HttpStatus status = HttpStatus.OK;
		if(isOK) {
			out.put("status", "ok");
		} else {
			out.put("statusCode", 401);
			out.put("message", "資格情報が見つかりませんでした。");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<HashMap<String, Object>>(out, headers, status);
	}
	
	@RequestMapping(path = "/api/v1/logout", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<HashMap<String, Object>> logout(HttpServletRequest request, ModelAndView mav, HttpSession session) {
		boolean isOK = true;
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Connection", "Keep-Alive");
		HashMap<String, Object> out = new HashMap<>();
		
		Object obj = session.getAttribute("userData");
		if(obj == null) {
			isOK = false;
		}
		
		session.setAttribute("userData", null);
		
		HttpStatus status = HttpStatus.OK;
		if(isOK) {
			out.put("status", "ok");
		} else {
			out.put("statusCode", 401);
			out.put("message", "資格情報が見つかりませんでした。");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<HashMap<String, Object>>(out, headers, status);
	}
	
}
