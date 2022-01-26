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
import com.example.demo.repository.ItemSalesRepository;
import com.example.demo.repository.SalesDataRepository;

@Controller
public class DeleteSalesHistoryApiController {

	@Autowired
	SalesDataRepository salesdata_repo;
	
	@Autowired
	ItemSalesRepository itemsales_repo;

	/**
	 * 販売情報を削除するAPI。
	 * 
	 * @Endpoint /api/v1/sales/history/{id}
	 * @Method DELETE
	 */
	@RequestMapping(path = "/api/v1/sales/history/{id}", method = RequestMethod.DELETE)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(Model model, HttpSession session,
			@PathVariable(name = "id") int id) {
		HashMap<String, Object> data = new HashMap<>();
		itemsales_repo.deleteBySalesCode(id);
		salesdata_repo.deleteById(id);
		data.put("result", "ok");
		return data;
	}

}
