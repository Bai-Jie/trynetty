package gq.baijie.tryit.netty.server.verticle

import gq.baijie.tryit.netty.server.env.Context
import gq.baijie.tryit.netty.server.verticle.service.EchoService
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.lang.groovy.GroovyVerticle

class MainVerticle extends GroovyVerticle {

    @Override
    void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture)
        Context.vertx = vertx

        vertx.deployVerticle("groovy:${EchoService.name}")
        vertx.deployVerticle("groovy:${ServerVerticle.name}") {AsyncResult<String> res ->
            if (res.succeeded()) {
                startFuture.complete()
            } else {
                startFuture.fail(res.cause())
                throw res.cause()
            }
        }
    }

}
