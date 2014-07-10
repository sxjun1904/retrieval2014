package com.sxjun.core.routes;


import com.jfinal.config.Routes;
import com.sxjun.retrieval.common.Global;
import com.sxjun.retrieval.controller.DatabaseController;
import com.sxjun.retrieval.controller.FiledMapperController;
import com.sxjun.retrieval.controller.FiledSpecialMapperController;
import com.sxjun.retrieval.controller.IndexCagetoryController;
import com.sxjun.retrieval.controller.InitFieldController;
import com.sxjun.retrieval.controller.RDatabaseIndexController;
import com.sxjun.retrieval.controller.TriggerViewController;
import com.sxjun.system.controller.ThemeController;

public class RetrievalAdminRoutes extends Routes {
	public void config() {
		add(Global.adminPath+"/database", DatabaseController.class,"retrieval/database");
		add(Global.adminPath+"/initField", InitFieldController.class,"retrieval/initField");
		add(Global.adminPath+"/theme", ThemeController.class);
		add(Global.adminPath+"/filedMapper", FiledMapperController.class,"retrieval/filedMapper");
		add(Global.adminPath+"/filedSpecialMapper", FiledSpecialMapperController.class,"retrieval/filedSpecialMapper");
		add(Global.adminPath+"/indexCategory", IndexCagetoryController.class,"retrieval/indexCategory");
		add(Global.adminPath+"/rDatabaseIndex", RDatabaseIndexController.class,"retrieval/rDatabaseIndex");
		add(Global.adminPath+"/triggerView", TriggerViewController.class,"retrieval/triggerView");
	}
}
