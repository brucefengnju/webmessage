package org.webmessage.util;

import java.util.HashMap;
import java.util.Map;

public class MimeTypes {
	private final static Map<String, String> mimeTypes = new HashMap<String, String>();
	static{
        mimeTypes.put("txt", "text/plain");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("csv", "text/csv");
        mimeTypes.put("htm", "text/html");
        mimeTypes.put("html", "text/html");
        mimeTypes.put("xml", "text/xml");
        mimeTypes.put("xsl", "text/xml");
        mimeTypes.put("js", "text/javascript"); 
        mimeTypes.put("js", "application/x-javascript");
        mimeTypes.put("xhtml", "application/xhtml+xml");
        mimeTypes.put("json", "application/json");
        mimeTypes.put("json", "text/json");
        mimeTypes.put("pdf", "application/pdf");
        mimeTypes.put("zip", "application/zip");
        mimeTypes.put("tar", "application/x-tar");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("tiff", "image/tiff");
        mimeTypes.put("tif", "image/tiff");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("svg", "image/svg+xml");
        mimeTypes.put("ico", "image/vnd.microsoft.icon");
	}
	public static String getMimeType(String key){
		return mimeTypes.get(key);
	}
}
