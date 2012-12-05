package org.webmessage.netty;

import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.Iterator;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.webmessage.handler.DefaultRequestHandlerContext;
import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.handler.http.HttpHandler;
import org.webmessage.http.DefaultHttpRequest;
import org.webmessage.http.DefaultHttpResponse;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

/**
 * This handler dispatches request to certain httphandler when server recive httprequest.  
 * @author brucefeng
 *
 */
public class NettyRequestHandler extends SimpleChannelUpstreamHandler {
	
	private ChannelHandlerContext nettyContext;
	private Iterator<HttpHandler> handlerIterator; 
	private RequestHandlerContext requestContext;
	
	private HttpRequest httpRequest;
	private HttpResponse httpResponse;

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
		
		if(e.getMessage() instanceof org.jboss.netty.handler.codec.http.HttpRequest){
			
			org.jboss.netty.handler.codec.http.HttpRequest nettyRequest = (org.jboss.netty.handler.codec.http.HttpRequest)e.getMessage();
			org.jboss.netty.handler.codec.http.HttpResponse nettyRresponse = new org.jboss.netty.handler.codec.http.DefaultHttpResponse(HTTP_1_1,OK);
			this.nettyContext = ctx;
			this.httpRequest = new DefaultHttpRequest(nettyRequest);
			this.httpResponse = new DefaultHttpResponse(nettyRresponse);
			
			this.requestContext = new DefaultRequestHandlerContext(this.handlerIterator,
					this.httpRequest,this.httpResponse,this.nettyContext);
			this.requestContext.nextHandler(this.httpRequest, this.httpResponse);
			
		}else{
			super.messageReceived(ctx, e);
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


	public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public RequestHandlerContext getRequestContext() {
		return requestContext;
	}


	public void setRequestContext(RequestHandlerContext requestContext) {
		this.requestContext = requestContext;
	}
	
}
