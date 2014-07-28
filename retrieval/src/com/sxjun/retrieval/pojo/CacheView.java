/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 线程监控Entity
 * @author sxjun
 * @version 2014-07-10
 */
public class CacheView extends BasePojo{
	
	private String cacheName; 	//缓存名称
	private String value; 	//缓存内容
	private String creationTime; //	创建时间
	private String lastAccessTime; 	//最后访问时间
	private String expirationTime; 	//过期时间
	private String lastUpdateTime; 	//最后更新时间
	private String hitCount; 	//命中次数
	private String timeToLive; //存活时间
	private String timeToIdle; //空闲时间
	
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getHitCount() {
		return hitCount;
	}
	public void setHitCount(String hitCount) {
		this.hitCount = hitCount;
	}
	public String getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(String timeToLive) {
		this.timeToLive = timeToLive;
	}
	public String getTimeToIdle() {
		return timeToIdle;
	}
	public void setTimeToIdle(String timeToIdle) {
		this.timeToIdle = timeToIdle;
	}
	
}


