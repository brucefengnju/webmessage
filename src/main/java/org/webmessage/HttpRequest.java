package org.webmessage;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface HttpRequest extends org.jboss.netty.handler.codec.http.HttpRequest {
	void setBody(String content);
	void setBody(byte[] content);
	
	String getBody();
	byte[] getBodyBytes();
	
	void addQqueryParam(String name,String value);
	
	Map<String,List<String>> getParameters();
	
	
	URI uri();
	
	List<HttpCookie> getCookies();
	HttpCookie getCookie(String name);
}
