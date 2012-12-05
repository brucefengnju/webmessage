package org.webmessage.helpers;

import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.webmessage.http.HttpRequest;

public class HttpRequestHelper {
	public static boolean isWebSocketRequest(HttpRequest req){
	       return (HttpHeaders.Values.UPGRADE.equalsIgnoreCase(req.getHeader(HttpHeaders.Names.CONNECTION))
	        		&& HttpHeaders.Values.WEBSOCKET.equalsIgnoreCase(req.getHeader(HttpHeaders.Names.UPGRADE)));
	 
	}

}
