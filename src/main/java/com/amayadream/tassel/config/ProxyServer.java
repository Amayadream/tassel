package com.amayadream.tassel.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 代理服务配置
 * @author : Amayadream
 * @date :   2018-02-06 18:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProxyServer implements Serializable {

    private String server;      //服务地址
    private Integer port;       //端口
    private String password;    //密码
    private String method;      //加密方式
    private Boolean enable;     //是否启用
    private String remark;      //备注

}
