package org.webmessage.handler.io;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;
import org.webmessage.util.MimeTypes;

public class StaticFileHandler extends FileHandler {
	
	private File root;
	
	public StaticFileHandler(Executor executor){
		super(executor);
		this.root  = new File("/");
	}
	public StaticFileHandler(Executor executor,File root){
		super(executor);
		this.root = root;
	}
	public StaticFileHandler(String rootDir){
		this(Executors.newSingleThreadExecutor(),new File(rootDir));
		
	}
	public StaticFileHandler(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) {
		super(request, response, routerContext);
	}
	public StaticFileHandler(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext,Executor executor) {
		super(request, response, routerContext,executor);
	}

	@Override
	public void notFound(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) throws Exception {
		response.setStatusCode(404);
		response.setContent("NOT FOUND");
		response.feedback(routerContext);
		routerContext.nextHandler();

	}

	@Override
	public void error(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext, Exception e) {
		response.addHeader("Content-Type", "text/plain");
		response.setContent(e.getMessage());
		try {
			response.feedback(routerContext);
			routerContext.nextHandler();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	@Override
	public void serve(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext, byte[] content)
			throws Exception {
		File file = this.resolveFile(request);
		String path = file.getPath();
		String key = path.substring(path.lastIndexOf("."));
		response.addHeader("Content-Type", MimeTypes.getMimeType(key));
		response.addHeader("Content-Length", content.length);
		response.setContent(content);
		response.feedback(routerContext);
		routerContext.nextHandler();
	}

	@Override
	public void notServe(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) throws Exception {
		response.setStatusCode(406);
		response.setContent("NOT SERVE");
		response.feedback(routerContext);
		routerContext.nextHandler();
	}

	@Override
	public File resolveFile(HttpRequest request) {
		
		try {
			String path = request.getPath();
			URL url = new URL(path);
			String filePath = url.getFile().split("[?]")[0];
			return new File(this.root,filePath);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
