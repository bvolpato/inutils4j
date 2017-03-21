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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * List utilities
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyListUtils {

  /**
   * Convert Collection to Set
   * @param collection Collection
   * @return Set
   */
  public static <T> Set<T> getSet(Collection<T> collection) {
    Set<T> set = new LinkedHashSet<T>();
    set.addAll(collection);

    return set;
  }

  /**
   * Joins a collection in a string using a delimiter
   * @param col Collection
   * @param delim Delimiter
   * @return String
   */
  public static <T> String join(Collection<T> col, String delim) {
    StringBuilder sb = new StringBuilder();
    Iterator<T> iter = col.iterator();
    if (iter.hasNext())
      sb.append(iter.next().toString());
    while (iter.hasNext()) {
      sb.append(delim);
      sb.append(iter.next().toString());
    }
    return sb.toString();
  }

  /**
   * List chop - create lists of lists 
   * @param list Input
   * @param length the maximum length of each sub-list 
   * @return a List of Lists for the given type (T)
   */
  public static <T> List<List<T>> chop(List<T> list, int length) {
      int useLength = length;
      if (useLength == 0) {
        useLength = list.size();
      }
      
      List<List<T>> parts = new ArrayList<>();
      final int listSize = list.size();
      for (int i = 0; i < listSize; i += useLength) {
          parts.add(new ArrayList<>(
                  list.subList(i, Math.min(listSize, i + useLength))));
      }
      return parts;
  }
}
