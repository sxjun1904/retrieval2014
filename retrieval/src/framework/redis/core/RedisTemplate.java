package framework.redis.core;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 类RedisTemplate.java的实现描述：Redis 模板类
 * 
 */
public class RedisTemplate<Key, Val> {
	
    private ShardedJedisPool shardedJedisPool;

    /**
     * redis 命令执行核心方法.
     * 
     * @param redisCallback
     * @return
     */
    public <V> V execute(RedisCallback<V> redisCallback) {

        ShardedJedis jedis = shardedJedisPool.getResource();
        try {
            return redisCallback.doInRedis(jedis);
        } finally {
            shardedJedisPool.returnBrokenResource(jedis);
        }
    }
    
    /**
     * shardedJedisPool destroy
     */
    public void destroy() {
        shardedJedisPool.destroy();
    }
    
}
