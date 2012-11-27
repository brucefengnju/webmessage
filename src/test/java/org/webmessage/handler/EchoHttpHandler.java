package org.webmessage.handler;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.webmessage.handler.http.HttpHandler;

public class EchoHttpHandler implements HttpHandler {

	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		response.setContent(ChannelBuffers.copiedBuffer("this is echo".getBytes()));
	}

}
