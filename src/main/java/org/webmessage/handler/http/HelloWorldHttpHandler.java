package org.webmessage.handler.http;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;
import org.webmessage.handler.RequestHandlerContext;

public class HelloWorldHttpHandler implements HttpHandler {

	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		response = new DefaultHttpResponse(HTTP_1_1, OK);
		
		response.setContent(ChannelBuffers.copiedBuffer("HelloWorld", CharsetUtil.UTF_8));
        response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
        routerContext.end(response);
	}

}
