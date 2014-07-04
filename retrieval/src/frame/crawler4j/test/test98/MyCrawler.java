package frame.crawler4j.test.test98;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import frame.crawler4j.crawler.Page;
import frame.crawler4j.crawler.WebCrawler;
import frame.crawler4j.parser.HtmlParseData;
import frame.crawler4j.url.WebURL;
 
public class MyCrawler extends WebCrawler {
 
    private final static Pattern FILTERS = Pattern
            .compile(".*(\\.(css|js|bmp|gif|jpe?g|ico"
                    + "|png|tiff?|mid|mp2|mp3|mp4"
                    + "|wav|avi|mov|mpeg|ram|m4v|pdf"
                    + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
 
    private final static String URL_PREFIX = "http://www.souche.com/pages/onsale/sale_car_list.html?";
    private final static Pattern URL_PARAMS_PATTERN = Pattern
            .compile("carbrand=brand-\\d+(&index=\\d+)?");
 
    private final static String CSV_PATH = "data/crawl/data.csv";
    private CsvWriter cw;
    private File csv;
 
    public MyCrawler() throws IOException {
        csv = new File(CSV_PATH);
 
        if (csv.isFile()) {
            csv.delete();
        }
 
        cw = new CsvWriter(new FileWriter(csv, true), ',');
        cw.write("title");
        cw.write("brand");
        cw.write("newPrice");
        cw.write("oldPrice");
        cw.write("mileage");
        cw.write("age");
        cw.write("stage");
        cw.endRecord();
        cw.close();
    }
 
    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        if (FILTERS.matcher(href).matches() || !href.startsWith(URL_PREFIX)) {
            return false;
        }
 
        String[] strs = href.split("\\?");
        if (strs.length < 2) {
            return false;
        }
 
        if (!URL_PARAMS_PATTERN.matcher(strs[1]).matches()) {
            return false;
        }
         
        return true;
    }
 
    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
 
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
 
            Document doc = Jsoup.parse(html);
            String brand = doc.select("div.choose_item").first().text();
 
            Elements contents = doc.select("div.list_content");
             
            if (contents.size() == 20 && !url.contains("index=")) {
                return;
            } else {
                System.out.println("URL: " + url);
            }
             
            for (Element c : contents) {
                Element info = c.select(".list_content_carInfo").first();
                String title = info.select("h1").first().text();
 
                Elements prices = info.select(".list_content_price div");
                String newPrice = prices.get(0).text();
                String oldPrice = prices.get(1).text();
 
                Elements others = info.select(".list_content_other div");
                String mileage = others.get(0).select("ins").first().text();
                String age = others.get(1).select("ins").first().text();
 
                String stage = "unknown";
                if (c.select("i.car_tag_zhijian").size() != 0) {
                    stage = c.select("i.car_tag_zhijian").text();
                } else if (c.select("i.car_tag_yushou").size() != 0) {
                    stage = "presell";
                }
 
                try {
                    cw = new CsvWriter(new FileWriter(csv, true), ',');
                    cw.write(title);
                    cw.write(brand);
                    cw.write(newPrice.replaceAll("[￥万]", ""));
                    cw.write(oldPrice.replaceAll("[￥万]", ""));
                    cw.write(mileage);
                    cw.write(age);
                    cw.write(stage);
                    cw.endRecord();
                    cw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
