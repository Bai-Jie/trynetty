package gq.baijie.tryit.netty.business.communicate;

import gq.baijie.tryit.netty.business.session.Session;
import gq.baijie.tryit.netty.domain.model.communicate.Message;

public interface ChannelInterface {

  void bindSession(Session session);

  void write(Message message);

}
