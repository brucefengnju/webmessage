package org.webmessage.handler;

import java.util.Iterator;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

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

	public RequestHandlerContext sendNext(HttpRequest request) {

		if(this.isEnd){
			return DefaultRequestHandlerContext.this;
		}
		if(this.handlerIterator.hasNext()){
			this.handlerIterator.next().handle(request, this.response, DefaultRequestHandlerContext.this);
		}else{
			this.end(response);
		}
		return DefaultRequestHandlerContext.this;
	}

	public RequestHandlerContext nexthandler(HttpRequest request,
			HttpResponse response) {
		
		if(this.isEnd){
			return DefaultRequestHandlerContext.this;
		}
		
		if(this.handlerIterator.hasNext()){
			this.handlerIterator.next().handle(request, response, DefaultRequestHandlerContext.this);
		}else{
			this.end(response);
		}
		
		return DefaultRequestHandlerContext.this;
	}
	public RequestHandlerContext end(HttpResponse response) {
		if(this.isEnd){
			return DefaultRequestHandlerContext.this;
		}
		
		this.nettyContext.getChannel().write(response);
		this.isEnd = true;
		
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
