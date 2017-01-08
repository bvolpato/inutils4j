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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


/**
 * Image (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyImageUtils {

  
  /**
   * Converts a File instance to a BufferedImage
   * @param file file to get
   * @return Image in memory
   * @throws IOException
   */
  public static BufferedImage getImage(File file) throws IOException {
    return ImageIO.read(file);
  }
  
  /**
   * Trims an image (removes all leading and trailing white spaces)
   * @param img Image in memory
   * @return Trimmed image
   */
  public static BufferedImage trim(BufferedImage img) {
    int width = getTrimmedWidth(img);
    int height = getTrimmedHeight(img);
    int xStart = getTrimmedXStart(img);
    int yStart = getTrimmedYStart(img);

    BufferedImage newImg = new BufferedImage(width - xStart, height - yStart, BufferedImage.TYPE_INT_RGB);
    Graphics g = newImg.createGraphics();
    g.drawImage(img, 0 - xStart, 0 - yStart, null);

    return newImg;
  }

  /**
   * Get the first non-white X point
   * @param img Image n memory
   * @return the x start
   */
  private static int getTrimmedXStart(BufferedImage img) {
    int height = img.getHeight();
    int width = img.getWidth();
    int xStart = width;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (img.getRGB(j, i) != Color.WHITE.getRGB() && j < xStart) {
          xStart = j;
          break;
        }
      }
    }

    return xStart;
  }

  
  /**
   * Get the last non-white X point
   * @param img Image in memory
   * @return the trimmed width
   */
  private static int getTrimmedWidth(BufferedImage img) {
    int height = img.getHeight();
    int width = img.getWidth();
    int trimmedWidth = 0;

    for (int i = 0; i < height; i++) {
      for (int j = width - 1; j >= 0; j--) {
        if (img.getRGB(j, i) != Color.WHITE.getRGB() && j > trimmedWidth) {
          trimmedWidth = j;
          break;
        }
      }
    }

    return trimmedWidth;
  }

  /**
   * Get the first non-white Y point
   * @param img Image in memory
   * @return the trimmed y start
   */
  private static int getTrimmedYStart(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    int yStart = height;

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (img.getRGB(i, j) != Color.WHITE.getRGB() && j < yStart) {
          yStart = j;
          break;
        }
      }
    }

    return yStart;
  }


  /**
   * Get the last non-white Y point
   * @param img Image in memory
   * @return The trimmed height
   */
  private static int getTrimmedHeight(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    int trimmedHeight = 0;

    for (int i = 0; i < width; i++) {
      for (int j = height - 1; j >= 0; j--) {
        if (img.getRGB(i, j) != Color.WHITE.getRGB() && j > trimmedHeight) {
          trimmedHeight = j;
          break;
        }
      }
    }

    return trimmedHeight;
  }


  /**
   * Resizes an image to the specified height, changing width in the same proportion
   * @param originalImage Image in memory
   * @param heightOut The height to resize 
   * @return New Image in memory
   */
  public static BufferedImage resizeToHeight(BufferedImage originalImage, int heightOut) {

    int width = originalImage.getWidth();

    int height = originalImage.getHeight();

    int heightPercent = (heightOut * 100) / height;

    int newWidth = (width * heightPercent) / 100;

    BufferedImage resizedImage =
        new BufferedImage(newWidth, heightOut, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(originalImage, 0, 0, newWidth, heightOut, null);
    g.dispose();

    return resizedImage;
  }

  /**
   * Join images
   * @param images Images to join
   * @param lines Total of lines
   * @param height Image height to scale
   * @param minHeight Minimum height of image.
   * @param scale If should scale images to fit
   * @return Joined image
   */
  public static BufferedImage joinImages(List<BufferedImage> images, int lines, int height, int minHeight, boolean scale) {

      // Array of input images.
      List<BufferedImage> input = new ArrayList<BufferedImage>();

      int actualHeight = height;

      // if not scale, start from 1, so will use the max height of images, not more than that.
      if (!scale) {
        actualHeight = 1;
      }

      for (BufferedImage imageToJoin : images) {
        try {
          BufferedImage img = MyImageUtils.trim(imageToJoin);

          if (scale) {
            actualHeight = Integer.min(actualHeight, img.getHeight());
          } else {
            actualHeight = Integer.max(actualHeight, img.getHeight());
          }
          input.add(img);

        } catch (Exception x) {
          x.printStackTrace();
        }
      }

      // minimum height for the posts is 480
      if (scale && actualHeight < minHeight 
          && input.size() > 0) {
        actualHeight = minHeight;
      }

      int totalWidth = 0;
      List<BufferedImage> fixedInput = new ArrayList<BufferedImage>();
      for (BufferedImage img : input) {
        BufferedImage newImg = img;
        if (scale && img.getHeight() > actualHeight) {
          newImg = MyImageUtils.resizeToHeight(img, actualHeight);
        }

        fixedInput.add(newImg);

        totalWidth += newImg.getWidth();
      }

      // Create the output image and fill with white.
      
      BufferedImage output = getWhiteImage(actualHeight * lines, totalWidth / lines);
      drawImages(lines, scale, input, actualHeight, output.getGraphics());
      
      return output;
  }

  /**
   * @param lines Lines to draw
   * @param scale If images should be scaled
   * @param input Images to draw
   * @param actualHeight Each image height
   * @param output Graphics to write
   */
  protected static void drawImages(int lines, boolean scale, List<BufferedImage> input,
      int actualHeight, Graphics output) {

    int imagePerLine = input.size() / lines;
    int image = 0;
    
    for (int row = 0; row < lines; row++) {
      int currentWidth = 0;

      for (int col = 0; col < imagePerLine; col++) {
        BufferedImage img = input.get(image++);
        if (scale && img.getHeight() > actualHeight) {
          img = MyImageUtils.resizeToHeight(img, actualHeight);
        }
 
        output.drawImage(img, currentWidth, actualHeight * row, null);
        currentWidth += img.getWidth();
      }
    }
  }

  /**
   * @param actualHeight
   * @param totalWidth
   * @return
   */
  protected static BufferedImage getWhiteImage(int actualHeight, int totalWidth) {
    BufferedImage output = new BufferedImage(totalWidth, actualHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D bg = output.createGraphics();
    bg.setBackground(Color.WHITE);
    bg.clearRect(0, 0, totalWidth, actualHeight);
    return output;
  }


}
