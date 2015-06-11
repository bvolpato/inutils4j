package org.brunocunha.inutils4j;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * HTTP (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyHTTPUtils {

	public static String getContent(String stringUrl) {
		try {
			URL url = new URL(stringUrl);
			return MyStreamUtils.readContent(url.openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	public static Map<String, List<String>> getResponseHeaders(String stringUrl) {
		return getResponseHeaders(stringUrl, true);
	}

	public static Map<String, List<String>> getResponseHeaders(String stringUrl, boolean followRedirects) {
		try {
			URL url = new URL(stringUrl);
			URLConnection conn = url.openConnection();
			return conn.getHeaderFields();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	
}
