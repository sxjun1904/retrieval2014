package frame.retrieval.oth.mapper;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;


public class MapperUtil {
	private XStream xstream = new XStream(new StaxDriver());
	private XStream xstreamDom = new XStream(new DomDriver());
	private XStream xstreamjson = new XStream(new JsonHierarchicalStreamDriver());  
	private Map<String,Class> alias = new HashMap<String,Class>();
	public MapperUtil() {
	}
	
	public MapperUtil(Map<String,Class> alias) {
		this.alias = alias;
	}
	
	/**
	 * 将对象转换成json
	 * @param o
	 * @return
	 */
	public String toJson(Object o){
		String json = null;
		json = xstreamjson.toXML(o);
		return json;
	}
	
	/**
	 * 将对象转换成xml
	 * @param o
	 * @return
	 */
	public String toXml(Object o){
		String xml = null;
		if(!alias.isEmpty())
			for (Map.Entry<String, Class> entry : alias.entrySet()) {
				xstreamDom.alias(entry.getKey(), entry.getValue());
			 }
		xml=xstreamDom.toXML(o);
		return xml;
	}
	/**
	 * 将json转换成对象
	 * @param xml
	 * @return
	 */
	public Object jsonToObject(String json){
		if(!alias.isEmpty())
			for (Map.Entry<String, Class> entry : alias.entrySet()) {
				xstream.alias(entry.getKey(), entry.getValue());
			}
		return xstream.fromXML(json);
	}
	
	/**
	 * 将xml转换成对象
	 * @param xml
	 * @return
	 */
	public Object xmlToObject(String xml){
		if(!alias.isEmpty())
			for (Map.Entry<String, Class> entry : alias.entrySet()) {
				xstreamDom.alias(entry.getKey(), entry.getValue());
			}
		return xstreamDom.fromXML(xml);
	}
	
	/**
	 * 获取alias
	 * @return
	 */
	public Map<String, Class> getAlias() {
		return alias;
	}

	/**
	 * 设置alias
	 * @param alias
	 */
	public void setAlias(Map<String, Class> alias) {
		this.alias = alias;
	}
	
	public static void main(String[] args) {
	}
}
