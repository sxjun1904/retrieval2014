package framework.retrieval.oth.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;


public class MapperUtil {
	private ObjectMapper objectMapper = new ObjectMapper();;
	private XStream xstream = new XStream(new StaxDriver());
	private XStream xstreamDom = new XStream(new DomDriver());
	private Map<String,Class> alias = new HashMap<String,Class>();
	public MapperUtil() {
	}
	
	public MapperUtil(Map<String,Class> alias) {
		this.alias = alias;
	}
	
	public String toJson(Object o){
		String json = null;
		try {
			json = objectMapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public String toXml(Object o){
		String xml = null;
		if(!alias.isEmpty())
		for (Map.Entry<String, Class> entry : alias.entrySet()) {
		    xstream.alias(entry.getKey(), entry.getValue());
		 }
		xml=xstream.toXML(o);
		return xml;
	}
	
	public static void main(String[] args) {
	}
}
