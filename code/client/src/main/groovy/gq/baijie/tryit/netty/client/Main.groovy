package gq.baijie.tryit.netty.client

import gq.baijie.tryit.netty.client.communicate.SimpleClientHandler
import gq.baijie.tryit.netty.gateway.netty5.communicate.MessageDecoder
import gq.baijie.tryit.netty.gateway.netty5.communicate.MessageEncoder
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

class Main {

    private String host;
    private int port;

    static void main(String... args) {
        if(args.length != 2) {
            printUsage()
            return
        }
        final String host = args[0]
        final int port = args[1] as int
        new Main(host: host, port: port).run()
    }

    static void printUsage() {
        println """
        |Usage:
        |    command <host> <port>
        """.stripMargin('|')
    }

    void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.with {
                group workerGroup
                channel NioSocketChannel
                option ChannelOption.SO_KEEPALIVE, true

                handler({SocketChannel ch ->
                    ch.pipeline().addLast(new MessageEncoder())
                    ch.pipeline().addLast(new MessageDecoder())
                    ch.pipeline().addLast(new SimpleClientHandler())
                } as ChannelInitializer<SocketChannel>)
            }


            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
