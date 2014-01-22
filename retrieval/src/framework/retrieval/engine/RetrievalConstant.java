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
package framework.retrieval.engine;

/**
 * 索引常量
 * @author 
 *
 */
public class RetrievalConstant {

	private RetrievalConstant() {

	}

	/**
	 * 索引保存所有字段名时使用的分隔符
	 */
	public static final String DEFAULT_INDEX_FIELD_NAME_SPLIT = "&&";

	/**
	 * 按创建时间排序的排序字段名称
	 */
	public static final String DEFAULT_INDEX_QUERY_SORT_CREATETIME_NAME = String
			.valueOf(RetrievalType.RDocItemSpecialName._IC);

	/**
	 * 默认最大索引文件大小
	 */
	public static final long DEFAULT_INDEX_MAX_FILE_SZIE = 3 * 1024 * 1024;

	/**
	 * 一次检测锁定的等待时间间隔
	 */
	public static final long INDEX_WRITER_CREATE_WAIT_TIME=100;
	
	/**
	 * 正常方式检测是否锁定的次数
	 */
	public static final int INDEX_WRITER_CREATE_CHECK_LOCKED_TIMES=100;

	/**
	 * 达到最大正常方式检测是否锁定的次数，转入检测文件变化的每次检测时间间隔
	 */
	public static final long INDEX_WRITER_CREATE_CHECK_FILE_WAIT_TIME=INDEX_WRITER_CREATE_WAIT_TIME*150;
	
	/**
	 * 达到最大正常方式检测是否锁定的次数，转入检测文件变化的每次的最大次数
	 */
	public static final int INDEX_WRITER_CREATE_CHECK_FILE_TIMES=6;

	/**
	 * 检测锁定等待的最大超时时间
	 */
	public static final long INDEX_WRITER_CREATE_WAIT_TIME_OUT=INDEX_WRITER_CREATE_WAIT_TIME*3000;

}