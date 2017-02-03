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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Concurrency (In)utilities. --
 * http://stackoverflow.com/questions/5715235/java-set-timeout-on-a-certain-block-of-code
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyConcurrencyUtils {

  /**
   * @param runnable Runnable to run
   * @param timeout Timeout number
   * @param timeUnit Time unit
   * @throws Exception Error
   */
  public static void runWithTimeout(final Runnable runnable, long timeout, TimeUnit timeUnit)
      throws Exception {
    runWithTimeout(new Callable<Object>() {
      @Override
      public Object call() throws Exception {
        runnable.run();
        return null;
      }
    }, timeout, timeUnit);
  }

  /**
   * @param callable Callable to run
   * @param timeout Timeout number
   * @param timeUnit Time unit
   * @throws Exception Error
   */
  public static <T> T runWithTimeout(Callable<T> callable, long timeout, TimeUnit timeUnit)
      throws Exception {
    final ExecutorService executor = Executors.newSingleThreadExecutor();
    final Future<T> future = executor.submit(callable);
    executor.shutdown();
    try {
      return future.get(timeout, timeUnit);
    } catch (TimeoutException e) {
      // remove this if you do not want to cancel the job in progress
      // or set the argument to 'false' if you do not want to interrupt the thread
      future.cancel(true);
      throw e;
    } catch (ExecutionException e) {
      // unwrap the root cause
      Throwable t = e.getCause();
      if (t instanceof Error) {
        throw (Error) t;
      } else if (t instanceof Exception) {
        throw (Exception) t;
      } else {
        throw new IllegalStateException(t);
      }
    }
  }
}
