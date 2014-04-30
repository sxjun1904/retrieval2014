package frame.retrieval.helper;

import java.io.Serializable;

/**
 * Descript：
 * @version:   1.0
 *
 *
 */

public class RetrievalDatabasePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5860922716947033079L;

	private String tableName="";
	private String keyfieldName="";
	private String recordId="";
	
	public RetrievalDatabasePage(){
		
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getKeyfieldName() {
		return keyfieldName;
	}

	public void setKeyfieldName(String keyfieldName) {
		this.keyfieldName = keyfieldName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	
}
