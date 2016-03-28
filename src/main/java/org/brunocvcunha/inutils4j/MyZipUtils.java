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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * ZIP (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyZipUtils {

  /**
   * Extracts the zip file to the output folder
   * 
   * @param zipFile ZIP File to extract
   * @param outputFolder Output Folder
   * @return A Collection with the extracted files
   * @throws IOException I/O Error
   */
  public static List<File> extract(File zipFile, File outputFolder) throws IOException {
    List<File> extracted = new ArrayList<File>();

    byte[] buffer = new byte[2048];

    if (!outputFolder.exists()) {
      outputFolder.mkdir();
    }

    ZipInputStream zipInput = new ZipInputStream(new FileInputStream(zipFile));

    ZipEntry zipEntry = zipInput.getNextEntry();

    while (zipEntry != null) {

      String neFileNameName = zipEntry.getName();
      File newFile = new File(outputFolder + File.separator + neFileNameName);

      newFile.getParentFile().mkdirs();

      if (!zipEntry.isDirectory()) {
        FileOutputStream fos = new FileOutputStream(newFile);

        int size;
        while ((size = zipInput.read(buffer)) > 0) {
          fos.write(buffer, 0, size);
        }

        fos.close();
        extracted.add(newFile);
      }

      zipEntry = zipInput.getNextEntry();
    }

    zipInput.closeEntry();
    zipInput.close();

    return extracted;

  }



  /**
   * Checks if a Zip is valid navigating through the entries
   * 
   * @param file File to validate 
   * @throws IOException I/O Error
   */
  public static void validateZip(File file) throws IOException {
    ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
    ZipEntry zipEntry = zipInput.getNextEntry();

    while (zipEntry != null) {
      zipEntry = zipInput.getNextEntry();
    }

    try {
      if (zipInput != null) {
        zipInput.close();
      }
    } catch (IOException e) {
    }
  }

  /**
   * Compress a directory into a zip file
   * 
   * @param dir Directory
   * @param zipFile ZIP file to create
   * @throws IOException I/O Error
   */
  public static void compress(File dir, File zipFile) throws IOException {

    FileOutputStream fos = new FileOutputStream(zipFile);
    ZipOutputStream zos = new ZipOutputStream(fos);

    recursiveAddZip(dir, zos, dir);

    zos.finish();
    zos.close();

  }

  /**
   * Recursively add files to a ZipOutputStream
   * 
   * @param parent Parent file
   * @param zout ZipOutputStream to append
   * @param fileSource The file source
   * @throws IOException I/O Error
   */
  public static void recursiveAddZip(File parent, ZipOutputStream zout, File fileSource)
      throws IOException {

    File[] files = fileSource.listFiles();

    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory()) {
        recursiveAddZip(parent, zout, files[i]);
        continue;
      }

      byte[] buffer = new byte[1024];

      FileInputStream fin = new FileInputStream(files[i]);

      ZipEntry zipEntry =
          new ZipEntry(files[i].getAbsolutePath()
              .replace(parent.getAbsolutePath(), "").substring(1)); //$NON-NLS-1$
      zout.putNextEntry(zipEntry);

      int length;
      while ((length = fin.read(buffer)) > 0) {
        zout.write(buffer, 0, length);
      }

      zout.closeEntry();

      fin.close();

    }

  }
}
