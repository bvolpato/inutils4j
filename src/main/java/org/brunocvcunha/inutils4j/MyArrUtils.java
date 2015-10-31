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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MyArrUtils {

  protected static Random _rnd = new Random();

  public static <T> int indexOf(T[] values, T value) {
    int count = (values == null) ? 0 : values.length;
    for (int ii = 0; ii < count; ++ii) {
      if (MyObjectUtils.equals(values[ii], value)) {
        return ii;
      }
    }
    return -1;
  }

  public static <T> T peekRandom(T[] list) {
    return peekRandom(Arrays.asList(list));
  }

  public static <T> T peekRandom(List<T> list) {
    int random = new Random().nextInt(list.size());
    return list.get(random);
  }

  public static <T> Set<T> peekRandom(T[] list, int quantity) {
    return peekRandom(Arrays.asList(list), quantity);
  }

  public static <T> Set<T> peekRandom(List<T> list, int quantity) {
    Set<T> set = new HashSet<T>(list);
    if (set.size() < quantity) {
      throw new IllegalArgumentException("List unique values cannot be less than quantity");
    }

    Set<T> returnSet = new HashSet<T>();
    while (returnSet.size() != quantity) {
      int random = new Random().nextInt(list.size());
      T obj = list.get(random);

      returnSet.add(obj);
    }

    return returnSet;
  }


  public static int indexOf(int[] values, int value) {
    int count = (values == null) ? 0 : values.length;
    for (int ii = 0; ii < count; ++ii) {
      if (values[ii] == value) {
        return ii;
      }
    }
    return -1;
  }

  public static int indexOf(byte[] values, byte value) {
    int count = (values == null) ? 0 : values.length;
    for (int ii = 0; ii < count; ++ii) {
      if (values[ii] == value) {
        return ii;
      }
    }
    return -1;
  }

  public static int indexOf(float[] values, float value) {
    int count = (values == null) ? 0 : values.length;
    for (int ii = 0; ii < count; ++ii) {
      if (values[ii] == value) {
        return ii;
      }
    }
    return -1;
  }

  public static void reverse(byte[] values) {
    reverse(values, 0, values.length);
  }

  public static void reverse(byte[] values, int offset, int length) {
    int aidx = offset;
    int bidx = offset + length - 1;
    while (bidx > aidx) {
      byte value = values[aidx];
      values[aidx] = values[bidx];
      values[bidx] = value;
      ++aidx;
      --bidx;
    }
  }

  public static void reverse(int[] values) {
    reverse(values, 0, values.length);
  }

  public static void reverse(int[] values, int offset, int length) {
    int aidx = offset;
    int bidx = offset + length - 1;
    while (bidx > aidx) {
      int value = values[aidx];
      values[aidx] = values[bidx];
      values[bidx] = value;
      ++aidx;
      --bidx;
    }
  }


  public static int findArray(byte[] largeArray, byte[] subArray) {

    /* If any of the arrays is empty then not found */
    if (largeArray.length == 0 || subArray.length == 0) {
      return -1;
    }

    /* If subarray is larger than large array then not found */
    if (subArray.length > largeArray.length) {
      return -1;
    }

    for (int i = 0; i < largeArray.length; i++) {
      /* Check if the next element of large array is the same as the first element of subarray */
      if (largeArray[i] == subArray[0]) {

        boolean subArrayFound = true;
        for (int j = 0; j < subArray.length; j++) {
          /* If outside of large array or elements not equal then leave the loop */
          if (largeArray.length <= i + j || subArray[j] != largeArray[i + j]) {
            subArrayFound = false;
            break;
          }
        }

        /* Sub array found - return its index */
        if (subArrayFound) {
          return i;
        }

      }
    }

    /* Return default value */
    return -1;
  }

}
