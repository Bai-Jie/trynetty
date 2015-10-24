package gq.baijie.tryit.netty.domain.business.session;

import gq.baijie.tryit.netty.domain.model.communicate.Message;

public interface FeatureProvider {

  /**
   * get a new {@link Feature} instance who can meet demand(request)
   *
   * @param requestType the type of request
   * @see Message
   */
  Feature newFeature(byte requestType);

}
