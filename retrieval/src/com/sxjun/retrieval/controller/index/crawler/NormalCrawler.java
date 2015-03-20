package com.sxjun.retrieval.controller.index.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.http.Header;

import com.jfinal.kit.StrKit;
import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.retrieval.pojo.RCrawlerIndex;

import frame.base.core.util.StringClass;
import frame.crawler4j.crawler.Page;
import frame.crawler4j.crawler.WebCrawler;
import frame.crawler4j.parser.HtmlParseData;
import frame.crawler4j.url.WebURL;
import frame.retrieval.engine.RetrievalType.RDatabaseDefaultDocItemType;
import frame.retrieval.engine.RetrievalType.RDatabaseDocItemType;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RFacade;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.facade.IRIndexOperatorFacade;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.internal.RDocItem;
 
public class NormalCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    
    private RetrievalApplicationContext retrievalApplicationContext;
	private RCrawlerIndex rdI;
	private CommonService<RCrawlerIndex> commonService = new ServiceProxy<RCrawlerIndex>().getproxy();
	
    public NormalCrawler(RetrievalApplicationContext retrievalApplicationContext,RCrawlerIndex rdI) {
    	this.retrievalApplicationContext = retrievalApplicationContext;
		this.rdI = rdI;
	}

	/**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            //return !FILTERS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/");
            //return !FILTERS.matcher(href).matches() && href.startsWith("http://www.jszj.com.cn/zaojia");
            return !FILTERS.matcher(href).matches();
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {   
    		int crawlerWebCount = CustomCrawlerController.add();
            int docid = page.getWebURL().getDocid(); //这是程序定义的ID
    		String url = page.getWebURL().getURL(); //URL地址
    		String domain = page.getWebURL().getDomain(); //域名，如baidu.com
    		String path = page.getWebURL().getPath(); //路径，不包含URL参数
    		String subDomain = page.getWebURL().getSubDomain(); //子域名，如www,
    		String parentUrl = page.getWebURL().getParentUrl(); //父页面，即从哪个页面发现的该URL的
    		String anchor = page.getWebURL().getAnchor(); //锚，即HTML显示的信息，如<a href="***">锚</a>
            System.out.println("==========================================================");
            System.out.println("crawlerWebCount:"+crawlerWebCount);
//            System.out.println("docid: " + docid);
//            System.out.println("URL: " + url);
//            System.out.println("domain: " + domain);
//            System.out.println("path: " + path);
//            System.out.println("subDomain: " + subDomain);
//            System.out.println("parentUrl: " + parentUrl);
//            System.out.println("anchor: " + anchor);

            if (StrKit.isBlank(anchor))
            	return;
            
            RFacade facade=retrievalApplicationContext.getFacade();
            NormalIndexDocument normalIndexDocument=facade.createNormalIndexDocument(false);
            RDocItem docItem1=new RDocItem();
            
            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();

                    //System.out.println("Text length: " + text.length());
                    //System.out.println("Text: " + text.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", ""));
                    //System.out.println("Html length: " + html.length());
                    //System.out.println("Html: " + html);
                    //System.out.println("Number of outgoing links: " + links.size());
                    
                    
					docItem1.setContent(rdI.getKeyField());
					docItem1.setName(StringClass.getString(RDatabaseDocItemType._DK));
					normalIndexDocument.addKeyWord(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(rdI.getName());
					docItem1.setName(StringClass.getString(RDatabaseDocItemType._DT));
					normalIndexDocument.addKeyWord(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(docid);
					docItem1.setName(StringClass.getString(RDatabaseDocItemType._DID));
					normalIndexDocument.addKeyWord(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(anchor);
					docItem1.setName(StringClass.getString(RDatabaseDefaultDocItemType._TITLE));
					normalIndexDocument.addContent(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(text.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", ""));
					docItem1.setName(StringClass.getString(RDatabaseDefaultDocItemType._RESUME));
					normalIndexDocument.addContent(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(url);
					docItem1.setName(StringClass.getString(RDatabaseDefaultDocItemType.PAGE_URL));
					normalIndexDocument.addKeyWord(docItem1);
					
					docItem1=new RDocItem();
					docItem1.setContent(url);
					docItem1.setName(StringClass.getString(RDatabaseDefaultDocItemType.CREATETIME));
					normalIndexDocument.addKeyWord(docItem1);
					
					normalIndexDocument.setIndexInfoType(rdI.getIndexCategory().getIndexInfoType());
					normalIndexDocument.setIndexPathType(rdI.getIndexCategory().getIndexPath());
					normalIndexDocument.setId(rdI.getKeyField());
            }
            Header[] responseHeaders = page.getFetchResponseHeaders(); //页面服务器返回的HTML头信息
    		if (responseHeaders != null) {
    			//System.out.println("Response headers:");
    			for (Header header : responseHeaders) {
    				//System.out.println("\t" + header.getName() + ": " + header.getValue());
    				if("Date".equals(header.getName())){
    					try {
							String dateString = header.getValue();
							SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'",Locale.US);
							Date date = sdf.parse(dateString);
							sdf = new SimpleDateFormat("yyyyMMddHHmmss");
							String numDateStr = sdf.format(date);
							//System.out.println("Date Num:"+numDateStr);
							
							docItem1=new RDocItem();
							docItem1.setContent(numDateStr);
							docItem1.setName(StringClass.getString(RDatabaseDefaultDocItemType.CREATETIME));
							normalIndexDocument.addNumberProperty(docItem1);
						} catch (ParseException e) {
							e.printStackTrace();
						}
    				}
    			}
    		}
    		String indexPathType = StringClass.getFormatPath(normalIndexDocument.getIndexPathType());
			indexPathType = ApplicationContext.initIndexSet(indexPathType);
			normalIndexDocument.setIndexPathType(indexPathType);
			IRDocOperatorFacade docOperatorFacade=facade.createDocOperatorFacade();
			docOperatorFacade.create(normalIndexDocument);
			if(crawlerWebCount%500==0){
				IRIndexOperatorFacade dbIndexOperatorHelper=retrievalApplicationContext.getFacade().createIndexOperatorFacade(normalIndexDocument.getIndexPathType());
				dbIndexOperatorHelper.optimize();
			}
            System.out.println("==========================================================");
    }
    
    public void onBeforeExit()
	{
		System.out.println("crawler is stopped,it's time to optimize the index!");
		String indexPathType = StringClass.getFormatPath(rdI.getIndexCategory().getIndexPath());
		indexPathType = ApplicationContext.initIndexSet(indexPathType);
		IRIndexOperatorFacade dbIndexOperatorHelper=retrievalApplicationContext.getFacade().createIndexOperatorFacade(indexPathType);
		dbIndexOperatorHelper.optimize();
		rdI.setIsInit("1");
		rdI.setMediacyTime("");
		commonService.put(RCrawlerIndex.class, rdI.getId(), rdI);
	}
}