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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;

public class MyFileUtils {

  public static void copyFile(File f1, File f2) throws IOException {
    if (!f2.getParentFile().exists()) {
      f2.getParentFile().mkdirs();
    }

    InputStream in = null;
    OutputStream out = null;

    try {
      in = new FileInputStream(f1);
      out = new FileOutputStream(f2);

      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }

    } catch (IOException ex) {
      throw ex;
    } finally {
      try {
        in.close();
      } catch (IOException ex) {
      }

      try {
        out.close();
      } catch (IOException ex) {
      }

      if (f2.exists()) {
        f2.setLastModified(f1.lastModified());
      }
    }
  }


  public static void copyFolder(File source, File destination) {
    if (source.isDirectory()) {
      if (!destination.exists()) {
        destination.mkdirs();
      }

      String files[] = source.list();

      for (String file : files) {
        File srcFile = new File(source, file);
        File destFile = new File(destination, file);

        copyFolder(srcFile, destFile);
      }
    } else {
      InputStream in = null;
      OutputStream out = null;

      try {
        in = new FileInputStream(source);
        out = new FileOutputStream(destination);

        byte[] buffer = new byte[1024];

        int length;
        while ((length = in.read(buffer)) > 0) {
          out.write(buffer, 0, length);
        }
      } catch (Exception e) {
        try {
          in.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }

        try {
          out.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  // http://www.mkyong.com/java/how-to-delete-directory-in-java/
  public static void delete(File file) throws IOException {

    if (file.isDirectory()) {

      // directory is empty, then delete it
      if (file.list().length == 0) {
        file.delete();
      } else {

        // list all the directory contents
        String files[] = file.list();

        for (String temp : files) {
          // construct the file structure
          File fileDelete = new File(file, temp);

          // recursive delete
          delete(fileDelete);
        }

        // check the directory again, if empty then delete it
        if (file.list().length == 0) {
          file.delete();
        }
      }

    } else {
      // if file, then delete it
      file.delete();
    }
  }

  public static void deleteChildren(File file) throws IOException {

    if (file.isDirectory()) {
      // list all the directory contents
      String files[] = file.list();

      for (String temp : files) {
        // construct the file structure
        File fileDelete = new File(file, temp);

        // recursive delete
        delete(fileDelete);
      }

    }
  }

  public static void deleteEmptyChildren(File dir) {
    if (dir == null || !dir.exists()) {
      return;
    }

    for (File son : dir.listFiles()) {
      if (son.isDirectory()) {
        if (son.listFiles().length == 0) {
          System.out.println("Deleting " + son.getAbsolutePath());
          son.delete();
        } else {
          deleteEmptyChildren(son);
        }
      }
    }
  }


  public static void scanFile(final List<File> lista, final File arquivo) {
    scanFile(lista, arquivo, -1);
  }

  public static void scanFile(final List<File> lista, final File arquivo, int stopAfter) {
    if (stopAfter > 0 && lista.size() > stopAfter) {
      return;
    }

    try {
      if (arquivo.isDirectory()) {
        System.out.println("Scan " + arquivo.getName());

        for (final File arquivoFilho : arquivo.listFiles()) {

          if (arquivoFilho.isDirectory()) {
            scanFile(lista, arquivoFilho, stopAfter);
          } else {
            lista.add(arquivoFilho);
          }


          if (stopAfter > 0 && lista.size() > stopAfter) {
            return;
          }

        }

      } else {
        // System.out.println(arquivo.getAbsolutePath());

        if (arquivo != null) {
          lista.add(arquivo);

        }
      }
    } catch (Exception e) {
      System.err.println("Error fetching file -->  " + arquivo.getAbsolutePath() + " ["
          + e.getMessage() + "]");
      e.printStackTrace();
    }
  }

  public static void scanFile(final List<File> lista, final File arquivo, final FileFilter filter) {

    try {
      if (arquivo.isDirectory()) {
        for (final File arquivoFilho : arquivo.listFiles(filter)) {
          scanFile(lista, arquivoFilho, filter);
        }

      } else {
        // System.out.println(arquivo.getAbsolutePath());

        if (arquivo != null) {
          lista.add(arquivo);
        }
      }
    } catch (Exception e) {

    }
  }


  public static File chooseFileOpen(File dir, String title) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(dir);
    chooser.setDialogTitle(title);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setDialogType(JFileChooser.OPEN_DIALOG);

    chooser.showSaveDialog(null);
    File saveTo = chooser.getSelectedFile();

    return saveTo;
  }

  public static File chooseFileSave(File dir, String title) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(dir);
    chooser.setDialogTitle(title);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setDialogType(JFileChooser.SAVE_DIALOG);

    chooser.showSaveDialog(null);
    File saveTo = chooser.getSelectedFile();

    return saveTo;
  }

  public static File chooseDirOpen(File dir, String title) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(dir);
    chooser.setDialogTitle(title);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setDialogType(JFileChooser.OPEN_DIALOG);

    chooser.showSaveDialog(null);
    File saveTo = chooser.getSelectedFile();

    return saveTo;
  }

  public static void sortLines(File file) throws IOException {
    Collection<String> lines = MyStringUtils.getContentListSplit(file, "\r?\n");

    Set<String> sort = MyStringUtils.fixList(FixType.ALPHABETICALDELETEREPEATED, lines);
    MyStringUtils.saveToFile(0, sort, file.getAbsolutePath());
  }

}
