package com.sxjun.retrieval.controller.index;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.quartz.Job;

import com.jfinal.kit.StrKit;
import com.sxjun.core.common.utils.DictUtils;
import com.sxjun.core.common.utils.SQLUtil;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.JustSchedule;
import com.sxjun.retrieval.pojo.RDatabaseIndex;
import com.sxjun.retrieval.pojo.RIndex;

import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseDocItemType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.engine.RetrievalType.RDocItemSpecialName;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.facade.IRQueryFacade;
import frame.retrieval.engine.query.RQuery;
import frame.retrieval.engine.query.item.QueryItem;
import frame.retrieval.engine.query.pagerank.HtmlEntity;
import frame.retrieval.engine.query.pagerank.HtmlPageRank;
import frame.retrieval.engine.query.result.QueryResult;
import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;
import frame.retrieval.task.quartz.QuartzManager;

public class IndexCommon {
	
	protected static RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();;
	
	public QueryItem createQueryItem(RetrievalType.RDocItemType docItemType,Object name,String value){
		QueryItem queryItem=retrievalApplicationContext.getFacade().createQueryItem(docItemType, String.valueOf(name), value);
		return queryItem;
	}
	
	public QueryResult[] getQueryResults(String[] indexPathTypes,QueryItem queryItem){
		IRQueryFacade queryFacade=retrievalApplicationContext.getFacade().createQueryFacade();
		indexPathTypes=ApplicationContext.getLocalIndexPathTypes(indexPathTypes);
		RQuery query=queryFacade.createRQuery(indexPathTypes);
		QueryResult[] queryResults=query.getQueryResults(queryItem,0,15);
		query.close();
		return queryResults;
	}
	
	/**
	 * 判断是否在索引中已经存在，则删除数据库中记录
	 * @param rdI
	 * @param nowTime
	 */
	public void judgeAndDelTrigRecord(RDatabaseIndex rdI,String nowTime){
		List<Map<String,String>> selectRecord = selectTrigRecord(rdI,nowTime);
		if(selectRecord!=null)
		for(Map<String,String> m : selectRecord){
			QueryItem queryItem0=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DT,m.get("tablename").toUpperCase());
			QueryItem queryItem1=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DID,m.get("columnvalue"));
			QueryItem queryItem2=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DK,m.get("columnname").toUpperCase());
			QueryItem queryItem=queryItem0.must(QueryItem.MUST,queryItem1).must(QueryItem.MUST,queryItem2);
			QueryResult[] queryResult2=getQueryResults(new String[]{rdI.getIndexCategory().getIndexPath()}, queryItem);
			if(queryResult2.length>0)
				delTrigRecordByID(rdI,m.get("rowguid"));
		}
	}
	
	/**
	 * 根据主键删除记录
	 * @param rdI
	 * @param id
	 */
	public void delTrigRecordByID(RDatabaseIndex rdI,String id){
		//删除触发器表中记录
		Database db =  rdI.getDatabase();
		RDatabaseType databaseType = DictUtils.changeToRDatabaseType(db.getDatabaseType());
		String url = JdbcUtil.getConnectionURL(databaseType, db.getIp(), db.getPort(), db.getDatabaseName());
		Connection conn =JdbcUtil.getConnection(databaseType, url, db.getUser(), db.getPassword());
		
		String delsql = " delete from "+SQLUtil.INDEX_TRIGGER_RECORD+" where 1=1 and rowguid='"+id+"' ";
		JdbcUtil.executeSql(conn, delsql, true);
	}
	
	/**
	 * 删除触发器表中的记录
	 * @param rdI
	 */
	public void delAllTrigRecord(RDatabaseIndex rdI,String nowTime,boolean isDel){
		//删除触发器表中记录
		Database db =  rdI.getDatabase();
		RDatabaseType databaseType = DictUtils.changeToRDatabaseType(db.getDatabaseType());
		String url = JdbcUtil.getConnectionURL(databaseType, db.getIp(), db.getPort(), db.getDatabaseName());
		Connection conn =JdbcUtil.getConnection(databaseType, url, db.getUser(), db.getPassword());
		
		String delsql = " delete from "+SQLUtil.INDEX_TRIGGER_RECORD+" where 1=1 and tablename ='"+rdI.getTableName()+"' ";
		if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
			delsql += " and insertdate< to_date(" + "'" + nowTime + "'" + ",'yyyy-MM-dd HH24:mi:ss') ";
		} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
			delsql += " and insertdate<convert(datetime,'"+nowTime+"')";
		} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
			delsql += " and insertdate<'"+nowTime+"' ";
		}
		if(!isDel)
			delsql += " and operatetype in('I','U')";
		JdbcUtil.executeSql(conn, delsql, true);
	}
	
	/**
	 * 定时任务
	 * @param rdI
	 * @param dij
	 */
	public void sechdule(RIndex rdI,Job dij){
		List<JustSchedule>  scheduleList = rdI.getJustScheduleList();
		for(JustSchedule js : scheduleList){
			JustBaseSchedule jbs = new JustBaseSchedule();
			jbs.setScheduleID(js.getId());
			jbs.setScheduleName(js.getScheduleName());
			JustBaseSchedulerManage jbsm = new JustBaseSchedulerManage(jbs);
			if(StrKit.notBlank(js.getFrequency())){
				jbs.setFrequency(js.getFrequency());
				jbs.setFrequencyUnits(js.getFrequencyUnits());
				jbsm.startUpJustScheduler(dij,QuartzManager.SCHEDULE_TYPE_TRIGGER_SIMPLE);
			}else{
				jbs.setExpression(js.getExpression());
				jbsm.startUpJustScheduler(dij,QuartzManager.SCHEDULE_TYPE_TRIGGER_CRON);
			}
		}
	}
	
	/**
	 * 
	 * @param rdI
	 */
	public List<Map<String,String>> selectTrigRecord(RDatabaseIndex rdI,String nowTime){
		//获取触发器表中记录
		Database db =  rdI.getDatabase();
		RDatabaseType databaseType = DictUtils.changeToRDatabaseType(db.getDatabaseType());
		String url = JdbcUtil.getConnectionURL(databaseType, db.getIp(), db.getPort(), db.getDatabaseName());
		Connection conn =JdbcUtil.getConnection(databaseType, url, db.getUser(), db.getPassword());
		
		String selectsql =	" select rowguid,tablename,columnname,columnvalue from "+SQLUtil.INDEX_TRIGGER_RECORD+" where 1=1 and operatetype in('I','U') and tablename ='"+rdI.getTableName()+"' ";
		if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
			selectsql += " and insertdate> to_date(" + "'" + nowTime + "'" + ",'yyyy-MM-dd HH24:mi:ss') ";
		} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
			selectsql += " and insertdate。convert(datetime,'"+nowTime+"')";
		} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
			selectsql += " and insertdate>'"+nowTime+"' ";
		}
		List<Map<String,String>> l = (List<Map<String,String>>) JdbcUtil.getMapList(conn, selectsql,true);
		return l;
	}
	
	/**
	 * 获取检索的数据库记录
	 * @param rdI
	 * @param nowTime
	 * @return
	 */
	public String getIndexTriggerSql(RDatabaseIndex rdI,String nowTime,boolean isDel){
		String sql = rdI.getTrigSql();
		RDatabaseType databaseType = DictUtils.changeToRDatabaseType(rdI.getDatabase().getDatabaseType());
		if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
			sql +=  " and a.insertdate< to_date(" + "'" + nowTime + "'" + ",'yyyy-MM-dd HH24:mi:ss') ";;
		} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
			sql +=  " and a.insertdate<convert(datetime,'"+nowTime+"')";
		} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
			sql +=  " and a.insertdate<'"+nowTime+"' ";
		}
		sql += " and a.operatetype in(";
		if(!isDel)
			sql += "'I','U'";
		else{
			sql = "select a.columnname,a.columnvalue,a.tablename from INDEX_TRIGGER_RECORD a  where 1=1";
			if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
				sql +=  " and a.insertdate< to_date(" + "'" + nowTime + "'" + ",'yyyy-MM-dd HH24:mi:ss') ";;
			} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
				sql +=  " and a.insertdate<convert(datetime,'"+nowTime+"')";
			} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
				sql +=  " and a.insertdate<'"+nowTime+"' ";
			}
			sql +=" and a.operatetype in('D'";
		}
			
		sql += ") order by a.insertdate asc";
		return sql;
	}
	
	/**
	 * 则删索引中记录
	 * @param rdI
	 * @param nowTime
	 */
	public int judgeAndDelIndexRecord(RDatabaseIndex rdI,String nowTime){
		IRDocOperatorFacade docOperatorFacade=retrievalApplicationContext.getFacade().createDocOperatorFacade();
		String sql = getIndexTriggerSql(rdI,nowTime,true);
		Database db =  rdI.getDatabase();
		RDatabaseType databaseType = DictUtils.changeToRDatabaseType(db.getDatabaseType());
		String url = JdbcUtil.getConnectionURL(databaseType, db.getIp(), db.getPort(), db.getDatabaseName());
		Connection conn =JdbcUtil.getConnection(databaseType, url, db.getUser(), db.getPassword());
		
		List<Map<String,String>> selectRecord = (List<Map<String,String>>) JdbcUtil.getMapList(conn, sql,true);
		if(selectRecord!=null){
			for(Map<String,String> m : selectRecord){
				QueryItem queryItem0=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DT,m.get("tablename").toUpperCase());
				QueryItem queryItem1=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DID,m.get("columnvalue"));
				QueryItem queryItem2=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DK,m.get("columnname").toUpperCase());
				QueryItem queryItem=queryItem0.must(QueryItem.MUST,queryItem1).must(QueryItem.MUST,queryItem2);
				QueryResult[] queryResult=getQueryResults(new String[]{rdI.getIndexCategory().getIndexPath()}, queryItem);
				if(queryResult!=null)
				for(QueryResult qr: queryResult){
					String documntId = qr.getQueryResultMap().get(String.valueOf(RetrievalType.RDocItemSpecialName._IID));
					String[] indexPathTypes=ApplicationContext.getLocalIndexPathTypes(new String[]{rdI.getIndexCategory().getIndexPath()});
					for(String indexPathType :indexPathTypes)
						docOperatorFacade.delete(indexPathType, documntId);
				}
				
			}
			return selectRecord.size();
		}else
			return 0;
	}
	
	public List<HtmlEntity> getPageRank(RDatabaseIndex rdI){
		List<HtmlEntity> he = null;
		try {
			Database db =  rdI.getDatabase();
			RDatabaseType databaseType = DictUtils.changeToRDatabaseType(db.getDatabaseType());
			String url = JdbcUtil.getConnectionURL(databaseType, db.getIp(), db.getPort(), db.getDatabaseName());
			Connection conn =JdbcUtil.getConnection(databaseType, url, db.getUser(), db.getPassword());
			
			String selectsql =	" select OriginalURL from "+rdI.getTableName();
			List<Map<String,String>> l = (List<Map<String,String>>) JdbcUtil.getMapList(conn, selectsql,true);
			String[] urls = new String[l.size()];
			for(int i=0;i<l.size();i++){
				urls[i] = l.get(i).get("OriginalURL");
			}
		
			he = HtmlPageRank.getHtmlPageRank(urls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return he;
	}
	
}
