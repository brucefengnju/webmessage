package org.webmessage.http;

import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;

public class DefaultHttpResponse implements HttpResponse {
	
	private org.jboss.netty.handler.codec.http.HttpResponse nettyResponse;
	private Charset charset = CharsetUtil.UTF_8;
	private AtomicBoolean isEnd = new AtomicBoolean(false);
	public DefaultHttpResponse(org.jboss.netty.handler.codec.http.HttpResponse nettyResponse){
		this.nettyResponse = nettyResponse;
	}
	public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status){
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

	public int getStatusCode() {
		return this.nettyResponse.getStatus().getCode();
	}

	public void setStatusCode(int statusCode) {
		
		this.nettyResponse.setStatus(HttpResponseStatus.valueOf(statusCode));
	}

	public void setContent(String content) {
		this.setContent(content.getBytes());
	}

	public void setContent(byte[] content) {
		
		this.setContent(ChannelBuffers.copiedBuffer(content));
	}

	public String getContentString() {
		return this.getContent().toString(this.getCharset());
	}

	public byte[] getContentBytes() {
		ChannelBuffer buffer = this.getContent();
		byte[] content = new byte[buffer.readableBytes()];
		buffer.getBytes(buffer.readerIndex(), content);
		return content;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public Charset getCharset() {
		return this.charset;
	}

	public void addCookie(HttpCookie cookie) {
		this.addCookie(cookie.getName(), cookie.getValue());
	}
	public void addCookie(String name,String value){
		CookieEncoder encoder = new CookieEncoder(true);
		encoder.addCookie(name,value);
		this.setHeader(HttpHeaders.Names.SET_COOKIE, encoder.encode());
	}

	public boolean end() {
		return this.isEnd.get();
	}

	public void feedback() {
		this.isEnd.set(true);
	}


}
