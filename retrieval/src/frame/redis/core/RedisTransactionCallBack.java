package frame.redis.core;

import redis.clients.jedis.Transaction;

/**
 * 类RedisTransaction.java的实现描述：Redis 事务处理回掉方法.
 * 
 */
public interface RedisTransactionCallBack<T> {

    /**
     * Redis 命令回调方法。
     * 
     * @param jedis
     * @param key
     * @return
     */
    T doTransaction(Transaction t);
}
