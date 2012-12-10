package org.webmessage.handler.http;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

public class HelloWorldHttpHandler implements HttpHandler {

	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		response.setContent("HelloWorld");
        response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
        routerContext.end(response);
	}

}
