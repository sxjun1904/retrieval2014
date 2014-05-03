package com.sxjun.retrieval.controller.index;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseFieldType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.engine.context.RFacade;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.index.all.database.DatabaseLink;
import frame.retrieval.engine.index.all.database.impl.DefaultDBGetMethodImpl;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import frame.retrieval.engine.index.doc.internal.RDocItem;
import frame.retrieval.test.init.TestInit;

public class NormalImageIndexCommon {
	
	/**
	 * 生成分页SQL
	 * @param sql
	 * @param from
	 * @param size
	 * @return
	 */
	public String getLimitString_Oracle(String sql, int from, int size) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ where rownum <= ");
		pagingSelect.append(from + size);
		pagingSelect.append(") where rownum_ > ");
		pagingSelect.append(from);
		return pagingSelect.toString();
	}
	
	public String getLimitString_MySql(String sql, int from, int size) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append(sql);
		pagingSelect.append(" LIMIT ");
		pagingSelect.append(from);
		pagingSelect.append(",");
		pagingSelect.append(size);
		return pagingSelect.toString();
	}
	
	public String getLimitString_SqlServer(String sql, int from, int size) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append(" select * from ( select row_number()over(order by tempcolumn)temprownumber,*");
		pagingSelect.append(" from (select top ");
		pagingSelect.append(from + size);
		pagingSelect.append(" tempcolumn=0,* from (");
		pagingSelect.append(sql);
		pagingSelect.append(" )t)tt)ttt ");
		pagingSelect.append(" where ttt.temprownumber> ");
		pagingSelect.append(from);
		return pagingSelect.toString();
	}

}
