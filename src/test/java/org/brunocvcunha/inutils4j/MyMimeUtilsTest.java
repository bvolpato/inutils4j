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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.brunocvcunha.inutils4j.MyMimeUtils;
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
