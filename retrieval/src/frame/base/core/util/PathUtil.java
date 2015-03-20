package frame.base.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PathUtil {
	public static String path;

	  private static String getWebDeploypath(String classPath)
	  {
	    int startIndex = classPath.indexOf("file:");
	    int index = classPath.indexOf("WEB-INF");
	    String deployWarPath = classPath.substring(startIndex + "file:".length() + 1, index);

	    if ((System.getProperty("os.name").indexOf("Linux") != -1) && (!deployWarPath.startsWith(File.separator))) {
	      deployWarPath = File.separator + deployWarPath;
	    }

	    return deployWarPath;
	  }

	  private static String getServicePath(String classPath)
	  {
	    int startIndex = classPath.indexOf("file:");
	    int index = classPath.indexOf("lib");
	    String deployWarPath = classPath.substring(startIndex + "file:".length() + 1, index);

	    if ((System.getProperty("os.name").indexOf("Linux") != -1) && (!deployWarPath.startsWith(File.separator))) {
	      deployWarPath = File.separator + deployWarPath;
	    }

	    return deployWarPath + File.separator + "lib" + File.separator;
	  }

	  public static String detectWebRootPath() {
	    try {
	      String path = PathUtil.class.getResource("/").getFile();
	      return new File(path).getParentFile().getParentFile().getCanonicalPath();
	    } catch (IOException e) {
	    	throw new RuntimeException(e);
	    }
	  }
	  
	  public static String getPath()
	  {
		  return getPath(null);
	  }

	  public static String getPath(String programType)
	  {
	    if (path == null)
	    {
	      URL url = PathUtil.class.getResource("PathUtil.class");
	      String classPath = url.toString();

	      if ((programType == null) || (programType.trim().length() == 0) || (programType.equals("web"))) {
	        path = getWebDeploypath(classPath);
	      }
	      else if (programType.equals("service")) {
	        path = getServicePath(classPath);
	      }
	      else
	      {
	        path = "src" + File.separator;
	      }
	    }
	    return path;
	  }
	  
	public static String getClassPath(){
		return System.getProperty("user.dir");
	}
	
	public static String getDefaultIndexPath(){ 
		String classPath = getPath();
		File file = new File(classPath).getParentFile();
		return file.getPath()+File.separator+"index";
	}
	
	public static String getDefaultTempImgPath(){ 
		String classPath = getPath();
		File file = new File(classPath).getParentFile();
		return file.getPath()+File.separator+"img";
	}
	
	public static String getDefaultTempCustomPath(){ 
		String classPath = getPath();
		File file = new File(classPath).getParentFile();
		return file.getPath()+File.separator+"custom";
	}
	
	public static String getDefaultRedisPath(){ 
		String classPath = getPath();
		File file = new File(classPath).getParentFile();
		return file.getPath()+File.separator+"redis-2.4.5"+File.separator+"64bit"+File.separator+"redis-server.exe";
	}
	
	public static String getDefaultMoveFileAfterIndexFolderPath(){ 
		String classPath = getPath();
		File file = new File(classPath).getParentFile();
		return file.getPath()+File.separator+"filebak";
	}
	
	public static void main(String[] args) {
		System.out.println(getDefaultRedisPath());
	}
}
