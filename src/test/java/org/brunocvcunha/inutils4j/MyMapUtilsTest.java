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

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MyMapUtilsTest {
  @Test
  public void testRank() {
    
    Map<String, Integer> age = new HashMap<String, Integer>();
    age.put("a", 10);
    age.put("b", 5);
    age.put("c", 2);
    age.put("d", 4);
    
    
    Map<String, Integer> orderedMap = MyMapUtils.rankMapOnIntegerValue(age);
    Set<String> keySet = orderedMap.keySet();
    Iterator<String> itera = keySet.iterator();

    assertEquals("a", itera.next());
    assertEquals("b", itera.next());
    assertEquals("d", itera.next());
    assertEquals("c", itera.next());
  }
}
