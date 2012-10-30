package org.webmessage.handler;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

public interface RequestHandlerContext {
	//RequestHandlerContext sendNext(HttpRequest request,HttpResponse response);
	RequestHandlerContext nexthandler(HttpRequest request,HttpResponse response);
	RequestHandlerContext end(HttpResponse response);
}
