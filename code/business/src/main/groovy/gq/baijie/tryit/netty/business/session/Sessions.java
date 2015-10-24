package gq.baijie.tryit.netty.business.session;

import gq.baijie.tryit.netty.business.impl.session.DefaultDynamicFeatureProvider;
import gq.baijie.tryit.netty.business.impl.session.DefaultSession;

public class Sessions {

  private static final DefaultDynamicFeatureProvider DEFAULT_FEATURE_PROVIDER =
      new DefaultDynamicFeatureProvider();

  public static DefaultDynamicFeatureProvider getDefaultFeatureProvider() {
    return DEFAULT_FEATURE_PROVIDER;
  }

  public static Session newSession() {
    final Session session = new DefaultSession();
    session.setFeatureProvider(getDefaultFeatureProvider());
    return session;
  }

}
