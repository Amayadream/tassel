package com.amayadream.tassel;

import com.amayadream.tassel.loader.config.ConfigLoader;
import com.amayadream.tassel.loader.pac.PacLoaderFactory;

/**
 * @author : Amayadream
 * @date :   2018-02-06 17:17
 */
public class Application {

    public static void main(String[] args) {
        //1.初始化系统配置(ProxyConfig)
        ConfigLoader configLoader = ConfigLoader.getInstance();

        //2.初始化PAC配置
        PacLoaderFactory pacLoaderFactory = PacLoaderFactory.getInstance(configLoader.proxyConfig().getPacSource());

        //3.启动代理服务
        SocksServer server = SocksServer.getInstance();
        server.start();
    }

}
