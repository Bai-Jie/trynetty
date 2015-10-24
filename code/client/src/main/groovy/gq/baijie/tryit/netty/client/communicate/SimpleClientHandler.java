package gq.baijie.tryit.netty.client.communicate;

import gq.baijie.tryit.netty.domain.model.communicate.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import okio.ByteString;

public class SimpleClientHandler extends ChannelHandlerAdapter {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("[client]channelActive. send a message to server:");
    ctx.writeAndFlush(newMessage());
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    Message message = (Message) msg;
    System.out.println("[client]receive a message from server:");
    System.out.println(message.getData().toString());
    System.out.println(message.getData().utf8());
    System.out.println("[client]close connection");
    ctx.close();
  }

  private static Message newMessage() {
    final byte type = 1;
    final ByteString data = ByteString.encodeUtf8("Hello World!\n你好，世界！");
    return new Message(type, data);
  }

}
