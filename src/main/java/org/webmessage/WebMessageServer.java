package org.webmessage;

import java.util.Map;
import java.util.concurrent.Future;

import org.webmessage.handler.HttpHandler;
import org.webmessage.handler.WebSocketHandler;

public interface WebMessageServer {
	
	Future<? extends WebMessageServer> start();
	Future<? extends WebMessageServer> stop();
	
	WebMessageServer addHandler(HttpHandler handler);
	WebMessageServer addHandler(String path,HttpHandler handler);
	WebMessageServer addHandler(String path,WebSocketHandler handler);
	WebMessageServer addHttpHandlers(Map<String,HttpHandler> handlers);
	WebMessageServer addWebSocketHandlers(Map<String,WebSocketHandler>handlers);
	
}