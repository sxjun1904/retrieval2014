package com.sxjun.retrieval.controller.index.crawler;

import java.io.File;
import java.util.Date;

import com.jfinal.kit.StrKit;
import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.retrieval.pojo.RCrawlerIndex;

import frame.base.core.util.DateTime;
import frame.base.core.util.PathUtil;
import frame.base.core.util.StringClass;
import frame.crawler4j.crawler.CrawlConfig;
import frame.crawler4j.crawler.CrawlController;
import frame.crawler4j.fetcher.PageFetcher;
import frame.crawler4j.robotstxt.RobotstxtConfig;
import frame.crawler4j.robotstxt.RobotstxtServer;
import frame.retrieval.engine.context.RetrievalApplicationContext;
 /*
  * https://code.google.com/p/crawler4j/
  */
public class CustomCrawlerController {
	private RetrievalApplicationContext retrievalApplicationContext;
	private RCrawlerIndex rdI;
	private NormalCrawler normalCrawler ;
	private static int crawlerWebCount = 0;
	private CommonService<RCrawlerIndex> commonService = new ServiceProxy<RCrawlerIndex>().getproxy();
	
	public CustomCrawlerController(RetrievalApplicationContext retrievalApplicationContext,RCrawlerIndex rdI){
		this.retrievalApplicationContext = retrievalApplicationContext;
		this.rdI = rdI;
		normalCrawler = new NormalCrawler(retrievalApplicationContext,rdI);
	}
	
	public void indexAll(){
		try {
			String crawlStorageFolder = PathUtil.getWorkspaceParentPath()+File.separator+"data"+File.separator+"crawler"+File.separator+StringClass.getFormatPath(rdI.getIndexCategory().getIndexPath());
			int numberOfCrawlers = Integer.valueOf(rdI.getNumberOfCrawlers());
			int maxDepthOfCrawling = Integer.valueOf(rdI.getMaxDepthOfCrawling());
			CrawlConfig config = new CrawlConfig();
			config.setCrawlStorageFolder(crawlStorageFolder);
			config.setMaxDepthOfCrawling(maxDepthOfCrawling);
			//config.setMaxPagesToFetch(1000);//最多爬取多少个页面
			config.setResumableCrawling("1".equals(rdI.getIndexOperatorType())?true:false);
			
			 /*
			 * Instantiate the controller for this crawl.
			 */
			PageFetcher pageFetcher = new PageFetcher(config);
			RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
			RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
			CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
			rdI.setIsInit("2");
			rdI.setMediacyTime(new DateTime().parseString(new Date(), null));
			commonService.put(RCrawlerIndex.class, rdI.getId(), rdI);
			
			String [] urls = rdI.getUrl().split(";");
			for(String url : urls)
				if(StrKit.notBlank(url))
					controller.addSeed(url);
			 /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(normalCrawler, numberOfCrawlers,true);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	public synchronized static int add(){
		return ++crawlerWebCount; 
	}
	
}