package com.amayadream.tassel.config.pac;

import com.amayadream.tassel.config.pac.rule.MatchMode;
import com.amayadream.tassel.config.pac.rule.ProxyRule;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : Amayadream
 * @date :   2018-02-07 15:03
 */
@Slf4j
public class GfwlistPacLoader implements PacLoader {

    @Override
    public byte[] proxyRules() {
        byte[] ruleBytes = new byte[]{};
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt")
                .get()
                .build();
        Call call = client.newCall(request);
        Response response;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                log.error("[GfwlistPacLoader] download gfwlist failed, http status: {}", response.code());
                return ruleBytes;
            }
            if (response.body() == null) {
                log.error("[GfwlistPacLoader] download gfwlist failed, response body is empty, http status: {}", response.code());
            }
            return response.body().bytes();
        } catch (IOException e) {
            log.error("[GfwlistPacLoader] download gfwlist failed", e);
            return ruleBytes;
        }
    }

    @Override
    public List<ProxyRule> analyzeRules(byte[] ruleBytes) {
        //1.还原规则字符串, 然后根据换行分割
        List<ProxyRule> proxyRules = new ArrayList<>();
        String ruleStr = new String(Base64.decodeBase64(ruleBytes), Charsets.UTF_8);
        if (StringUtils.isEmpty(ruleStr)) {
            return proxyRules;
        }
        String[] ruleStrArray = ruleStr.split("\n");

        //2.遍历并处理每条规则
        for (String rule : ruleStrArray) {
            if (StringUtils.isEmpty(rule)) {
                continue;
            }
            rule = rule.trim(); //去除空格防止误判
            //2.1 忽略注释行
            if (rule.startsWith("!") || rule.startsWith("[")) {
                continue;
            }

            //2.2 处理禁用模式
            ProxyRule proxyRule = new ProxyRule();
            proxyRule.setDisable(false);
            if (rule.startsWith("@@")) {
                proxyRule.setDisable(true);
                rule = rule.substring("@@".length());
            }

            //2.3 处理域名模式
            if (rule.startsWith("||")) {
                proxyRule.setMatchMode(MatchMode.DOMAIN);
                proxyRule.setRule(rule.substring("||".length()));
                proxyRules.add(proxyRule);
                continue;
            }

            //2.4 TODO 处理首匹配模式
            if (rule.startsWith("|")) {
                continue;
            }

            //2.5 TODO 处理尾匹配模式
            if (rule.endsWith("|")) {
                continue;
            }

            //2.6 TODO 处理正则模式
            if (rule.startsWith("/") && rule.endsWith("/")) {
                continue;
            }

            //2.7 处理关键词模式
            proxyRule.setMatchMode(MatchMode.KEYWORD);
            proxyRule.setRule(rule);
            proxyRules.add(proxyRule);
        }
        return proxyRules;
    }

    @Override
    public boolean decideProxy(List<ProxyRule> proxyRules, String url) {
        //TODO URL匹配
        return false;
    }

    public static void main(String[] args) {
        PacLoader pacLoader = new GfwlistPacLoader();
        byte[] ruleBytes = pacLoader.proxyRules();
        List<ProxyRule> proxyRules = pacLoader.analyzeRules(ruleBytes);
        proxyRules.forEach(System.out::println);
    }

}
