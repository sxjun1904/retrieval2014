package com.sxjun.retrieval.controller.index;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.common.SQLUtil;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public class DatabaseIndexAllItem1Impl extends DatabaseIndexAllItemCommon implements ICreateIndexAllItem{
	
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new CommonService<RDatabaseIndex>();
	
	public DatabaseIndexAllItem1Impl(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class.getSimpleName());
	}
	
	@Override
	public List<RDatabaseIndexAllItem> deal(RetrievalApplicationContext retrievalApplicationContext) {
		
		List <RDatabaseIndexAllItem> l = new ArrayList<RDatabaseIndexAllItem>();
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			if("0".endsWith(rdI.getIsError())&&"1".endsWith(rdI.getIsInit())&&"0".endsWith(rdI.getIsOn())){
				
				rdI.setIsInit("2");
				commonService.put(RDatabaseIndex.class.getSimpleName(), rdI.getId(), rdI);
				
				String nowTime = new DateTime().getNowDateTime();
				String sql = rdI.getTrigSql();
				RDatabaseType databaseType = DictUtils.changeToRDatabaseType(rdI.getDatabase().getDatabaseType());
				if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
					sql +=  " and a.insertdate< to_date(" + "'" + nowTime + "'" + ",'yyyy-MM-dd HH24:mi:ss') ";;
				} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
					sql +=  " and a.insertdate<convert(datetime,'"+nowTime+"')";
				} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
					sql +=  " and a.insertdate<'"+nowTime+"' ";
				}
				sql +=  " and a.operatetype in('I','U') order by a.insertdate asc";
				
				l.add(create(retrievalApplicationContext,rdI,sql,nowTime));
			}
		}
		return l;
	}

	
	/**
	 * 删除触发器表
	 */
	@Override
	public void afterDeal(Object databaseIndexAllItem) {
		Map<String,String> transObj = (Map<String, String>) ((RDatabaseIndexAllItem)databaseIndexAllItem).getTransObject();
		String nowTime = transObj.get("nowTime");
		RDatabaseIndex rdI = commonService.get(RDatabaseIndex.class.getSimpleName(), transObj.get("id"));
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
		rdI.setIsInit("1");
		commonService.put(RDatabaseIndex.class.getSimpleName(), rdI.getId(), rdI);
	}

}
