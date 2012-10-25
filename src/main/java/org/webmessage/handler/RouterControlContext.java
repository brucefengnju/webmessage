package org.webmessage.handler;

import org.jboss.netty.handler.codec.http.HttpRequest;

public interface RouterControlContext {
	RouterControlContext sendNext(HttpRequest request);
}
