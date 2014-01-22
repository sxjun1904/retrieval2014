package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

public class User extends BasePojo{
	private static final long serialVersionUID = -6504068216661631050L;
	
	private String username;
	private String password;
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

}
