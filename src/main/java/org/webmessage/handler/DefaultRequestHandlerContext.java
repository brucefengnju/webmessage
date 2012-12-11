package org.webmessage.handler;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import java.util.Iterator;
import java.util.List;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.webmessage.channel.BaseWebSocketChannel;
import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.http.HttpHandler;
import org.webmessage.handler.websocket.WebSocketHandler;
import org.webmessage.helpers.HttpRequestHelper;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;
import org.webmessage.netty.WebSocketServerHandler;

public class DefaultRequestHandlerContext implements RequestHandlerContext {
	private Iterator<HttpHandler> handlerIterator;
	private HttpRequest defaultRequest;
	private HttpResponse defaultResponse;
	
	private ChannelHandlerContext nettyContext;
	
	public DefaultRequestHandlerContext(Iterator<HttpHandler> handlerIterator,
			org.jboss.netty.handler.codec.http.HttpRequest request, org.jboss.netty.handler.codec.http.HttpResponse response,ChannelHandlerContext nettyContext) {
		this.handlerIterator = handlerIterator;
		this.nettyContext = nettyContext;
	}
	
	public DefaultRequestHandlerContext(Iterator<HttpHandler> handlerIterator,
			HttpRequest request, HttpResponse response,ChannelHandlerContext nettyContext) {
		this.handlerIterator = handlerIterator;
		this.nettyContext = nettyContext;
		this.defaultRequest = request;
		this.defaultResponse = response;
	}

	public DefaultRequestHandlerContext(List<HttpHandler> handlers,
			HttpRequest request, HttpResponse response,ChannelHandlerContext nettyContext) {
		this.handlerIterator = handlers.iterator();
		this.nettyContext = nettyContext;
		this.defaultRequest = request;
		this.defaultResponse = response;
	}
	public RequestHandlerContext nextHandler() {
		return nextHandler(this.defaultRequest,this.defaultResponse);
	}

	public RequestHandlerContext nextHandler(HttpRequest request,HttpResponse response) {
		this.defaultRequest = request;
		this.defaultResponse = response;
		
		if(this.handlerIterator.hasNext()){
			this.handlerIterator.next().handle(request, response, DefaultRequestHandlerContext.this);
		}else{
			if(response.isEnd()){
				this.end(response);
			}else{
				response.setStatusCode(404);
				response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
				response.setContent("NOT FOUND");
				this.end(response);
			}
			
		}

		return DefaultRequestHandlerContext.this;
	}
	
	public RequestHandlerContext end(HttpResponse response) {
		if(response.isEnd()){
			return DefaultRequestHandlerContext.this; 
		}
		boolean keepAlive = HttpHeaders.isKeepAlive(this.defaultRequest); 
        if (keepAlive) {
            response.setHeader(HttpHeaders.Names.CONTENT_LENGTH, response.getContent().readableBytes());
            response.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        
		ChannelFuture future = this.nettyContext.getChannel().write(response);
		if(keepAlive){
			future.addListener(ChannelFutureListener.CLOSE);
		}
		this.defaultResponse = response;
		return DefaultRequestHandlerContext.this;
	}

	public Iterator<HttpHandler> getHandlerIterator() {
		return handlerIterator;
	}

	public void setHandlerIterator(Iterator<HttpHandler> handlerIterator) {
		this.handlerIterator = handlerIterator;
	}

	public HttpRequest getDefaultRequest() {
		return defaultRequest;
	}
	public void setDefaultRequest(HttpRequest defaultRequest) {
		this.defaultRequest = defaultRequest;
	}
	public HttpResponse getDefaultResponse() {
		return defaultResponse;
	}
	public void setDefaultResponse(HttpResponse defaultResponse) {
		this.defaultResponse = defaultResponse;
	}
	public ChannelHandlerContext getNettyContext() {
		return nettyContext;
	}
	public void setNettyContext(ChannelHandlerContext nettyContext) {
		this.nettyContext = nettyContext;
	}
	
	public WebSocketChannel convertToWebsocketHandler(WebSocketHandler handler) {
		if(HttpRequestHelper.isWebSocketRequest(this.defaultRequest)){
			this.handshake(this.defaultRequest);
			WebSocketChannel channel = new BaseWebSocketChannel(this.nettyContext);
			this.nettyContext.getPipeline().replace("messagehandler", "wshandler", new WebSocketServerHandler(handler,channel));
			handler.onOpen(channel);
			return channel;
		}
		return null;

	}

	private void handshake(HttpRequest request){
		if(HttpRequestHelper.isWebSocketRequest(request)){
			WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
		              getWebSocketLocation(request), null, false);
			WebSocketServerHandshaker  handshaker = wsFactory.newHandshaker(request);
		      if (handshaker == null) {
		          wsFactory.sendUnsupportedWebSocketVersionResponse(this.nettyContext.getChannel());
		      } else {
		        handshaker.handshake(this.nettyContext.getChannel(), request).addListener(WebSocketServerHandshaker.HANDSHAKE_LISTENER);
		      }
		}
	}
	private String getWebSocketLocation(HttpRequest request){
		return "ws://" + request.getHeader(HttpHeaders.Names.HOST);
	}

}
