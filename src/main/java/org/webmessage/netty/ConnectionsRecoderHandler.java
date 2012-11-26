package org.webmessage.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

public class ConnectionsRecoderHandler extends SimpleChannelUpstreamHandler {
	
	private ChannelGroup openedChannels;

	public ConnectionsRecoderHandler() {
		openedChannels = new DefaultChannelGroup();
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		this.openedChannels.add(e.getChannel());
	}
	
	public void closeAllOpenChannels(){
		this.openedChannels.close().awaitUninterruptibly();
	}
	
	
}
