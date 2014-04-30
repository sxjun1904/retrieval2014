package frame.retrieval.engine.index.doc.database;

import java.io.Serializable;

import frame.retrieval.engine.index.doc.AbstractIndexDocument;
import frame.retrieval.engine.index.doc.database.internal.RDatabaseDocument;

/**
 * 当条数据库记录Document
 * @author 
 *
 */
public class DatabaseIndexDocument extends AbstractIndexDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737929916612807540L;
	
	private RDatabaseDocument rDocument=null;
	
	public DatabaseIndexDocument(boolean fullContentFlag){
		rDocument=new RDatabaseDocument(fullContentFlag);
	}
	
	/**
	 * 设置数据库表名和记录ID
	 * @param tableName
	 */
	public void setTableNameAndRecordId(String tableName,String recordId){
		rDocument.setTableNameAndRecordId(tableName,recordId);
	}
	
	/**
	 * 获取数据库表名
	 * @return
	 */
	public String getTableName(){
		return rDocument.getTableName();
	}
	
	/**
	 * 获取数据库主键ID
	 * @return
	 */
	public String getRecordId(){
		return rDocument.getRecordId();
	}
	
	/**
	 * 设置关键字段名
	 * @param keyfieldName
	 */
	public void setKeyfieldName(String keyfieldName){
		rDocument.setKeyfieldName(keyfieldName);
	}
	
	/**
	 * 获取关键字段名
	 * @return
	 */
	public String getKeyfieldName(){
		return rDocument.getKeyfieldName();
	}

	public RDatabaseDocument getRDocument() {
		return rDocument;
	}
	
	/**
	 * 设置标题
	 */
	public void setDefaultTitle(String title){
		rDocument.setDefaultTitle(title);
	}
	/**
	 * 返回标题
	 * @return
	 */
	public String getDefaultTitle(){
		return rDocument.getDefaultTitle();
	}
	
	/**
	 * 设置摘要
	 */
	public void setDefaultResume(String resume){
		rDocument.setDefaultResume(resume);
	}
	
	/**
	 * 返回摘要
	 * @return
	 */
	public String getDefaultResume(){
		return rDocument.getDefaultResume();
	}


}
