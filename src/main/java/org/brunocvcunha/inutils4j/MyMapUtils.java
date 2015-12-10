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

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Map utilities
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyMapUtils {

  public static <K> Map<K, Integer> rankMapOnIntegerValue(Map<K, Integer> inputMap) {
    Map<K, Integer> newMap = new TreeMap<K, Integer>(new IntegerValueComparator(inputMap));
    newMap.putAll(inputMap);

    Map<K, Integer> linkedMap = new LinkedHashMap<K, Integer>(newMap);
    return linkedMap;
  }


}


class IntegerValueComparator implements Comparator<Object> {
  Map<?, Integer> base;

  public IntegerValueComparator(Map<?, Integer> base) {
    this.base = base;
  }

  public int compare(Object a, Object b) {
    if (base.get(a) >= base.get(b)) {
      return -1;
    } else {
      return 1;
    }
  }
}
