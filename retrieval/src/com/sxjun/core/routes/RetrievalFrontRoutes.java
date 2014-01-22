package com.sxjun.core.routes;


import com.jfinal.config.Routes;
import com.sxjun.retrieval.common.Global;
import com.sxjun.retrieval.controller.SearchController;

public class RetrievalFrontRoutes extends Routes {
	public void config() {
		add(Global.frontPath+"/search", SearchController.class,"retrieval/search");
	}
}
