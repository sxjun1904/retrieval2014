package framework.base.snoic.base.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {
	 public static Object getObjByClassName(String classname)
	  {
	    Object obj = null;
	    if (classname != null) {
	      try {
	        Class a = Class.forName(classname);
	        obj = a.newInstance();
	      }
	      catch (InstantiationException e) {
	        e.printStackTrace();
	      }
	      catch (IllegalAccessException e) {
	        e.printStackTrace();
	      }
	      catch (ClassNotFoundException e) {
	        e.printStackTrace();
	      }
	    }
	    return obj;
	  }

	  public static Object getObjByClassNameAndParameter(String classname, Object[] parameter)
	  {
	    Object obj = null;
	    if (classname != null) {
	      try {
	        Class a = Class.forName(classname);

	        Constructor con = a.getConstructor(getParameterClass(parameter));
	        obj = con.newInstance(parameter);
	      }
	      catch (IllegalArgumentException e) {
	        e.printStackTrace();
	      }
	      catch (InstantiationException e) {
	        e.printStackTrace();
	      }
	      catch (IllegalAccessException e) {
	        e.printStackTrace();
	      }
	      catch (InvocationTargetException e) {
	        e.printStackTrace();
	      }
	      catch (SecurityException e) {
	        e.printStackTrace();
	      }
	      catch (NoSuchMethodException e) {
	        e.printStackTrace();
	      }
	      catch (ClassNotFoundException e) {
	        e.printStackTrace();
	      }
	    }
	    return obj;
	  }

	  public static Object invokeMethod(String className, String methodName)
	  {
	    return invokeMethodWithObjHasParame(className, getObjByClassName(className), methodName, new Object[0]);
	  }

	  public static Object invokeMethodHasParame(String className, String methodName, Object[] parameter)
	  {
	    return invokeMethodWithObjHasParame(className, getObjByClassName(className), methodName, parameter);
	  }

	  public static Object invokeMethodWithObj(String className, Object obj, String methodName)
	  {
	    return invokeMethodWithObjHasParame(className, obj, methodName, new Object[0]);
	  }

	  public static Object invokeMethodWithObjHasParame(String className, Object obj, String methodName, Object[] parameter)
	  {
	    return invokeMethodWithObjHasSpecialParame(className, obj, methodName, parameter, getParameterClass(parameter));
	  }

	  private static Class[] getParameterClass(Object[] parameter)
	  {
	    Class[] methodParameters = (Class[])null;
	    if ((parameter != null) && (parameter.length > 0)) {
	      methodParameters = new Class[parameter.length];
	      for (int i = 0; i < parameter.length; i++) {
	        methodParameters[i] = parameter[i].getClass();
	      }
	    }
	    return methodParameters;
	  }

	  public static Object invokeMethodWithObjHasSpecialParame(String className, Object obj, String methodName, Object[] parameter, Class[] methodParameters)
	  {
	    Object object = null;
	    try {
	      Method method = Class.forName(className).getMethod(methodName.trim(), methodParameters);
	      object = method.invoke(obj, parameter);
	    }
	    catch (IllegalArgumentException e) {
	      e.printStackTrace();
	    }
	    catch (IllegalAccessException e) {
	      e.printStackTrace();
	    }
	    catch (InvocationTargetException e) {
	      e.printStackTrace();
	    }
	    catch (SecurityException e) {
	      e.printStackTrace();
	    }
	    catch (NoSuchMethodException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    return object;
	  }

	  public static List<String> getMethodMsg(String className)
	  {
	    List retValue = new ArrayList();
	    try
	    {
	      Class myClass = Class.forName(className);
	      Method[] m = myClass.getDeclaredMethods();
	      for (int i = 0; i < m.length; i++) {
	        String meth = m[i].toString();

	        meth = meth.substring(meth.indexOf("(") + 1, meth.indexOf(")"));

	        String ret = meth + ";" + m[i].getName() + ";" + m[i].getReturnType();
	        retValue.add(ret);
	      }
	      return retValue;
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    return retValue;
	  }
}
