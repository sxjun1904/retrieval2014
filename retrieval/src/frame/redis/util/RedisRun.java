package frame.redis.util;

import java.io.IOException;

import frame.retrieval.engine.context.DefaultRetrievalProperties;

public class RedisRun {
	
	public void execRedis(){
		if(DefaultRetrievalProperties.YES.equals(DefaultRetrievalProperties.getDefault_retrieval_redis_start().trim())){
			Runtime r = Runtime.getRuntime();
	        //应用程序所在的路径
	        String redisServerPath = DefaultRetrievalProperties.getDefault_retrieval_redis_server_path();
	        try {
	        	Process pro = r.exec(redisServerPath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	        	//利用该方法结束开启的进程
	        	//pro.destroy();
	        }
		}
	}
}
