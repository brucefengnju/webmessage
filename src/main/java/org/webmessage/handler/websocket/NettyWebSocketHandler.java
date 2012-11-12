package org.webmessage.handler.websocket;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.helpers.HttpRequestHelper;

public class NettyWebSocketHandler implements WebSocketHandler {

	private ChannelHandlerContext ctx;
	private String webSocketPath;
	private WebSocketServerHandshaker handshaker;
	
	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		if(HttpRequestHelper.isWebSocketRequest(request)){
			this.handshake(request);
			//handle the websocket request
			//在此处添加ChannelHandler
		}else{
			routerContext.nexthandler(request, response);
		}
	}

	public void onOpen(WebSocketChannel channel) {

	}

	public void onClose(WebSocketChannel channel) {

	}

	public void onMessage(WebSocketChannel channel) {

	}

	public void onError(WebSocketChannel channel) {

	}
	public void handshake(HttpRequest request){
		if(HttpRequestHelper.isWebSocketRequest(request)){
			WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
		              getWebSocketLocation(request), null, false);
		      handshaker = wsFactory.newHandshaker(request);
		      if (handshaker == null) {
		          wsFactory.sendUnsupportedWebSocketVersionResponse(ctx.getChannel());
		      } else {
		        handshaker.handshake(ctx.getChannel(), request).addListener(WebSocketServerHandshaker.HANDSHAKE_LISTENER);
		      }
		}
	}
	private String getWebSocketLocation(HttpRequest request){
		return "ws://" + request.getHeader(HttpHeaders.Names.HOST) + this.webSocketPath;
	}


}
