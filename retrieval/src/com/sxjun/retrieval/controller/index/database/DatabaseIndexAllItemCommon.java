package com.sxjun.retrieval.controller.index.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.StrKit;
import com.sxjun.common.proxy.ServiceProxy;
import com.sxjun.common.service.CommonService;
import com.sxjun.common.utils.DictUtils;
import com.sxjun.retrieval.controller.index.IndexCommon;
import com.sxjun.retrieval.pojo.FiledMapper;
import com.sxjun.retrieval.pojo.FiledSpecialMapper;
import com.sxjun.retrieval.pojo.IndexCategory;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.index.all.database.DatabaseConnection;
import frame.retrieval.engine.index.all.database.DatabaseLink;
import frame.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public class DatabaseIndexAllItemCommon extends IndexCommon{
	
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();
	
	public RDatabaseIndexAllItem create(RetrievalApplicationContext retrievalApplicationContext,RDatabaseIndex rdI,String sql,String nowTime){
		String tableName = rdI.getTableName();
		String keyField = rdI.getKeyField();
		RDatabaseIndexAllItem databaseIndexAllItem = retrievalApplicationContext.getFacade().createDatabaseIndexAllItem(false);
		
		Map<String,Object> transObj = new HashMap<String,Object>();
		transObj.put("rdI", rdI);
		transObj.put("nowTime", nowTime);
		
		IndexCategory ic = rdI.getIndexCategory();
		databaseIndexAllItem.setIndexPathType(ic.getIndexPath());
		databaseIndexAllItem.setIndexInfoType(ic.getIndexInfoType());
		databaseIndexAllItem.setIndexOperatorType("0".endsWith(rdI.getIndexOperatorType())?RetrievalType.RIndexOperatorType.INSERT:RetrievalType.RIndexOperatorType.UPDATE);
		databaseIndexAllItem.setTableName(tableName);
		databaseIndexAllItem.setKeyField(keyField);
		databaseIndexAllItem.setDefaultTitleFieldName(rdI.getDefaultTitleFieldName());
		databaseIndexAllItem.setDefaultResumeFieldName(rdI.getDefaultResumeFieldName());
		databaseIndexAllItem.setPageSize(5000);
		databaseIndexAllItem.setSql(sql);
		databaseIndexAllItem.setParam(new Object[] {});
		databaseIndexAllItem.setTransObject(transObj);
		
		
		//特殊字段处理
		Map<String,Integer[]> sqlSpecialFieldMapper = new HashMap<String,Integer[]>();
		List<FiledSpecialMapper> fsm =  rdI.getFiledSpecialMapperLsit();
		for(FiledSpecialMapper fs : fsm){
			String[] sf = fs.getSqlField().split(";");
			for(String s : sf){
				Integer[] m = sqlSpecialFieldMapper.get(s);
				if(m==null){
					sqlSpecialFieldMapper.put(s, new Integer[]{Integer.parseInt(fs.getSpecialType())});
				}else{
					Integer[] m_n = new Integer[m.length+1];
					for(int i=0;i<m.length;i++){
						m_n[i]=m[i];
					}
					m_n[m.length] = Integer.parseInt(fs.getSpecialType());
					sqlSpecialFieldMapper.put(s, m_n);
				}
			}
		}
		databaseIndexAllItem.setFieldSpecialMapper(sqlSpecialFieldMapper);
		
		//映射字段处理
		Map<String,String> fieldMapper = new HashMap<String,String>();
		List<FiledMapper> fm = rdI.getFiledMapperLsit();
		for(FiledMapper f : fm){
			String[] ss = f.getSqlField().split(";");
			for(String s : ss){
				fieldMapper.put(s,f.getIndexField());
			}
		}
		databaseIndexAllItem.setFieldMapper(fieldMapper);
		
		//是否取出重复
		databaseIndexAllItem.setRmDuplicate("0".endsWith(rdI.getRmDuplicate())?true:false);
		
		//是否有拦截器
		if(StrKit.notBlank(rdI.getDatabaseRecordInterceptor())){
			try {
				IIndexAllDatabaseRecordInterceptor iiadri = (IIndexAllDatabaseRecordInterceptor) Class.forName(rdI.getDatabaseRecordInterceptor()).newInstance();
				databaseIndexAllItem.setDatabaseRecordInterceptor(iiadri);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
			
		//设置数据库的连接信息
		RDatabaseType databaseType = DictUtils.changeToRDatabaseType(rdI.getDatabase().getDatabaseType());
		DatabaseConnection dc = new DatabaseConnection(rdI.getDatabase().getIp(),rdI.getDatabase().getPort(),rdI.getDatabase().getDatabaseName());
		DatabaseLink databaseLink= new DatabaseLink(databaseType,dc,rdI.getDatabase().getUser(),rdI.getDatabase().getPassword());
		databaseIndexAllItem.setDatabaseLink(databaseLink);
		
		return databaseIndexAllItem;
	}

}
