package com.example.demo.controller.api.app;

import java.util.HashMap;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.EndPointNotFoundException;
import com.example.demo.form.PasswordForm;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.SalesDataRepository;

@Controller
public class DANGER_ItemAllDeleteApiController {

	@Autowired
	ItemRepository repository;
	
	@Value("${app.ALLDELETEPASSWORD:SpringSpring}") 
	private String password_con;

	/**
	 * ！危険な操作！
	 * 販売履歴をすべて削除します。
	 * パスワードはapplication.propertiesに記述。
	 * @throws EndPointNotFoundException 
	 * 
	 * @Endpoint /api/v1/DANGER/SalesDataAllClear
	 * @Method GET
	 */
	@RequestMapping(path = "/api/v1/DANGER/ProductDataAllClear", method = RequestMethod.POST)
	@ResponseBody // JSONとしてレスポンスするために使う
	@CrossOrigin
	public HashMap<String, Object> viewPage(Model model, HttpSession session, @RequestBody PasswordForm password) throws EndPointNotFoundException {
		if(!password_con.equals(password.getPassword())) throw new EndPointNotFoundException();
		HashMap<String, Object> data = new HashMap<>();
		repository.deleteAll();
		data.put("result", "ok");
		return data;
	}

}
