package org.webmessage.handler;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import java.util.Iterator;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.util.CharsetUtil;
import org.webmessage.channel.WebSocketChannel;
import org.webmessage.handler.http.HttpHandler;
import org.webmessage.handler.websocket.WebSocketHandler;

public class DefaultRequestHandlerContext implements RequestHandlerContext {
	private Iterator<HttpHandler> handlerIterator;
	private HttpRequest defaultRequest;
	private HttpResponse defaultResponse;
	private ChannelHandlerContext nettyContext;
	private boolean isEnd = false;

	public DefaultRequestHandlerContext(Iterator<HttpHandler> handlerIterator,
			HttpRequest request, HttpResponse response,ChannelHandlerContext nettyContext) {
		this.handlerIterator = handlerIterator;
		this.defaultRequest = request;
		this.defaultResponse = response;
		this.nettyContext = nettyContext;
	}

	public RequestHandlerContext nextHandler() {
		return nextHandler(this.defaultRequest,this.defaultResponse);
	}

	public RequestHandlerContext nextHandler(HttpRequest request,
			HttpResponse response) {
		this.defaultRequest = request;
		this.defaultResponse = response;
		if(this.isEnd){
			return DefaultRequestHandlerContext.this;
		}
		
		if(this.handlerIterator.hasNext()){
			this.handlerIterator.next().handle(request, response, DefaultRequestHandlerContext.this);
		}else{
			if(response.getContentLength() == 0){
				response.setStatus(HttpResponseStatus.NOT_FOUND);
				response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
				response.setContent(ChannelBuffers.copiedBuffer("NOT FOUND", CharsetUtil.UTF_8));
			}
			this.end(response);
		}
		
		return DefaultRequestHandlerContext.this;
	}
	
	public RequestHandlerContext end(HttpResponse response) {
		if(this.isEnd){
			return DefaultRequestHandlerContext.this;
		}
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
		
		this.isEnd = true;
		this.defaultResponse = response;
		return DefaultRequestHandlerContext.this;
	}

	public Iterator<HttpHandler> getHandlerIterator() {
		return handlerIterator;
	}

	public void setHandlerIterator(Iterator<HttpHandler> handlerIterator) {
		this.handlerIterator = handlerIterator;
	}

	public HttpRequest getRequest() {
		return defaultRequest;
	}

	public void setRequest(HttpRequest request) {
		this.defaultRequest = request;
	}

	public HttpResponse getResponse() {
		return defaultResponse;
	}

	public void setResponse(HttpResponse response) {
		this.defaultResponse = response;
	}
	public ChannelHandlerContext getNettyContext() {
		return nettyContext;
	}
	public void setNettyContext(ChannelHandlerContext nettyContext) {
		this.nettyContext = nettyContext;
	}
	public WebSocketChannel convertToWebsocketHandler(WebSocketHandler handler) {
		return null;
	}

}
