package org.webmessage.http;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;
import org.webmessage.helpers.CookieWrap;

public class DefaultHttpRequest extends AbstractHttpRequest {

	public DefaultHttpRequest(org.jboss.netty.handler.codec.http.HttpRequest nettyRequest){
		super(nettyRequest);
	}
	
	public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri){
		super(new org.jboss.netty.handler.codec.http.DefaultHttpRequest(httpVersion,method,uri));
	}
	
	public void setBody(String content) {
		this.setBody(content.getBytes());
	}

	public void setBody(byte[] content) {
		this.getNettyRequest().setContent(ChannelBuffers.copiedBuffer(content));
	}

	public String getBody() {
		return this.getNettyRequest().getContent().toString(CharsetUtil.UTF_8);
	}

	public byte[] getBodyBytes() {
		ChannelBuffer buffer = this.getNettyRequest().getContent();
		byte[] content = new byte[buffer.readableBytes()];
		buffer.getBytes(buffer.readerIndex(), content);
		return content;
	}


	public Map<String, List<String>> getParameters() {
		if(this.getMethod() == HttpMethod.GET){
			return new  QueryStringDecoder(this.getUri()).getParameters();
		}else if(this.getMethod() == HttpMethod.POST){
			return new QueryStringDecoder(this.getBody()).getParameters();
		}
		return Collections.emptyMap();
	}

	public List<HttpCookie> getCookies() {
		String value = this.getHeader("Cookie");
		Set<Cookie> cookies = new CookieDecoder().decode(value);
		List<HttpCookie> httpCookies = new ArrayList<HttpCookie>();
		Iterator<Cookie> it = cookies.iterator();
		while(it.hasNext()){
			httpCookies.add(CookieWrap.parseCookie(it.next()));
		}
		if(httpCookies.isEmpty()){
			return Collections.emptyList();
		}
		return httpCookies;
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

	public String getPath() {
		return new QueryStringDecoder(this.getUri()).getPath();
	}

}
