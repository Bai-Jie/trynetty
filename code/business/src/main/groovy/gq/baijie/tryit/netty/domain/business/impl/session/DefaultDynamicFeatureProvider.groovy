package gq.baijie.tryit.netty.domain.business.impl.session

import gq.baijie.tryit.netty.domain.business.session.DynamicFeatureProvider
import gq.baijie.tryit.netty.domain.business.session.Feature

class DefaultDynamicFeatureProvider implements DynamicFeatureProvider {

    private final Map<Byte, Class<Feature>> features = [:]

    @Override
    void registerFeature(byte requestType, Class<Feature> featureClass) {
        features[requestType] = featureClass
    }

    @Override
    void unregisterFeature(byte requestType) {
        features.remove(requestType)
    }

    @Override
    boolean hasFeature(byte requestType) {
        return features[requestType]
    }

    @Override
    Feature newFeature(byte requestType) {
        return features[requestType].newInstance()
    }

}
