package com.sxjun.retrieval.controller.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.StringKit;
import com.sxjun.core.plugin.redis.RedisKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.pojo.FiledMapper;
import com.sxjun.retrieval.pojo.FiledSpecialMapper;
import com.sxjun.retrieval.pojo.IndexCagetory;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.RetrievalType.RDatabaseType;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.ICreateIndexAllItem;
import framework.retrieval.engine.index.all.database.DatabaseConnection;
import framework.retrieval.engine.index.all.database.DatabaseLink;
import framework.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public class DatabaseIndexAllItemImpl implements ICreateIndexAllItem{
	
	private List<RDatabaseIndex> rDatabaseIndexList;
	
	public DatabaseIndexAllItemImpl(){
		rDatabaseIndexList = RedisKit.getObjs(RDatabaseIndex.class.getSimpleName());
	}
	
	public DatabaseIndexAllItemImpl(List<RDatabaseIndex> rDatabaseIndexList){
		this.rDatabaseIndexList = rDatabaseIndexList;
	}

	@Override
	public List<RDatabaseIndexAllItem> generateApplicationData(RetrievalApplicationContext retrievalApplicationContext) {
		
		List <RDatabaseIndexAllItem> l = new ArrayList<RDatabaseIndexAllItem>();
		
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			String tableName = rdI.getTableName();
			String keyField = rdI.getKeyField();
			String sql = rdI.getSql();
			RDatabaseIndexAllItem databaseIndexAllItem = retrievalApplicationContext.getFacade().createDatabaseIndexAllItem(false);
			
			IndexCagetory ic = RedisKit.get(IndexCagetory.class.getSimpleName(),rdI.getIndexPath_id());
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
						sqlSpecialFieldMapper.put(s, m);
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
					fieldMapper.put(f.getIndexField(), s);
				}
			}
			databaseIndexAllItem.setFieldMapper(fieldMapper);
			
			//是否取出重复
			databaseIndexAllItem.setRmDuplicate("0".endsWith(rdI.getRmDuplicate())?true:false);
			
			//是否有拦截器
			if(StringKit.notBlank(rdI.getDatabaseRecordInterceptor())){
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
			l.add(databaseIndexAllItem);
		}
		return l;
	}

}
