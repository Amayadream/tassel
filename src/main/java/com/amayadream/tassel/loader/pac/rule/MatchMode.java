package com.amayadream.tassel.loader.pac.rule;

/**
 * 匹配模式
 * @author : Amayadream
 * @date :   2018-02-07 16:46
 */
public enum MatchMode {

    /**
     * 关键字匹配规则, 会在整个(http)url里查找是否存在这个关键字, 不包含https
     * 例如: example.com
     * 匹配: http://www.example.com/foo
     * 匹配: http://www.google.com/search?q=www.example.com
     * 不匹配: https://www.example.com/
     */
    KEYWORD,

    /**
     * 域名匹配规则, 会匹配整个域名(包含子域名), 无论http/https请求
     * 例如: ||example.com
     * 匹配: http://example.com/foo
     * 匹配: https://subdomain.example.com/bar
     * 不匹配: http://www.google.com/search?q=example.com
     */
    DOMAIN,


}
