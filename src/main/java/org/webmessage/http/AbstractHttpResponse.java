package org.webmessage.http;

import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.webmessage.handler.RequestHandlerContext;

public abstract class AbstractHttpResponse implements HttpResponse {

	private org.jboss.netty.handler.codec.http.HttpResponse nettyResponse;
	
	public AbstractHttpResponse(org.jboss.netty.handler.codec.http.HttpResponse nettyResponse){
		this.nettyResponse = nettyResponse;
	}
	public AbstractHttpResponse(HttpVersion version, HttpResponseStatus status){
		this.nettyResponse = new org.jboss.netty.handler.codec.http.DefaultHttpResponse(version,status);
	}
	public HttpResponseStatus getStatus() {
		return this.nettyResponse.getStatus();
	}

	public void setStatus(HttpResponseStatus status) {
		this.nettyResponse.setStatus(status);
	}

	public String getHeader(String name) {
		return this.nettyResponse.getHeader(name);
	}

	public List<String> getHeaders(String name) {
		return this.nettyResponse.getHeaders(name);
	}

	public List<Entry<String, String>> getHeaders() {
		return this.nettyResponse.getHeaders();
	}

	public boolean containsHeader(String name) {
		return this.nettyResponse.containsHeader(name);
	}

	public Set<String> getHeaderNames() {
		return this.nettyResponse.getHeaderNames();
	}

	public HttpVersion getProtocolVersion() {
		return this.nettyResponse.getProtocolVersion();
	}

	public void setProtocolVersion(HttpVersion version) {
		this.nettyResponse.setProtocolVersion(version);
	}

	public ChannelBuffer getContent() {
		return this.nettyResponse.getContent();
	}

	public void setContent(ChannelBuffer content) {
		this.nettyResponse.setContent(content);
	}

	public void addHeader(String name, Object value) {
		this.nettyResponse.addHeader(name, value);
	}

	public void setHeader(String name, Object value) {
		this.nettyResponse.setHeader(name, value);
	}

	public void setHeader(String name, Iterable<?> values) {
		this.nettyResponse.setHeader(name, values);
	}

	public void removeHeader(String name) {
		this.nettyResponse.removeHeader(name);
	}

	public void clearHeaders() {
		this.nettyResponse.clearHeaders();
	}

	@Deprecated
	public long getContentLength() {
		return this.nettyResponse.getContentLength();
	}

	@Deprecated
	public long getContentLength(long defaultValue) {
		return this.nettyResponse.getContentLength(defaultValue);
	}

	public boolean isChunked() {
		return this.nettyResponse.isChunked();
	}

	public void setChunked(boolean chunked) {
		this.nettyResponse.setChunked(chunked);

	}

	@Deprecated
	public boolean isKeepAlive() {
		return this.nettyResponse.isKeepAlive();
	}

	public abstract int getStatusCode();

	public abstract void setStatusCode(int statusCode);

	public abstract void setContent(String content);

	public abstract void setContent(byte[] content);

	public abstract String getContentString();

	public abstract byte[] getContentBytes();

	public abstract void setCharset(Charset charset);

	public abstract Charset getCharset();

	public abstract void addCookie(HttpCookie cookie);


	public abstract void addCookie(String name, String value);

	public abstract boolean isEnd();

	@Deprecated
	public abstract void feedback();
	
	public abstract void feedback(RequestHandlerContext context) throws Exception;
	
	public org.jboss.netty.handler.codec.http.HttpResponse getNettyResponse() {
		return nettyResponse;
	}
	
	public void setNettyResponse(
			org.jboss.netty.handler.codec.http.HttpResponse nettyResponse) {
		this.nettyResponse = nettyResponse;
	}
	
	
}
