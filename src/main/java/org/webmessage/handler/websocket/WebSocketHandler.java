package org.webmessage.handler.websocket;

import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.http.HttpHandler;

public interface WebSocketHandler extends HttpHandler{
	void onOpen(WebSocketChannel channel)throws Exception;
	void onClose(WebSocketChannel channel)throws Exception;
	void onMessage(WebSocketChannel channel,String message) throws Exception;
	void onMessage(WebSocketChannel channel,byte[] message)throws Exception;
	void onError(WebSocketChannel channel)throws Exception;
	void onPong(WebSocketChannel channel,byte[] message)throws Exception;
	void onPing(WebSocketChannel channel,byte[] message)throws Exception;
}
