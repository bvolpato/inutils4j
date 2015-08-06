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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.brunocvcunha.inutils4j.MyArrUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Default tests.
 *
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyArrUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testeNumbers() {
        byte[] a = {1, 2, 3};

        MyArrUtils.reverse(a);

        assertEquals(3, a[0]);
        assertEquals(2, a[1]);
        assertEquals(1, a[2]);
    }
    
    @Test
    public void testePeek() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        for (int x = 0; x < 1000; x++) {
	        Integer random = MyArrUtils.peekRandom(list);
	        assertTrue(random >= 1);
	        assertTrue(random <= 3);
        }
    }
    
    @Test
    public void testePeekRandom() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);

        Set<Integer> randomSet = MyArrUtils.peekRandom(list, 3);
        assertEquals(3, randomSet.size());
        
        assertTrue(randomSet.contains(1));
        assertTrue(randomSet.contains(2));
        assertTrue(randomSet.contains(3));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testePeekRandomError() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);

        Set<Integer> randomSet = MyArrUtils.peekRandom(list, 4);
        assertEquals(4, randomSet.size());
    }
    
}
