package com.example.demo.controller.api.app;

import java.util.HashMap;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Item;
import com.example.demo.form.NewItemInsertForm;
import com.example.demo.repository.ItemRepository;

@Controller
public class AddNewItemApiController {

	@Autowired
	ItemRepository repository;

	/**
	 * 新規に商品を追加するAPI。
	 * @Endpoint /api/v1/item/new
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/item/new", method = RequestMethod.POST)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(
			Model model,
			HttpSession session,
			@RequestBody NewItemInsertForm form
		) {
		HashMap<String, Object> data = new HashMap<>();
		if(
				form.getName().equals("") ||
				form.getPrice() < 0 ||
				form.getGenre() < 0 || 
				form.getAmount() < 0
			) {
			return null;
		} else {
			repository.save(Item.genItem(
				0,
				form.getName(),
				form.getPrice(),
				form.getAmount(),
				form.getGenre(),
				form.getCode() == null ? "" : form.getCode()
			));
			data.put("result","ok");
		}
		return data;
	}

}
