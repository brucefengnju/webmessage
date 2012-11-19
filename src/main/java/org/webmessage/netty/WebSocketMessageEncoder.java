package org.webmessage.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class WebSocketMessageEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if(msg instanceof String){
			TextWebSocketFrame frame = new TextWebSocketFrame((String)msg);
			return frame;
		}else if(msg instanceof byte[]){
			ChannelBuffer buffer = ChannelBuffers.copiedBuffer((byte[])msg);
			BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buffer);
			return frame;
		}
		return msg;
	}

}
