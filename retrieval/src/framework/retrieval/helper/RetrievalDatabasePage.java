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
