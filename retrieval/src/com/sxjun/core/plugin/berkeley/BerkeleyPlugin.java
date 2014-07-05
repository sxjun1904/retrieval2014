package com.sxjun.core.plugin.berkeley;

import com.jfinal.plugin.IPlugin;


/**
 * User: sxjun
 * Date: 14-07-04
 * Time: 上午7:21
 */
public class BerkeleyPlugin implements IPlugin {
    private static BerkeleyManager berkeleyManager;
        
    public BerkeleyPlugin() {
    	BerkeleyPlugin.berkeleyManager = new BerkeleyManager();
    }

    public boolean start() {
    	berkeleyManager.init();
    	BerkeleyKit.init(berkeleyManager);
        return true;
    }

    public boolean stop() {
    	berkeleyManager.cleanlog();
    	berkeleyManager.destroy();
        return true;
    }
}

