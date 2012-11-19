package org.webmessage.channel;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.webmessage.Protocol;

public class BaseWebSocketChannel implements WebSocketChannel {
	
	private ChannelHandlerContext ctx;
	private boolean open;
	
	public BaseWebSocketChannel(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		this.open = false; 
	}
	
	public WebSocketChannel sendMessage(String message) {
		
		//return this.sendMessage(message.getBytes());
		Channel channel = this.ctx.getChannel();
		if(channel.isWritable()){
			//channel.write(new TextWebSocketFrame(message));
			channel.write(message);
			//channel.write(new BinaryWebSocketFrame(ChannelBuffers.copiedBuffer(message.getBytes())));
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

	public WebSocketChannel close() {
		closeChannel();
		return this;
	}

	public boolean isOpen() {
		return this.open;
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
