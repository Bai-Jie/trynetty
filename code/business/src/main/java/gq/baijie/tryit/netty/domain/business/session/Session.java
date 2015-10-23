package gq.baijie.tryit.netty.domain.business.session;

import gq.baijie.tryit.netty.domain.business.communicate.ChannelInterface;
import gq.baijie.tryit.netty.domain.model.communicate.Message;

public interface Session {

    void bindChannelInterface(ChannelInterface channelInterface);

    /**
     * user make a request via {@link ChannelInterface}
     * @param message request message
     */
    void request(Message message);

    /**
     * {@link Feature} responds to user
     * @param message response message
     */
    void respond(Message message);

}
