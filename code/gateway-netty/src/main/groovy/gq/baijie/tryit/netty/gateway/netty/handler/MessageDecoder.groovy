package gq.baijie.tryit.netty.gateway.netty.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import gq.baijie.tryit.netty.domain.model.network.Message

import java.nio.ByteBuffer

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
            ByteBuffer
            Message result = Message.fromByteBuffer(readBytes(frame)) //TODO avoid copy
            frame.release()
            return result
        }
    }

    //TODO override extractFrame method. See ObjectDecoder

    private static ByteBuffer readBytes(ByteBuf buf) {
        final ByteBuffer result = ByteBuffer.allocate(buf.readableBytes())
        buf.readBytes(result)
        result.flip()
        return result
    }

}
