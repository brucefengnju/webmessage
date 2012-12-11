package org.webmessage.netty;

import org.jboss.netty.buffer.ChannelBuffer;
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
import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.websocket.WebSocketHandler;

public class WebSocketServerHandler extends SimpleChannelUpstreamHandler {
	private WebSocketHandler websocketHandler;
	private WebSocketChannel websocketChannel;
	
	public WebSocketServerHandler(){
	}
	
	public WebSocketServerHandler(WebSocketHandler websocketHandler) {
		this.websocketHandler = websocketHandler;
	}
	
	public WebSocketServerHandler(WebSocketHandler websocketHandler,
			WebSocketChannel websocketChannel) {
		this.websocketHandler = websocketHandler;
		this.websocketChannel = websocketChannel;
	}

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
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
	}

	
	@Override
	public void channelBound(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		ctx.getPipeline().remove("messageencoder");
	}

	@Override
	public void channelUnbound(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		this.websocketHandler.onClose(this.websocketChannel);
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		if(frame instanceof PingWebSocketFrame){
			ChannelBuffer buffer = frame.getBinaryData();
			if(buffer.readable()){
				byte[] msg = new byte[buffer.readableBytes()];
				buffer.getBytes(buffer.readerIndex(), msg);
				try {
					this.websocketHandler.onPing(this.websocketChannel, msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(frame instanceof PongWebSocketFrame){
			ChannelBuffer buffer = frame.getBinaryData();
			if(buffer.readable()){
				byte[] msg = new byte[buffer.readableBytes()];
				buffer.getBytes(buffer.readerIndex(), msg);
				try {
					this.websocketHandler.onPong(this.websocketChannel, msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(frame instanceof CloseWebSocketFrame){
			
		}else if(frame instanceof TextWebSocketFrame){
			String msg = ((TextWebSocketFrame)frame).getText();
			try {
				this.websocketHandler.onMessage(this.websocketChannel, msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(frame  instanceof BinaryWebSocketFrame){
			ChannelBuffer buffer = ((BinaryWebSocketFrame)frame).getBinaryData();
			if(buffer.readable()){
				byte[] msg = new byte[buffer.readableBytes()];
				buffer.getBytes(buffer.readerIndex(), msg);
				try {
					this.websocketHandler.onMessage(this.websocketChannel, msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
