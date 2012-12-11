package org.webmessage.channel;

public interface WebSocketChannel{
	WebSocketChannel sendMessage(String message) throws Exception;
	WebSocketChannel sendMessage(byte[] message) throws Exception;
	
	WebSocketChannel close();
	WebSocketChannel ping(byte[] message) throws Exception;
	WebSocketChannel pong(byte[] message)throws Exception;
	String getProtocol();
}
