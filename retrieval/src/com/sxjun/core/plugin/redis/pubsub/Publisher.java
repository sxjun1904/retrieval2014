package com.sxjun.core.plugin.redis.pubsub;

import redis.clients.jedis.Jedis;

public class Publisher {
	 
	 public void publish(final Jedis redisClient) {
	  
	  new Thread(new Runnable() {
	   @Override
	   public void run() {
	    try {
	     Thread.currentThread().sleep(2000);
	    } catch (InterruptedException e) {
	     e.printStackTrace();
	    }
	    System.out.println("发布：news.share");
	    redisClient.publish("news.share", "ok");
	    redisClient.publish("news.share", "hello word");
	   }
	  }).start();

	 }

	}