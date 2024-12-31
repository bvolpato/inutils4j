/*
 * Copyright © 2014 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brunocvcunha.inutils4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

/**
 * HTTP (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyHTTPUtils {

  /**
   * Get content for URL only
   * 
   * @param stringUrl URL to get content
   * @return the content
   * @throws IOException I/O error happened
   */
  public static String getContent(String stringUrl) throws IOException {
    InputStream stream = getContentStream(stringUrl);
    return MyStreamUtils.readContent(stream);
  }

  /**
   * Get stream for URL only
   * 
   * @param stringUrl URL to get content
   * @return the input stream 
   * @throws IOException I/O error happened
   */
    public static InputStream getContentStream(String stringUrl) throws MalformedURLException, IOException {
        URL url = new URL(stringUrl);
        
        URLConnection urlConnection = url.openConnection();
        
        InputStream is = urlConnection.getInputStream();
        if ("gzip".equals(urlConnection.getContentEncoding())) {
           is = new GZIPInputStream(is);
        }

        return is;
    }

  /**
   * Get content for url/parameters
   * 
   * @param stringUrl URL to get content
   * @param parameters HTTP parameters to pass
   * @return content the response content
   * @throws IOException I/O error happened
   */
  public static String getContent(String stringUrl, Map<String, String> parameters)
      throws IOException {
    return getContent(stringUrl, parameters, null);
  }

  /**
   * Get content for url/parameters/proxy
   * 
   * @param stringUrl the URL to get
   * @param parameters HTTP parameters to pass
   * @param proxy Proxy to use
   * @return content the response content
   * @throws IOException I/O error happened
   */
  public static String getContent(String stringUrl, Map<String, String> parameters, Proxy proxy)
      throws IOException {
    URL url = new URL(stringUrl);
    URLConnection conn;

    if (proxy != null) {
      conn = url.openConnection(proxy);
    } else {
      conn = url.openConnection();
    }

    conn.setConnectTimeout(5000); // set connect timeout to 5 seconds
    conn.setReadTimeout(60000); // set read timeout to 60 seconds

    if (parameters != null) {
      for (Entry<String, String> entry : parameters.entrySet()) {
        conn.addRequestProperty(entry.getKey(), entry.getValue());
      }
    }

    if (conn instanceof HttpURLConnection) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
        int errorCode = httpURLConnection.getResponseCode();
        InputStream err = httpURLConnection.getErrorStream();
        if (err != null) {
            if ("gzip".equals(conn.getContentEncoding())) {
                err = new GZIPInputStream(err);
            }
            
            throw new IOException("Error fetching " + stringUrl + ": " + MyStreamUtils.readContent(err));
        }
        
    }
    
    InputStream is = conn.getInputStream();
    if ("gzip".equals(conn.getContentEncoding())) {
       is = new GZIPInputStream(is);
    }
    
    return MyStreamUtils.readContent(is);
  }



  /**
   * Post content / get response
   *
   * @param stringUrl URL to use
   * @param parameters HTTP headers
   * @param input Input data (payload)
   * @param charset Charset in the input
   * @return response HTTP response
   * @throws IOException I/O error happened
   */
  public static String getContentPost(String stringUrl, Map<String, String> parameters,
                                      String input, String charset) throws IOException {
    return getContentPost(stringUrl, parameters, input, Charset.forName(charset));
  }

  /**
   * Post content / get response
   * 
   * @param stringUrl URL to use
   * @param parameters HTTP headers
   * @param input Input data (payload)
   * @param charset Charset in the input
   * @return response HTTP response
   * @throws IOException I/O error happened
   */
  public static String getContentPost(String stringUrl, Map<String, String> parameters,
          String input, Charset charset) throws IOException {
      return getContentPost(stringUrl, parameters, input, charset, false);
  }
  /**
   * Post content / get response
   * 
   * @param stringUrl URL to use
   * @param parameters HTTP headers
   * @param input Input data (payload)
   * @param charset Charset in the input
   * @param followRedirects Follow Redirects
   * @return response HTTP response
   * @throws IOException I/O error happened
   */
  public static String getContentPost(String stringUrl, Map<String, String> parameters,
                                      String input, Charset charset, boolean followRedirects) throws IOException {
    URL url = new URL(stringUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setFollowRedirects(followRedirects);
    
    if (parameters != null) {
      for (Entry<String, String> entry : parameters.entrySet()) {
        conn.addRequestProperty(entry.getKey(), entry.getValue());
      }
    }

    OutputStream output = null;
    try {
      output = conn.getOutputStream();
      output.write(input.getBytes(charset));
    } finally {
      if (output != null) {
        output.close();
      }
    }

    return MyStreamUtils.readContent(conn.getInputStream());
  }


  /**
   * Get the response headers for URL
   * 
   * @param stringUrl URL to use
   * @return headers HTTP Headers
   * @throws IOException I/O error happened
   */
  public static Map<String, List<String>> getResponseHeaders(String stringUrl) throws IOException {
    return getResponseHeaders(stringUrl, true);
  }

  /**
   * Get the response headers for URL, following redirects
   * 
   * @param stringUrl URL to use
   * @param followRedirects whether to follow redirects
   * @return headers HTTP Headers
   * @throws IOException I/O error happened
   */
  public static Map<String, List<String>> getResponseHeaders(String stringUrl,
      boolean followRedirects) throws IOException {
    URL url = new URL(stringUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setInstanceFollowRedirects(followRedirects);
    
    InputStream is = conn.getInputStream();
    if ("gzip".equals(conn.getContentEncoding())) {
       is = new GZIPInputStream(is);
    }

    Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>(conn.getHeaderFields());
    headers.put("X-Content", Arrays.asList(MyStreamUtils.readContent(is)));
    headers.put("X-URL", Arrays.asList(conn.getURL().toString()));
    headers.put("X-Status", Arrays.asList(String.valueOf(conn.getResponseCode())));
    
    return headers;
  }

  /**
   * Download a specified URL to a file
   * 
   * @param stringUrl URL to use
   * @param parameters HTTP Headers
   * @param fileToSave File to save content
   * @throws IOException I/O error happened
   */
  public static void downloadUrl(String stringUrl, Map<String, String> parameters, File fileToSave)
      throws IOException {
    URL url = new URL(stringUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setFollowRedirects(true);
    
    if (parameters != null) {
      for (Entry<String, String> entry : parameters.entrySet()) {
        conn.addRequestProperty(entry.getKey(), entry.getValue());
      }
    }


    boolean redirect = false;

    // normally, 3xx is redirect
    int status = conn.getResponseCode();
    if (status != HttpURLConnection.HTTP_OK) {
        if (status == HttpURLConnection.HTTP_MOVED_TEMP
            || status == HttpURLConnection.HTTP_MOVED_PERM
                || status == HttpURLConnection.HTTP_SEE_OTHER)
        redirect = true;
    }

    if (redirect) {

        // get redirect url from "location" header field
        String newUrl = conn.getHeaderField("Location");

        // get the cookie if need, for login
        String cookies = conn.getHeaderField("Set-Cookie");

        // open the new connnection again
        conn = (HttpURLConnection) new URL(newUrl).openConnection();
        conn.setRequestProperty("Cookie", cookies);

    }
    
    byte[] data = MyStreamUtils.readContentBytes(conn.getInputStream());
    FileOutputStream fos = new FileOutputStream(fileToSave);
    fos.write(data);
    fos.close();
  }

  /**
   * Return the content from an URL in byte array
   * 
   * @param stringUrl URL to get
   * @return byte array
   * @throws IOException I/O error happened
   */
  public static byte[] getContentBytes(String stringUrl) throws IOException {
    URL url = new URL(stringUrl);
    byte[] data = MyStreamUtils.readContentBytes(url.openStream());
    return data;
  }


  /**
   * Execute a HTTP request
   * 
   * @param stringUrl URL
   * @param method Method to use
   * @param parameters Params
   * @param input Input / Payload
   * @param charset Input Charset
   * @return response
   * @throws IOException
   */
  public static String httpRequest(String stringUrl, String method, Map<String, String> parameters,
      String input, String charset) throws IOException {
    URL url = new URL(stringUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod(method);

    if (parameters != null) {
      for (Entry<String, String> entry : parameters.entrySet()) {
        conn.addRequestProperty(entry.getKey(), entry.getValue());
      }
    }

    if (input != null) {
      OutputStream output = null;
      try {
        output = conn.getOutputStream();
        output.write(input.getBytes(charset));
      } finally {
        if (output != null) {
          output.close();
        }
      }
    }

    return MyStreamUtils.readContent(conn.getInputStream());
  }
}
