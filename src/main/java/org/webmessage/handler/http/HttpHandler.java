package org.webmessage.handler.http;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.webmessage.handler.RequestHandlerContext;

public interface HttpHandler {
	void handle(HttpRequest request,HttpResponse response,RequestHandlerContext routerContext);
}
