package frame.retrieval.engine.index.doc.internal;

import java.io.Serializable;

import frame.retrieval.engine.RetrievalType;
import frame.base.core.util.StringClass;

/**
 * 待索引文档中的字段
 * @author 
 *
 */
public class RDocItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4327254469035911247L;

	/**
	 * 字段名称
	 */
	private String name;
	
	/**
	 * 字段内容
	 */
	private String content;
	
	/**
	 * 字段类型
	 */
	private RetrievalType.RDocItemType itemType;

	/**
	 * 获取字段名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置字段名称,全部以大写字母的形式保存
	 * @param name
	 */
	public void setName(Object name) {
		this.name = StringClass.getString(name).toUpperCase();
	}

	/**
	 * 获取内容
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * @param content
	 */
	public void setContent(Object content) {
		this.content = StringClass.getString(content);
	}

	/**
	 * 获取索引字段类型
	 * @return
	 */
	public RetrievalType.RDocItemType getItemType() {
		return itemType;
	}
	
	/**
	 * 设置索引字段类型
	 * @param itemType
	 */
	void setItemType(RetrievalType.RDocItemType itemType) {
		this.itemType = itemType;
	}

	public String toString(){
		return this.getClass()+"@"+this.getName()+"|"+this.getContent();
	}
	
}
