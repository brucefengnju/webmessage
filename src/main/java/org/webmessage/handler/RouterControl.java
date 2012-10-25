package org.webmessage.handler;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

public interface RouterControl {
	RouterControlContext nextHandler();
	RouterControlContext nexthandler(HttpRequest request,HttpResponse response);
	RouterControlContext getContext();
}
