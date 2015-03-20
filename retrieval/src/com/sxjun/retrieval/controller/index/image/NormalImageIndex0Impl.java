package com.sxjun.retrieval.controller.index.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.dbutils.QueryRunner;

import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.core.common.utils.DictUtils;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.job.NormalImageIndexJob1;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.index.doc.NormalIndexDocument;

public class NormalImageIndex0Impl extends NormalImageIndexCommon implements ICreateIndexAllItem{
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();

	public NormalImageIndex0Impl(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class);
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
			if("0".equals(rdI.getIsError())&&"0".equals(rdI.getIsInit())&&"0".equals(rdI.getIsOn())&&(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE,rdI.getIndexCategory().getIndexPathType()))){
				rdI.setIsInit("2");
				rdI.setMediacyTime(new DateTime().parseString(new Date(), null));
				commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
				
				String nowTime =  new DateTime().getNowDateTime();
				//删除触发器表中记录
				delAllTrigRecord(rdI,nowTime,false);
				
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
		rdI.setMediacyTime("");
		commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
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
