/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.retrieval.helper;

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
