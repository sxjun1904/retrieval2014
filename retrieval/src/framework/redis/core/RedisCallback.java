package framework.redis.core;

import redis.clients.jedis.ShardedJedis;

/**
 * 类RedisCallback.java的实现描述：Redis的回调接口
 * 
 * @author liulin 2012-11-29 上午9:58:51
 */
public interface RedisCallback<T> {

    /**
     * Redis 命令回调方法。
     * 
     * @param jedis
     * @param key
     * @return
     */
    T doInRedis(ShardedJedis jedis);

}
