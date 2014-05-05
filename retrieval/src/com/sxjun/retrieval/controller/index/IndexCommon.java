package com.sxjun.retrieval.controller.index;

import java.sql.Connection;
import java.util.List;

import org.quartz.Job;

import com.jfinal.kit.StringKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.common.SQLUtil;
import com.sxjun.retrieval.controller.job.DataaseIndexJob1;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.JustSchedule;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;
import frame.retrieval.task.quartz.QuartzManager;

public class IndexCommon {
	public void delAllTrigRecord(RDatabaseIndex rdI){
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
	}
	
	public void sechdule(RDatabaseIndex rdI,Job dij){
		List<JustSchedule>  scheduleList = rdI.getJustScheduleList();
		for(JustSchedule js : scheduleList){
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
