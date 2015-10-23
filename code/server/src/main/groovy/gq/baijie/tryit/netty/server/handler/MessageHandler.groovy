package gq.baijie.tryit.netty.server.handler

import gq.baijie.tryit.netty.domain.model.network.Message
import io.vertx.groovy.core.eventbus.Message as VertxMessage
import gq.baijie.tryit.netty.server.env.Context
import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import io.vertx.core.AsyncResult

class MessageHandler extends ChannelHandlerAdapter {

    @Override
    void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = msg as Message;
        Context.vertx.eventBus().send("${Message.name}.${message.type}", message)
        {AsyncResult<VertxMessage<Message>> ar ->
            if (ar.succeeded()) {
//                if (!ar.result().body() instanceof Message) { //TODO fast failure?
//                    throw new IllegalStateException("Unknown replay: ${ar.result().body()}")
//                }
                if (ctx.channel().active) {
                    ctx.channel().write(ar.result().body())
                }
            }//TODO else {} fast failure?
        }
    }

}
