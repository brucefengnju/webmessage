package org.webmessage.channel;

import org.jboss.netty.handler.codec.http.HttpRequest;

public interface HttpChannel {
	
	HttpRequest getMessage();
	void close();
	String getProtocol();
	
}
