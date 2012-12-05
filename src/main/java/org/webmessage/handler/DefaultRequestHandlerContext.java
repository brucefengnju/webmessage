package org.webmessage.handler;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import java.util.Iterator;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.http.HttpHandler;
import org.webmessage.handler.websocket.WebSocketHandler;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

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
	

	public RequestHandlerContext nextHandler() {
		return nextHandler(this.defaultRequest,this.defaultResponse);
	}

	public RequestHandlerContext nextHandler(HttpRequest request,HttpResponse response) {
		this.defaultRequest = request;
		this.defaultResponse = response;
		if(this.defaultResponse.end()){
			this.end(this.defaultResponse);
			return DefaultRequestHandlerContext.this;
		}

		if(this.handlerIterator.hasNext()){
			this.handlerIterator.next().handle(request, response, DefaultRequestHandlerContext.this);
		}else{
			if(response.end()){
				this.end(response);

			}else{
				response.setStatus(HttpResponseStatus.NOT_FOUND);
				response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
				response.setContent("NOT FOUND");
				this.end(response);
			}
			
		}

		return DefaultRequestHandlerContext.this;
	}
	
	public RequestHandlerContext end(HttpResponse response) {
		boolean keepAlive = HttpHeaders.isKeepAlive(this.defaultRequest); 
        if (keepAlive) {
        	if(response == null){
        		System.out.println("error");
        	}
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
	
	public WebSocketChannel convertToWebsocketHandler(HttpHandler handler) {
		return null;
	}

}
