package org.webmessage;

import java.util.concurrent.Future;

import org.webmessage.handler.http.HttpHandler;
import org.webmessage.handler.websocket.WebSocketHandler;

public interface WebMessageServer {
	
	Future<? extends WebMessageServer> start();
	Future<? extends WebMessageServer> stop();
	
	WebMessageServer addHandler(HttpHandler handler);
	WebMessageServer addHandler(String path,HttpHandler handler);
	WebMessageServer addHandler(String path,WebSocketHandler handler);
	
}
