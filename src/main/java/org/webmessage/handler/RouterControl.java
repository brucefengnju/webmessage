package org.webmessage.handler;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

public interface RouterControl {
	RequestHandlerContext nextHandler();
	RequestHandlerContext nexthandler(HttpRequest request,HttpResponse response);
	RequestHandlerContext getContext();
}