package org.brunocunha.inutils4j.tests;

import static org.junit.Assert.assertEquals;

import org.brunocunha.inutils4j.MyDateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Default tests.
 *
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyDateUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testeDisplay() {
        int secondsHour = 3600;
        int secondsHourMin = 3660;

        assertEquals(MyDateUtils.disposeSeconds(secondsHour), "1h");
        assertEquals(MyDateUtils.disposeSeconds(secondsHourMin), "1h1m");
    }
}
