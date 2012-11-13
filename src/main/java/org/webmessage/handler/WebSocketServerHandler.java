package org.webmessage.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.jboss.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketServerHandler extends SimpleChannelUpstreamHandler {
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		Object msg = e.getMessage();
		if(msg instanceof WebSocketFrame){
			WebSocketFrame frame = (WebSocketFrame)msg;
			this.handleWebSocketFrame(ctx, frame);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
		e.getCause().printStackTrace();
	}
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		System.out.println("do open");
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		System.out.print("do closed");
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
		System.out.print("do writeComplete");
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		if(frame instanceof PingWebSocketFrame){
			System.out.println("do ping ");
			
		}else if(frame instanceof PongWebSocketFrame){
			System.out.println("do pong ");
			
		}else if(frame instanceof CloseWebSocketFrame){
			System.out.println("do close ");
			
		}else if(frame instanceof TextWebSocketFrame){
			System.out.println("do text ");
			
		}else if(frame instanceof BinaryWebSocketFrame){
			System.out.println("do binary");
		}
	}
}
