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

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class MySystemUtils {

  public String osName;
  public String osVersion;
  public String osArch;
  public String javaVersion;
  public String javaVendor;
  public long freeMemory;
  public long usedMemory;
  public long totalMemory;
  public long maxMemory;
  public boolean isHeadless;
  public int bitDepth;
  public int refreshRate;
  public boolean isFullScreen;
  public int displayWidth;
  public int displayHeight;

  public static void main(String[] args) {
    System.out.println(new MySystemUtils());
  }

  public MySystemUtils() {
    update();
  }

  public void update() {
    this.osName = System.getProperty("os.name");
    this.osVersion = System.getProperty("os.version");
    this.osArch = System.getProperty("os.arch");
    this.javaVersion = System.getProperty("java.version");
    this.javaVendor = System.getProperty("java.vendor");

    Runtime rtime = Runtime.getRuntime();
    this.freeMemory = (rtime.freeMemory() / 1024L);
    this.totalMemory = (rtime.totalMemory() / 1024L);
    this.usedMemory = (this.totalMemory - this.freeMemory);
    this.maxMemory = (rtime.maxMemory() / 1024L);

    this.isHeadless = GraphicsEnvironment.isHeadless();
    if (this.isHeadless) {
      return;
    }
    try {
      GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice gd = env.getDefaultScreenDevice();
      DisplayMode mode = gd.getDisplayMode();
      this.bitDepth = mode.getBitDepth();
      this.refreshRate = (mode.getRefreshRate() / 1024);
      this.isFullScreen = (gd.getFullScreenWindow() != null);
      this.displayWidth = mode.getWidth();
      this.displayHeight = mode.getHeight();
    } catch (Throwable t) {
      this.isHeadless = true;
    }
  }

  public String osToString() {
    return this.osName + " (" + this.osVersion + "-" + this.osArch + ")";
  }

  public String jvmToString() {
    return this.javaVersion + ", " + this.javaVendor;
  }

  public String memoryToString() {
    return this.freeMemory + "k free, " + this.usedMemory + "k used, " + this.totalMemory
        + "k total, " + this.maxMemory + "k max";
  }

  public String videoToString() {
    if (this.isHeadless) {
      return "headless or unavailable";
    }

    String sdepth = this.bitDepth + "-bit";
    String srate = this.refreshRate + "kHz";
    String sfull = (this.isFullScreen) ? "full-screen" : "windowed";
    return this.displayWidth + "x" + this.displayHeight + ", " + sdepth + ", " + srate + ", "
        + sfull;
  }

  public static File createTempDirectory() {
    File temp;
    try {
      temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
      temp.delete();
      temp.mkdirs();

      return temp;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static File createTempFile() {
    File temp;
    try {
      temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
      return temp;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("OS: ").append(osToString()).append("\n");
    buf.append("JVM: ").append(jvmToString()).append("\n");
    buf.append("Memory: ").append(memoryToString()).append("\n");
    buf.append("Video: ").append(videoToString());
    return buf.toString();
  }
}
