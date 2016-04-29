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

import java.util.Random;

/**
 * Number Utilities
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyNumberUtils {

  // https://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
  /**
   * Returns an integer between interval
   * @param min Minimum value
   * @param max Maximum value
   * @return int number
   */
  public static int randomIntBetween(int min, int max) {
    Random rand = new Random();
    return rand.nextInt((max - min) + 1) + min;
  }

  // https://stackoverflow.com/questions/13725478/java-random-long-value-in-an-interval
  /**
   * Returns a long between interval
   * @param min Minimum value
   * @param max Maximum value
   * @return long number
   */
  public static long randomLongBetween(long min, long max) {
    Random rand = new Random();
    return min + (long) (rand.nextDouble() * (max - min));
  }
  
  /**
   * Toss a Coin ;-)
   * @return true = heads, false = tails
   */
  public static boolean tossCoin() {
    return new Random().nextInt(2) == 1; 
  }
  
}
