package org.webmessage.netty;

import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.Iterator;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.webmessage.handler.DefaultRequestHandlerContext;
import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.handler.http.HttpHandler;

/**
 * This handler dispatches request to certain httphandler when server recive httprequest.  
 * @author brucefeng
 *
 */
public class NettyRequestHandler extends SimpleChannelUpstreamHandler {
	
	private ChannelHandlerContext nettyContext;
	private Iterator<HttpHandler> handlerIterator; 
	private HttpRequest request;
	private HttpResponse response;
	private RequestHandlerContext requestContext;

	public NettyRequestHandler(Iterator<HttpHandler> handlerIterator) {

		this.handlerIterator = handlerIterator;
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		ctx.getChannel().close();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		
		if(e.getMessage() instanceof HttpRequest){
			
			this.request = (HttpRequest)e.getMessage();
			this.response = new DefaultHttpResponse(HTTP_1_1,OK);
			this.nettyContext = ctx;
			this.requestContext = new DefaultRequestHandlerContext(this.handlerIterator,
					this.request,this.response,this.nettyContext);
			this.requestContext.nextHandler(this.request, this.response);
			
		}
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
