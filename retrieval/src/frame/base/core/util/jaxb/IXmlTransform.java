package frame.base.core.util.jaxb;


/**
 * XML和对象之间的转化
 * @author 
 *
 *
 */

public interface IXmlTransform {
	
	public IXmlMarshal getXmlMarshal();

	public void setXmlMarshal(IXmlMarshal xmlMarshal);

	public IXmlUnmarshal getXmlUnmarshal();

	public void setXmlUnmarshal(IXmlUnmarshal xmlUnmarshal);

	/**
	 * xml转化成对象
	 * @param xmlString
	 * @return Object
	 */
	public Object xmlToObject(String xmlString);
	
	/**
	 * 对象转化成xml
	 * @param object
	 * @return String
	 */
	public String objectToXml(Object object);
}
