package com.example.demo.controller.api.app;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

import com.example.demo.entity.Genre;
import com.example.demo.repository.GenreRepository;

@Controller
public class GetGenreApiController {
	
	@Autowired
	GenreRepository repository;
	
	/**
	 * 商品一覧を返すAPI。
	 * @Endpoint /api/v1/genre/{id}
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/genre/{id}", method = RequestMethod.GET)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(
			Model model,
			HttpSession session,
			@PathVariable(name = "id") int id
		) {
		HashMap<String, Object> data = new HashMap<>();
		data.put("genre", repository.findById(id));
		return data;
	}
	
}
