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
}
