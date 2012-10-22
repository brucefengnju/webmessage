package org.webmessage;

import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

public class DefaultWebMessageServerTest {
	private DefaultWebMessageServer server;
	@After
	public void releaseResource(){
		if(server.isRunning()){
			server.stop();
		}
	}
	@Test
	public void testStart() throws InterruptedException, ExecutionException{
		server  = new DefaultWebMessageServer();
		server.start().get();
		Assert.assertTrue(server.isRunning());
	}
	@Test
	public void testStop() throws InterruptedException, ExecutionException{
		server = new DefaultWebMessageServer();
		server.start().get();
		server.stop().get();
		Assert.assertFalse(server.isRunning());
		
	}
	

}
