package org.webmessage.channel;

public interface WebSocketChannel extends HttpChannel{
	void sendMessage(String message);
	void sendMessage(byte[] message);
	boolean isOpen();
}
