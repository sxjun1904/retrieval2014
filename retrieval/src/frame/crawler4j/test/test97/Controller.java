package frame.crawler4j.test.test97;

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
            int numberOfCrawlers = 1;

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
    		config.setMaxDepthOfCrawling(2);// 深度，即从入口URL开始算，URL是第几层。如入口A是1，从A中找到了B，B中又有C，则B是2，C是3
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
            //controller.addSeed("http://www.huangpuqu.sh.cn/shhp/Default.htm");
            
//            controller.addSeed("http://www.ccost.com/index.asp");//工程造价咨询企业造价师管理
            controller.addSeed("http://www.jszj.com.cn/zaojia");//欢迎访问江苏省工程造价信息网！
//            controller.addSeed("http://www.jtzyzg.org.cn/");//欢迎光临交通职业资格网
//            controller.addSeed("http://www.njrsks.com/");//欢迎来到南京市人事考试网
//            controller.addSeed("http://www.jscons.gov.cn/jscons/default.aspx");//江苏建筑业网
//            controller.addSeed("http://www.jshrss.gov.cn/sy2011/index.html");//江苏人力资源和社会保障网
//            controller.addSeed("http://www.jsrczx.com/");//江苏人事人才公共服务网
//            controller.addSeed("http://www.jscz.gov.cn/pub/jscz/");//江苏省财政厅
//            controller.addSeed("http://www.jszjxh.com/w/portal/index");//江苏省工程造价管理协会
//            controller.addSeed("http://www.jsaec.org.cn/");//江苏省工程咨询协会
//            controller.addSeed("http://www.jsgsj.gov.cn/baweb/show/sj/index.jsp");//江苏省工商行政管理局
//            controller.addSeed("http://www.jszb.com.cn/jszb/");//江苏省建设工程招标网
//            controller.addSeed("http://www.jscin.gov.cn/web/kspxw/default.aspx");//江苏省建设考试培训网
//            controller.addSeed("http://www.jscin.gov.cn/web/zyzc/");//江苏省建设执业资格注册管理系统
//            controller.addSeed("http://www.jscd.gov.cn/");//江苏省交通运输厅门户网站
//            controller.addSeed("http://jszjycjy.com/w/portal/index");//江苏省造价从业人员远程教育网
//            controller.addSeed("http://www.jsqts.gov.cn/zjxx/");//江苏省质量技术监督局
//            controller.addSeed("http://www.jscin.gov.cn/web/");//江苏省住房和城乡建设厅
//            controller.addSeed("http://www.ccgp-jiangsu.gov.cn/pub/jszfcg/");//江苏政府采购网
//            controller.addSeed("http://www.njgs.gov.cn/");//南京工商局
//            controller.addSeed("http://www.njgcztbxh.com/");//南京建设工程招标投标协会
//            controller.addSeed("http://www.njszj.cn/ZJWEB/");//南京市工程造价信息网
//            controller.addSeed("http://ggzy.njzwfw.gov.cn/njggzy/");//南京市公共资源交易中心
//            controller.addSeed("http://www.njszj.cn/ZJWEB/");//南京市建设工程造价管理系统
//            controller.addSeed("http://www.njhrss.gov.cn/");//南京市人力资源和社会保障网
//            controller.addSeed("http://www.njzj.gov.cn/");//南京质监门户网站
//            controller.addSeed("http://www.njzz.gov.cn/");//南京专业技术职称资格网
//            controller.addSeed("http://www.jsgsj.gov.cn:58888/province/");//全国企业信用信息公示系统（江苏）
//            controller.addSeed("http://jzsgl.coc.gov.cn/archi/Portal/index.aspx");//一级建造师注册管理系统
//            controller.addSeed("http://zbszc.ctba.org.cn/");//招标师注册电子网络信息平台
//            controller.addSeed("http://www.cnaec.com.cn/default.aspx");//中国工程咨询网
//            controller.addSeed("http://www.ceca.org.cn/");//中国建设工程造价管理协会
//            controller.addSeed("http://www.cces.net.cn/guild/sites/tmxh/default.asp");//中国土木工程学会
//            controller.addSeed("http://www.sdpc.gov.cn/");//中华人民共和国国家发展和改革委员会
//            controller.addSeed("http://www.moc.gov.cn/");//中华人民共和国交通运输部
//            controller.addSeed("http://www.mohurd.gov.cn/wbdt/index.html");//中华人民共和国住房和城乡建设部
//            controller.addSeed("http://www.mohurd.gov.cn/");//中华人民共和国住房和城乡建设部
            
            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(MyCrawler.class, numberOfCrawlers);    
    }
}