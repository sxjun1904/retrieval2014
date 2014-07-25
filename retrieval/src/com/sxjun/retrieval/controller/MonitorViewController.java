/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Trigger;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.common.SQLUtil;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.DstgView;
import com.sxjun.retrieval.pojo.IsInitView;
import com.sxjun.retrieval.pojo.RDatabaseIndex;
import com.sxjun.retrieval.pojo.TriggerView;

import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;

/**
 * 数据监控Controller
 * @author sxjun
 * @version 2014-07-10
 */
public class MonitorViewController extends Controller {
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();
	CommonService<Database> dsCommonService = new ServiceProxy<Database>().getproxy();
	
	public void listTrigger() {
		List<TriggerView> list = getTriggerViewList();
		setAttr("triggerView",list);
		render("triggerViewList.jsp");
	}
	
	public void listIsInit() {
		List<RDatabaseIndex> list = commonService.getObjs(RDatabaseIndex.class);
		List<IsInitView> isInitViewList = new ArrayList<IsInitView>();
		for(RDatabaseIndex rdI:list){
			IsInitView isInitView = new IsInitView();
			isInitView.setDatabaseName(rdI.getDatabase().getDatabaseName());
			isInitView.setIndexInfoType(rdI.getIndexCategory().getIndexInfoType());
			isInitView.setIsInit(rdI.getIsInit());
			isInitView.setMediacyTime(rdI.getMediacyTime());
			isInitView.setTableName(rdI.getTableName());
			String info = "正常";
			if(StrKit.notBlank(rdI.getMediacyTime())){
				Date date = new DateTime().parseDate(rdI.getMediacyTime(), null);
				long diff = new Date().getTime() - date.getTime();
				long minutes = diff / (1000 * 60);
				if(10<minutes&&minutes<20)
					info = "基本正常,继续观察";
				else if(minutes<30&&minutes>=20)
					info = "可能存在异常,请检查";
				else if(minutes>=30)
					info = "存在异常,请检查";
			}
			isInitView.setInfo(info);
			isInitViewList.add(isInitView);
		}
		setAttr("isInitView",isInitViewList);
		render("isInitViewList.jsp");
	}
	
	public void listDSTG() {
		List<Database> list = dsCommonService.getObjs(Database.class);
		List<DstgView> dstgViewList = new ArrayList<DstgView>();
		for(Database db : list){
			
			RDatabaseType type = DictUtils.changeToRDatabaseType(db.getDatabaseType());
			String url = JdbcUtil.getConnectionURL(type, db.getIp(), db.getPort(), db.getDatabaseName());
			Connection conn =JdbcUtil.getConnection(type, url, db.getUser(), db.getPassword());
			String triggerlike = SQLUtil.getTriggerLike(type, db.getDatabaseName());
			List<Map<String,String>> result = (List<Map<String, String>>) JdbcUtil.getMapList(conn, triggerlike, true);
			if(result!=null)
				for(Map<String,String> map :result){
					DstgView dstgView= new DstgView();
					dstgView.setId(db.getId());
					dstgView.setDatabasename(db.getDatabaseName());
					dstgView.setTriggername(map.get("trigger_name"));
					dstgViewList.add(dstgView);
				}
		}
		setAttr("dstgView",dstgViewList);
		render("dstgViewList.jsp");
	}
	
	public void deleteDstg(){
		String trigger = getPara();
		String id = getPara("id");
		Database db = dsCommonService.get(Database.class, id);
		RDatabaseType type = DictUtils.changeToRDatabaseType(db.getDatabaseType());
		String url = JdbcUtil.getConnectionURL(type, db.getIp(), db.getPort(), db.getDatabaseName());
		Connection conn =JdbcUtil.getConnection(type, url, db.getUser(), db.getPassword());
		String sql = SQLUtil.getDeleteTriggerSql(type,trigger);
		JdbcUtil.executeSql(conn, sql, true);
		listDSTG();
	}
	
	public List<TriggerView> getTriggerViewList(){
		JustBaseSchedulerManage jsm = new JustBaseSchedulerManage();
		List<Trigger> tgList = jsm.getTriggers();
		List<TriggerView> tgviewList = new ArrayList();
		for (Trigger tg : tgList) {
			TriggerView tgview = new TriggerView();
			tgview.setClassSimpleName(tg.getClass().getSimpleName());
			tgview.setFullJobName(tg.getFullJobName());
			tgview.setFullName(tg.getFullName());
			tgview.setJobName(tg.getJobName());
			tgview.setId(tg.getFireInstanceId());
			tgview.setName(tg.getName());
			tgview.setNextFireTime(tg.getNextFireTime()!=null?new DateTime().parseString(tg.getNextFireTime(),null):"");
			tgview.setStartTime(new DateTime().parseString(tg.getStartTime(),null));
			tgviewList.add(tgview);
		}
		return tgviewList;
	}
}
