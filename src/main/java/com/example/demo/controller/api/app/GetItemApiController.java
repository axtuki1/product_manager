package com.example.demo.controller.api.app;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

@Controller
public class GetItemApiController {

	@Autowired
	ItemRepository repository;

	/**
	 * 商品一覧を返すAPI。
	 * 
	 * @Endpoint /api/v1/item/{id}
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/item/{id}", method = RequestMethod.GET)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(Model model, HttpSession session,
			@PathVariable(name = "id") int id) {
		HashMap<String, Object> data = new HashMap<>();
		Optional<Item> list = repository.findById(id);
		if(list == null || list.isEmpty()) return null;
		data.put("itemData", list);
		return data;
	}

}
