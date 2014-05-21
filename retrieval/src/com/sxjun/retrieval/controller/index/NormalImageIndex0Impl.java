package com.sxjun.retrieval.controller.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.dbutils.QueryRunner;
import org.quartz.Job;

import com.jfinal.kit.StringKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.job.DataaseIndexJob1;
import com.sxjun.retrieval.controller.job.NormalImageIndexJob1;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.JustSchedule;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseDocItemType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.query.item.QueryItem;
import frame.retrieval.engine.query.result.QueryResult;
import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;
import frame.retrieval.task.quartz.QuartzManager;

public class NormalImageIndex0Impl extends NormalImageIndexCommon implements ICreateIndexAllItem{
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new CommonService<RDatabaseIndex>();

	public NormalImageIndex0Impl(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class.getSimpleName());
	}
	
	public NormalImageIndex0Impl(RDatabaseIndex rDatabaseIndex){
		rDatabaseIndexList = new ArrayList<RDatabaseIndex>();
		rDatabaseIndexList.add(rDatabaseIndex);
	}
	
	@Override
	public List<NormalIndexDocument> deal(RetrievalApplicationContext retrievalApplicationContext) {
		this.retrievalApplicationContext = retrievalApplicationContext;
		
		List<NormalIndexDocument> l = null;
		
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			if("0".endsWith(rdI.getIsError())&&"0".endsWith(rdI.getIsInit())&&"0".endsWith(rdI.getIsOn())&&(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).endsWith(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE,rdI.getIndexCategory().getIndexPathType()))){
				rdI.setIsInit("2");
				commonService.put(RDatabaseIndex.class.getSimpleName(), rdI.getId(), rdI);
				
				String nowTime =  new DateTime().getNowDateTime();
				//删除触发器表中记录
				delAllTrigRecord(rdI,nowTime);
				
				String sql = rdI.getSql();
				l = create(retrievalApplicationContext,rdI,sql,nowTime);
				//启动定时任务
				if("1".equals(rdI.getStyle())){//复合风格
					sechdule(rdI,new NormalImageIndexJob1());
				}
			}
		}
		return l;
	}
	
	@Override
	public void afterDeal(Object o) {
		fh.delFolder(retrievalApplicationContext.getDefaultRetrievalProperties().getDefault_temp_image_folder());
		List<NormalIndexDocument> l = (List<NormalIndexDocument>) o;
		Map<String,Object>  transObj = (Map<String,Object>) l.get(0).getTransObject();
		RDatabaseIndex rdI = (RDatabaseIndex) transObj.get("rdI");
		String nowTime = (String) transObj.get("nowTime");
		judgeAndDelTrigRecord(rdI,nowTime);
		rdI.setIsInit("1");
		commonService.put(RDatabaseIndex.class.getSimpleName(), rdI.getId(), rdI);
	}

	public static void testBlob() throws Exception{
		RDatabaseType databaseType = DictUtils.changeToRDatabaseType("0");
		String url = JdbcUtil.getConnectionURL(databaseType, "127.0.0.1" ,"3306", "jeecg");
		Connection conn =JdbcUtil.getConnection(databaseType, url, "root", "11111");
		
        String sql="insert into image (binaryField) values(?)";
        for(int i=1;i<29;i++){
        	File file=new File("D:/images/img"+String.valueOf(i)+".jpg");
        	if(file.exists()){
	            byte[] b=new byte[(int)file.length()];
	            InputStream in=new FileInputStream(file);
	            in.read(b);
	            SerialBlob blob=new SerialBlob(b);
	            QueryRunner runner=new QueryRunner();
	            runner.update(conn,sql,blob);
        	}
        }
        
    }
	
	public static void main(String[] args) {
		try {
			testBlob();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
