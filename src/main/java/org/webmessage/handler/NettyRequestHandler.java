package org.webmessage.handler;

import java.util.Iterator;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

public class NettyRequestHandler extends SimpleChannelUpstreamHandler {
	
	private ChannelHandlerContext nettyContext;
	private Iterator<HttpHandler> handlerIterator; 
	private HttpRequest request;
	private HttpResponse response;
	private RequestHandlerContext requestContext;
	
	public NettyRequestHandler() {
		
	}
	

	public NettyRequestHandler(Iterator<HttpHandler> handlerIterator) {

		this.handlerIterator = handlerIterator;
	}


	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		
		super.channelClosed(ctx, e);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		ctx.getChannel().close();
	}

	@Override
	public void handleUpstream(ChannelHandlerContext arg0, ChannelEvent arg1)
			throws Exception {
		
		super.handleUpstream(arg0, arg1);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {

		this.nettyContext = ctx;
		this.requestContext = new DefaultRequestHandlerContext(this.handlerIterator,
				this.request,this.response,this.nettyContext);
		this.request = (HttpRequest)e.getMessage();
		this.requestContext.nexthandler(this.request, this.response);
		System.out.println(this.response);
		
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
		super.writeComplete(ctx, e);
	}


	public ChannelHandlerContext getNettyContext() {
		return nettyContext;
	}


	public void setNettyContext(ChannelHandlerContext nettyContext) {
		this.nettyContext = nettyContext;
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


	public RequestHandlerContext getRequestContext() {
		return requestContext;
	}


	public void setRequestContext(RequestHandlerContext requestContext) {
		this.requestContext = requestContext;
	}
	
	
}
