package org.webmessage.channel;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.webmessage.Protocol;

public class BaseWebSocketChannel implements WebSocketChannel {
	
	private ChannelHandlerContext ctx;
	
	public BaseWebSocketChannel(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
	public WebSocketChannel sendMessage(String message) {
		
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			TextWebSocketFrame txtmesg = new TextWebSocketFrame(message);
			channel.write(txtmesg);
		}
		return this;

	}

	public WebSocketChannel sendMessage(byte[] message) {
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			BinaryWebSocketFrame binmsg = 
					new BinaryWebSocketFrame(ChannelBuffers.copiedBuffer(message));
			channel.write(binmsg);
		}
		return this;
	}

	public WebSocketChannel ping(byte[] message) {
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			PingWebSocketFrame frame = 
					new PingWebSocketFrame(ChannelBuffers.copiedBuffer(message));
			channel.write(frame);
		}
		return this;
	}

	public WebSocketChannel pong(byte[] message) {
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			PongWebSocketFrame frame = 
					new PongWebSocketFrame(ChannelBuffers.copiedBuffer(message));
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
