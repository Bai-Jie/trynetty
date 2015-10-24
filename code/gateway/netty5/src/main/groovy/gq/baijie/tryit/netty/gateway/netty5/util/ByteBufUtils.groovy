package gq.baijie.tryit.netty.gateway.netty5.util

import gq.baijie.tryit.netty.domain.model.communicate.Message
import io.netty.buffer.ByteBuf
import okio.ByteString

class ByteBufUtils {

    static Message readMessage(ByteBuf buf) {
        final byte type = buf.readByte()
        final int length = buf.readUnsignedShort()
        final byte[] data = new byte[length]
        buf.readBytes(data, 0, length)                      //copy?
        new Message(type, ByteString.of(data, 0, length))   //copy?
        //TODO avoid copy
    }

    static void writeMessage(ByteBuf buf, Message message) {
        buf.with {
            writeByte message.type
            writeShort message.length
            writeBytes message.data.toByteArray()   //copy twice? TODO avoid copy
        }
    }

}
