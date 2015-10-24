package gq.baijie.tryit.netty.gateway.netty5.communicate;

import gq.baijie.tryit.netty.business.communicate.ChannelInterface;
import gq.baijie.tryit.netty.business.session.Session;
import gq.baijie.tryit.netty.domain.model.communicate.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MessageHandler extends ChannelHandlerAdapter implements ChannelInterface {

  private ChannelHandlerContext channelHandlerContext;

  private Session session;

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    this.channelHandlerContext = ctx;
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    this.channelHandlerContext = null;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    session.request((Message) msg);
  }

  //TODO close session

  @Override
  public void bindSession(Session session) {
    this.session = session;
  }

  @Override
  public void write(Message message) {
    if (channelHandlerContext != null) {
      channelHandlerContext.writeAndFlush(message);
    } else {
      throw new IllegalStateException(
          "shouldn't write before handlerAdded or after handlerRemoved");
    }
  }

}
