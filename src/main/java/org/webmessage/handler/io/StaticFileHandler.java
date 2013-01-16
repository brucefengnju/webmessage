package org.webmessage.handler.io;

import java.io.File;

import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

public class StaticFileHandler extends FileHandler {

	@Override
	public void notFound(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) throws Exception {

	}

	@Override
	public void error(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext, Exception e) {

	}

	@Override
	public void serve(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext, byte[] content)
			throws Exception {

	}

	@Override
	public void notServe(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) throws Exception {

	}

	@Override
	public File resolveFile(HttpRequest request) {
		return null;
	}

}
