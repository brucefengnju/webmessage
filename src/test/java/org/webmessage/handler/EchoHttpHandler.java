package org.webmessage.handler;

import org.webmessage.handler.http.HttpHandler;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

public class EchoHttpHandler implements HttpHandler {

	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		response.setContent("this is echo");
		//routerContext.end(response);
		response.feedback(routerContext);
		routerContext.nextHandler();
	}

}
