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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Genre;
import com.example.demo.repository.GenreRepository;

@Controller
public class GetGenreListApiController {
	
	@Autowired
	GenreRepository repository;
	
	/**
	 * 商品一覧を返すAPI。
	 * @Endpoint /api/v1/items 
	 * @Method GET
	 * @param LIMIT  返すデータ数を指定する Default: 30
	 * @param offset 先頭からずらす個数    Default: 0
	 */
	@RequestMapping(path = "/api/v1/genre", method = RequestMethod.GET)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(
			Model model,
			HttpSession session
		) {
		HashMap<String, Object> data = new HashMap<>();
		List<Genre> genre = repository.findAll();
		data.put("genre", genre);
		return data;
	}
	
}
