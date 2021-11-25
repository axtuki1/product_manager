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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.repository.ItemRepository;

@Controller
public class GetItemListApiController {

	@Autowired
	ItemRepository repository;

	/**
	 * 商品一覧を返すAPI。
	 * @Endpoint /api/v1/items 
	 * @Method GET
	 * @param LIMIT  返すデータ数を指定する Default: 30
	 * @param offset 先頭からずらす個数    Default: 0
	 */
	@RequestMapping(path = "/api/v1/items", method = RequestMethod.GET)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(
			Model model,
			HttpSession session,
			@RequestParam(name = "offset", defaultValue = "0") int offset,
			@RequestParam(name = "limit", defaultValue = "30") int limit,
			@RequestParam(name = "sortC", defaultValue = "id") String sort,
			@RequestParam(name = "sortV", defaultValue = "ASC") String sortVector
		) {
		HashMap<String, Object> data = new HashMap<>();
		sort = sort.toLowerCase();
		switch(sort) {
		case "id":
		case "name":
		case "price":
		case "amount":
			sort = "item_"+sort;
			break;
		default:
			sort = "item_id";
			break;
		}
		data.put("items", repository.findAll());
		return data;
	}

}
