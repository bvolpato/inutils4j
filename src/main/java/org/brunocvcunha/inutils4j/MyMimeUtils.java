package org.brunocvcunha.inutils4j;

import java.io.File;

import eu.medsea.mimeutil.MimeUtil2;

public class MyMimeUtils {
	public static void main(String[] args) {
		System.out.println(getMimeType(new File("E:\\TFS\\Protheus\\Fontes_Doc\\V11\\Docs_Prod\\2_ESP_MP11\\0_BTs_ESP\\000000199552011_SPA.pdf")));
	}

	public static String getMimeType(File file) {
		MimeUtil2 mimeUtil = new MimeUtil2();
		mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
		//mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
		String mimeType = MimeUtil2.getMostSpecificMimeType(mimeUtil.getMimeTypes(file)).toString();

		return mimeType;
		
	}
}
