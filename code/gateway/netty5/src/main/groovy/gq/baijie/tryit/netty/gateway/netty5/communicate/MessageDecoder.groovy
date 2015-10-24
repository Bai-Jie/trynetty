package gq.baijie.tryit.netty.gateway.netty5.communicate

import gq.baijie.tryit.netty.domain.model.communicate.Message
import gq.baijie.tryit.netty.gateway.netty5.util.ByteBufUtils
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.LengthFieldBasedFrameDecoder

class MessageDecoder extends LengthFieldBasedFrameDecoder {

    MessageDecoder() {
        super(Message.MAX_FRAME_LENGTH, Message.LENGTH_FIELD_OFFSET, Message.LENGTH_FIELD_LENGTH)
    }

    @Override
    protected Message decode(ChannelHandlerContext ctx, ByteBuf inbf) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, inbf)
        if (frame == null) {
            return null
        } else {
            final Message result = ByteBufUtils.readMessage(frame)
            frame.release()
            return result
        }
    }

    //TODO override extractFrame method. See ObjectDecoder

}
