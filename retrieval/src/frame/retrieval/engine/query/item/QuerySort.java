package frame.retrieval.engine.query.item;

import java.io.Serializable;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

/**
 * 搜索结果排序对象
 * @author 
 *
 */
public class QuerySort implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1704989704340181056L;

	private String fieldName;
	private boolean ascFlag=true;
	private int sortFieldType=SortField.STRING;
	private Sort sort;

	public QuerySort(Sort sort){
		this.sort=sort;
	}
	
	/**
	 * 搜索结果排序对象
	 * @param fieldName	如果该值设置为NULL,则使用索引ID进行排序
	 * @param ascFlag 是否正序排列
	 */
	public QuerySort(String fieldName,boolean ascFlag){
		this.fieldName=fieldName;
		this.ascFlag=ascFlag;
	}
	
	/**
	 * 搜索结果排序对象
	 * @param fieldName	如果该值设置为NULL,则使用索引ID进行排序
	 * @param sortFieldType	排序字段类型，使用SortField类中的静态变量，如SortField.STRING
	 * @param ascFlag 是否正序排列
	 */
	public QuerySort(String fieldName,int sortFieldType,boolean ascFlag){
		this.fieldName=fieldName;
		this.ascFlag=ascFlag;
		this.sortFieldType=sortFieldType;
	}

	/**
	 * 获取Lucene排序对象
	 * @return
	 */
	public Sort getSort(){
		if(sort!=null){
			return sort;
		}else{
			return new Sort(new SortField(fieldName,sortFieldType,ascFlag));
		}
	}
	
	public String toString(){
		return fieldName+"@"+ascFlag;
	}
}
