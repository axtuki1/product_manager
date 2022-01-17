package com.example.demo.controller.api.app;

import java.util.ArrayList;
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
import com.example.demo.entity.ItemMovement;
import com.example.demo.entity.ItemSales;
import com.example.demo.entity.SalesData;
import com.example.demo.form.NewItemInsertForm;
import com.example.demo.form.RegisterForm;
import com.example.demo.repository.ItemMovementRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ItemSalesRepository;
import com.example.demo.repository.SalesDataRepository;

@Controller
public class RegisterApiController {

	@Autowired
	ItemSalesRepository item_sales_repo;
	
	@Autowired
	SalesDataRepository sales_data_repo;
	
	@Autowired
	ItemRepository item_repo;

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
		
		sales_data_repo.save(SalesData.genData((int)form.get("total"), (int)form.get("payment"), "CASH"));
		
		SalesData sales_data = sales_data_repo.findTopByOrderByIdDesc().get(0);
		int id = sales_data.getId();
		
		ArrayList<ItemSales> itemmove = new ArrayList<>();
		
		List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) form.get("items");
		for(HashMap<String, Object> item : items) {
			itemmove.add(ItemSales.gen(
					id,
					(int)item.get("itemId"),
					(int)item.get("amount"),
					(int)item.get("price")
			));
			Item item_data = item_repo.getById((int)item.get("itemId"));
			item_data.setAmount(item_data.getAmount() - (int)item.get("amount"));
			item_repo.save(item_data);
		}
		item_sales_repo.saveAll(itemmove);
		
		data.put("sales_data", sales_data);
		
		return data;
	}

}
