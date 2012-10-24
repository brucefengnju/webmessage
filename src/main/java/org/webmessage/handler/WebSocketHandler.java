package org.webmessage.handler;

import org.webmessage.channel.WebSocketChannel;

public interface WebSocketHandler extends HttpHandler{
	
	void onOpen(WebSocketChannel channel);
	void onClose(WebSocketChannel channel);
	void onMessage(WebSocketChannel channel);
	void onError(WebSocketChannel channel);
}
