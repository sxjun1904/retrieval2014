package com.sxjun.retrieval.controller.index.image;

import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sxjun.common.utils.DictUtils;
import com.sxjun.retrieval.controller.index.IndexCommon;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.FiledMapper;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

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
import frame.retrieval.engine.index.all.database.IBigDataOperator;
import frame.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;
import frame.retrieval.engine.index.all.database.impl.BigDataOperator;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.internal.RDocItem;

public class NormalImageIndexCommon  extends IndexCommon{
	protected FileHelper fh = new FileHelper();
	
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
	
	public List<NormalIndexDocument> create(RetrievalApplicationContext retrievalApplicationContext,RDatabaseIndex rdI,String sql,String nowTime){
		RFacade facade=retrievalApplicationContext.getFacade();
		Database db =  rdI.getDatabase();
		RDatabaseType databaseType = DictUtils.changeToRDatabaseType(db.getDatabaseType());
		String url = JdbcUtil.getConnectionURL(databaseType, db.getIp(), db.getPort(), db.getDatabaseName());
		Connection conn =JdbcUtil.getConnection(databaseType, url, db.getUser(), db.getPassword());
		
		Map<String,Object> transObj = new HashMap<String,Object>();
		transObj.put("rdI", rdI);
		transObj.put("nowTime", nowTime);
		
		int pagesize = 200;
		int pagenum = 1;
		boolean next = true;
		List<NormalIndexDocument> l = new ArrayList<NormalIndexDocument>();
		
		while(next){
			if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
				sql = getLimitString_Oracle(sql, (pagenum-1)*pagesize, pagenum*pagesize);
			} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
				sql = getLimitString_SqlServer(sql,(pagenum-1)*pagesize, pagenum*pagesize);
			} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
				sql = getLimitString_MySql(sql, (pagenum-1)*pagesize, pagenum*pagesize);
			}
			List<Map> result = (List<Map>) JdbcUtil.getMapList(conn, sql, true);
			
			for(Map map : result){
				try {
					NormalIndexDocument normalIndexDocument=facade.createNormalIndexDocument(false);
					IIndexAllDatabaseRecordInterceptor iiadri = (IIndexAllDatabaseRecordInterceptor) Class.forName(rdI.getDatabaseRecordInterceptor()).newInstance();
					IIndexAllDatabaseRecordInterceptor databaseRecordInterceptor = iiadri;
					
					Map fieldTypeMap=null;
					if(databaseRecordInterceptor!=null){
						map=(Map)databaseRecordInterceptor.interceptor(map);
						fieldTypeMap=databaseRecordInterceptor.getFieldsType();
					}
					
					String recordId=StringClass.getString(map.get(rdI.getKeyField()));
					String _title = StringClass.getString(map.get(rdI.getDefaultTitleFieldName()));
					String _resume = StringClass.getString(map.get(rdI.getDefaultResumeFieldName()));
					Object o = map.get(rdI.getBinaryField());
					
					IBigDataOperator bigDataOperator = new BigDataOperator();
					if(o.getClass().getSimpleName().equals("byte[]"))
						o = bigDataOperator.getByteFromBlob((byte[]) o);
					else if(o.getClass().getSimpleName().equals("BLOB"))
						o = bigDataOperator.getByteFromBlob((Blob) o);
					
					String _randomname = UUID.randomUUID().toString()+".jpg";
					String filename = retrievalApplicationContext.getDefaultRetrievalProperties().getDefault_temp_image_folder()+File.separator+_randomname;
					File _tempFile = new File(retrievalApplicationContext.getDefaultRetrievalProperties().getDefault_temp_image_folder());
					if(!_tempFile.exists())
						_tempFile.mkdirs();
					String newImg = PathUtil.getPath()+"static"+File.separator+"img"+File.separator+_randomname;
					fh.byte2File((byte[])o,new File(filename));
					
					ImageUtil.resize3Style(filename, newImg);
					
					RDocItem docItem1=new RDocItem();
					docItem1.setContent(rdI.getKeyField());
					docItem1.setName(StringClass.getString(RDatabaseDocItemType._DK));
					normalIndexDocument.addKeyWord(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(rdI.getTableName());
					docItem1.setName(StringClass.getString(RDatabaseDocItemType._DT));
					normalIndexDocument.addKeyWord(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(recordId);
					docItem1.setName(StringClass.getString(RDatabaseDocItemType._DID));
					normalIndexDocument.addKeyWord(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(_title);
					docItem1.setName(StringClass.getString(RDatabaseDefaultDocItemType._TITLE));
					normalIndexDocument.addContent(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(_resume);
					docItem1.setName(StringClass.getString(RDatabaseDefaultDocItemType._RESUME));
					normalIndexDocument.addContent(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent("img/"+_randomname);
					docItem1.setName(StringClass.getString(RDatabaseDefaultDocItemType._PATH));
					normalIndexDocument.addKeyWord(docItem1);
					
					if("0".equals(rdI.getRmDuplicate())){
						map.remove(rdI.getKeyField());
						map.remove(rdI.getDefaultTitleFieldName());
						map.remove(rdI.getDefaultResumeFieldName());
						map.remove(rdI.getBinaryField());
					}
					
					Object[][] objects=UtilTool.getMapKeyValue(map);
					int len=objects.length;
					
					//映射字段处理
					Map<String,String> fieldMapper = new HashMap<String,String>();
					List<FiledMapper> fm = rdI.getFiledMapperLsit();
					for(FiledMapper f : fm){
						String[] ss = f.getSqlField().split(";");
						for(String s : ss){
							fieldMapper.put(f.getIndexField(), s);
						}
					}
					
					for(int j=0;j<len;j++){
						String name=StringClass.getString(objects[j][0]).toUpperCase();
						String type=StringClass.getString((fieldTypeMap.get(name)));
						//替换数据库字段
						if(fieldMapper!=null&&!fieldMapper.isEmpty()){
							if(fieldMapper.get(name)!=null)
								name =  fieldMapper.get(name);
						}
						String content=StringClass.getString(objects[j][1]);
						RDocItem docItem=new RDocItem();
						docItem.setName(name);
						docItem.setContent(content);
						if(fieldTypeMap!=null){
							if(!type.equals("")){
								if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.KEYWORD))){
									normalIndexDocument.addKeyWord(docItem);
								}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.DATE))){
									normalIndexDocument.addKeyWord(docItem);
								}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.NUMBER))){
									normalIndexDocument.addKeyWord(docItem);
								}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.PROPERTY))){
									normalIndexDocument.addKeyWord(docItem);
								}else{
									normalIndexDocument.addKeyWord(docItem);
								}
							}else{
								normalIndexDocument.addKeyWord(docItem);	
							}
						}else{
							normalIndexDocument.addKeyWord(docItem);				
						}
					}
					normalIndexDocument.setIndexInfoType(rdI.getIndexCategory().getIndexInfoType());
					normalIndexDocument.setIndexPathType(rdI.getIndexCategory().getIndexPath());
					normalIndexDocument.setId(rdI.getKeyField());
					normalIndexDocument.setTransObject(transObj);
					l.add(normalIndexDocument);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			if(result==null||result.size()<pagesize)
				next = false;
			else{
				pagenum++;
			}
		}
		return l;
	}
}
