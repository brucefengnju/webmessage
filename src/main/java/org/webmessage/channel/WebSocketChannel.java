package org.webmessage.channel;

public interface WebSocketChannel{
	WebSocketChannel sendMessage(String message);
	WebSocketChannel sendMessage(byte[] message);
	
	WebSocketChannel close();
	WebSocketChannel ping(byte[] message);
	WebSocketChannel pong(byte[] message);
	String getProtocol();
}
