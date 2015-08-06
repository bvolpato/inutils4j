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

import static org.junit.Assert.assertTrue;

import org.brunocvcunha.inutils4j.MyNumberUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Default tests.
 *
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyNumberUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testeRandomInt() {
    	
    	//Num of tests
    	for (int x = 0; x < 10000; x++) {
    		
    		int random = MyNumberUtils.randomIntBetween(0, 10);
    		assertTrue(random >= 0);
    		assertTrue(random <= 10);
    	}
    	
    }
    
    
    @Test
    public void testeRandomLong() {
    	
    	//Num of tests
    	for (int x = 0; x < 10000; x++) {
    		
    		long random = MyNumberUtils.randomLongBetween(9000000, 900000000);
    		assertTrue(random >= 9000000);
    		assertTrue(random <= 900000000);
    	}
    	
    }
    
}
