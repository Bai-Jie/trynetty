package gq.baijie.tryit.netty.domain.business.session;

public interface FeatureProvider {

    /**
     * get a new {@link Feature} instance who can meet demand(request)
     * @param requestType the type of request
     */
    Feature newFeature(byte requestType);

}
