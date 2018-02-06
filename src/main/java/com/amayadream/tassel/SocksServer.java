package com.amayadream.tassel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Amayadream
 * @date :   2018-02-06 17:21
 */
@Slf4j
public class SocksServer {

    private static SocksServer socks5Server = new SocksServer();

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

    private SocksServer() {

    }

    public static SocksServer getInstance() {
        return socks5Server;
    }

    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//            .childHandler(new SocksServerInitializer())
            ;

            log.info("[SocksServer] start at port 1081");

            bootstrap.bind(1081).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("[SocksServer] server has error ...", e);
        } finally {
            stop();
        }
    }

    public void stop() {
        log.info("[SocksServer] stop server ...");
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

}
