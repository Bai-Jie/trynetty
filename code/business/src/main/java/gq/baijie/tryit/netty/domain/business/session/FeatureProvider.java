package gq.baijie.tryit.netty.domain.business.session;

import gq.baijie.tryit.netty.domain.model.communicate.Message;

public interface FeatureProvider {

  /**
   * has {@link Feature} instance who can meet demand(request)
   *
   * @param requestType the type of request
   * @return true if and only if {@code newFeature(requestType) != null}
   * @see #newFeature
   * @see Message
   */
  boolean hasFeature(byte requestType);

  /**
   * get a new {@link Feature} instance who can meet demand(request)
   *
   * @param requestType the type of request
   * @see Message
   */
  Feature newFeature(byte requestType);

}
