package com.amayadream.tassel.loader.pac;

import com.amayadream.tassel.loader.pac.rule.ProxyRule;

import java.util.List;

/**
 *
 * @author : Amayadream
 * @date :   2018-02-07 14:43
 */
public interface PacLoader {

    /**
     * 获取规则源数据
     * @return
     */
    byte[] fetchRules();

    /**
     * 解析规则
     * @param ruleBytes 规则源数据
     * @return
     */
    List<ProxyRule> analyzeRules(byte[] ruleBytes);

    /**
     * 判断是否走代理(默认或禁用的不走代理, 其他走代理)
     * @param proxyRules 代理规则列表
     * @param url        请求URL
     * @return
     */
    boolean decideProxy(List<ProxyRule> proxyRules, String url);

}
