package org.brunocunha.inutils4j.tests;

import static org.junit.Assert.assertTrue;

import org.brunocunha.inutils4j.MyNumberUtils;
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
