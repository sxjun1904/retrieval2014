package frame.retrieval.test.pagerank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.logging.Log;

import com.mysql.jdbc.log.LogFactory;

public class HtmlGet {
    public static String getContent(String strUrl) {
       try {
           URL url = new URL(strUrl);
           BufferedReader br = new BufferedReader(new InputStreamReader(url
           .openStream(), "utf-8"));
           String s = "";
           StringBuffer sb = new StringBuffer("");
           while ((s = br.readLine()) != null) {
              sb.append(s + "\r\n");
           }
           br.close();
           return sb.toString();
           // return sb.toString();
       } catch (Exception e) {
           return null;
       }
    }
    public static void main(String[] args) throws IOException {
       String url="http://hi.baidu.com/haifengjava";
       System.out.println(HtmlGet.getContent(url));
    }
	}