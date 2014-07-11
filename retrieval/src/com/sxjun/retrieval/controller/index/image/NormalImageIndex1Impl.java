package com.sxjun.retrieval.controller.index.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.dbutils.QueryRunner;

import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.FiledMapper;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.base.core.util.ImageUtil;
import frame.base.core.util.JdbcUtil;
import frame.base.core.util.PathUtil;
import frame.base.core.util.StringClass;
import frame.base.core.util.UtilTool;
import frame.base.core.util.file.FileHelper;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseDefaultDocItemType;
import frame.retrieval.engine.RetrievalType.RDatabaseDocItemType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.engine.context.RFacade;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.index.all.database.IBigDataOperator;
import frame.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;
import frame.retrieval.engine.index.all.database.impl.BigDataOperator;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.internal.RDocItem;

public class NormalImageIndex1Impl extends NormalImageIndexCommon implements ICreateIndexAllItem{
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();

	public NormalImageIndex1Impl(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class);
	}
	
	public NormalImageIndex1Impl(RDatabaseIndex rDatabaseIndex){
		rDatabaseIndexList = new ArrayList<RDatabaseIndex>();
		rDatabaseIndexList.add(rDatabaseIndex);
	}
	
	@Override
	public List<NormalIndexDocument> deal(RetrievalApplicationContext retrievalApplicationContext) {
		this.retrievalApplicationContext = retrievalApplicationContext;
		List<NormalIndexDocument> l = null;
		
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			if("0".equals(rdI.getIsError())&&"0".equals(rdI.getIsInit())&&"0".equals(rdI.getIsOn())&&(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE,rdI.getIndexCategory().getIndexPathType()))){
				rdI.setIsInit("2");
				rdI.setMediacyTime(new DateTime().parseString(new Date(), null));
				commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
				String nowTime = new DateTime().getNowDateTime();
				String sql = getIndexTriggerSql(rdI,nowTime,false);
				l = create(retrievalApplicationContext,rdI,sql,nowTime);
			}
		}
		return l;
	}
	
	@Override
	public void afterDeal(Object o) {
		List<NormalIndexDocument> l = (List<NormalIndexDocument>) o;
		Map<String,Object>  m = (Map<String,Object>) l.get(0).getTransObject();
		RDatabaseIndex rdI = (RDatabaseIndex) m.get("rdI");
		//删除图片
		String nowTime = (String) m.get("nowTime");
		long time = new DateTime().getStrig2Long(nowTime);
		ArrayList<String> fileList = fh.getFileList(retrievalApplicationContext.getDefaultRetrievalProperties().getDefault_temp_image_folder());
		for(String s : fileList){
			File f = new File(s);
			long f_time = f.lastModified();
			if(f_time<time)
				fh.deleteFile(s);
		}
		//删除索引
		if("1".endsWith(rdI.getIndexOperatorType())){
			int delcount = judgeAndDelIndexRecord(rdI,nowTime);
			System.out.println("删除"+delcount+"条记录");
		}
		delAllTrigRecord(rdI,nowTime,true);
		rdI.setIsInit("1");
		rdI.setMediacyTime("");
		commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
	}
}
