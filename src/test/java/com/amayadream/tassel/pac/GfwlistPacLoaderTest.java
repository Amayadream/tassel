package com.amayadream.tassel.pac;

import com.amayadream.tassel.config.pac.GfwlistPacLoader;
import com.amayadream.tassel.config.pac.PacLoader;
import com.amayadream.tassel.config.pac.rule.ProxyRule;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : Amayadream
 * @date :   2018-02-08 11:27
 */
@Slf4j
public class GfwlistPacLoaderTest {

    public static void main(String[] args) {
        PacLoader pacLoader = new GfwlistPacLoader();

        Stopwatch watch1 = Stopwatch.createStarted();
        byte[] ruleBytes = pacLoader.proxyRules();
        List<ProxyRule> proxyRules = pacLoader.analyzeRules(ruleBytes);
        watch1.stop();
        log.info("获取gfwlist成功, 一共解析出 {} 条, 总计耗时: {} ms", proxyRules.size(), watch1.elapsed(TimeUnit.MILLISECONDS));

        Stopwatch watch2 = Stopwatch.createStarted();
        String url = "http://www.bianlei.com";
        boolean flag = pacLoader.decideProxy(proxyRules, url);
        watch2.stop();
        log.info("地址({})的代理判断结果为: {}, 总计耗时: {} ms", url, flag, watch2.elapsed(TimeUnit.MILLISECONDS));
    }

}
