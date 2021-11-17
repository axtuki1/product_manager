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
public class DeleteItemApiController {

	@Autowired
	ItemRepository repository;

	/**
	 * 商品を削除するAPI。
	 * 
	 * @Endpoint /api/v1/item/{id}
	 * @Method DELETE
	 */
	@RequestMapping(path = "/api/v1/item/{id}", method = RequestMethod.DELETE)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public ResponseEntity<HashMap<String, Object>> viewPage(Model model, HttpSession session,
			@PathVariable(name = "id") int id) {
		boolean authOK = true, isOK = true;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Connection", "Keep-Alive");
		HashMap<String, Object> out = new HashMap<>(), data = new HashMap<>();
		Object obj = session.getAttribute("userData");
		if (obj == null) {
			authOK = false;
		} else {
			repository.deleteById(id);
			data.put("result", "ok");
		}

		HttpStatus status = HttpStatus.OK;
		if (!authOK) {
			out.put("statusCode", 401);
			out.put("message", "認証に失敗しました。");
			status = HttpStatus.UNAUTHORIZED;
		} else if (isOK) {
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
