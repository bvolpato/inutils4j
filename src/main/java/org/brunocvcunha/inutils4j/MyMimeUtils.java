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

import eu.medsea.mimeutil.MimeUtil2;

public class MyMimeUtils {
  public static void main(String[] args) {
    System.out
        .println(getMimeType(new File(
            "E:\\TFS\\Protheus\\Fontes_Doc\\V11\\Docs_Prod\\2_ESP_MP11\\0_BTs_ESP\\000000199552011_SPA.pdf")));
  }

  public static String getMimeType(File file) {
    MimeUtil2 mimeUtil = new MimeUtil2();
    mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
    mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
    // mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
    String mimeType = MimeUtil2.getMostSpecificMimeType(mimeUtil.getMimeTypes(file)).toString();

    return mimeType;

  }
}
