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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Reflection (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyReflectionUtils {

  private static Logger log = Logger.getLogger(MyReflectionUtils.class);

  /**
   * Builds a instance of the class for a map containing the values, without specifying the handler for differences
   * 
   * @param clazz
   * @param values
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IntrospectionException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  public static <T> T buildInstanceForMap(Class<T> clazz, Map<String, Object> values)
      throws InstantiationException, IllegalAccessException, IntrospectionException,
      IllegalArgumentException, InvocationTargetException {
    return buildInstanceForMap(clazz, values, new MyDefaultReflectionDifferenceHandler());
  }


  /**
   * Builds a instance of the class for a map containing the values
   * 
   * @param clazz
   * @param values
   * @param differenceHandler
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IntrospectionException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  public static <T> T buildInstanceForMap(Class<T> clazz, Map<String, Object> values, MyReflectionDifferenceHandler differenceHandler)
      throws InstantiationException, IllegalAccessException, IntrospectionException,
      IllegalArgumentException, InvocationTargetException {

    log.debug("Building new instance of Class " + clazz.getName());

    T instance = clazz.newInstance();

    for (String key : values.keySet()) {
      Object value = values.get(key);

      if (value == null) {
        log.debug("Value for field " + key + " is null, so ignoring it...");
        continue;
      }
      
      log.debug(
          "Invoke setter for " + key + " (" + value.getClass() + " / " + value.toString() + ")");
      Method setter = null;
      try {
        setter = new PropertyDescriptor(key.replace('.', '_'), clazz).getWriteMethod();
      } catch(Exception e) {
        throw new IllegalArgumentException("Setter for field " + key + " was not found", e);
      }

      Class<?> argumentType = setter.getParameterTypes()[0];

      if (argumentType.isAssignableFrom(value.getClass())) {
        setter.invoke(instance, value);
      } else {

        Object newValue = differenceHandler.handleDifference(value, setter.getParameterTypes()[0]);
        setter.invoke(instance, newValue);

      }
    }

    return instance;
  }



  
  
  
}


