package org.webmessage;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.webmessage.handler.HttpHandler;
import org.webmessage.handler.WebSocketHandler;

public class DefaultWebMessageServer implements WebMessageServer {
	private SocketAddress socketAddress;
	private Executor bossExecutor;
	private Executor workerExecutor;
	private ServerBootstrap bootstrap; 
	private Channel serverChannel;
	private List<HttpHandler> httpHanlders;
	private List<WebSocketHandler> webSocketHandlers;
	public DefaultWebMessageServer(){
		this.bossExecutor = Executors.newCachedThreadPool();
		this.workerExecutor = Executors.newCachedThreadPool();
		this.httpHanlders = new ArrayList<HttpHandler>();
		this.webSocketHandlers = new ArrayList<WebSocketHandler>();
		this.socketAddress = new InetSocketAddress("localhost",8080);
	}
	public DefaultWebMessageServer(SocketAddress soketAddress){
		this();
		this.socketAddress = new InetSocketAddress("localhost",8080);
	}
	public DefaultWebMessageServer(Executor bossExecutor,Executor workerExecutor,SocketAddress socketAddress){
		this.bossExecutor = bossExecutor;
		this.workerExecutor = workerExecutor;
		this.socketAddress = socketAddress;
		this.httpHanlders = new ArrayList<HttpHandler>();
		this.webSocketHandlers = new ArrayList<WebSocketHandler>();
	}

	public Future<DefaultWebMessageServer> start() {
		FutureTask<DefaultWebMessageServer> future = new FutureTask<DefaultWebMessageServer>(new Callable<DefaultWebMessageServer>(){

			public DefaultWebMessageServer call() throws Exception {
				if(serverChannel !=null && serverChannel.isBound()){
					throw new IllegalStateException("Server already started.");
				}
				bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(bossExecutor,workerExecutor));
				bootstrap.setPipelineFactory(new ChannelPipelineFactory(){
					
					public ChannelPipeline getPipeline() throws Exception {
		                ChannelPipeline pipeline = Channels.pipeline();

		                pipeline.addLast("decoder", new HttpRequestDecoder());
		                pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));
		                pipeline.addLast("encoder", new HttpResponseEncoder());
		                pipeline.addLast("deflater", new HttpContentCompressor());
		                return pipeline;
					}
					
				});
				serverChannel = bootstrap.bind(socketAddress);
				return DefaultWebMessageServer.this;
			}
			
		});
		Thread thread = new Thread(future,"DefaultWebMessageServer");
		thread.start();
		return future;
	}

	public Future<DefaultWebMessageServer> stop() {
		FutureTask<DefaultWebMessageServer> future = new FutureTask<DefaultWebMessageServer>(new Callable<DefaultWebMessageServer>(){

			public DefaultWebMessageServer call() throws Exception {
				if(serverChannel != null){
					serverChannel.close();
				}	
				if(bootstrap!=null){
					bootstrap.releaseExternalResources();
					bootstrap = null;
				}
				if(serverChannel != null){
					serverChannel.getCloseFuture().await();
				}
				return DefaultWebMessageServer.this;
			}
			
		}); 
		final Thread thread = new Thread(future,"stop-default-webmessageserver");
		thread.start();
		return future;
	}

	public WebMessageServer addHandler(HttpHandler handler) {
		// TODO Auto-generated method stub
		return this;
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
	public boolean isRunning(){
		return serverChannel !=null && serverChannel.isBound();
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
