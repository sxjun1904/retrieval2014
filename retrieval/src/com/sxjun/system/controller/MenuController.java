package com.sxjun.system.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.sxjun.system.pojo.Menu;

public class MenuController extends Controller{
	public void index(){
		render("menuTree.jsp");
	}
}
