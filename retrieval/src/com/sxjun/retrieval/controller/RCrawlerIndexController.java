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
	
//	public void save(){
//		RCrawlerIndex rdI = getModel(RCrawlerIndex.class);
//		String[] indexFields = getParaValues("filedMapper.indexField");
//		String[] sqlFields = getParaValues("filedMapper.sqlField");
//		List<FiledMapper> fmList = new ArrayList<FiledMapper>();
//		for(int i=0;i<indexFields.length;i++){
//			FiledMapper fm = new FiledMapper();
//			fm.setId(UUID.randomUUID().toString());
//			fm.setSqlField(sqlFields[i]);
//			fm.setIndexField(indexFields[i]);
//			fmList.add(fm);
//		}
//		
//		String[] specialTypes = getParaValues("filedSpecialMapper.specialType");
//		String[] sqlSpecialFields = getParaValues("filedSpecialMapper.sqlField");
//		List<FiledSpecialMapper> fsmList = new ArrayList<FiledSpecialMapper>();
//		for(int i=0;i<specialTypes.length;i++){
//			FiledSpecialMapper fsm = new FiledSpecialMapper();
//			fsm.setId(UUID.randomUUID().toString());
//			fsm.setSqlField(sqlSpecialFields[i]);
//			fsm.setSpecialType(specialTypes[i]);
//			fsmList.add(fsm);
//		}
//		//定时任务
//		String[] scheduleNames = getParaValues("justSchedule.scheduleName");
//		String[] expression = getParaValues("justSchedule.expression");
//		List<JustSchedule> justList = new ArrayList<JustSchedule>();
//		if(expression!=null&&expression.length>0)
//		for(int i=0;i<expression.length;i++){
//			JustSchedule fsm = new JustSchedule();
//			if(StrKit.isBlank(fsm.getId()))
//				fsm.setId(UUID.randomUUID().toString());
//			if(scheduleNames!=null&&StrKit.notBlank(scheduleNames[i]))
//				fsm.setScheduleName(scheduleNames[i]);
//			else
//				fsm.setScheduleName(rdI.getTableName()+"#"+fsm.getId());
//			fsm.setExpression(expression[i].trim());
//			justList.add(getJustSchedule(fsm));
//		}
//		
//		IndexCategory ic = commonService.get(IndexCategory.class,rdI.getIndexPath_id());
//		rdI.setIndexCategory(ic);
//		
//		rdI.setJustScheduleList(justList);
//		rdI = checkRCrawlerIndex(rdI);
//		/*if("0".endsWith(rdI.getIsError())&&"0".endsWith(rdI.getIsInit())&&"0".endsWith(rdI.getIsOn())){
//			Job dij = new DataaseIndexJob0();
//			JustBaseSchedule jbs = new JustBaseSchedule();
//			jbs.setScheduleID(UUID.randomUUID().toString());
//			jbs.setExecCount("1");
//			jbs.setScheduleName(UUID.randomUUID().toString());
//			jbs.setTransObject(rdI);
//			JustBaseSchedulerManage jbsm = new JustBaseSchedulerManage(jbs);
//			jbsm.startUpJustScheduler(dij);
//		}*/
//		save(rdI);
//	}
	
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
	
//	public RCrawlerIndex checkRCrawlerIndex(RCrawlerIndex rdI){
//		IndexCategory ic = rdI.getIndexCategory();
//		String ipt = DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, ic.getIndexPathType());//DB/FILE/IMAGE
//		String iserror = "0";
//		String error = "成功";
//		if(StrKit.isBlank(rdI.getDatabase_id())){
//			iserror = "1";
//			error = "未选择数据库";
//		}else if(StrKit.isBlank(rdI.getTableName())){
//			iserror = "1";
//			error = "未选择表";
//		}else if(StrKit.isBlank(rdI.getKeyField())){
//			iserror = "1";
//			error = "未选择主键";
//		}else if(StrKit.isBlank(rdI.getIndexOperatorType())){
//			iserror = "1";
//			error = "未选择操作类型";
//		}else if(StrKit.isBlank(rdI.getDefaultTitleFieldName())){
//			iserror = "1";
//			error = "未选择标题";
//		}else if(StrKit.isBlank(rdI.getDefaultResumeFieldName())&& (DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.DB.getValue())).endsWith(ipt)){
//			iserror = "1";
//			error = "未选择摘要";
//		}else if(StrKit.isBlank(rdI.getBinaryField())&&(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).endsWith(ipt)){
//			iserror = "1";
//			error = "未填写图片字段";
//		}
//		
//		if(StrKit.isBlank(rdI.getIndexTriggerRecord())){
//			if(StrKit.notBlank(rdI.getTableName()))
//				rdI.setIndexTriggerRecord(rdI.getTableName());
//		}
//		
//		//创建触发器
//		if(!isTableExist(rdI.getDatabase(),SQLUtil.INDEX_TRIGGER_RECORD)){
//			createIndexTrigger(rdI.getDatabase());
//		}
//		
//		//判断触发器是否存在，不存在的话就添加
//		/*if(!triggerIsExist(rdI.getDatabase(),rdI.getTableName(),"C"))
//			createTrigger(rdI.getDatabase(), rdI.getTableName(), rdI.getKeyField(), "C");
//		if(!triggerIsExist(rdI.getDatabase(),rdI.getTableName(),"U"))
//			createTrigger(rdI.getDatabase(), rdI.getTableName(), rdI.getKeyField(), "U");
//		if(!triggerIsExist(rdI.getDatabase(),rdI.getTableName(),"D"))
//			createTrigger(rdI.getDatabase(), rdI.getTableName(), rdI.getKeyField(), "D");*/
//		//直接删除后创建触发器
//		deleteTrigger(rdI.getDatabase(),rdI.getIndexTriggerRecord(),"C");
//		deleteTrigger(rdI.getDatabase(),rdI.getIndexTriggerRecord(),"U");
//		deleteTrigger(rdI.getDatabase(),rdI.getIndexTriggerRecord(),"D");
//		createTrigger(rdI.getDatabase(), rdI.getIndexTriggerRecord(), rdI.getKeyField(), "C");
//		createTrigger(rdI.getDatabase(), rdI.getIndexTriggerRecord(), rdI.getKeyField(), "U");
//		createTrigger(rdI.getDatabase(), rdI.getIndexTriggerRecord(), rdI.getKeyField(), "D");
//		
//		String sql = "";
//		if(rdI.getFiledMapperLsit()!=null&&iserror.equals("0")){
//			for(FiledMapper fm : rdI.getFiledMapperLsit()){
//				if(StrKit.isBlank(fm.getSqlField())){
//					iserror = "1";
//					error = "字段映射未填不完整";
//					break;
//				}else{
//					String[] f = fm.getSqlField().split(";");
//					for(String s : f){
//						sql +=s+",";
//					}
//				}
//			}
//			sql = sql.substring(0,sql.length()-1);
//		}
//		
//		String resume = rdI.getDefaultResumeFieldName();
//		String binaryF1 = "",binaryF2="";
//		if((DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).endsWith(ipt)){
//			binaryF1 = ",b."+rdI.getBinaryField();
//			binaryF2 = ","+rdI.getBinaryField();
//		}
//			
//		String trigsql = "select b."+rdI.getKeyField()+",b."+rdI.getDefaultTitleFieldName()+",b."+resume+binaryF1+",b."+ sql.replace(",", ",b.") +" from "+SQLUtil.INDEX_TRIGGER_RECORD+
//				" a left join "+rdI.getTableName()+" b on a.columnvalue=b."+rdI.getKeyField()+" where 1=1 ";
//		
//		sql = "select "+rdI.getKeyField()+","+rdI.getDefaultTitleFieldName()+","+resume+binaryF2+","+ sql +" from "+rdI.getTableName()+" where 1=1 ";
//		
//		if(rdI.getFiledSpecialMapperLsit()!=null&&iserror.equals("0")){
//			for(FiledSpecialMapper fsm :rdI.getFiledSpecialMapperLsit()){
//				String sf = fsm.getSqlField();
//				String[] f = sf.split(";");
//				for(String s : f){
//					if(sql.toUpperCase().indexOf(s.toUpperCase())<0){
//						iserror = "1";
//						error = "特殊字段映射不匹配";
//						break;
//					}
//				}
//				if(iserror.equals("1"))
//					break;
//			}
//		}
//		
//		if(StrKit.notBlank(rdI.getCondtion())){
//			String condtion = rdI.getCondtion().trim();
//			if(condtion.startsWith("and")){
//				sql += " "+condtion;
//				trigsql = trigsql+" "+condtion+" order by a.insertdate ";
//			}else{ 
//				sql += "and "+condtion;
//				trigsql = trigsql+"and "+condtion+" order by a.insertdate ";
//			}
//		}	
//		
//		if(StrKit.isBlank(rdI.getDatabaseRecordInterceptor()))
//			rdI.setDatabaseRecordInterceptor("com.sxjun.retrieval.controller.index.DatabaseRecordInterceptor");
//		
//		
//		rdI.setSql(sql);
//		rdI.setTrigSql(trigsql);
//		rdI.setIsError(iserror);
//		rdI.setError(error);
//		return rdI;
//	}
	
	public void delete(){
		String id=getPara();
		RCrawlerIndex rdI = commonService.get(RCrawlerIndex.class, id);
		commonService.remove(RCrawlerIndex.class, id);
		list();
	}

	
//	/*
//	 * 获取redis中配置的数据库
//	 */
//	public void databases(){
//		List<Database> dbs = dbCommonService.getObjs(Database.class);
//		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
//		for(Database db : dbs){
//			Map<String,String> m = new HashMap<String,String>();
// 			m.put("dbname",db.getId()+";"+db.getDatabaseName());
//			l.add(m);
//		}
//		renderJson("dbs",l);
//	}
//	
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
	
//	/**
//	 * 获取数据字段
//	 * @return
//	 */
//	public List<Map<String,String>> findFields(){
//		List<InitField> ifds = ifCommonService.getObjs(InitField.class);
//		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
//		for(InitField ifd : ifds){
//			Map<String,String> m = new HashMap<String,String>();
//			m.put("field",ifd.getField());
//			l.add(m);
//		}
//		return l;
//	}
//	
//	public void initFields(){
//		renderJson("initFields",findFields());
//	}
//	
//	public void indexPathType(){
//		String id = getPara("id");
//		IndexCategory ic = commonService.get(IndexCategory.class,id);
//		renderJson("pathtype",DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, ic.getIndexPathType()));
//	}
//	
//	
//	public List<RCrawlerIndex> getRCrawlerIndexList(){
//		return null;
//	}
	
}
