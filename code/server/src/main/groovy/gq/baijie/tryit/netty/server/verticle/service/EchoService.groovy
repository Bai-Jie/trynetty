package gq.baijie.tryit.netty.server.verticle.service

import gq.baijie.tryit.netty.domain.model.network.Message
import io.vertx.groovy.core.eventbus.Message as VertxMessage
import io.vertx.lang.groovy.GroovyVerticle

class EchoService extends GroovyVerticle {
    private static final String EVENT_ADDRESS = "${Message.name}.1"

    @Override
    void start() throws Exception {
        super.start()
        vertx.eventBus().consumer(EVENT_ADDRESS) {VertxMessage<Message> message ->
            message.reply(message.body())
        }
    }
}
