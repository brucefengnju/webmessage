package org.webmessage.handler;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Test;
import org.webmessage.DefaultWebMessageServer;
import org.webmessage.handler.http.HelloWorldHttpHandler;
import org.webmessage.handler.websocket.BaseWebSocketHandler;

public class WebSocketTest {
	private DefaultWebMessageServer server;
	//@After
	public void releaseResource(){
		if(server.isRunning()){
			server.stop();
		}
	}
	
	//@Test
	public static void main(String[]args) throws Exception, ExecutionException{
		DefaultWebMessageServer server = new DefaultWebMessageServer();
		server.addHandler("/websocket",new EchoWebSocketHandler());
		server.start().get();
	}
	public void testHandshake(){
		this.server = new DefaultWebMessageServer();
		this.server.addHandler("/websocket",new BaseWebSocketHandler());
		this.server.start();
	}
	
}
