package com.sxjun.retrieval.controller;

import com.jfinal.core.Controller;


public class TokenController extends Controller {
	public void index(){
		
	}
	
	public void createToken(){
		String tokenName = getPara("tokenName");
		int secondsOfTimeOut = getParaToInt("secondsOfTimeOut");
		createToken(tokenName, secondsOfTimeOut);
	}
}
