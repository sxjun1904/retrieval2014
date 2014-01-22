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
package framework.base.snoic.system.conf;

/**
 * Descript: 系统初始化引导
 *
 *
 */

public class SystemInitConfig {
	
	/**
	 * 配置文件存放路径
	 */
	private String configpath="";
	
	/**
	 * 引导文件名
	 */
	private String systemFilename="";
	
	/**
	 * 路径是否为相对路径
	 */
	private boolean relativize=false;
	
	public String getConfigpath() {
		return configpath;
	}
	
	public void setConfigpath(String configpath) {
		this.configpath = configpath;
	}
	
	public String getSystemFilename() {
		return systemFilename;
	}
	
	public void setSystemFilename(String systemFilename) {
		this.systemFilename = systemFilename;
	}

	public boolean isRelativize() {
		return relativize;
	}

	public void setRelativize(boolean relativize) {
		this.relativize = relativize;
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(super.toString()+"=[");
		sb.append("configpath="+configpath);
		sb.append(",systemFilename="+systemFilename);
		sb.append(",relativize="+relativize);
		sb.append("]");
		
		return sb.toString();
	}
	
}
