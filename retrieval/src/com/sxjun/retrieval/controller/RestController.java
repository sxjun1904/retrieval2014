package com.sxjun.retrieval.controller;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.jfinal.core.Controller;
import com.sxjun.retrieval.pojo.User;

public class RestController extends Controller {
	public void index(){
		
	}
	
    public void get() { 
		User user = new User();
		user.setId("1");
		user.setPassword("1111");
		user.setUsername("asdf");
		renderJson(user);
    } 
}
