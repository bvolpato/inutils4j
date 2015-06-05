package org.brunocunha.inutils4j.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.brunocunha.inutils4j.MyArrUtils;
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
