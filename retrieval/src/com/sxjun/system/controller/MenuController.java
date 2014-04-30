package com.sxjun.system.controller;

import com.jfinal.core.Controller;

public class MenuController extends Controller{
	public void index(){
		render("menuTree.jsp");
	}
}
