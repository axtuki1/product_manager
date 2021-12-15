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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Item;
import com.example.demo.form.NewItemInsertForm;
import com.example.demo.form.RegisterForm;
import com.example.demo.repository.ItemRepository;

@Controller
public class RegisterApiController {

	@Autowired
	ItemRepository repository;

	/**
	 * レジスター画面から購入アイテムを受け取り、登録する。
	 * 
	 * @Endpoint /api/v1/register
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/register", method = RequestMethod.POST)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(Model model, HttpSession session,
			@RequestBody HashMap<String, Object> form) {
		HashMap<String, Object> data = new HashMap<>();// 入力値チェック
		List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) form.get("items");
		for(HashMap<String, Object> item : items) {
			
		}
		return data;
	}

}
