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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Classpath (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyClasspathUtils {
  private static final String ADD_URL_METHOD = "addURL";

  /**
   * Add an URL to the given classloader
   * 
   * @param loader ClassLoader
   * @param url URL to add
   * @throws IOException I/O Error
   * @throws InvocationTargetException Invocation Error
   * @throws IllegalArgumentException Illegal Argument
   * @throws IllegalAccessException Illegal Access
   * @throws SecurityException Security Constraint
   * @throws NoSuchMethodException Method not found
   */
  public static void addJarToClasspath(ClassLoader loader, URL url) throws IOException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
    URLClassLoader sysloader = (URLClassLoader) loader;
    Class<?> sysclass = URLClassLoader.class;

    Method method =
        sysclass.getDeclaredMethod(MyClasspathUtils.ADD_URL_METHOD, new Class[] {URL.class});
    method.setAccessible(true);
    method.invoke(sysloader, new Object[] {url});

  }

}
