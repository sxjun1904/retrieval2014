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

/**
 * 内置常量
 * 
 * @author 
 *
 */
class RetrievalLoaderConstant {
	private RetrievalLoaderConstant(){
		
	}
	
	/**
	 * Lucene参数
	 * 
	 * 单个字段最大长度
	 * 
	 */
	static final int DEFAULT_LUCENE_PARAM_MAX_FIELD_LENGTH=10000;

	/**
	 * Lucene参数
	 * 
	 * 控制用于buffer索引文档的内存上限，如果buffer的索引文档个数到达该上限就写入硬盘，越大索引速度越快。
	 * 
	 */
	static final double DEFAULT_LUCENE_PARAM_RAM_BUFFER_SIZE_MB=16;

	/**
	 * Lucene参数
	 * 
	 * 		和LUCENE_PARAM_RAM_BUFFER_SIZE_MB这两个参数是可以一起使用的，一起使用时只要有一个触发条件满足就写入硬盘，生成一个新的索引segment文件
	 * 
	 */
	static final int DEFAULT_LUCENE_PARAM_MAX_BUFFERED_DOCS=3000;
	
	/**
	 * Lucene参数
	 * 
	 * 		MergeFactor 这个参数就是控制当硬盘中有多少个子索引segments，MergeFactor这个不能设置太大，
	 * 		特别是当MaxBufferedDocs比较小时（segment 越多），否则会导致open too many files错误，甚至导致虚拟机外面出错。
	 */
	static final int DEFAULT_LUCENE_PARAM_MERGE_FACTOR=100;
	
	/**
	 * Lucene参数
	 * 		该参数决定写入内存索引文档个数，到达该数目后就把该内存索引写入硬盘，生成一个新的索引segment文件，越大索引速度越快。
	 */
	static final int DEFAULT_LUCENE_PARAM_MAX_MERGE_DOCS=Integer.MAX_VALUE;
	

	/**
	 * Lucene参数
	 * 
	 * 	Lucene的版本号
	 */
	static final Version DEFAULT_LUCENE_PARAM_VERSION=Version.LUCENE_30;
	
	/**
	 * 
	 * 批量创建文件索引时每页最大的文件索引文档数量
	 */
	static final int DEFAULT_INDEX_MAX_FILE_DOCUMENT_PAGE_SIZE=20;
	
	/**
	 * 建索引允许的最大文件大小 2M
	 */
	static final long DEFAULT_INDEX_MAX_INDEX_FILE_SIZE=3*1024*1024;
	
	/**
	 * 
	 * 批量创建数据库索引时，从数据库中读取的每页数据最大记录数
	 */
	static final int DEFAULT_INDEX_MAX_DB_DOCUMENT_PAGE_SIZE=2000;
	
	/**
	 * 解析文本文件内容时使用的默认字符集
	 */
	static final String DEFAULT_INDEX_DEFAULT_CHARSET="utf-8";
	
	
	/**
	 * ==========================================================
	 * 					设置索引检索执行参数
	 * ==========================================================
	 */

	/**
	 * 查询返回的最大结果集
	 */
	static final int DEFAULT_QUERY_RESULT_TOP_DOCS_NUM=3000;
}
