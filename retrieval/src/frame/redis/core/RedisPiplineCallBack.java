package frame.redis.core;

import redis.clients.jedis.ShardedJedisPipeline;

/**
 * 类RedisPiplineCallBack.java的实现描述：Redis Pipline 的回调接口
 * 
 * @author liulin 2012-11-29 下午11:36:32
 */
public interface RedisPiplineCallBack<T> {

    /**
     * Redis 命令回调方法。
     * 
     * @param jedis
     * @param key
     * @return
     */
    T doInRedis(ShardedJedisPipeline jedis);
}
