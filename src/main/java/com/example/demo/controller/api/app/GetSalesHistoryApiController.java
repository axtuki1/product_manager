package com.example.demo.controller.api.app;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.repository.ItemMovementRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ItemSalesRepository;
import com.example.demo.repository.SalesDataRepository;

@Controller
public class GetSalesHistoryApiController {

	@Autowired
	SalesDataRepository repository;
	
	@Autowired
	ItemSalesRepository itemSales_repository;

	@Autowired
	JdbcTemplate jdbc;
	
	/**
	 * 販売情報を返すAPI。
	 * 
	 * @Endpoint /api/v1/item/{id}
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/sales/history/{id}", method = RequestMethod.GET)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(Model model, HttpSession session,
			@PathVariable(name = "id") int id) {
		HashMap<String, Object> data = new HashMap<>();
		data.put("salesData", repository.findById(id));
		data.put(
					"move", 
					jdbc.queryForList(
							"SELECT A.item_id, item_name, A.item_price, A.item_amount FROM `item_sales` A LEFT OUTER JOIN `items` B ON A.item_id = B.item_id WHERE A.sales_code = ?", 
							id
					)
				);
		return data;
	}

}
