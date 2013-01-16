package org.webmessage.handler.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

import org.webmessage.handler.RequestHandlerContext;
import org.webmessage.handler.http.HttpHandler;
import org.webmessage.http.HttpRequest;
import org.webmessage.http.HttpResponse;

public abstract class FileHandler implements HttpHandler {

	private HttpRequest request;
	private HttpResponse response;
	private RequestHandlerContext routerContext;
	private Executor executor;
	public void handle(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext) throws Exception {
		executor.execute(new IOReader(resolveFile(request)));
	}
	
	public abstract void notFound(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext)throws Exception ;
	public abstract void error(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext,Exception e);
	public abstract void serve(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext,byte[] content)throws Exception ;
	public abstract void notServe(HttpRequest request, HttpResponse response,
			RequestHandlerContext routerContext)throws Exception ;
	public abstract File resolveFile(HttpRequest request);
	
	protected class IOReader implements Runnable{
		private File file;
		
		public IOReader(File file) {
			this.file = file;
		}

		public void run() {
			try{
				if(!file.exists()){
					notFound(request,response,routerContext);
				}else if(file.isDirectory()){
					notServe(request,response,routerContext);
				}else{
					byte[] content = read();
					serve(request,response,routerContext,content);
				}
			}catch(Exception e){
				error(request,response,routerContext,e);
			}
			
		}
		public byte[] read() throws IOException{
			
			byte[] content = new byte[(int)file.length()];
			@SuppressWarnings("resource")
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			in.read(content);
			return content;
		}
		
	}
}
