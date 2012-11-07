package org.webmessage.client;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestEncoder;
import org.jboss.netty.handler.codec.http.HttpResponseDecoder;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketVersion;



public class WsClient {
	private URI uri;
	private WebSocketClientHandshaker handshaker;
	private ClientBootstrap bootstrap;
	private ChannelFuture future;
	private Channel clientChannel;
	
	public WsClient(URI uri) {
		this.uri = uri;
	}

	public void run() throws Exception{
	       this.bootstrap =
	               new ClientBootstrap(
	                       new NioClientSocketChannelFactory(
	                               Executors.newCachedThreadPool(),
	                               Executors.newCachedThreadPool()));

	           String protocol = uri.getScheme();
	           if (!protocol.equals("ws")) {
	               throw new IllegalArgumentException("Unsupported protocol: " + protocol);
	           }

	           HashMap<String, String> customHeaders = new HashMap<String, String>();
	           customHeaders.put("MyHeader", "MyValue");

	           // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
	           // If you change it to V00, ping is not supported and remember to change
	          // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
	           this.handshaker = new WebSocketClientHandshakerFactory().newHandshaker(
	                           uri, WebSocketVersion.V13, null, false, customHeaders);

	            bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
	               public ChannelPipeline getPipeline() throws Exception {
	                   ChannelPipeline pipeline = Channels.pipeline();

	                   pipeline.addLast("decoder", new HttpResponseDecoder());
	                   pipeline.addLast("encoder", new HttpRequestEncoder());
	                   pipeline.addLast("ws-handler", new WebSocketClientHandler(handshaker));
	                   return pipeline;
	               }
	          });

	           // Connect
	           System.out.println("WebSocket Client connecting");
	           this.future =
	                   bootstrap.connect(
	                           new InetSocketAddress(uri.getHost(), uri.getPort()));
	           this.future.syncUninterruptibly();

	}
	public void sendHandshake() throws Exception{

		this.clientChannel = future.getChannel();
        handshaker.handshake(this.clientChannel).syncUninterruptibly();
	}

	public void stop(){
		if(this.clientChannel!= null){
			this.clientChannel.close();
		}
		if(this.bootstrap != null){
			this.bootstrap.releaseExternalResources();
		}
	}
}
