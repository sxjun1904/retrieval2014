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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 
 *
 */
public class ParametersInfo {
	private Map parametersMap=new HashMap();
	
	/**
	 * 设置参数
	 * @param name
	 * @param value
	 */
	public void setParameter(String name,String value){
		parametersMap.put(name,value);
	}
	
	/**
	 * 清除单个参数
	 * @param name
	 */
	public void clearParameter(String name){
		parametersMap.remove(name);
	}
	
	/**
	 * 清除所有参数
	 *
	 */
	public void clearParameters(){
		parametersMap.clear();
	}
	
	/**
	 * @return Returns the parametersMap.
	 */
	public Map getParametersMap() {
		return parametersMap;
	}

	/**
	 * 获取参数值
	 * @param name
	 * @return String
	 */
	public String getValue(String name){
		String value=null;
		value=(String)parametersMap.get(name);
		return value;
	}
}
