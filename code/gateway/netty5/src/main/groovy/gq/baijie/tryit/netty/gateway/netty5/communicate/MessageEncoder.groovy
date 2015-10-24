package gq.baijie.tryit.netty.gateway.netty5.communicate

import gq.baijie.tryit.netty.domain.model.communicate.Message
import gq.baijie.tryit.netty.gateway.netty5.util.ByteBufUtils
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        ByteBufUtils.writeMessage(out, msg)
    }

}
