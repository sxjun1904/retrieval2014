/**
 * code generation
 */
package com.sxjun.system.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sxjun.retrieval.controller.BaseController;
import com.sxjun.system.pojo.User;

import frame.base.core.util.MD5Util;

/**
 * 用户管理Controller
 * @author sxjun
 * @version 2014-07-05
 */
public class UserController extends BaseController<User> {

	public void list() {
		list(User.class);
	}
	
	public void form(){
		User user = new User();
		setAttr("user", user);
		form(User.class);
	}
	
	public void save(){
		User user = getModel(User.class);
		user.setPassword(MD5Util.GetMD5Code(user.getPassword()));
		save(user);
	}
	
	public void delete(){
		delete(User.class);
	}

	public List<User> getUserList(){
		return null;
	}
}
