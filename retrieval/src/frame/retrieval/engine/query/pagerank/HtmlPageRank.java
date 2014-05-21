package frame.retrieval.engine.query.pagerank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;


/**
 * pagerank算法实现
 * 
 * @author sxjun
 * 
 */
public class HtmlPageRank {

	/* 阀值 */
	public static double MAX = 0.00000000001;

	/* 阻尼系数 */
	public static double alpha = 0.85;

	public static String htmldoc = "D:\\workspace\\Test\\WebRoot\\htmldoc";

	public static Map<String, HtmlEntity> map = new HashMap<String, HtmlEntity>();

	public static List<HtmlEntity> list = new ArrayList<HtmlEntity>();

	public static double[] init;

	public static double[] pr;
	
	public static List<HtmlEntity> getHtmlPageRank(String[] urls) throws Exception{
		loadHtml(urls);
		pr = doPageRank();
		while (!(checkMax())) {
			System.arraycopy(pr, 0, init, 0, init.length);
			pr = doPageRank();
		}
		for (int i = 0; i < pr.length; i++) {
			HtmlEntity he=list.get(i);
			he.setPr(pr[i]);
		}
		
	/*	List<HtmlEntity> finalList=new ArrayList<HtmlEntity>();
		Collections.sort(list,new Comparator(){

			public int compare(Object o1, Object o2) {
				HtmlEntity h1=(HtmlEntity)o1;
				HtmlEntity h2=(HtmlEntity)o2;
				int em=0;
				if(h1.getPr()> h2.getPr()){
					em=-1;
				}else{
					em=1;
				}
				return em;
			}
			
		});*/
		
		for(HtmlEntity he:list){
			System.out.println(he.getPath()+" : "+he.getPr());
		}
		return list;
	}
	
	public static void loadHtml(String[] urls) throws Exception {
		int len = urls.length;
		init = new double[urls.length];
		String[][] htmls = new String[len][2];
		for(int i =0;i<len;i++){
			htmls[i][0] = urls[i];
			htmls[i][1] = HtmlGet.getContent(urls[i]);
		}
		loadHtml(htmls);
	}
	
	/**
	 * utl,html
	 * @param htmlfiles
	 * @throws Exception
	 */
	public static void loadHtml(String[][] htmlfiles) throws Exception {
		for (int i = 0; i < htmlfiles.length; i++) {
			String html = htmlfiles[i][1];
			HtmlEntity he = new HtmlEntity();
			he.setPath(htmlfiles[i][0]);
			he.setContent(html.toString());
			Parser parser = Parser.createParser(html.toString(), "gb2312");
			HtmlPage page = new HtmlPage(parser);
			parser.visitAllNodesWith(page);
			NodeList nodelist = page.getBody();
			nodelist = nodelist.extractAllNodesThatMatch(new TagNameFilter("A"), true);
			for (int j = 0; j < nodelist.size(); j++) {
				LinkTag outlink = (LinkTag) nodelist.elementAt(j);
				he.getOutLinks().add(outlink.getAttribute("href"));
			}

			map.put(he.getPath(), he);
			list.add(he);
			init[i] = 0.0;

		}
		for (int i = 0; i < list.size(); i++) {
			HtmlEntity he = list.get(i);
			List<String> outlink = he.getOutLinks();
			for (String ol : outlink) {
				HtmlEntity he0 = map.get(ol);
				if(he0!=null)
					he0.getInLinks().add(he.getPath());
			}
		}

	}

	/* pagerank步骤 */

	/**
	 * 加载文件夹下的网页文件，并且初始化pr值(即init数组)，计算每个网页的外链和内链
	 */
	public static void loadHtml() throws Exception {
		File file = new File(htmldoc);
		File[] htmlfiles = file.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				if (pathname.getPath().endsWith(".html")) {
					return true;
				}
				return false;
			}

		});
		init = new double[htmlfiles.length];
		for (int i = 0; i < htmlfiles.length; i++) {
			File f = htmlfiles[i];
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));
			String line = br.readLine();
			StringBuffer html = new StringBuffer();
			while (line != null) {
				line = br.readLine();
				html.append(line);
			}
			HtmlEntity he = new HtmlEntity();
			he.setPath(f.getAbsolutePath());
			he.setContent(html.toString());
			Parser parser = Parser.createParser(html.toString(), "gb2312");
			HtmlPage page = new HtmlPage(parser);
			parser.visitAllNodesWith(page);
			NodeList nodelist = page.getBody();
			nodelist = nodelist.extractAllNodesThatMatch(new TagNameFilter("A"), true);
			for (int j = 0; j < nodelist.size(); j++) {
				LinkTag outlink = (LinkTag) nodelist.elementAt(j);
				he.getOutLinks().add(outlink.getAttribute("href"));
			}

			map.put(he.getPath(), he);
			list.add(he);
			init[i] = 0.0;

		}
		for (int i = 0; i < list.size(); i++) {
			HtmlEntity he = list.get(i);
			List<String> outlink = he.getOutLinks();
			for (String ol : outlink) {
				HtmlEntity he0 = map.get(ol);
				he0.getInLinks().add(he.getPath());
			}
		}

	}
	
	/**
	 * 计算pagerank
	 * 
	 * @param init
	 * @param alpho
	 * @return
	 */
	private static double[] doPageRank() {
		double[] pr = new double[init.length];
		for (int i = 0; i < init.length; i++) {
			double temp = 0;
			HtmlEntity he0 = list.get(i);
			for (int j = 0; j < init.length; j++) {
				HtmlEntity he = list.get(j);
				// 计算对本页面链接相关总值
				if (i != j && he.getOutLinks().size() != 0
						&& he.getOutLinks().contains(he0.getPath())/*he0.getInLinks().contains(he.getPath())*/) {
					temp = temp + init[j] / he.getOutLinks().size();
				}

			}
			//经典的pr公式
			pr[i] = alpha + (1 - alpha) * temp;
		}
		return pr;
	}

	/**
	 * 判断前后两次的pr数组之间的差别是否大于我们定义的阀值 假如大于，那么返回false，继续迭代计算pr
	 * 
	 * @param pr
	 * @param init
	 * @param max
	 * @return
	 */
	private static boolean checkMax() {
		boolean flag = true;
		for (int i = 0; i < pr.length; i++) {
			if (Math.abs(pr[i] - init[i]) > MAX) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	
	

}

