package gq.baijie.tryit.netty.server.env;


import io.vertx.groovy.core.Vertx;

/**
 * <strong>Warning</strong>: set this before any other threads start
 */
public class Context {
    public static Vertx vertx;

    public static final int PORT_NUMBER = 45678;
}
