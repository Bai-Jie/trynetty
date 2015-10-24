package gq.baijie.tryit.netty.server

import gq.baijie.tryit.netty.business.session.Session
import gq.baijie.tryit.netty.business.session.Sessions
import gq.baijie.tryit.netty.feature.echo.EchoFeature
import gq.baijie.tryit.netty.gateway.netty5.communicate.MessageDecoder
import gq.baijie.tryit.netty.gateway.netty5.communicate.MessageEncoder
import gq.baijie.tryit.netty.gateway.netty5.communicate.MessageHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

class Main {

    private int port;

    static void main(String... args) {
        init()

        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new Main(port).run();
    }

    static void init() {
        Sessions.defaultFeatureProvider.registerFeature((byte)1, EchoFeature)
    }

    public Main(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.with {
                group bossGroup, workerGroup
                channel NioServerSocketChannel
                option ChannelOption.SO_BACKLOG, 128
                childOption ChannelOption.SO_KEEPALIVE, true

                childHandler({SocketChannel ch->
                    ch.pipeline().addLast(new MessageEncoder())
                    ch.pipeline().addLast(new MessageDecoder())
                    ch.pipeline().addLast(newMessageHandler())
                } as ChannelInitializer<SocketChannel>)
            }

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    static MessageHandler newMessageHandler() {
        final MessageHandler messageHandler = new MessageHandler()
        Session session = Sessions.newSession()

        messageHandler.bindSession session
        session.bindChannelInterface messageHandler

        return messageHandler
    }

}
