package com.amayadream.tassel.config;

import com.amayadream.tassel.config.common.ProxyMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局代理配置
 * @author : Amayadream
 * @date :   2018-02-06 18:32
 */
@Data
@Builder
@AllArgsConstructor
public class ProxyConfig implements Serializable {

    private Integer localPort;              //本地端口
    private ProxyMode proxyMode;            //代理模式(DIRECT:直连模式, PAC:PAC模式, PROXY:代理模式)
    private Integer index;                  //默认节点索引(如果开启负载均衡则此配置失效)
    private Boolean enableLoadBalance;      //是否开启负载均衡
    private List<ProxyServer> proxyList;    //代理服务列表

    /**
     * 初始化默认配置
     */
    public ProxyConfig() {
        this.localPort = 1081;
        this.proxyMode = ProxyMode.DIRECT;
        this.index = 0;
        this.enableLoadBalance = true;
        this.proxyList = new ArrayList<>();
    }

}
