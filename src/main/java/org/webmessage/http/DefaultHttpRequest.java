package org.webmessage.http;

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;
import org.webmessage.helpers.CookieWrap;

public class DefaultHttpRequest implements HttpRequest {
	private org.jboss.netty.handler.codec.http.HttpRequest nettyRequest;
	
	public DefaultHttpRequest(org.jboss.netty.handler.codec.http.HttpRequest nettyRequest){
		this.nettyRequest = nettyRequest;
	}

	public HttpMethod getMethod() {
		return this.nettyRequest.getMethod();
	}

	public void setMethod(HttpMethod method) {
		this.nettyRequest.setMethod(method);

	}

	public String getUri() {
		return this.nettyRequest.getUri();
	}

	public void setUri(String uri) {
		this.nettyRequest.setUri(uri);
	}

	public String getHeader(String name) {
		return this.nettyRequest.getHeader(name);
	}

	public List<String> getHeaders(String name) {
		return this.nettyRequest.getHeaders(name);
	}

	public List<Entry<String, String>> getHeaders() {
		return this.nettyRequest.getHeaders();
	}

	public boolean containsHeader(String name) {
		return this.nettyRequest.containsHeader(name);
	}

	public Set<String> getHeaderNames() {
		return this.nettyRequest.getHeaderNames();
	}

	public HttpVersion getProtocolVersion() {
		return this.nettyRequest.getProtocolVersion();
	}

	public void setProtocolVersion(HttpVersion version) {
		this.nettyRequest.setProtocolVersion(version);
	}

	public ChannelBuffer getContent() {
		return this.nettyRequest.getContent();
	}

	public void setContent(ChannelBuffer content) {
		this.nettyRequest.setContent(content);
	}

	public void addHeader(String name, Object value) {
		this.nettyRequest.addHeader(name, value);

	}

	public void setHeader(String name, Object value) {
		this.nettyRequest.setHeader(name, value);
	}

	public void setHeader(String name, Iterable<?> values) {
		this.setHeader(name, values);
	}

	public void removeHeader(String name) {
		this.nettyRequest.removeHeader(name);

	}

	public void clearHeaders() {
		this.nettyRequest.clearHeaders();
	}

	@Deprecated
	public long getContentLength() {
		return this.nettyRequest.getContentLength();
	}

	@Deprecated
	public long getContentLength(long defaultValue) {
		return this.nettyRequest.getContentLength(defaultValue);
	}

	public boolean isChunked() {
		return this.nettyRequest.isChunked();
	}

	public void setChunked(boolean chunked) {
		this.nettyRequest.setChunked(chunked);
	}

	@Deprecated
	public boolean isKeepAlive() {
		return this.nettyRequest.isKeepAlive();
	}

	public void setBody(String content) {
		this.setBody(content.getBytes());
	}

	public void setBody(byte[] content) {
		this.nettyRequest.setContent(ChannelBuffers.copiedBuffer(content));
	}

	public String getBody() {
		return this.nettyRequest.getContent().toString(CharsetUtil.UTF_8);
	}

	public byte[] getBodyBytes() {
		ChannelBuffer buffer = this.nettyRequest.getContent();
		byte[] content = new byte[buffer.readableBytes()];
		buffer.getBytes(buffer.readerIndex(), content);
		return content;
	}

	public void addQueryParam(String name, String value) {
		

	}

	public Map<String, List<String>> getParameters() {
		// TODO Auto-generated method stub
		
		return null;
	}

	public List<HttpCookie> getCookies() {
		String value = this.getHeader("Cookie");
		Set<Cookie> cookies = new CookieDecoder().decode(value);
		List<HttpCookie> httpCookies = new ArrayList<HttpCookie>();
		Iterator<Cookie> it = cookies.iterator();
		while(it.hasNext()){
			httpCookies.add(CookieWrap.parseCookie(it.next()));
		}
		
		return httpCookies.iterator().hasNext()?httpCookies:Collections.EMPTY_LIST;
	}

	public HttpCookie getCookie(String name) {
		Iterator<HttpCookie> it = this.getCookies().iterator();
		while(it.hasNext()){
			HttpCookie cookie = it.next();
			if(cookie.getName().equals(name)){
				return cookie;
			}
		}
		return null;
	}

}
