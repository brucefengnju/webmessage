package org.webmessage.http;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.Map;
/**
 * {@link HttpRequest}
 * 
 * @author brucefeng
 *
 */
public interface HttpRequest extends org.jboss.netty.handler.codec.http.HttpRequest {
	/**
	 * set and get body of http request.
	 * @param content
	 */
	void setBody(String content);
	void setBody(byte[] content);
	
	String getBody();
	byte[] getBodyBytes();
	/**
	 * add query param of http request. 
	 * @param name
	 * @param value
	 */
	
	Map<String,List<String>> getParameters();
		
	/**
	 * get cookie in http request.
	 * @return
	 */
	List<HttpCookie> getCookies();
	HttpCookie getCookie(String name);
	
	String getPath();
}
