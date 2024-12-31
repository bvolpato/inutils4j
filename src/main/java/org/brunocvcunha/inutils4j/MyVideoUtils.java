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

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Video (In)utilities.
 * width/height sample from https://github.com/sannies/mp4parser/
 * -- Here be dragons
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyVideoUtils {

  private static List<String> containers = Arrays.asList("moov", "mdia", "trak");

  /**
   * Returns the dimensions for the video
   * @param videoFile Video file
   * @return the dimensions
   * @throws IOException
   */
  public static Dimension getDimension(File videoFile) throws IOException {
    try (FileInputStream fis = new FileInputStream(videoFile)) {
      return getDimension(fis, new AtomicReference<ByteBuffer>());
    }
  }

  /**
   * Internal loop to fiond w/h headers
   * @param fis Stream
   * @param lastTkhdHolder Holder
   * @return Dimensions
   * @throws IOException
   */
  private static Dimension getDimension(InputStream fis, AtomicReference<ByteBuffer> lastTkhdHolder)
      throws IOException {

    while (fis.available() > 0) {
      byte[] header = new byte[8];
      fis.read(header);

      long size = readUint32(header, 0);
      String type = new String(header, 4, 4, "ISO-8859-1");
      if (containers.contains(type)) {

        Dimension dimension = getDimension(fis, lastTkhdHolder);
        if (dimension != null) {
          return dimension;
        }

      } else if (type.equals("tkhd")) {
        byte[] lastTkhd = new byte[(int) (size - 8)];
        lastTkhdHolder.set(ByteBuffer.wrap(lastTkhd));
        fis.read(lastTkhd);
      } else if (type.equals("hdlr")) {
        byte[] hdlr = new byte[(int) (size - 8)];
        fis.read(hdlr);
        if (hdlr[8] == 0x76 && hdlr[9] == 0x69 && hdlr[10] == 0x64 && hdlr[11] == 0x65) {
          byte[] lastTkhd = lastTkhdHolder.get().array();
          return new Dimension((int) readFixedPoint1616(lastTkhd, lastTkhd.length - 8),
              (int) readFixedPoint1616(lastTkhd, lastTkhd.length - 4));
        }
      } else {
        fis.skip(size - 8);
      }
    }

    return null;
  }
  
  private static long readUint32(byte[] b, int s) {
    long result = 0;
    result |= ((b[s + 0] << 24) & 0xFF000000);
    result |= ((b[s + 1] << 16) & 0xFF0000);
    result |= ((b[s + 2] << 8) & 0xFF00);
    result |= ((b[s + 3]) & 0xFF);
    return result;
  }

  private static double readFixedPoint1616(byte[] b, int s) {
    return ((double) readUint32(b, s)) / 65536;
  }



}
