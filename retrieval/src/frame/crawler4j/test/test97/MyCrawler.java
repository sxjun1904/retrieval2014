package frame.crawler4j.test.test97;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;

import frame.crawler4j.crawler.Page;
import frame.crawler4j.crawler.WebCrawler;
import frame.crawler4j.parser.HtmlParseData;
import frame.crawler4j.url.WebURL;
 
public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            //return !FILTERS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/");
            return !FILTERS.matcher(href).matches() && href.startsWith("http://www.huangpuqu.sh.cn/");
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {          
            int docid = page.getWebURL().getDocid(); //这是程序定义的ID
    		String url = page.getWebURL().getURL(); //URL地址
    		String domain = page.getWebURL().getDomain(); //域名，如baidu.com
    		String path = page.getWebURL().getPath(); //路径，不包含URL参数
    		String subDomain = page.getWebURL().getSubDomain(); //子域名，如www,
    		String parentUrl = page.getWebURL().getParentUrl(); //父页面，即从哪个页面发现的该URL的
    		String anchor = page.getWebURL().getAnchor(); //锚，即HTML显示的信息，如<a href="***">锚</a>
            System.out.println("==========================================================");
            System.out.println("docid: " + docid);
            System.out.println("URL: " + url);
            System.out.println("domain: " + domain);
            System.out.println("path: " + path);
            System.out.println("subDomain: " + subDomain);
            System.out.println("parentUrl: " + parentUrl);
            System.out.println("anchor: " + anchor);

            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();

                    System.out.println("Text length: " + text.length());
                    //System.out.println("Text: " + text);
                    System.out.println("Html length: " + html.length());
                    //System.out.println("Html: " + html);
                    System.out.println("Number of outgoing links: " + links.size());
            }
            Header[] responseHeaders = page.getFetchResponseHeaders(); //页面服务器返回的HTML头信息
    		if (responseHeaders != null) {
    			System.out.println("Response headers:");
    			for (Header header : responseHeaders) {
    				System.out.println("\t" + header.getName() + ": " + header.getValue());
    			}
    		}
            System.out.println("==========================================================");
    }
}