package org.webmessage.http;

import java.net.HttpCookie;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpVersion;

public abstract class AbstractHttpRequest implements HttpRequest {
	private org.jboss.netty.handler.codec.http.HttpRequest nettyRequest;
	
	public AbstractHttpRequest(org.jboss.netty.handler.codec.http.HttpRequest nettyRequest){
		this.nettyRequest = nettyRequest;
	}
	
	public AbstractHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri){
		this.nettyRequest = 
				new org.jboss.netty.handler.codec.http.DefaultHttpRequest(httpVersion,method,uri);
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
		this.nettyRequest.setHeader(name, values);
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

	public abstract void setBody(String content);

	public abstract void setBody(byte[] content);

	public abstract String getBody();

	public abstract byte[] getBodyBytes();

	public abstract Map<String, List<String>> getParameters();

	public abstract List<HttpCookie> getCookies() ;

	public abstract HttpCookie getCookie(String name);

	public abstract String getPath();

	public org.jboss.netty.handler.codec.http.HttpRequest getNettyRequest() {
		return nettyRequest;
	}

	public void setNettyRequest(
			org.jboss.netty.handler.codec.http.HttpRequest nettyRequest) {
		this.nettyRequest = nettyRequest;
	}
	
}
