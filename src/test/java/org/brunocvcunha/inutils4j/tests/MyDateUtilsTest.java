package org.brunocvcunha.inutils4j.tests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.brunocvcunha.inutils4j.MyDateUtils;
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
    
    @Test
    public void testeAgoToday() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 10);;
        date.set(Calendar.MONTH, 5);;
        date.set(Calendar.YEAR, 2015);;
        
        Date calculated = MyDateUtils.calculateAgo(date.getTime(), "today");
        assertEquals(date.getTime().getTime(), calculated.getTime());
        
    }
    

    @Test
    public void testeAgoYesterday() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 10);;
        date.set(Calendar.MONTH, 5);;
        date.set(Calendar.YEAR, 2015);;
        
        Calendar calculatedCalendar = Calendar.getInstance();
        calculatedCalendar.setTime(MyDateUtils.calculateAgo(date.getTime(), "yesterday"));
        
        assertEquals(9, calculatedCalendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(5, calculatedCalendar.get(Calendar.MONTH));
        assertEquals(2015, calculatedCalendar.get(Calendar.YEAR));
        
    }
    
    @Test
    public void testeDaysAgo() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 10);;
        date.set(Calendar.MONTH, 5);;
        date.set(Calendar.YEAR, 2015);;
        
        Calendar calculatedCalendar = Calendar.getInstance();
        calculatedCalendar.setTime(MyDateUtils.calculateAgo(date.getTime(), "2 days ago"));
        
        assertEquals(8, calculatedCalendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(5, calculatedCalendar.get(Calendar.MONTH));
        assertEquals(2015, calculatedCalendar.get(Calendar.YEAR));
        
    }

    @Test
    public void testeWeeksAgo() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 27);;
        date.set(Calendar.MONTH, 5);;
        date.set(Calendar.YEAR, 2015);;
        
        Calendar calculatedCalendar = Calendar.getInstance();
        calculatedCalendar.setTime(MyDateUtils.calculateAgo(date.getTime(), "2 weeks ago"));
        
        assertEquals(13, calculatedCalendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(5, calculatedCalendar.get(Calendar.MONTH));
        assertEquals(2015, calculatedCalendar.get(Calendar.YEAR));
        
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testExceptionInvalid() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 10);;
        date.set(Calendar.MONTH, 5);;
        date.set(Calendar.YEAR, 2015);;
        
        Calendar calculatedCalendar = Calendar.getInstance();
        calculatedCalendar.setTime(MyDateUtils.calculateAgo(date.getTime(), "2 dias atras"));
        
        
    }

    
}
