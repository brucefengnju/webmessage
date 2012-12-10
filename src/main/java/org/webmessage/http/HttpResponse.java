package org.webmessage.http;

import java.net.HttpCookie;
import java.nio.charset.Charset;
/**
 * HttpResponse 
 * @author brucefeng
 *
 */
public interface HttpResponse extends org.jboss.netty.handler.codec.http.HttpResponse{
	/**
	 * get and set status code of http response
	 * @return
	 */
	int getStatusCode();
	void setStatusCode(int statusCode);

	/**
	 * get and set content of http resopnse
	 * @param content
	 */
	void setContent(String content);
	void setContent(byte[] content);
	String getContentString();
	byte[] getContentBytes();
	
	/**
	 * get and set charset of reponse, default charset is utf-8 
	 * @param charset
	 */
	void setCharset(Charset charset);
	Charset getCharset();
	
	void addCookie(HttpCookie cookie);
	void addCookie(String name,String value);
	/**
	 * 
	 * @return true when httpresponse need to write back
	 */
	boolean  isEnd();
	
	/**
	 * write response back to client  
	 */
	void feedback();
}
