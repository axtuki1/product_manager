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
public class EditItemApiController {

	@Autowired
	ItemRepository repository;

	/**
	 * 新規に商品を追加するAPI。
	 * @Endpoint /api/v1/item/new
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/item/{id}/edit", method = RequestMethod.POST)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public ResponseEntity<HashMap<String, Object>> viewPage(
			Model model,
			HttpSession session,
			@RequestBody NewItemInsertForm form,
			@PathVariable(name = "id") int id
		) {
		boolean authOK = true,isOK = true;
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Connection", "Keep-Alive");
		HashMap<String, Object> out = new HashMap<>(), data = new HashMap<>();
		Object obj = session.getAttribute("userData");
		if(obj == null) {
			authOK = false;
		} else {
			// 入力値チェック
			if(
					form.getPrice() < 0 ||
					form.getGenre() < 0 || 
					form.getAmount() < 0
				) {
				isOK = false;
			} else {
				repository.save(Item.genItem(
					id,
					form.getName(),
					form.getPrice(),
					form.getAmount(),
					form.getGenre()
				));
				data.put("result","ok");
			}
			
		}
		
		HttpStatus status = HttpStatus.OK;
		if(!authOK){
			out.put("statusCode", 401);
			out.put("message", "認証に失敗しました。");
			status = HttpStatus.UNAUTHORIZED;
		} else if(isOK) {
			out.put("data", data);
			out.put("statusCode", 200);
			out.put("message", "ok");
		} else {
			out.put("statusCode", 400);
			out.put("message", "入力が不正です。");
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<HashMap<String, Object>>(out, headers, status);
	}

}
