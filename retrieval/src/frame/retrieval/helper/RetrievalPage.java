package frame.retrieval.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * RetrievalPage
 * @author 
 *
 */
public class RetrievalPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1669606956866147829L;

	private String sourceIndexType="";
	private String title="";
	private String content="";
	private String createTime="";
	private RetrievalDatabasePage retrievalDatabasePage;
	private RetrievalFilePage[] retrievalFilePages;
	private Map<String,String> retrievalResultFields = new HashMap<String,String>();
	
	public RetrievalPage(){
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSourceIndexType() {
		return sourceIndexType;
	}

	public void setSourceIndexType(String sourceIndexType) {
		this.sourceIndexType = sourceIndexType;
	}

	public RetrievalDatabasePage getRetrievalDatabasePage() {
		return retrievalDatabasePage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setRetrievalDatabasePage(
			RetrievalDatabasePage retrievalDatabasePage) {
		this.retrievalDatabasePage = retrievalDatabasePage;
	}

	public RetrievalFilePage[] getRetrievalFilePages() {
		return retrievalFilePages;
	}

	public void setRetrievalFilePages(RetrievalFilePage[] retrievalFilePages) {
		this.retrievalFilePages = retrievalFilePages;
	}

	public Map<String, String> getRetrievalResultFields() {
		return retrievalResultFields;
	}

	public void setRetrievalResultFields(Map<String, String> retrievalResultFields) {
		this.retrievalResultFields = retrievalResultFields;
	}

}
