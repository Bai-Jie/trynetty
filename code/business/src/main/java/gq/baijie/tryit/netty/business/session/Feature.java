package gq.baijie.tryit.netty.business.session;

import gq.baijie.tryit.netty.domain.model.communicate.Message;

public interface Feature {

  void bindSession(Session session);

  void request(Message message);

  //TODO onDestory/end/exit

}
