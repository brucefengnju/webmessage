package org.webmessage.channel;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import org.webmessage.Protocol;

public class BaseWebSocketChannel implements WebSocketChannel {
	
	private ChannelHandlerContext ctx;
	
	public BaseWebSocketChannel(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
	public WebSocketChannel sendMessage(String message) {
		
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			channel.write(message);
		}
		return this;

	}

	public WebSocketChannel sendMessage(byte[] message) {
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			channel.write(message);
		}
		return this;
	}

	public WebSocketChannel ping(byte[] message) {
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			PingWebSocketFrame frame = new PingWebSocketFrame(ChannelBuffers.copiedBuffer(message));
			channel.write(frame);
		}
		return this;
	}

	public WebSocketChannel pong(byte[] message) {
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			PongWebSocketFrame frame = new PongWebSocketFrame(ChannelBuffers.copiedBuffer(message));
			channel.write(frame);
		}
		return this;
	}
	public WebSocketChannel close() {
		closeChannel();
		return this;
	}

	public String getProtocol() {
		return Protocol.WEBSOCKET_PROTOCOL;
	}
	
	protected void closeChannel(){
		ctx.getChannel().write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
}
