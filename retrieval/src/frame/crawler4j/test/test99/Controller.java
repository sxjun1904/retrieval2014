package frame.crawler4j.test.test99;

import frame.crawler4j.crawler.CrawlConfig;
import frame.crawler4j.crawler.CrawlController;
import frame.crawler4j.fetcher.PageFetcher;
import frame.crawler4j.robotstxt.RobotstxtConfig;
import frame.crawler4j.robotstxt.RobotstxtServer;
 /*
  * https://code.google.com/p/crawler4j/
  */
public class Controller {
    public static void main(String[] args) throws Exception {
            String crawlStorageFolder = "/data/crawl/root";
            int numberOfCrawlers = 7;

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
    		//config.setMaxDepthOfCrawling(2);// 深度，即从入口URL开始算，URL是第几层。如入口A是1，从A中找到了B，B中又有C，则B是2，C是3
    		//config.setMaxPagesToFetch(1000);//最多爬取多少个页面
    		config.setResumableCrawling(false);

            /*
             * Instantiate the controller for this crawl.
             */
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
            //controller.addSeed("http://www.ics.uci.edu/~welling/");
            //controller.addSeed("http://www.ics.uci.edu/~lopes/");
            //controller.addSeed("http://www.ics.uci.edu/");
//            controller.addSeed("http://beta.yandu.gov.cn/Front/default.aspx");
            controller.addSeed("http://www.jszj.com.cn/zaojia/default.aspx");

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(MyCrawler.class, numberOfCrawlers);    
    }
}