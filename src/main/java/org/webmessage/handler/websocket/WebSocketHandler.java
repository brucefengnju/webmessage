package org.webmessage.handler.websocket;

import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.http.HttpHandler;

public interface WebSocketHandler extends HttpHandler{
	void onOpen(WebSocketChannel channel);
	void onClose(WebSocketChannel channel);
	void onMessage(WebSocketChannel channel,String message);
	void onMessage(WebSocketChannel channel,byte[] message);
	void onError(WebSocketChannel channel);
	void onPong(WebSocketChannel channel,byte[] message);
	void onPing(WebSocketChannel channel,byte[] message);
}
