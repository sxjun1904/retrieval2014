<%@ page contentType="text/html; charset=gb2312" language="java" import="java.io.*" errorPage="" %> 
 <html> 
 <head> 
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312"> 
 <title>Untitled Document</title> 
 </head> 

 <body> 
当前WEB应用的物理路径：<%=application.getRealPath("/")%><BR> 
当前你求请的JSP文件的物理路径：<%=application.getRealPath(request.getRequestURI())%><BR> 
 <% 
 String path=application.getRealPath(request.getRequestURI()); 
 String dir=new File(path).getParent(); 
 out.println("当前JSP文件所在目录的物理路径"+dir+"</br>"); 
 String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1); 
 out.println("web URL 路径:"+realPath1); 
 %> 
 </body> 
 </html> 