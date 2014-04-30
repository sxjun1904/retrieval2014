package com.sxjun.system.pojo;

import java.io.Serializable;

public class BasePojo implements Serializable{
	private static final long serialVersionUID = -7868855862441569281L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 获取主键
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置主键
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
}
