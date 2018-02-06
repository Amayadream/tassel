package com.amayadream.tassel;

/**
 * @author : Amayadream
 * @date :   2018-02-06 17:17
 */
public class Application {

    public static void main(String[] args) {
        SocksServer server = SocksServer.getInstance();
        server.start();
    }

}
