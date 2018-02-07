package com.amayadream.tassel.config.pac.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 代理规则
 * @author : Amayadream
 * @date :   2018-02-07 16:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProxyRule implements Serializable {

    private String rule;            //代理规则
    private MatchMode matchMode;    //匹配模式
    private Boolean disable;        //禁用模式

}
