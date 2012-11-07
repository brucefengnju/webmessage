package org.webmessage.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.webmessage.helpers.HttpRequestHelper;

public class WebSocketHandshakeHandler implements HttpHandler {
	private ChannelHandlerContext ctx;
	private String webSocketPath;
	private WebSocketServerHandshaker handshaker;
	
	public WebSocketHandshakeHandler() {
		
	}

	public WebSocketHandshakeHandler(ChannelHandlerContext ctx,
			String webSocketPath) {
		super();
		this.ctx = ctx;
		this.webSocketPath = webSocketPath;
	}

	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		if(HttpRequestHelper.isWebSocketRequest(request)){
			WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
		              getWebSocketLocation(request), null, false);
		      handshaker = wsFactory.newHandshaker(request);
		      if (handshaker == null) {
		          wsFactory.sendUnsupportedWebSocketVersionResponse(ctx.getChannel());
		      } else {
		         handshaker.handshake(ctx.getChannel(), request).addListener(WebSocketServerHandshaker.HANDSHAKE_LISTENER);
		      }
		}else{
			routerContext.nexthandler(request, response);
		}

	}
	
	private String getWebSocketLocation(HttpRequest request){
		return "ws://" + request.getHeader(HttpHeaders.Names.HOST) + this.webSocketPath;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public String getWebSocketPath() {
		return webSocketPath;
	}

	public void setWebSocketPath(String webSocketPath) {
		this.webSocketPath = webSocketPath;
	}

	public WebSocketServerHandshaker getHandshaker() {
		return handshaker;
	}

	public void setHandshaker(WebSocketServerHandshaker handshaker) {
		this.handshaker = handshaker;
	}

}
