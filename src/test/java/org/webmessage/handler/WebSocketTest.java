package org.webmessage.handler;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.webmessage.DefaultWebMessageServer;
import org.webmessage.handler.websocket.WebSocketHandshakeHandler;

public class WebSocketTest {
	private DefaultWebMessageServer server;
	@After
	public void releaseResource(){
		if(server.isRunning()){
			server.stop();
		}
	}
	
	@Before
	public void startServer() throws Exception {
		this.server = new DefaultWebMessageServer();
		this.server.start().get();
	}
	@Test
	public void testHandshake(){
		WebSocketHandshakeHandler handler = new WebSocketHandshakeHandler();
	}
}
