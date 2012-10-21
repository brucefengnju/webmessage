package org.webmessage.handler;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

public interface HttpHandler {
	void handle(HttpRequest request,HttpResponse response);
}
