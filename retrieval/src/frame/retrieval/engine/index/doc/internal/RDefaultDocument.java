package frame.retrieval.engine.index.doc.internal;

import java.io.Serializable;

import frame.retrieval.engine.RetrievalType;

/**
 * 普通类型Document
 * @author 
 *
 */
public class RDefaultDocument extends RDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3737671086618412214L;

	public RDefaultDocument(boolean fullContentFlag){
		super(fullContentFlag);
		//设置索引来源为文本类型
		setSourceIndexType(RetrievalType.RIndexSourceType.T);
	}
	
	/**
	 * 设置日期类型索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setDatePropertieField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addDateProperty(docItem);
	}
	
	
	/**
	 * 设置数值类型索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setNumberPropertieField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addNumberProperty(docItem);
	}
	
	
	/**
	 * 设置索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setPropertieField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addProperty(docItem);
	}
	
	/**
	 * 设置索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setUnTokenizedPropertyField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addUnTokenizedProperty(docItem);
	}
	
	/**
	 * 设置索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setUnTokenizedStoreOnlyPropertyField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addUnTokenizedStoreOnlyProperty(docItem);
	}
	
	/**
	 * 设置索引文本内容
	 * @param itemType
	 * @param content
	 */
	protected void setContentField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addContent(docItem);
	}
	
	
	/**
	 * 获取索引字段内容
	 * @param itemType
	 * @return
	 */
	protected String getField(Object itemType){
		RDocItem docItem=getDocItem(itemType);
		if(docItem==null){
			return null;
		}else{
			return docItem.getContent();
		}
	}
	
	public String toString(){
		return this.getClass()+"@"+this.getId()+"["+getSourceIndexType()+"]";
	}
	
}
