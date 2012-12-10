package org.webmessage.handler.websocket;

import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.helpers.HttpRequestHelper;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

public class BaseWebSocketHandler implements WebSocketHandler {

	/**
	private ChannelHandlerContext ctx;
	private WebSocketServerHandshaker handshaker;
	**/
	public BaseWebSocketHandler() {
	}
	/**
	public BaseWebSocketHandler(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	

	public BaseWebSocketHandler(ChannelHandlerContext ctx, String webSocketPath) {
		this.ctx = ctx;
	}

	 **/
	
	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		if(HttpRequestHelper.isWebSocketRequest(request)){
			//this.handshake(request);
			routerContext.convertToWebsocketHandler(this);
		}else{
			routerContext.nextHandler(request, response);
		}
	}

	public void onOpen(WebSocketChannel channel) {
		
	}

	public void onClose(WebSocketChannel channel) {
		
	}

	public void onMessage(WebSocketChannel channel,String message) {
		
	}
	
	public void onMessage(WebSocketChannel channel, byte[] message) {
		
	}


	public void onError(WebSocketChannel channel) {
		
	}
	
	public void onPong(WebSocketChannel channel, byte[] message) {
		
	}


	public void onPing(WebSocketChannel channel, byte[] message) {
		
	}
	
	/**
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
	 **/
}
