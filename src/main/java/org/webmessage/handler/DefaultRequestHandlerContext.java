package org.webmessage.handler;

import java.util.Iterator;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.webmessage.handler.http.HttpHandler;
import org.webmessage.helpers.HttpResponseHelper;

public class DefaultRequestHandlerContext implements RequestHandlerContext {
	private Iterator<HttpHandler> handlerIterator;
	private HttpRequest request;
	private HttpResponse response;
	private ChannelHandlerContext nettyContext;
	private boolean isEnd = false;
	
	public DefaultRequestHandlerContext(Iterator<HttpHandler> handlerIterator) {
		this.handlerIterator = handlerIterator;
	}
	public DefaultRequestHandlerContext(Iterator<HttpHandler> handlerIterator,
			HttpRequest request, HttpResponse response,ChannelHandlerContext nettyContext) {
		this.handlerIterator = handlerIterator;
		this.request = request;
		this.response = response;
		this.nettyContext = nettyContext;
	}

	public RequestHandlerContext nexthandler(HttpRequest request,
			HttpResponse response) {
		this.request = request;
		if(this.isEnd){
			return DefaultRequestHandlerContext.this;
		}
		
		if(this.handlerIterator.hasNext()){
			this.handlerIterator.next().handle(request, response, DefaultRequestHandlerContext.this);
		}else{
			response = HttpResponseHelper.helloWorldResponse();
			this.end(response);
		}
		
		return DefaultRequestHandlerContext.this;
	}
	public RequestHandlerContext end(HttpResponse response) {
		if(this.isEnd){
			return DefaultRequestHandlerContext.this;
		}
		boolean keepAlive = HttpHeaders.isKeepAlive(this.request); 
        if (keepAlive) {
            response.setHeader(HttpHeaders.Names.CONTENT_LENGTH, response.getContent().readableBytes());
            response.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        
		ChannelFuture future = this.nettyContext.getChannel().write(response);
		if(keepAlive){
			future.addListener(ChannelFutureListener.CLOSE);
		}
		
		this.isEnd = true;
		this.response = response;
		return DefaultRequestHandlerContext.this;
	}

	public Iterator<HttpHandler> getHandlerIterator() {
		return handlerIterator;
	}

	public void setHandlerIterator(Iterator<HttpHandler> handlerIterator) {
		this.handlerIterator = handlerIterator;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

}
