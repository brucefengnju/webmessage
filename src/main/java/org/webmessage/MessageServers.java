package org.webmessage;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
/**
 * create a new {@link WebMessageServer} object.
 * <h3>Use static import</h3>
 * This classes is intended to be used with Java 5 static import statement:
 * <pre>
 * import static org.webmessage.{@link MessageServers}.*;
 * @{link WebMessageServer} webMessageServer = createServer();
 * @{link WebMessageServer} webMessageServer = createServer(8080);
 * 
 */

public final class MessageServers {
	
	/**
	 * Returns a new {@link WebMessageServer} object,which run on 8080 port at localhost.
	 * @return {@link WebMessageServer}
	 * @see DefaultWebMessageServer
	 */
	public static WebMessageServer createServer(){
		return new DefaultWebMessageServer();
	}
	
	/**
	 * Returns a new {@link WebMessageServer} object, which run on the provided port.
	 * @param port
	 * @return {@link WebMessageServer}
	 * @see DefaultWebMessageServer
	 */
	public static WebMessageServer createServer(int port){
		return new DefaultWebMessageServer(port);
	}
	
	/**
	 * Returns a new {@link WebMessageServer} object, which run on the provided port
	 * using the bossExecutor and workerExecutor as the boss-worker thread model. 
	 * @param bossExecutor
	 * @param workerExecutor
	 * @param port
	 * @return {@link WebMessageServer}
	 * @see DefaultWebMessageServer
	 */
	public static WebMessageServer createServer(Executor bossExecutor,Executor workerExecutor,int port){
		SocketAddress socketAddress = new InetSocketAddress("localhost",port);
		
		return new DefaultWebMessageServer(bossExecutor,workerExecutor,socketAddress);
	}

}
