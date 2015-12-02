/**
 * Copyright (C) 2014 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HTTP (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyHTTPUtils {

  public static String getContent(String stringUrl) throws IOException {
    URL url = new URL(stringUrl);
    return MyStreamUtils.readContent(url.openStream());
  }

  public static String getContent(String stringUrl, Map<String, String> parameters)
      throws IOException {
    URL url = new URL(stringUrl);
    URLConnection conn = url.openConnection();

    if (parameters != null) {
      for (Entry<String, String> entry : parameters.entrySet()) {
        conn.addRequestProperty(entry.getKey(), entry.getValue());
      }
    }

    return MyStreamUtils.readContent(conn.getInputStream());
  }


  public static String getContentPost(String stringUrl, Map<String, String> parameters,
      String input, String charset) throws IOException {
    URL url = new URL(stringUrl);
    URLConnection conn = url.openConnection();
    conn.setDoOutput(true);

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


  public static Map<String, List<String>> getResponseHeaders(String stringUrl) throws IOException {
    return getResponseHeaders(stringUrl, true);
  }

  public static Map<String, List<String>> getResponseHeaders(String stringUrl,
      boolean followRedirects) throws IOException {
    URL url = new URL(stringUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setInstanceFollowRedirects(followRedirects);
    return conn.getHeaderFields();
  }

  public static void downloadUrl(String stringUrl, File fileToSave) throws IOException {
    URL url = new URL(stringUrl);
    byte[] data = MyStreamUtils.readContentBytes(url.openStream());
    FileOutputStream fos = new FileOutputStream(fileToSave);
    fos.write(data);
    fos.close();
  }


}
