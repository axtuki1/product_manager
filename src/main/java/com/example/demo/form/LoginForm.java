package com.example.demo.form;

import org.apache.commons.codec.digest.DigestUtils;

public class LoginForm {
	private String username, password;
	private String[] loginOption;
	
	public LoginForm(String username, String password, String[] loginOption){
		this.username = username;
		this.password = password;
		this.loginOption = loginOption;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 属性PasswordをSHA-256でハッシュ化した文字列を返す。
	 * @return
	 */
	public String getPasswordHash() {
		return DigestUtils.sha256Hex( this.password );
	}
	
	public String[] getLoginOption() {
		return loginOption;
	}

	public void setLoginOption(String[] loginOption) {
		this.loginOption = loginOption;
	}
}
