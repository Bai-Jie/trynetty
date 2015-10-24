package gq.baijie.tryit.netty.feature.echo;

import gq.baijie.tryit.netty.domain.business.session.Feature;
import gq.baijie.tryit.netty.domain.business.session.Session;
import gq.baijie.tryit.netty.domain.model.communicate.Message;

public class EchoFeature implements Feature {

  private Session session;

  @Override
  public void bindSession(Session session) {
    this.session = session;
  }

  @Override
  public void request(Message message) {
    if (session == null) {
      throw new IllegalStateException("shouldn't do request before bindSession");
    }
    session.respond(message);
  }
}
