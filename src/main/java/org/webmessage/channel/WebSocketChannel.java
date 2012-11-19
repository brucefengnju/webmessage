package org.webmessage.channel;

public interface WebSocketChannel{
	WebSocketChannel sendMessage(String message);
	WebSocketChannel sendMessage(byte[] message);
	
	WebSocketChannel close();
	boolean isOpen();
	String getProtocol();
}
