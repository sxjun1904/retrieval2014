package com.sxjun.retrieval.constant;

import java.io.Serializable;

public class DefaultConstant {
	
	public enum DatabaseType implements Serializable {
		/**
		 * MySql数据库
		 */
		MYSQL("0"),
		/**
		 * Oracle数据库
		 */
		ORACLE("1"),
		/**
		 * SqlServer数据库
		 */
		SQLSERVER("2");
		private final String value;
		DatabaseType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	public enum FieldType implements Serializable {
		/**
		 * MySql数据库
		 */
		BOLOB("0"),
		/**
		 * Oracle数据库
		 */
		CLOB("1"),
		/**
		 * SqlServer数据库
		 */
		RMHTML("2");
		private final String value;
		FieldType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	public enum ItemType implements Serializable {
		/**
		 * 不分词
		 */
		KEYWORD("0"),
		/**
		 * 不分词
		 */
		DATE("1"),
		/**
		 * 不分词
		 */
		NUMBER("2"),
		/**
		 * 分词
		 */
		PROPERTY("3"),
		/**
		 * 分词
		 */
		CONTENT("4");
		private final String value;
		ItemType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	public enum IndexPathType implements Serializable {
		/**
		 * 数据库
		 */
		DB("0"),
		/**
		 * 文件
		 */
		FILE("1"),
		/**
		 * 图片
		 */
		IMAGE("2");
		private final String value;
		IndexPathType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
}
