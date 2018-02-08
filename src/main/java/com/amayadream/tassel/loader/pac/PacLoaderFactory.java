package com.amayadream.tassel.loader.pac;

import com.amayadream.tassel.loader.PacSource;
import com.amayadream.tassel.loader.pac.rule.ProxyRule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * PAC加载器工厂
 * @author : Amayadream
 * @date :   2018-02-07 14:50
 */
@Slf4j
public class PacLoaderFactory {

    private static volatile PacLoaderFactory instance;

    private static PacLoader pacLoader;                 //PAC加载器
    private static PacSource pacSource;                 //PAC源
    private static List<ProxyRule> proxyRules;          //代理规则列表

    private PacLoaderFactory(PacSource source) {
        load(source);
    }

    /**
     * 初始化
     * @param source PAC源
     * @return
     */
    public static PacLoaderFactory getInstance(PacSource source) {
        if (instance == null) {
            synchronized (PacLoaderFactory.class) {
                instance = new PacLoaderFactory(pacSource);
            }
        }
        return instance;
    }

    /**
     * 载入PAC配置
     * @param source PAC源
     */
    private void load(PacSource source) {
        if (source == PacSource.GFWLIST) {
            pacLoader = new GfwlistPacLoader();
        } else {
            pacLoader = new GfwlistPacLoader();
        }
        pacSource = source;
        proxyRules = pacLoader.analyzeRules(pacLoader.fetchRules());
    }

    /**
     * 刷新PAC配置
     * @return
     */
    public PacLoaderFactory refresh() {
        return refresh(pacSource);
    }

    /**
     * 刷新PAC配置(指定源)
     * @param source PAC源
     * @return
     */
    public PacLoaderFactory refresh(PacSource source) {
        load(source);
        return instance;
    }

    /**
     * 判断是否需要走代理
     * @param url 访问URL
     * @return
     */
    public boolean decideProxy(String url) {
        return pacLoader.decideProxy(proxyRules, url);
    }

}
