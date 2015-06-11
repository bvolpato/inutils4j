package org.brunocunha.inutils4j;

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

	public static byte[] readContentBytes(InputStream ios) throws IOException {
		ByteArrayOutputStream ous = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

		}
		return ous.toByteArray();
	}

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
