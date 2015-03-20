package com.sxjun.core.init;

import javax.servlet.http.HttpServlet;

import com.jfinal.kit.StrKit;
import com.sxjun.core.common.utils.Global;

import frame.base.core.util.ReflectUtil;

public class InitClazz{
	private static final long serialVersionUID = 4931248284969990014L;
	public static String initclazz = Global.getInitClazz();
	public static void init(){
		try {
			if(StrKit.notBlank(initclazz)){
				String[] initclazzs = initclazz.split(";");
				for(String clazz :initclazzs){
					if(StrKit.notBlank(clazz)){
						String[] classAndMethod = clazz.split(":");
						String className = classAndMethod[0];
						String methodName = null;
						if(classAndMethod.length>1)
							methodName = classAndMethod[1];
						else
							methodName = "init";
						// 反射调用
						ReflectUtil.invokeMethod(className, methodName);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
