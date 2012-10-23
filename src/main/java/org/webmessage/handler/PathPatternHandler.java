package org.webmessage.handler;

import java.util.regex.Pattern;

public abstract class PathPatternHandler implements HttpHandler{
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
	
}
