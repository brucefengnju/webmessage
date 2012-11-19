package org.webmessage.handler.websocket;

import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.http.HttpHandler;

public interface WebSocketHandler extends HttpHandler{
	void onOpen(WebSocketChannel channel);
	void onClose(WebSocketChannel channel);
	void onMessage(WebSocketChannel channel,String message);
	void onError(WebSocketChannel channel);
}
