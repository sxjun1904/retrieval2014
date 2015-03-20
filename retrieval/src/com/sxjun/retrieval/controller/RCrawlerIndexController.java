/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.Job;

import com.jfinal.kit.StrKit;
import com.sxjun.core.common.controller.BaseController;
import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.core.common.utils.DictUtils;
import com.sxjun.core.common.utils.SQLUtil;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.job.CrawlerIndexJob0;
import com.sxjun.retrieval.controller.job.DatabaseIndexJob0;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.FiledMapper;
import com.sxjun.retrieval.pojo.FiledSpecialMapper;
import com.sxjun.retrieval.pojo.IndexCategory;
import com.sxjun.retrieval.pojo.InitField;
import com.sxjun.retrieval.pojo.JustSchedule;
import com.sxjun.retrieval.pojo.RCrawlerIndex;

import frame.base.core.util.JdbcUtil;
import frame.base.core.util.StringClass;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;

/**
 * 索引设置Controller
 * @author sxjun
 * @version 2014-03-11
 */
public class RCrawlerIndexController extends BaseController<RCrawlerIndex> {

	private CommonService<RCrawlerIndex> commonService = new ServiceProxy<RCrawlerIndex>().getproxy();
	private CommonService<Database> dbCommonService = new ServiceProxy<Database>().getproxy();
	private CommonService<IndexCategory> idCommonService = new ServiceProxy<IndexCategory>().getproxy();
	private CommonService<InitField> ifCommonService = new ServiceProxy<InitField>().getproxy();

	public void list() {
		list(RCrawlerIndex.class);
	}
	
	
	public void form(){
//		setAttr("initFields", findFields());
		form(RCrawlerIndex.class);
	}


	@Override
	public void save() {
		RCrawlerIndex rdI = getModel(RCrawlerIndex.class);
		IndexCategory ic = commonService.get(IndexCategory.class,rdI.getIndexPath_id());
		rdI.setIndexCategory(ic);
		
		//定时任务
		String[] scheduleNames = getParaValues("justSchedule.scheduleName");
		String[] expression = getParaValues("justSchedule.expression");
		List<JustSchedule> justList = new ArrayList<JustSchedule>();
		if(expression!=null&&expression.length>0)
		for(int i=0;i<expression.length;i++){
			JustSchedule fsm = new JustSchedule();
			if(StrKit.isBlank(fsm.getId()))
				fsm.setId(UUID.randomUUID().toString());
			if(scheduleNames!=null&&StrKit.notBlank(scheduleNames[i]))
				fsm.setScheduleName(scheduleNames[i]);
			else
				fsm.setScheduleName(rdI.getName()+"#"+fsm.getId());
			fsm.setExpression(expression[i].trim());
			justList.add(getJustSchedule(fsm));
		}
		rdI.setJustScheduleList(justList);
		rdI = checkRCrawlerIndex(rdI);
		/*if("0".endsWith(rdI.getIsError())&&"0".endsWith(rdI.getIsInit())&&"0".endsWith(rdI.getIsOn())){
			Job dij = new DataaseIndexJob0();
			JustBaseSchedule jbs = new JustBaseSchedule();
			jbs.setScheduleID(UUID.randomUUID().toString());
			jbs.setExecCount("1");
			jbs.setScheduleName(UUID.randomUUID().toString());
			jbs.setTransObject(rdI);
			JustBaseSchedulerManage jbsm = new JustBaseSchedulerManage(jbs);
			jbsm.startUpJustScheduler(dij);
		}*/
		save(rdI);
	}
	
	public void init(){
		Job dij = new CrawlerIndexJob0();
		JustBaseSchedule jbs = new JustBaseSchedule();
		jbs.setScheduleID(UUID.randomUUID().toString());
		jbs.setExecCount("1");
		jbs.setScheduleName(UUID.randomUUID().toString());
		JustBaseSchedulerManage jbsm = new JustBaseSchedulerManage(jbs);
		jbsm.startUpJustScheduler(dij);
		msg = MSG_OK;
		setAttr("msg",msg);
		renderJson(new String[]{"msg"});
	}
	
	
	/**
	 * h 小时；m 分；s 秒
	 * @param js
	 * @return
	 */
	
	public JustSchedule getJustSchedule(JustSchedule js){
		String exp = js.getExpression().trim();
		if(StrKit.notBlank(exp)){
			String start = exp.substring(0,exp.length()-1);
			String end = exp.substring(exp.length()-1);
			if(StringClass.isNumeric(start)&&(end.equals("s")||end.equals("m")||end.equals("h"))){
				js.setFrequency(start);
				js.setFrequencyUnits(end);
			}
		}
		return js;
	}
	
	public RCrawlerIndex checkRCrawlerIndex(RCrawlerIndex rdI){
		IndexCategory ic = rdI.getIndexCategory();
		String ipt = DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, ic.getIndexPathType());//DB/FILE/IMAGE
		String iserror = "0";
		String error = "成功";
		if(StrKit.isBlank(rdI.getName())){
			iserror = "1";
			error = "未填写索引名称";
		}else if (StrKit.isBlank(rdI.getUrl())){
			iserror = "1";
			error = "URL地址未填写";
		}
		if (StrKit.isBlank(rdI.getNumberOfCrawlers())){
			rdI.setNumberOfCrawlers("5");
		}else if(!StringClass.isNumeric(rdI.getNumberOfCrawlers())){
			iserror = "1";
			error = "线程数设置错误，必须为数字";
		}
		if (StrKit.isBlank(rdI.getMaxDepthOfCrawling())){
			rdI.setMaxDepthOfCrawling("2");
		}else if(!StringClass.isNumeric(rdI.getMaxDepthOfCrawling())){
			iserror = "1";
			error = "爬取深度设置错误，必须为数字";
		}
		rdI.setIsError(iserror);
		rdI.setError(error);
		return rdI;
	}
	
	
	public void delete(){
		String id=getPara();
		RCrawlerIndex rdI = commonService.get(RCrawlerIndex.class, id);
		commonService.remove(RCrawlerIndex.class, id);
		list();
	}

	
	public Map<String,String> addToMap(IndexCategory ic){
		Map<String,String> m = new HashMap<String,String>();
		m.put("index_name",ic.getId()+";"+ic.getIndexInfoType());
		return m;
	}
	
	/**
	 * 获取索引路径
	 */
	public void indexPathes(){
		Integer t = getParaToInt("t")!=null?getParaToInt("t"):0;
		List<IndexCategory> ics = idCommonService.getObjs(IndexCategory.class);
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		
		switch (t) {
			case 0:{
				for(IndexCategory ic : ics){
					if(ic.getIndexPathType().equals("0"))
						l.add(addToMap(ic));
				}
				break;
			}
			case 1:{
				for(IndexCategory ic : ics){
					if(ic.getIndexPathType().equals("1"))
						l.add(addToMap(ic));
				}
				break;
			}
			case 2:{
				for(IndexCategory ic : ics){
					if(ic.getIndexPathType().equals("2"))
						l.add(addToMap(ic));
				}
				break;
			}
			case 3:{
				for(IndexCategory ic : ics){
					if(ic.getIndexPathType().equals("3"))
						l.add(addToMap(ic));
				}
				break;
			}
		}
		
		
		renderJson("indexPathes",l);
	}
	
	
}
