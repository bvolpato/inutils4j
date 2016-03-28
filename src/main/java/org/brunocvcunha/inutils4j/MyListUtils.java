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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * List utilities
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyListUtils {

  public <T> Set<T> getSet(Collection<T> collection) {
    Set<T> set = new TreeSet<T>();
    set.addAll(collection);

    return set;
  }

  public <T> String join(Collection<T> col, String delim) {
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

}
