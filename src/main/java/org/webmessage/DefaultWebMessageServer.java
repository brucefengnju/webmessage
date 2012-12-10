package org.webmessage;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
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
import org.webmessage.handler.PathPatternHandler;
import org.webmessage.handler.http.HttpHandler;
import org.webmessage.handler.websocket.WebSocketHandler;
import org.webmessage.netty.ConnectionsRecoderHandler;
import org.webmessage.netty.NettyRequestHandler;

/**
 * Default WebMessageServer which implements interface {@link WebMessageServer}
 *
 */
public class DefaultWebMessageServer implements WebMessageServer {
	private SocketAddress socketAddress;
	private Executor bossExecutor;
	private Executor workerExecutor;
	private ServerBootstrap bootstrap; 
	private Channel serverChannel;
	private List<HttpHandler> httpHanlders;

	private NettyRequestHandler nettyHandler;
	
	// This handler is used to close server channel when server is closing.
	private ConnectionsRecoderHandler connectionRecoderHandler;
	
	public DefaultWebMessageServer(){
		this.bossExecutor = Executors.newCachedThreadPool();
		this.workerExecutor = Executors.newCachedThreadPool();
		this.httpHanlders = new ArrayList<HttpHandler>();
		this.socketAddress = new InetSocketAddress("localhost",8080);
		this.connectionRecoderHandler = null;
		this.nettyHandler = null;
	}
	public DefaultWebMessageServer(int port){
		this(new InetSocketAddress("localhost",port));
	}
	public DefaultWebMessageServer(SocketAddress soketAddress){
		this.bossExecutor = Executors.newCachedThreadPool();
		this.workerExecutor = Executors.newCachedThreadPool();
		this.httpHanlders = new ArrayList<HttpHandler>();
		this.connectionRecoderHandler = null;
		this.nettyHandler = null;
		
		this.socketAddress = soketAddress;
	}
	
	public DefaultWebMessageServer(Executor bossExecutor,Executor workerExecutor,SocketAddress socketAddress){
		this.bossExecutor = bossExecutor;
		this.workerExecutor = workerExecutor;
		this.socketAddress = socketAddress;
		this.httpHanlders = new ArrayList<HttpHandler>();
	}

	public Future<DefaultWebMessageServer> start() {

		this.nettyHandler = new NettyRequestHandler(this.httpHanlders);
		this.connectionRecoderHandler = new ConnectionsRecoderHandler();
		FutureTask<DefaultWebMessageServer> future = new FutureTask<DefaultWebMessageServer>(new Callable<DefaultWebMessageServer>(){

			public DefaultWebMessageServer call() throws Exception {
				if(serverChannel !=null && serverChannel.isBound()){
					throw new IllegalStateException("Server already started.");
				}
				bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(bossExecutor,workerExecutor));
				bootstrap.setPipelineFactory(new ChannelPipelineFactory(){
					
					public ChannelPipeline getPipeline() throws Exception {
		                ChannelPipeline pipeline = Channels.pipeline();
		                pipeline.addLast("connectionRecoder", connectionRecoderHandler);
		                pipeline.addLast("decoder", new HttpRequestDecoder());
		                pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));
		                pipeline.addLast("encoder", new HttpResponseEncoder());
		                pipeline.addLast("deflater", new HttpContentCompressor());
		                pipeline.addLast("messagehandler",nettyHandler);
		                return pipeline;
					}
					
				});
				serverChannel = bootstrap.bind(socketAddress);
				return DefaultWebMessageServer.this;
			}
			
		});
		
		//start the server thread.
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
				if(connectionRecoderHandler != null){
					connectionRecoderHandler.closeAllOpenChannels();
					connectionRecoderHandler = null;
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
		this.httpHanlders.add(handler);
		return DefaultWebMessageServer.this;
	}

	public WebMessageServer addHandler(String path, HttpHandler handler) {
		
		this.httpHanlders.add(new PathPatternHandler(path,handler));
		
		return DefaultWebMessageServer.this;
	}

	public WebMessageServer addHandler(String path, WebSocketHandler handler) {
		
		this.httpHanlders.add(new PathPatternHandler(path,handler));
		return DefaultWebMessageServer.this;
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
