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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.repository.ItemRepository;

@Controller
public class GetItemByCodeApiController {

	@Autowired
	ItemRepository repository;

	/**
	 * 商品情報をバーコードから返すAPI。
	 * 
	 * @Endpoint /api/v1/item/{id}
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/item/code/{code}", method = RequestMethod.GET)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(Model model, HttpSession session,
			@PathVariable(name = "code") String code) {
		HashMap<String, Object> data = new HashMap<>();
		data.put("itemData", repository.findByCode(code));
		return data;
	}

}
