package org.webmessage;

import java.util.concurrent.Future;

import org.webmessage.handler.http.HttpHandler;
import org.webmessage.handler.websocket.WebSocketHandler;

/**
 * An event based webmessageserver.
 * to create an instance, use {@link MessageServers.createServer(init)}.
 * @see MessageServers
 * @see HttpHandler
 * @see WebSocketHandler
 */
public interface WebMessageServer {
	
	/**
	 * Setup a server
	 * @return Future<? extends WebMessageServer>
	 */
	Future<? extends WebMessageServer> start();
	
	/**
	 * Stop a running server and release resources.
	 * @return Future<? extends WebMessageServer>
	 */
	Future<? extends WebMessageServer> stop();
	
	/**
	 * Add a HttpHandler. When a http request arrived ,this handler will be
	 * invoked firstly.
	 * The HttpHandler should either handler the request or pass the request to 
	 * the next handler.This is repeated until a HttpHandler return a HttpResponse.
	 * If there are no remainnig handlers,the WebMessageServer will return 404 NOT FOUND
	 * to the web browser.
	 * 
	 * @param handler HttpHandler
	 * @return current WebMessageServer
	 * @see HttpHandler
	 */
	WebMessageServer addHandler(HttpHandler handler);
	
	/**
	 * Add a HttpHandler that will only response to a certain path(e.g "/demo/test").
	 * @param path
	 * @param handler HttpHandler
	 * @return current WebMessageServer
	 */
	WebMessageServer addHandler(String path,HttpHandler handler);
	
	/**
	 * Add a WebSocketHandler which handles certain path websocket path.
	 * @param path
	 * @param handler WebSocketHandler
	 * @return current WebMessageServer
	 * @see WebSocketHandler
	 */
	WebMessageServer addHandler(String path,WebSocketHandler handler);
	
}
