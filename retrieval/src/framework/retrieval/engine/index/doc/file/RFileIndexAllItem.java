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
package framework.retrieval.engine.index.doc.file;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import framework.base.snoic.base.util.StringClass;
import framework.retrieval.engine.RetrievalConstant;
import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.facade.IRDocOperatorFacade;
import framework.retrieval.engine.facade.IRQueryFacade;
import framework.retrieval.engine.index.all.file.IIndexAllFileInterceptor;

/**
 * 文件批量索引对象
 * @author 
 *
 */
public class RFileIndexAllItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RetrievalType.RIndexOperatorType indexOperatorType=null;
	private IIndexAllFileInterceptor indexAllFileInterceptor;
	private IRQueryFacade queryFacade=null;
	private IRDocOperatorFacade docOperatorFacade=null;

	/**
	 * 是否同时再索引中生成一个将所有内容组合存放的字段
	 */
	private boolean fullContentFlag=false;
	
	/**
	 * 索引路径类型
	 */
	private String indexPathType=null;

	/**
	 * 索引信息分类标志
	 * 		如：知识库、信息发布、停电信息
	 */
	private String indexInfoType="";
	
	/**
	 * 文件根目录
	 */
	private String fileBasePath="";
	
	/**
	 * 是否包括子目录
	 */
	private boolean includeSubDir=true;

	/**
	 * 需要建索引的文件类型
	 */
	private List<String> fileTypes=new ArrayList<String>();
	
	/**
	 * 每页文件数量
	 */
	private int pageSize=5;
	
	/**
	 *  最大文件分页数量，每页读取的文件数量不允许超过这个最大值
	 */
	private int maxPageSize=10;
	
	/**
	 *  最大文件分页数量，每页读取的文件数量不允许超过这个最大值
	 */
	private String charsetName=null;

	/**
	 * 最大索引文件大小
	 */
	private long maxIndexFileSize=RetrievalConstant.DEFAULT_INDEX_MAX_FILE_SZIE;
	
	/**
	 * 
	 * @param queryFacade 
	 * @param docOperatorFacade 
	 * @param fullContentFlag 是否同时再索引中生成一个将所有内容组合存放的字段
	 * @param maxPageSize 	  最大文件分页数量，每页读取的文件数量不允许超过这个最大值
	 * @param charsetName 	  文本文件索引时使用的字符集
	 */
	public RFileIndexAllItem(IRQueryFacade queryFacade,IRDocOperatorFacade docOperatorFacade,boolean fullContentFlag,int maxPageSize,String charsetName){
		this.queryFacade=queryFacade;
		this.docOperatorFacade=docOperatorFacade;
		this.fullContentFlag=fullContentFlag;
		this.maxPageSize=maxPageSize;
		this.charsetName=charsetName;
	}

	public IRQueryFacade getQueryFacade() {
		return queryFacade;
	}

	public IRDocOperatorFacade getDocOperatorFacade() {
		return docOperatorFacade;
	}

	public IIndexAllFileInterceptor getIndexAllFileInterceptor() {
		return indexAllFileInterceptor;
	}

	public void setIndexAllFileInterceptor(
			IIndexAllFileInterceptor indexAllFileInterceptor) {
		this.indexAllFileInterceptor = indexAllFileInterceptor;
	}

	/**
	 * 是否将所有内容组合成一个字段存放
	 * @return
	 */
	public boolean isFullContentFlag() {
		return fullContentFlag;
	}

	/**
	 * 获取信息分类
	 * @return
	 */
	public String getIndexInfoType() {
		return indexInfoType;
	}

	/**
	 * 设置信息分类
	 * @param indexInfoType
	 */
	public void setIndexInfoType(String indexInfoType) {
		this.indexInfoType = indexInfoType;
	}

	/**
	 * 获取索引路径类型
	 * @return
	 */
	public String getIndexPathType() {
		return indexPathType;
	}

	/**
	 * 设置索引路径类型
	 * @param indexPathType
	 */
	public void setIndexPathType(String indexPathType) {
		this.indexPathType = indexPathType.toUpperCase();
	}

	/**
	 * 获取索引操作类型
	 * @return
	 */
	public RetrievalType.RIndexOperatorType getIndexOperatorType() {
		return indexOperatorType;
	}

	/**
	 * 设置索引操作类型
	 * @param indexOperatorType
	 */
	public void setIndexOperatorType(RetrievalType.RIndexOperatorType indexOperatorType) {
		this.indexOperatorType = indexOperatorType;
	}

	/**
	 * 获取文件基本路径
	 * @return
	 */
	public String getFileBasePath() {
		return fileBasePath;
	}

	/**
	 * 设置文件基本路径
	 * @param fileBasePath
	 */
	public void setFileBasePath(String fileBasePath) {
		this.fileBasePath = fileBasePath;
	}

	/**
	 * 是否包含子文件夹
	 * @return
	 */
	public boolean isIncludeSubDir() {
		return includeSubDir;
	}

	/**
	 * 设置是否包含子文件夹
	 * @param includeSubDir
	 */
	public void setIncludeSubDir(boolean includeSubDir) {
		this.includeSubDir = includeSubDir;
	}
	
	/**
	 * 获取每页最大的文件数量，默认值为 10
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页最大的文件数量，默认值为 10
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if(pageSize>maxPageSize){
			pageSize=maxPageSize;
		}
		this.pageSize = pageSize;
	}


	public long getMaxIndexFileSize() {
		return maxIndexFileSize;
	}

	public String getCharsetName() {
		return charsetName;
	}

	/**
	 * 获取需要建索引的文件类型
	 * @return
	 */
	public List<String> getFileTypes() {
		return fileTypes;
	}

	/**
	 * 设置需要建索引的文件类型
	 * 			如果不设置任何类型，则默认对所有类型的文件创建索引，因此如果要对所有类型的文件创建索引，请不要调用此方法
	 * 			如果设置了类型，则只对设置了类型的文件创建索引；
	 * 			
	 * @param fileTypes
	 */
	public void addFileType(String fileType) {
		fileType=StringClass.getString(fileType);
		if(fileType.equals("")){
			return;
		}
		if(this.fileTypes==null){
			this.fileTypes=new ArrayList<String>();
		}
		this.fileTypes.add(fileType.toUpperCase());
	}
	
}
