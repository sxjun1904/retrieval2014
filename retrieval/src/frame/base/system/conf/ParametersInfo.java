package frame.base.system.conf;

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
