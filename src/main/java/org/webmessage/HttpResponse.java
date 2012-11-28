package org.webmessage;

import java.net.HttpCookie;

public interface HttpResponse extends org.jboss.netty.handler.codec.http.HttpResponse{
	int getStatusCode();
	void setStatusCode();

	void setContent(String content);
	void setContent(byte[] content);
	String getContentString();
	byte[] getContentBytes();
	
	void addCookie(HttpCookie cookie);
	
	void feedback();
	void end();

}
