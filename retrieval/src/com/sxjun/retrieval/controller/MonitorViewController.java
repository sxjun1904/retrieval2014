/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;

import org.apache.log4j.Logger;
import org.quartz.Trigger;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.common.SQLUtil;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.CacheView;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.DstgView;
import com.sxjun.retrieval.pojo.IsInitView;
import com.sxjun.retrieval.pojo.RDatabaseIndex;
import com.sxjun.retrieval.pojo.TriggerView;

import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;

/**
 * 数据监控Controller
 * @author sxjun
 * @version 2014-07-10
 */
public class MonitorViewController extends Controller {
	private static final Logger logger = Logger.getLogger(MonitorViewController.class);
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();
	CommonService<Database> dsCommonService = new ServiceProxy<Database>().getproxy();
	/**
	 * 展示触发器
	 */
	public void listTrigger() {
		List<TriggerView> list = getTriggerViewList();
		setAttr("triggerView",list);
		render("triggerViewList.jsp");
	}
	/**
	 * 展示状态
	 */
	public void listIsInit() {
		List<RDatabaseIndex> list = commonService.getObjs(RDatabaseIndex.class);
		List<IsInitView> isInitViewList = new ArrayList<IsInitView>();
		for(RDatabaseIndex rdI:list){
			IsInitView isInitView = new IsInitView();
			isInitView.setDatabaseName(rdI.getDatabase().getDatabaseName());
			isInitView.setIndexInfoType(rdI.getIndexCategory().getIndexInfoType());
			isInitView.setIsInit(rdI.getIsInit());
			isInitView.setMediacyTime(rdI.getMediacyTime());
			isInitView.setTableName(rdI.getTableName());
			String info = "正常";
			if(StrKit.notBlank(rdI.getMediacyTime())){
				Date date = new DateTime().parseDate(rdI.getMediacyTime(), null);
				long diff = new Date().getTime() - date.getTime();
				long minutes = diff / (1000 * 60);
				if(10<minutes&&minutes<20)
					info = "基本正常,继续观察";
				else if(minutes<30&&minutes>=20)
					info = "可能存在异常,请检查";
				else if(minutes>=30)
					info = "存在异常,请检查";
			}
			isInitView.setInfo(info);
			isInitViewList.add(isInitView);
		}
		setAttr("isInitView",isInitViewList);
		render("isInitViewList.jsp");
	}
	/**
	 * 展示触发器
	 */
	public void listDSTG() {
		List<Database> list = dsCommonService.getObjs(Database.class);
		List<DstgView> dstgViewList = new ArrayList<DstgView>();
		for(Database db : list){
			
			RDatabaseType type = DictUtils.changeToRDatabaseType(db.getDatabaseType());
			String url = JdbcUtil.getConnectionURL(type, db.getIp(), db.getPort(), db.getDatabaseName());
			Connection conn =JdbcUtil.getConnection(type, url, db.getUser(), db.getPassword());
			String triggerlike = SQLUtil.getTriggerLike(type, db.getDatabaseName());
			List<Map<String,String>> result = (List<Map<String, String>>) JdbcUtil.getMapList(conn, triggerlike, true);
			if(result!=null)
				for(Map<String,String> map :result){
					DstgView dstgView= new DstgView();
					dstgView.setId(db.getId());
					dstgView.setDatabasename(db.getDatabaseName());
					dstgView.setTriggername(map.get("trigger_name"));
					dstgViewList.add(dstgView);
				}
		}
		setAttr("dstgView",dstgViewList);
		render("dstgViewList.jsp");
	}
	
	/**
	 * 删除触发器
	 */
	public void deleteDstg(){
		String trigger = getPara();
		String id = getPara("id");
		Database db = dsCommonService.get(Database.class, id);
		RDatabaseType type = DictUtils.changeToRDatabaseType(db.getDatabaseType());
		String url = JdbcUtil.getConnectionURL(type, db.getIp(), db.getPort(), db.getDatabaseName());
		Connection conn =JdbcUtil.getConnection(type, url, db.getUser(), db.getPassword());
		String sql = SQLUtil.getDeleteTriggerSql(type,trigger);
		JdbcUtil.executeSql(conn, sql, true);
		listDSTG();
	}
	
	/**
	 * 获取触发器
	 * @return
	 */
	public List<TriggerView> getTriggerViewList(){
		JustBaseSchedulerManage jsm = new JustBaseSchedulerManage();
		List<Trigger> tgList = jsm.getTriggers();
		List<TriggerView> tgviewList = new ArrayList();
		for (Trigger tg : tgList) {
			TriggerView tgview = new TriggerView();
			tgview.setClassSimpleName(tg.getClass().getSimpleName());
			tgview.setFullJobName(tg.getFullJobName());
			tgview.setFullName(tg.getFullName());
			tgview.setJobName(tg.getJobName());
			tgview.setId(tg.getFireInstanceId());
			tgview.setName(tg.getName());
			tgview.setNextFireTime(tg.getNextFireTime()!=null?new DateTime().parseString(tg.getNextFireTime(),null):"");
			tgview.setStartTime(new DateTime().parseString(tg.getStartTime(),null));
			tgviewList.add(tgview);
		}
		return tgviewList;
	}
	
	/**
	 * 展示缓存
	 */
	public void listCache(){
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] cacheNames = CacheKit.getCacheManager().getCacheNames();
		List<CacheView> cacheList = new ArrayList<CacheView>();
		for(String cacheName : cacheNames){
			CacheView  cacheView = new CacheView();
			Cache cache = CacheKit.getCacheManager().getCache(cacheName);
			cacheView.setCacheName(cacheName);
			//缓存元素集合  
	        logger.info("-----------------------"+cacheName+":缓存元素统计数据---------------------------------"); 
			List keys = cache.getKeys();  
	        for (Object key : keys) {  
	            Element ele = cache.get(key);  
	            logger.info("内容: " + ele.getValue());  
	            cacheView.setValue(ele.getValue().toString());
	            logger.info("创建时间: " + sf.format(ele.getCreationTime()));  
	            cacheView.setCreationTime(sf.format(ele.getCreationTime()));
	            logger.info("最后访问时间: " + sf.format(ele.getLastAccessTime()));  
	            cacheView.setLastAccessTime(sf.format(ele.getLastAccessTime()));
	            logger.info("过期时间: " + sf.format(ele.getExpirationTime()));  
	            cacheView.setExpirationTime(sf.format(ele.getExpirationTime()));
	            logger.info("最后更新时间: " + sf.format(ele.getLastUpdateTime()));  
	            cacheView.setLastUpdateTime(sf.format(ele.getLastUpdateTime()));
	            logger.info("命中次数: " + ele.getHitCount());  
	            cacheView.setHitCount(Long.toString(ele.getHitCount()));
	            logger.info("存活时间: " + ele.getTimeToLive() + "ms");  
	            cacheView.setTimeToLive(ele.getTimeToLive() + "ms");
	            logger.info("空闲时间: " + ele.getTimeToIdle() + "ms");  
	            cacheView.setTimeToIdle(ele.getTimeToIdle() + "ms");
	            cacheList.add(cacheView);
	        } 
	        logger.info("-----------------------"+cacheName+":缓存总统计数据---------------------------------");  
	        long elementsInMemory1 = cache.getMemoryStoreSize();  
	        logger.info("得到缓存对象占用内存的数量：" + elementsInMemory1);  
	  
	        long elementsInMemory2 = cache.getDiskStoreSize();  
	        logger.info("得到缓存对对象占用磁盘的数量：" + elementsInMemory2);  
	  
	        //获取缓存统计对象  
	        Statistics stat = cache.getStatistics();  
	        long hits = stat.getCacheHits();  
	        logger.info("得到缓存读取的命中次数：" + hits);  
	  
	        long memoryHits = stat.getInMemoryHits();  
	        logger.info("得到内存中缓存读取的命中次数：" + memoryHits);  
	  
	        long diskHits = stat.getOnDiskHits();  
	        logger.info("得到磁盘中缓存读取的命中次数：" + diskHits);  
	  
	        long cacheMisses = stat.getCacheMisses();  
	        logger.info("得到缓存读取的丢失次数：" + cacheMisses);  
	  
	        long evictionCount = stat.getEvictionCount();  
	        logger.info("得到缓存读取的已经被销毁的对象丢失次数：" + evictionCount);  
	          
	        logger.info("--------------------------------------------------------"); 
	        
	        setAttr("cacheView",cacheList);
			render("cacheViewList.jsp");
		}
	}
}
