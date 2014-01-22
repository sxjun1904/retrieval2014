package com.sxjun.core.plugin.redis;

import com.jfinal.plugin.IPlugin;


/**
 * User: sxjun
 * Date: 13-10-11
 * Time: 上午7:21
 */
public class RedisPlugin implements IPlugin {
    private static RedisManager redisManager;
        
    public RedisPlugin(String host, int port, int dbIndex) {
        RedisPlugin.redisManager = new RedisManager(host, port, dbIndex);
    }

    public boolean start() {
        redisManager.initPool();
        //redisManager.initShardedPool();
        RedisKit.init(redisManager);
        return true;
    }

    public boolean stop() {
        redisManager.destroy();
        return true;
    }
}

