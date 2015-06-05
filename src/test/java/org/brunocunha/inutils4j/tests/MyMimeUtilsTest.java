package org.brunocunha.inutils4j.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.brunocunha.inutils4j.MyMimeUtils;
import org.junit.Test;

public class MyMimeUtilsTest {

	@Test
	public void testPdf() throws URISyntaxException {
		assertEquals(MyMimeUtils.getMimeType(getFile("mime_teste")), "application/pdf");
	}
	
	@Test
	public void testPleno() throws URISyntaxException {
		assertEquals(MyMimeUtils.getMimeType(getFile("mime_teste2.txt")), "text/plain");
	}
	
	public File getFile(String resourceName) throws URISyntaxException {
		URL url = ClassLoader.getSystemResource(resourceName);
		File arq = new File(url.toURI());
		
		return arq;
	}
}
