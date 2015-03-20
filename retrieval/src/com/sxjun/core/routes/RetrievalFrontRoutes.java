package com.sxjun.core.routes;


import com.jfinal.config.Routes;
import com.sxjun.core.common.utils.Global;
import com.sxjun.retrieval.controller.InitController;
import com.sxjun.retrieval.controller.SearchController;
import com.sxjun.retrieval.controller.TokenController;

public class RetrievalFrontRoutes extends Routes {
	public void config() {
		add(Global.frontPath+"/search", SearchController.class,"retrieval/search");
		add(Global.frontPath+"/init", InitController.class);
		add(Global.frontPath+"/createToken", TokenController.class);
	}
}
