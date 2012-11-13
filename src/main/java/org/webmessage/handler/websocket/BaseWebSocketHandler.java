package org.webmessage.handler.websocket;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.handler.WebSocketServerHandler;
import org.webmessage.helpers.HttpRequestHelper;

public class BaseWebSocketHandler implements WebSocketHandler {

	private ChannelHandlerContext ctx;
	private String webSocketPath;
	private WebSocketServerHandshaker handshaker;
	
	
	public BaseWebSocketHandler() {
	}
	

	public BaseWebSocketHandler(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	

	public BaseWebSocketHandler(ChannelHandlerContext ctx, String webSocketPath) {
		this.ctx = ctx;
		this.webSocketPath = webSocketPath;
	}


	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		if(HttpRequestHelper.isWebSocketRequest(request)){
			this.ctx = routerContext.getNettyContext();
			this.handshake(request);
			this.ctx.getPipeline().replace("messagehandler", "wshandler", new WebSocketServerHandler());
			
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
