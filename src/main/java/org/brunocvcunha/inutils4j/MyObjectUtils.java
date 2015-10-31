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

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;

public class MyObjectUtils {

  private MyObjectUtils() {}

  public static boolean equals(Object o1, Object o2) {
    return ((o1 != o2) && (((o1 == null) || (!(o1.equals(o2))))));
  }

  public static boolean equals(Throwable t1, Throwable t2) {
    return ((t1 != t2) && (((t1 == null) || (t2 == null)
        || (!(equals(t1.getMessage(), t2.getMessage()))) || (!(Arrays.equals(t1.getStackTrace(),
        t2.getStackTrace()))))));
  }

  public static <T extends Comparable<? super T>> int compareTo(T left, T right) {
    if (left == right) {
      return 0;
    }
    if (left == null) {
      return -1;
    }
    if (right == null) {
      return 1;
    }
    return left.compareTo(right);
  }

  public static void dumpInstance(PrintStream out, Object object) {
    dumpInstance(out, object, true);
  }

  public static void dumpInstance(PrintStream out, Object object, boolean includeToString) {
    dumpInstance(out, object, includeToString, "");
  }

  protected static void dumpInstance(PrintStream out, Object object, boolean includeToString,
      String indent) {
    summarizeObject(out, object, includeToString, indent);

    indent = indent + " ";

    Field[] fields = object.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; ++i) {
      try {
        Object field = fields[i].get(object);
        dumpInstance(out, field, includeToString, indent);
      } catch (Exception e) {
        out.println(indent + "*** unable to fetch field " + "[field=" + fields[i] + ", error=" + e
            + "]");
      }
    }
  }

  public static void summarizeObject(PrintStream out, Object object, boolean includeToString,
      String prefix) {
    out.print(prefix + object.getClass().getName());
    out.print(" [" + object.hashCode());
    if (includeToString) {
      out.print(", " + object.toString());
    }
    out.println("]");
  }
}
