package com.example.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {

	@RequestMapping(path = "/**", method = RequestMethod.GET)
	@ResponseBody
	public Resource endpoint(ModelAndView mav) {
		try {
			return new ClassPathResource("static/index.html");
		} catch (Exception e) {
			mav.setStatus(HttpStatus.NOT_FOUND);
			return new ClassPathResource("static/error.html");
		}
	}
	

	@RequestMapping(path = "/{dataType:vue|css|js}/**", method = RequestMethod.GET)
	@ResponseBody
	public Resource endpoint_resource(ModelAndView mav, HttpServletRequest request) {
		try {
			Resource res = new ClassPathResource("static"+request.getRequestURI());
			res.getFile(); // 存在確認させるためのアレ
			return res;
		} catch (FileNotFoundException e) {
			mav.setStatus(HttpStatus.NOT_FOUND);
			return new ClassPathResource("static/error.html");
		} catch (IOException e) {
			mav.setStatus(HttpStatus.NOT_FOUND);
			return new ClassPathResource("static/error.html");
		}
	}

	// レジスター用
	
	@RequestMapping(path = "/register/**", method = RequestMethod.GET)
	@ResponseBody
	public Resource register_endpoint(ModelAndView mav) {
		try {
			return new ClassPathResource("static/register/index.html");
		} catch (Exception e) {
			mav.setStatus(HttpStatus.NOT_FOUND);
			return new ClassPathResource("static/error.html");
		}
	}
	
	@RequestMapping(path = "/register/{dataType:vue|css|js}/**", method = RequestMethod.GET)
	@ResponseBody
	public Resource register_endpoint_resource(ModelAndView mav, HttpServletRequest request) {
		try {
			Resource res = new ClassPathResource("static/"+request.getRequestURI());
			res.getFile(); // 存在確認させるためのアレ
			return res;
		} catch (FileNotFoundException e) {
			mav.setStatus(HttpStatus.NOT_FOUND);
			return new ClassPathResource("static/error.html");
		} catch (IOException e) {
			mav.setStatus(HttpStatus.NOT_FOUND);
			return new ClassPathResource("static/error.html");
		}
	}
	
	
	
	@RequestMapping(path = "/api/v1/**")
	@ResponseBody
	@CrossOrigin
	public HashMap<String, Object> apiDefaultError(ModelAndView model) {
		model.setStatus(HttpStatus.NOT_FOUND);
		return new HashMap<String, Object>() {
			{
				put("error", "指定されたエンドポイントは存在しません。");
				put("statusCode", 404);
			}
		};
	}
}
