package org.webmessage.handler;

import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.websocket.BaseWebSocketHandler;

public class EchoWebSocketHandler extends BaseWebSocketHandler {

	@Override
	public void onOpen(WebSocketChannel channel) {
		System.out.println("echo open");
	}

	@Override
	public void onClose(WebSocketChannel channel) {
		System.out.println("Echo close");
	}

	@Override
	public void onMessage(WebSocketChannel channel, String message) throws Exception {
		channel.sendMessage(message);
		System.out.println("echo text message");
	}

	@Override
	public void onMessage(WebSocketChannel channel, byte[] message) throws Exception {
		channel.sendMessage(message);
		System.out.println("echo binary message");
	}

	@Override
	public void onError(WebSocketChannel channel) {
		System.out.println("echo error");
	}

	@Override
	public void onPong(WebSocketChannel channel, byte[] message) {
		System.out.println("echo pong");
	}

	@Override
	public void onPing(WebSocketChannel channel, byte[] message) {
		System.out.println("echo ping");
	}

}
