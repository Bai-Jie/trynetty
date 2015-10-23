package gq.baijie.tryit.netty.server.verticle

import gq.baijie.tryit.netty.server.env.Context
import gq.baijie.tryit.netty.server.handler.MessageHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.vertx.core.Future
import io.vertx.lang.groovy.GroovyVerticle

class ServerVerticle extends GroovyVerticle {

    @Override
    void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture)
        int port = Context.PORT_NUMBER

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MessageHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port)

            f.addListener({ChannelFuture future ->
                future.channel().closeFuture().addListener{
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                }
                startFuture.complete()
            } as ChannelFutureListener)
        } catch (Exception e){
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            startFuture.fail(e)
            throw e
        }
    }

}
