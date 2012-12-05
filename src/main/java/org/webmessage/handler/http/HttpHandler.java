package org.webmessage.handler.http;

import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

public interface HttpHandler {
	void handle(HttpRequest request,HttpResponse response,RequestHandlerContext routerContext);
}
