package com.amayadream.tassel.config.loader;

import com.alibaba.fastjson.JSONObject;
import com.amayadream.tassel.config.ProxyConfig;
import com.amayadream.tassel.constant.Constants;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 配置加载器
 * @author : Amayadream
 * @date :   2018-02-07 09:58
 */
@Slf4j
public class ConfigLoader {

    private static volatile ConfigLoader instance;

    private static ProxyConfig proxyConfig;     //代理配置
    private static long lastModified;           //配置文件最后修改时间

    private ConfigLoader() {
        load(Constants.CONFIG_DIR + "config.json", true);
    }

    /**
     * 初始化
     * @return
     */
    public static ConfigLoader getInstance() {
        if (instance == null) {
            synchronized (ConfigLoader.class) {
                if (instance == null) {
                    instance = new ConfigLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 获取代理配置
     * @return
     */
    public ProxyConfig proxyConfig() {
        return proxyConfig;
    }

    /**
     * 刷新配置
     * @return
     */
    public ConfigLoader refresh() {
        return refresh(false);
    }

    /**
     * 刷新配置
     * @param force 是否强制刷新配置
     * @return
     */
    public ConfigLoader refresh(boolean force) {
        load(Constants.CONFIG_DIR, force);
        return instance;
    }

    /**
     * 加载配置文件
     * @param filePath  文件目录(相对)
     * @param force     是否强制加载(是否考虑文件是否修改)
     */
    private void load(String filePath, boolean force) {
        File file = new File(filePath);
        if (!file.isFile()) {
            throw new RuntimeException(String.format("[ConfigLoader] config load failed, file [%s] not found", filePath));
        }

        if (!force && file.lastModified() == lastModified) {
            log.debug("[ConfigLoader] config not modified, skip this config load");
            return;
        }

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            //初始化配置实体
            proxyConfig = new ProxyConfig();
            proxyConfig = JSONObject.parseObject(bis, Charsets.UTF_8, ProxyConfig.class);
            lastModified = file.lastModified();
        } catch (IOException e) {
            log.error("[ConfigLoader] config load failed, maybe file format error, now use default config", e);
        }
    }

}
