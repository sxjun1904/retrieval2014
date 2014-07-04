package frame.crawler4j.test.test98;

import frame.crawler4j.crawler.CrawlConfig;
import frame.crawler4j.crawler.CrawlController;
import frame.crawler4j.fetcher.PageFetcher;
import frame.crawler4j.robotstxt.RobotstxtConfig;
import frame.crawler4j.robotstxt.RobotstxtServer;
 /*
  * http://www.2cto.com/kf/201403/283835.html
  */
public class Controller {
    public static void main(String[] args) throws Exception {
            String crawlStorageFolder = "data/crawl/root";
            int numberOfCrawlers = 7;
 
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
 
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
            /*controller.addSeed("http://www.ics.uci.edu/~welling/");
            controller.addSeed("http://www.ics.uci.edu/~lopes/");
            controller.addSeed("http://www.ics.uci.edu/");*/
            controller.addSeed("http://www.souche.com/pages/onsale/sale_car_list.html");
            
            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(MyCrawler.class, numberOfCrawlers);    
    }
}