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

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * Reflection Difference Handling
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyDefaultReflectionDifferenceHandler implements MyReflectionDifferenceHandler {

  private static Logger log = Logger.getLogger(MyDefaultReflectionDifferenceHandler.class);
  
  public Object handleDifference(Object value, Class<?> toClazz) {
    log.debug("Handling value conversion (" + value.getClass() + " / "
        + value.toString() + ") to " + toClazz);
    
    if (toClazz.isAssignableFrom(String.class)) {
      return value.toString();
    }
    
    if (toClazz.isAssignableFrom(Date.class)) {
      
      if (value instanceof GregorianCalendar) {
        return ((GregorianCalendar)value).getTime();
      }
    }
    
    //return null;
    throw new IllegalArgumentException("Need to convert value (" + value.getClass() + " / "
        + value.toString() + ") to " + toClazz);
    
  }
}


