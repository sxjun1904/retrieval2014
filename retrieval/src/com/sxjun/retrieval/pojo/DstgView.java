/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 触发器监控Entity
 * @author sxjun
 * @version 2014-07-10
 */
public class DstgView extends BasePojo{
	private String databasename; 	
	private String triggername;
	public String getDatabasename() {
		return databasename;
	}
	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}
	public String getTriggername() {
		return triggername;
	}
	public void setTriggername(String triggername) {
		this.triggername = triggername;
	} 	
	
}


