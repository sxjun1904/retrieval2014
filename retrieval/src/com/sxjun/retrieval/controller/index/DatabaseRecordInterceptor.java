package com.sxjun.retrieval.controller.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.InitField;

import frame.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;

public class DatabaseRecordInterceptor implements IIndexAllDatabaseRecordInterceptor{
	
	private CommonService<InitField> commonService = new CommonService<InitField>();

	/**
	 * 将数据库中查询中的记录进行加工处理
	 * @param record
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map interceptor(Map record) {
		
//		record.put("DOC_CREATE_TIME", String.valueOf(System.currentTimeMillis()));
		return record;
	}

	@SuppressWarnings("unchecked")
	public Map getFieldsType() {
		List<InitField> ifs = commonService.getObjs(InitField.class.getSimpleName());
		Map<String,String> m = new HashMap<String,String>();
		for(InitField _if:ifs){
			m.put(_if.getField(),_if.getFieldType());
		}
		return m;
	}

}
