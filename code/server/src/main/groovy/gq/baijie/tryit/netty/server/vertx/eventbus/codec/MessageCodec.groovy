package gq.baijie.tryit.netty.server.vertx.eventbus.codec

import gq.baijie.tryit.netty.domain.model.network.Message
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec as MessageCodecI

import java.nio.ByteBuffer

class MessageCodec implements MessageCodecI<Message, Message> {

    @Override
    void encodeToWire(Buffer buffer, Message message) {
        message.with {
            buffer.appendByte(type)
            buffer.appendShort(length)
            final byte[] bytes = new byte[length]
            data.rewind()
            data.get(bytes, 0, length)
            data.rewind()
            buffer.appendBytes(bytes)
        }
    }

    @Override
    Message decodeFromWire(int pos, Buffer buffer) {
        Message message = new Message()
        message.with {
            type = buffer.getByte(pos)
            pos += Byte.BYTES
            length = buffer.getShort(pos)
            pos += Short.BYTES
            ByteBuffer byteBuffer = ByteBuffer.allocate(length)
            byteBuffer.put(buffer.getBytes(pos, pos + length))
            byteBuffer.rewind()
            data = byteBuffer
        }
        return null
    }

    @Override
    Message transform(Message message) {
        return message.copy()
    }

    @Override
    String name() {
        return Message.name
    }

    @Override
    byte systemCodecID() {
        return -1
    }
}
