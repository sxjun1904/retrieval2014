
package org.apache.activemq.web;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitConnServlet extends HttpServlet {
	private static final long serialVersionUID = 1508882785073011332L;

	public InitConnServlet() {  
        super();  
    }  
	
	public void init() throws ServletException {  
		InitConnThread init = new InitConnThread("mqtt");
		//Thread t = new Thread(init); 
		//t.start();
    }  

}
