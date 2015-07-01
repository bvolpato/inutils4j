package org.brunocvcunha.inutils4j;

import java.net.HttpURLConnection;
import java.net.URL;
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
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(followRedirects);
			return conn.getHeaderFields();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	
}
