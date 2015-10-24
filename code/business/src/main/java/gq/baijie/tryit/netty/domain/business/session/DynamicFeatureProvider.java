package gq.baijie.tryit.netty.domain.business.session;

import gq.baijie.tryit.netty.domain.model.communicate.Message;

public interface DynamicFeatureProvider extends FeatureProvider {

  /**
   * register a {@link Feature} class whose instance can meet demand(request)
   *
   * @param requestType  what demand(request) this feature can meet
   * @param featureClass the {@link Feature} class correspond to the {@code requestType}
   * @see #unregisterFeature
   * @see Message
   */
  void registerFeature(byte requestType, Class<Feature> featureClass);

  /**
   * unregister a {@link Feature} class registered by {@link #registerFeature}
   *
   * @param requestType the type of request
   * @see #registerFeature
   * @see Message
   */
  void unregisterFeature(byte requestType);

}
