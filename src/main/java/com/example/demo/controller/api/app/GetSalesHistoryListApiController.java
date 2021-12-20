package com.example.demo.controller.api.app;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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
import com.example.demo.repository.SalesDataRepository;

@Controller
public class GetSalesHistoryListApiController {

	@Autowired
	SalesDataRepository sales_repo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 販売履歴を返すAPI。
	 * 
	 * @Endpoint /api/v1/sales/history
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/sales/history", method = RequestMethod.GET)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(Model model, HttpSession session) {
		
		
		
		
		
		
		return null;
	}

}
