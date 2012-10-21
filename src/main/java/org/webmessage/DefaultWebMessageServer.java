package org.webmessage;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.webmessage.handler.HttpHandler;
import org.webmessage.handler.WebSocketHandler;

public class DefaultWebMessageServer implements WebMessageServer {
	private Executor bossExecutor;
	private Executor workerExecutor;
	
	public DefaultWebMessageServer(){
		this.bossExecutor = Executors.newCachedThreadPool();
		this.workerExecutor = Executors.newCachedThreadPool();
	}
	public DefaultWebMessageServer(Executor bossExecutor,Executor workerExecutor){
		this.bossExecutor = bossExecutor;
		this.workerExecutor = workerExecutor;
	}

	public Future<? extends WebMessageServer> start() {
		// TODO Auto-generated method stub
		return null;
	}

	public Future<? extends WebMessageServer> stop() {
		// TODO Auto-generated method stub
		return null;
	}

	public WebMessageServer addHandler(HttpHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	public WebMessageServer addHandler(String path, HttpHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	public WebMessageServer addHandler(String path, WebSocketHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	public WebMessageServer addHttpHandlers(Map<String, HttpHandler> handlers) {
		// TODO Auto-generated method stub
		return null;
	}

	public WebMessageServer addWebSocketHandlers(
			Map<String, WebSocketHandler> handlers) {
		// TODO Auto-generated method stub
		return null;
	}
	public Executor getBossExecutor() {
		return bossExecutor;
	}
	public void setBossExecutor(Executor bossExecutor) {
		this.bossExecutor = bossExecutor;
	}
	public Executor getWorkerExecutor() {
		return workerExecutor;
	}
	public void setWorkerExecutor(Executor workerExecutor) {
		this.workerExecutor = workerExecutor;
	}
	
}
