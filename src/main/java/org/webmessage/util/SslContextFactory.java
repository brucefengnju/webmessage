package org.webmessage.util;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.webmessage.exception.WebMessageException;
/**
 * This code was forked from 
 * <a href="https://github.com/joewalnes/webbit">webbit</a> and modified.
 */
public class SslContextFactory {
	private static final String PROTOCOL = "TLS";
	private KeyStore keyStore;
	
	private SslContextFactory(InputStream keyStore,char[] storePass)
			throws WebMessageException{
        try {
        	this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			this.keyStore.load(keyStore, storePass);
		} catch (Exception e) {
			throw new WebMessageException(e);
		} 
	}
	
	public static SslContextFactory getInstance(InputStream keyStore,String storePass)
			throws WebMessageException{
		return new SslContextFactory(keyStore,storePass.toCharArray());
	}
	
	public SSLContext getServerContext(String pass) throws WebMessageException{
        try {
            String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
            if (algorithm == null){
            	algorithm = "SunX509";
            }
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(this.keyStore, pass.toCharArray());

            SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
            sslContext.init(kmf.getKeyManagers(), null, null);
            return sslContext;
        } catch (Exception e) {
            throw new WebMessageException(e);
        
        }
	}
	
	public SSLContext getClientContext() throws Exception{
		try {
	        SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
	        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
	        tmf.init(this.keyStore);
	        TrustManager[] trustManagers = tmf.getTrustManagers();
	        sslContext.init(null, trustManagers, null);
	        return sslContext;
		} catch (Exception e) {
			throw new WebMessageException(e);
		} 

	}
}
