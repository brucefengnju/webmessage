package org.webmessage.handler;

import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.websocket.WebSocketHandler;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

public interface RequestHandlerContext {
	RequestHandlerContext nextHandler(HttpRequest request,HttpResponse response)throws Exception;
	RequestHandlerContext nextHandler() throws Exception;
	RequestHandlerContext end(HttpResponse response)throws Exception;
	WebSocketChannel convertToWebsocketHandler(WebSocketHandler handler)throws Exception;
}
