package com.amayadream.tassel.config.pac;

import com.amayadream.tassel.config.common.PacSource;
import lombok.extern.slf4j.Slf4j;

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

    private PacLoaderFactory(PacSource pacSource) {
        //TODO init PacLoader
    }

    /**
     * 初始化
     * @param pacSource PAC源
     * @return
     */
    public static PacLoaderFactory getInstance(PacSource pacSource) {
        if (instance == null) {
            synchronized (PacLoaderFactory.class) {
                instance = new PacLoaderFactory(pacSource);
            }
        }
        return instance;
    }



}
