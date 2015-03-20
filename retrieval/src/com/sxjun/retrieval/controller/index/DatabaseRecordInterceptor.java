package com.sxjun.retrieval.controller.index;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.retrieval.pojo.InitField;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.base.core.util.StringClass;
import frame.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;

public class DatabaseRecordInterceptor implements IIndexAllDatabaseRecordInterceptor{
	
	private CommonService<InitField> commonService = new ServiceProxy<InitField>().getproxy();
	private static DateTime dt= new DateTime();

	/**
	 * 将数据库中查询中的记录进行加工处理
	 * @param record
	 * @return
	 */
	@Override
	public Map interceptor(Map record) {
		
//		record.put("DOC_CREATE_TIME", String.valueOf(System.currentTimeMillis()));
//		String s =  StringClass.getString(record.get("UpdateTime"));//phone modify by sxjun
		String s =  StringClass.getString(record.get("CREATETIME"));
		Date date = dt.parseDate(s,null);
		record.put("CREATETIME",dt.parseString(date,"yyyyMMddHHmmss"));
//		Map<String,Double> pagerank = DatabaseIndexAllItem0Impl.pagerank;
//		record.put("pagerank",String.valueOf(pagerank.get(record.get("OriginalURL"))));
		return record;
	}

	@Override
	public Map getFieldsType() {
		List<InitField> ifs = commonService.getObjs(InitField.class);
		Map<String,String> m = new HashMap<String,String>();
		for(InitField _if:ifs){
			m.put(_if.getField(),_if.getFieldType());
		}
		return m;
	}

	@Override
	public Map<String, Object> interceptor(Map<String, Object> record,Map<String, String> fieldMapper) {
		Set<Map.Entry<String, String>> set = fieldMapper.entrySet();
        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            if("CREATETIME".endsWith(entry.getValue())){
            	String s =  StringClass.getString(record.get(entry.getKey()));
        		Date date = dt.parseDate(s,null);
        		record.put("CREATETIME",dt.parseString(date,"yyyyMMddHHmmss"));
        		break;
            }
        }
		return record;
	}

}
