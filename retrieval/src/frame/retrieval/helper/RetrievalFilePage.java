package frame.retrieval.helper;

import java.io.Serializable;

/**
 * RetrievalFilePage
 * @author 
 *
 */
public class RetrievalFilePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1704416670020783675L;

	private String fileId="";
	private String fileName="";
	private String fileContent="";
	private String fileRelativePath="";
	private String modfiyTime="";
	private String tableName="";
	private String recordId="";
	
	public RetrievalFilePage(){
		
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileRelativePath() {
		return fileRelativePath;
	}

	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getModfiyTime() {
		return modfiyTime;
	}

	public void setModfiyTime(String modfiyTime) {
		this.modfiyTime = modfiyTime;
	}
	
	
}
