package gq.baijie.tryit.netty.domain.model.network

import com.sun.xml.internal.bind.v2.TODO

import java.nio.ByteBuffer

class Message {
    public static final int MAX_FRAME_LENGTH = 2 ** 16 - 1;
    public static final int LENGTH_FIELD_OFFSET = 1;
    public static final int LENGTH_FIELD_LENGTH = 2;

    byte type
    /** the length of {@link #data} */
    short length
    ByteBuffer data

    static Message fromByteBuffer(ByteBuffer buffer) {
        Message result = new Message()
        result.with {
            type = buffer.get()
            length = buffer.getShort()
            data = copyByteBuffer(buffer) //TODO avoid copy
            assert data.remaining() == length
        }
        return result
    }
    private static ByteBuffer copyByteBuffer(ByteBuffer buffer) {
        ByteBuffer result = ByteBuffer.allocate(buffer.remaining())
        result.put(buffer)
        result.flip()
        return result
    }

    public Message copy() {
        Message clone = new Message()
        clone.with {
            type = this.type
            length = this.length
            data = copyByteBuffer(this.data)
            this.data.rewind()
        }
        return clone
    }

}
