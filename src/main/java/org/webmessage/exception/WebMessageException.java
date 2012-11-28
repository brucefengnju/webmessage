package org.webmessage.exception;

public class WebMessageException extends Exception {

	private static final long serialVersionUID = 4095016456190305993L;

	public WebMessageException() {
		super();
	}

	public WebMessageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
	}

	public WebMessageException(String message, Throwable cause) {
		super(message,cause);
	}

	public WebMessageException(String message) {
		super(message);
	}

	public WebMessageException(Throwable cause) {
		super(cause);
	}
	
	
	
}
