package org.webmessage.http;

import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;
import org.webmessage.handler.RequestHandlerContext;

public class DefaultHttpResponse extends AbstractHttpResponse {
	
	private Charset charset = CharsetUtil.UTF_8;
	private AtomicBoolean isEnd = new AtomicBoolean(false);
	public DefaultHttpResponse(org.jboss.netty.handler.codec.http.HttpResponse nettyResponse){
		super(nettyResponse);
	}
	public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status){
		super(new org.jboss.netty.handler.codec.http.DefaultHttpResponse(version,status));
	}

	public int getStatusCode() {
		return this.getNettyResponse().getStatus().getCode();
	}

	public void setStatusCode(int statusCode) {
		
		this.getNettyResponse().setStatus(HttpResponseStatus.valueOf(statusCode));
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

	public boolean isEnd() {
		return this.isEnd.get();
	}

	@Deprecated
	public void feedback() {
		this.isEnd.set(true);
	}

	public void feedback(RequestHandlerContext context) throws Exception{
		context.end(this);
		this.isEnd.set(true);
	}

}
