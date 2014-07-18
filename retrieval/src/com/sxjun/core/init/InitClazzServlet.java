package com.sxjun.core.init;

import javax.servlet.http.HttpServlet;

import com.jfinal.kit.StringKit;
import com.sxjun.retrieval.common.Global;

import frame.base.core.util.ReflectUtil;

public class InitClazzServlet extends HttpServlet {
	private static final long serialVersionUID = 4931248284969990014L;
	public static String initclazz = Global.getInitClazz();
	public InitClazzServlet() {  
        super();  
    }  
	public void init(){
		try {
			if(StringKit.notBlank(initclazz)){
				String[] initclazzs = initclazz.split(";");
				for(String clazz :initclazzs){
					if(StringKit.notBlank(clazz)){
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
