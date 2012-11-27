package org.webmessage.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

public interface RequestHandlerContext {
	RequestHandlerContext nextHandler(HttpRequest request,HttpResponse response);
	RequestHandlerContext nextHandler();
	RequestHandlerContext end(HttpResponse response);
	ChannelHandlerContext getNettyContext();
}
