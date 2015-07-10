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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Stream (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyStreamUtils {

	public static final String CARRIAGE_RETURN = "\r\n";

	/**
	 * Gets string content from InputStream
	 * @param is
	 * @return
	 */
	public static String readContent(InputStream is) {
		String retorno = "";
		try {
			String linha;
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			StringBuffer out = new StringBuffer();

			while ((linha = in.readLine()) != null) {
				out.append(linha).append(CARRIAGE_RETURN);
			}
			retorno = out.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * Converts an input stream to byte array
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] readContentBytes(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();
	}

	/**
	 * Checks if the InputStream have the text
	 * @param in
	 * @param text
	 * @return
	 */
	public static boolean streamHasText(InputStream in, String text) {
		final byte[] readBuffer = new byte[8192];

		StringBuffer sb = new StringBuffer();
		try {
			if (in.available() > 0) {
				int bytesRead = 0;
				while ((bytesRead = in.read(readBuffer)) != -1) {
					sb.append(new String(readBuffer, 0, bytesRead));

					if (sb.toString().contains(text)) {
						sb = null;
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

}
