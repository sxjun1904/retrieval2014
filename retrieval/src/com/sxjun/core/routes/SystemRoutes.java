package com.sxjun.core.routes;

import com.jfinal.config.Routes;
import com.sxjun.retrieval.common.Global;
import com.sxjun.retrieval.controller.LoginController;
import com.sxjun.retrieval.controller.RestController;
import com.sxjun.system.controller.MenuController;

public class SystemRoutes extends Routes {
	public void config() {
		add("/", LoginController.class);
		add("/login", LoginController.class);
		add(Global.adminPath+"/menu", MenuController.class,"system/menu");
		add(Global.frontPath+"/rest", RestController.class);
	}
}
