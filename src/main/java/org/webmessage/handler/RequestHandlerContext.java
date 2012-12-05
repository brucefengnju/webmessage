package org.webmessage.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.http.HttpHandler;
import org.webmessage.handler.websocket.WebSocketHandler;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

public interface RequestHandlerContext {
	RequestHandlerContext nextHandler(HttpRequest request,HttpResponse response);
	RequestHandlerContext nextHandler();
	RequestHandlerContext end(HttpResponse response);
	ChannelHandlerContext getNettyContext();
	WebSocketChannel convertToWebsocketHandler(HttpHandler handler); 
}
