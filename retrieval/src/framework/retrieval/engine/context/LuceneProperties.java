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
package framework.retrieval.engine.context;

import org.apache.lucene.util.Version;

import framework.retrieval.engine.pool.IIndexReaderPool;
import framework.retrieval.engine.pool.IIndexWriterPool;
import framework.retrieval.engine.pool.impl.IndexReaderPool;
import framework.retrieval.engine.pool.impl.IndexWriterPool;

/**
 * Lucene参数对象
 * 
 * @author 
 *
 */
public class LuceneProperties {

	
	/**
	 * 索引文件绝对路径根目录
	 */
	private String indexBasePath=null;

	/**
	 * IndexReader对象池
	 * 
	 */
	private IIndexReaderPool indexReaderPool=null;
	
	/**
	 * IndexWriter对象池
	 */
	private IIndexWriterPool indexWriterPool=null;
	
	/**
	 * 
		<br>=====================================================================================
		<br>Lucene参数，如果不设置则使用默认值  LUCENE_30
		<br>设置Lucene版本号，将影响到索引文件格式及查询结果
		<br>Use by certain classes to match version compatibility across releases of Lucene. 
		<br>	LUCENE_20
		<br>	LUCENE_21
		<br>	LUCENE_22
		<br>	LUCENE_23
		<br>	LUCENE_24
		<br>	LUCENE_29
		<br>	LUCENE_30
		<br>====================================================================================
	 */
	Version propertyValueLuceneVersion=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_VERSION;
	static final String PROPERTIES_NAME_LUCENE_PARAM_VERSION="LUCENE_PARAM_VERSION";
	

	/**
	 * 
		<br>=====================================================================================
		<br>Lucene参数，如果不设置则使用默认值 DEFAULT_MAX_FIELD_LENGTH=10000
		<br>====================================================================================
	 */
	int propertyValueLuceneMaxFieldLength=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_MAX_FIELD_LENGTH;
	static final String PROPERTIES_NAME_LUCENE_PARAM_MAX_FIELD_LENGTH="LUCENE_PARAM_MAX_FIELD_LENGTH";
	
	

	/**
	 * 
		<br>=====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 DEFAULT_RAM_BUFFER_SIZE_MB=16
		<br>控制用于buffer索引文档的内存上限，如果buffer的索引文档个数到达该上限就写入硬盘，越大索引速度越快。
		<br>====================================================================================
	 */
	double propertyValueLuceneRamBufferSizeMB=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_RAM_BUFFER_SIZE_MB;
	static final String PROPERTIES_NAME_LUCENE_PARAM_RAM_BUFFER_SIZE_MB="LUCENE_PARAM_RAM_BUFFER_SIZE_MB";
	
	
	

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 
		<br>和LUCENE_PARAM_RAM_BUFFER_SIZE_MB这两个参数是可以一起使用的，一起使用时只要有一个触发条件
		<br>满足就写入硬盘，生成一个新的索引segment文件
		<br>====================================================================================
	 */
	int propertyValueLuceneMaxBufferedDocs=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_MAX_BUFFERED_DOCS;
	static final String PROPERTIES_NAME_LUCENE_PARAM_MAX_BUFFERED_DOCS="LUCENE_PARAM_MAX_BUFFERED_DOCS";
	
	
	

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 10
		<br>MergeFactor 这个参数就是控制当硬盘中有多少个子索引segments，MergeFactor这个不能设置太大，
		<br>特别是当MaxBufferedDocs比较小时（segment 越多），否则会导致open too many files错误，甚至导致虚拟机外面出错。
		<br>====================================================================================
	 */
	int propertyValueLuceneMergerFactor=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_MERGE_FACTOR;
	static final String PROPERTIES_NAME_LUCENE_PARAM_MERGE_FACTOR="LUCENE_PARAM_MERGE_FACTOR";
	
	
	

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值  Integer.MAX_VALUE
		<br>该参数决定写入内存索引文档个数，到达该数目后就把该内存索引写入硬盘，生成一个新的索引segment文件，越大索引速度越快。
		<br>====================================================================================
	 */
	int propertyValueLuceneMaxMergeDocs=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_MAX_MERGE_DOCS;
	static final String PROPERTIES_NAME_LUCENE_PARAM_MAX_MERGE_DOCS="LUCENE_PARAM_MAX_MERGE_DOCS";
	
	public LuceneProperties(){

		indexReaderPool=new IndexReaderPool();
		indexWriterPool=new IndexWriterPool(indexReaderPool);
		
	}

	/**
	 * 获取索引文件绝对路径根目录
	 * @return
	 */
	public String getIndexBasePath() {
		return indexBasePath;
	}

	/**
	 * 设置索引文件绝对路径根目录
	 * @param indexBasePath
	 */
	public void setIndexBasePath(String indexBasePath) {
		this.indexBasePath = indexBasePath;
	}

	public IIndexWriterPool getIndexWriterPool(){
		return indexWriterPool;
	}

	public IIndexReaderPool getIndexReaderPool() {
		return indexReaderPool;
	}

	/**
	 * 
		<br>=====================================================================================
		<br>Lucene参数，如果不设置则使用默认值  LUCENE_30
		<br>设置Lucene版本号，将影响到索引文件格式及查询结果
		<br>====================================================================================
	 */
	public Version getPropertyValueLuceneVersion() {
		return propertyValueLuceneVersion;
	}

	/**
	 * 
		<br>=====================================================================================
		<br>Lucene参数，如果不设置则使用默认值  LUCENE_30
		<br>设置Lucene版本号，将影响到索引文件格式及查询结果
		<br>====================================================================================
	 */
	public void setPropertyValueLuceneVersion(Version propertyValueLuceneVersion) {
		this.propertyValueLuceneVersion = propertyValueLuceneVersion;
	}
	
	/**
	 * 
		<br>=====================================================================================
		<br>Lucene参数，如果不设置则使用默认值 DEFAULT_MAX_FIELD_LENGTH=10000
		<br>====================================================================================
	 */
	public int getPropertyValueLuceneMaxFieldLength() {
		return propertyValueLuceneMaxFieldLength;
	}
	
	/**
	 * 
		<br>=====================================================================================
		<br>Lucene参数，如果不设置则使用默认值 DEFAULT_MAX_FIELD_LENGTH=10000
		<br>====================================================================================
	 */
	public void setPropertyValueLuceneMaxFieldLength(
			int propertyValueLuceneMaxFieldLength) {
		this.propertyValueLuceneMaxFieldLength = propertyValueLuceneMaxFieldLength;
	}
	
	/**
	 * 
		<br>=====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 DEFAULT_RAM_BUFFER_SIZE_MB=16
		<br>控制用于buffer索引文档的内存上限，如果buffer的索引文档个数到达该上限就写入硬盘，越大索引速度越快。
		<br>====================================================================================
	 */
	public double getPropertyValueLuceneRamBufferSizeMB() {
		return propertyValueLuceneRamBufferSizeMB;
	}

	/**
	 * 
		<br>=====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 DEFAULT_RAM_BUFFER_SIZE_MB=16
		<br>控制用于buffer索引文档的内存上限，如果buffer的索引文档个数到达该上限就写入硬盘，越大索引速度越快。
		<br>====================================================================================
	 */
	public void setPropertyValueLuceneRamBufferSizeMB(
			double propertyValueLuceneRamBufferSizeMB) {
		this.propertyValueLuceneRamBufferSizeMB = propertyValueLuceneRamBufferSizeMB;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 
		<br>和LUCENE_PARAM_RAM_BUFFER_SIZE_MB这两个参数是可以一起使用的，一起使用时只要有一个触发条件
		<br>满足就写入硬盘，生成一个新的索引segment文件
		<br>====================================================================================
	 */
	public int getPropertyValueLuceneMaxBufferedDocs() {
		return propertyValueLuceneMaxBufferedDocs;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 
		<br>和LUCENE_PARAM_RAM_BUFFER_SIZE_MB这两个参数是可以一起使用的，一起使用时只要有一个触发条件
		<br>满足就写入硬盘，生成一个新的索引segment文件
		<br>====================================================================================
	 */
	public void setPropertyValueLuceneMaxBufferedDocs(
			int propertyValueLuceneMaxBufferedDocs) {
		this.propertyValueLuceneMaxBufferedDocs = propertyValueLuceneMaxBufferedDocs;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 10
		<br>MergeFactor 这个参数就是控制当硬盘中有多少个子索引segments，MergeFactor这个不能设置太大，
		<br>特别是当MaxBufferedDocs比较小时（segment 越多），否则会导致open too many files错误，甚至导致虚拟机外面出错。
		<br>====================================================================================
	 */
	public int getPropertyValueLuceneMergerFactor() {
		return propertyValueLuceneMergerFactor;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值 10
		<br>MergeFactor 这个参数就是控制当硬盘中有多少个子索引segments，MergeFactor这个不能设置太大，
		<br>特别是当MaxBufferedDocs比较小时（segment 越多），否则会导致open too many files错误，甚至导致虚拟机外面出错。
		<br>====================================================================================
	 */
	public void setPropertyValueLuceneMergerFactor(
			int propertyValueLuceneMergerFactor) {
		this.propertyValueLuceneMergerFactor = propertyValueLuceneMergerFactor;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值  Integer.MAX_VALUE
		<br>该参数决定写入内存索引文档个数，到达该数目后就把该内存索引写入硬盘，生成一个新的索引segment文件，越大索引速度越快。
		<br>====================================================================================
	 */
	public int getPropertyValueLuceneMaxMergeDocs() {
		return propertyValueLuceneMaxMergeDocs;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Lucene 参数，如果不设置，则使用默认值  Integer.MAX_VALUE
		<br>该参数决定写入内存索引文档个数，到达该数目后就把该内存索引写入硬盘，生成一个新的索引segment文件，越大索引速度越快。
		<br>====================================================================================
	 */
	public void setPropertyValueLuceneMaxMergeDocs(
			int propertyValueLuceneMaxMergeDocs) {
		this.propertyValueLuceneMaxMergeDocs = propertyValueLuceneMaxMergeDocs;
	}
}
