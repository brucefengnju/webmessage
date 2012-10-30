package org.webmessage.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

public class PathPatternHandler implements HttpHandler{
	private Pattern pathPattern;
	private HttpHandler handler;
	
	public PathPatternHandler(String path,HttpHandler handler){
		this.pathPattern = Pattern.compile(path);
		this.handler = handler;
	}
	public PathPatternHandler(Pattern path,HttpHandler handler){
		this.pathPattern = path;
		this.handler = handler;
	}
	public Pattern getPathPattern() {
		return pathPattern;
	}
	public void setPathPattern(Pattern pathPattern) {
		this.pathPattern = pathPattern;
	}
	public HttpHandler getHandler() {
		return handler;
	}
	public void setHandler(HttpHandler handler) {
		this.handler = handler;
	}
	
	public void handle(HttpRequest request, HttpResponse response,RequestHandlerContext context) {
		
		Matcher m = this.pathPattern.matcher(request.getUri());
		if(m.matches()){
			this.handler.handle(request, response,context);
		}else{
			context.nexthandler(request,response);
		}
	}
	
}
