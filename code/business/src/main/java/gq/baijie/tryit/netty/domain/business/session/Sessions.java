package gq.baijie.tryit.netty.domain.business.session;

import gq.baijie.tryit.netty.domain.business.impl.session.DefaultDynamicFeatureProvider;
import gq.baijie.tryit.netty.domain.business.impl.session.DefaultSession;

public class Sessions {

  private static final FeatureProvider defaultFeatureProvider = new DefaultDynamicFeatureProvider();

  public static FeatureProvider getDefaultFeatureProvider() {
    return defaultFeatureProvider;
  }

  public static Session newSession() {
    final Session session = new DefaultSession();
    session.setFeatureProvider(getDefaultFeatureProvider());
    return session;
  }

}
