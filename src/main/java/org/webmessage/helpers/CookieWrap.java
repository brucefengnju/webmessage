package org.webmessage.helpers;

import java.net.HttpCookie;
import java.util.Iterator;

import org.jboss.netty.handler.codec.http.Cookie;

public final class CookieWrap {
	public final static HttpCookie parseCookie(Cookie cookie){
		HttpCookie httpCookie = new HttpCookie(cookie.getName(),cookie.getValue());
		httpCookie.setComment(cookie.getComment());
		httpCookie.setCommentURL(cookie.getCommentUrl());
		httpCookie.setDomain(cookie.getDomain());
		httpCookie.setMaxAge(cookie.getMaxAge());
		httpCookie.setPath(cookie.getPath());
		Iterator<Integer> ports = cookie.getPorts().iterator();
		StringBuffer sb = new StringBuffer();
		if(ports.hasNext()){
			sb.append(ports.next());
		}
		while(ports.hasNext()){
			sb.append(",");
			sb.append(ports.next());
		}
		httpCookie.setPortlist(sb.toString());
		httpCookie.setSecure(cookie.isSecure());
		httpCookie.setHttpOnly(cookie.isHttpOnly());
		httpCookie.setDiscard(cookie.isDiscard());
		httpCookie.setVersion(cookie.getVersion());
		
		return httpCookie;
	}

}
