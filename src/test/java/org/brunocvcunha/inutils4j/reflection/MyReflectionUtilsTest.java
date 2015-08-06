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
package org.brunocvcunha.inutils4j.reflection;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Some testing for reflection utilities
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyReflectionUtilsTest {

  @Test
  public void simpleReflect() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
    
    Map<String, Object> brunoMap = new HashMap<String, Object>();
    brunoMap.put("name", "Bruno Candido Volpato da Cunha");
    brunoMap.put("age", 24);
    
    PersonPOJO brunoVO = MyReflectionUtils.buildInstanceForMap(PersonPOJO.class, brunoMap);
    
    assertEquals("Bruno Candido Volpato da Cunha", brunoVO.getName());
    assertEquals((Integer) 24, brunoVO.getAge());
  }
  
  
}
class PersonPOJO {
  private String name;
  private Integer age;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Integer getAge() {
    return age;
  }
  public void setAge(Integer age) {
    this.age = age;
  }
  
  
}
