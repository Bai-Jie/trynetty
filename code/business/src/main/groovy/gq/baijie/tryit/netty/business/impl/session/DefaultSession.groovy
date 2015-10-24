package gq.baijie.tryit.netty.business.impl.session

import gq.baijie.tryit.netty.business.communicate.ChannelInterface
import gq.baijie.tryit.netty.business.session.Feature
import gq.baijie.tryit.netty.business.session.FeatureProvider
import gq.baijie.tryit.netty.business.session.Session
import gq.baijie.tryit.netty.domain.model.communicate.Message

import javax.annotation.Nullable

class DefaultSession implements Session {

    private ChannelInterface channelInterface

    private FeatureProvider featureProvider

    private final Map<Byte, Feature> liveFeatures = [:]

    @Nullable
    private FeatureProvider getFeatureProvider() {
        featureProvider
    }

    @Nullable
    private ChannelInterface getChannelInterface() {
        channelInterface
    }

    @Nullable
    private Feature getFeature(byte requestType) {
        Feature feature = liveFeatures[requestType]
        if (!feature) {
            feature = getFeatureProvider()?.newFeature(requestType)
            if (feature) {
                feature.bindSession(this)
                liveFeatures[requestType] = feature
            }
        }
        return feature
    }

    @Override
    void bindChannelInterface(ChannelInterface channelInterface) {
        this.channelInterface = channelInterface
    }

    @Override
    void setFeatureProvider(FeatureProvider featureProvider) {
        this.featureProvider = featureProvider
    }

    @Override
    void request(Message message) {
        getFeature(message.type)?.request(message) //TODO fast failure
    }

    @Override
    void respond(Message message) {
        final ChannelInterface channelInterface1 = getChannelInterface()
        if (channelInterface1) {
            channelInterface1.write(message)
        } else {
            throw new IllegalStateException("write before bind ChannelInterface")
        }
    }

}
