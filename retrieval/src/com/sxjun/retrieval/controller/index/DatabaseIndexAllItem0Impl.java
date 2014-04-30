package com.sxjun.retrieval.controller.index;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.Job;

import com.jfinal.kit.StringKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.common.SQLUtil;
import com.sxjun.retrieval.controller.job.DataaseIndexJob1;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.JustSchedule;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;
import frame.retrieval.task.quartz.QuartzManager;
import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public class DatabaseIndexAllItem0Impl extends DatabaseIndexAllItemCommon implements ICreateIndexAllItem{
	
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new CommonService<RDatabaseIndex>();
	
	public DatabaseIndexAllItem0Impl(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class.getSimpleName());
	}
	
	public DatabaseIndexAllItem0Impl(RDatabaseIndex rDatabaseIndex){
		rDatabaseIndexList = new ArrayList<RDatabaseIndex>();
		rDatabaseIndexList.add(rDatabaseIndex);
		this.rDatabaseIndexList = rDatabaseIndexList;
	}

	@Override
	public List<RDatabaseIndexAllItem> deal(RetrievalApplicationContext retrievalApplicationContext) {
		
		List <RDatabaseIndexAllItem> l = new ArrayList<RDatabaseIndexAllItem>();
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			if("0".endsWith(rdI.getIsError())&&"0".endsWith(rdI.getIsInit())&&"0".endsWith(rdI.getIsOn())){
				
				rdI.setIsInit("2");
				commonService.put(RDatabaseIndex.class.getSimpleName(), rdI.getId(), rdI);
				
				//删除触发器表中记录
				String nowTime =  new DateTime().getNowDateTime();
				Database db =  rdI.getDatabase();
				String delsql = " delete from "+SQLUtil.INDEX_TRIGGER_RECORD+" where 1=1 and operatetype in('I','U') and tablename ='"+rdI.getTableName()+"' ";
				RDatabaseType databaseType = DictUtils.changeToRDatabaseType(db.getDatabaseType());
				if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
					delsql += " and insertdate< to_date(" + "'" + nowTime + "'" + ",'yyyy-MM-dd HH24:mi:ss') ";;
				} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
					delsql += " and insertdate<convert(datetime,'"+nowTime+"')";
				} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
					delsql += " and insertdate<'"+nowTime+"' ";
				}
				String url = JdbcUtil.getConnectionURL(databaseType, db.getIp(), db.getPort(), db.getDatabaseName());
				Connection conn =JdbcUtil.getConnection(databaseType, url, db.getUser(), db.getPassword());
				JdbcUtil.executeSql(conn, delsql, true);
				
				//启动定时任务
				String sql = rdI.getSql();
				l.add(create(retrievalApplicationContext,rdI,sql,null));
				List<JustSchedule>  scheduleList = rdI.getJustScheduleList();
				for(JustSchedule js : scheduleList){
					Job dij = new DataaseIndexJob1();
					JustBaseSchedule jbs = new JustBaseSchedule();
					jbs.setScheduleID(js.getId());
					jbs.setScheduleName(js.getScheduleName());
					JustBaseSchedulerManage jbsm = new JustBaseSchedulerManage(jbs);
					if(StringKit.notBlank(js.getFrequency())){
						jbs.setFrequency(js.getFrequency());
						jbs.setFrequencyUnits(js.getFrequencyUnits());
						jbsm.startUpJustScheduler(dij,QuartzManager.SCHEDULE_TYPE_TRIGGER_SIMPLE);
					}else{
						jbs.setExpression(js.getExpression());
						jbsm.startUpJustScheduler(dij,QuartzManager.SCHEDULE_TYPE_TRIGGER_CRON);
					}
				}
			}
		}
		return l;
	}

	@Override
	public void afterDeal(RDatabaseIndexAllItem databaseIndexAllItem) {
		Map<String,String> transObj = (Map<String, String>) databaseIndexAllItem.getTransObject();
		RDatabaseIndex rdI = commonService.get(RDatabaseIndex.class.getSimpleName(), transObj.get("id"));
		rdI.setIsInit("1");
		commonService.put(RDatabaseIndex.class.getSimpleName(), rdI.getId(), rdI);
	}

}
