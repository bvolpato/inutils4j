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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        date.set(Calendar.DAY_OF_MONTH, 10);
        date.set(Calendar.MONTH, 5);
        date.set(Calendar.YEAR, 2015);
        
        Date calculated = MyDateUtils.calculateAgo(date.getTime(), "today");
        assertEquals(date.getTime().getTime(), calculated.getTime());
        
    }
    

    @Test
    public void testeAgoYesterday() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 10);
        date.set(Calendar.MONTH, 5);
        date.set(Calendar.YEAR, 2015);
        
        Calendar calculatedCalendar = Calendar.getInstance();
        calculatedCalendar.setTime(MyDateUtils.calculateAgo(date.getTime(), "yesterday"));
        
        assertEquals(9, calculatedCalendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(5, calculatedCalendar.get(Calendar.MONTH));
        assertEquals(2015, calculatedCalendar.get(Calendar.YEAR));
        
    }
    
    @Test
    public void testeDaysAgo() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 10);
        date.set(Calendar.MONTH, 5);
        date.set(Calendar.YEAR, 2015);
        
        Calendar calculatedCalendar = Calendar.getInstance();
        calculatedCalendar.setTime(MyDateUtils.calculateAgo(date.getTime(), "2 days ago"));
        
        assertEquals(8, calculatedCalendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(5, calculatedCalendar.get(Calendar.MONTH));
        assertEquals(2015, calculatedCalendar.get(Calendar.YEAR));
        
    }

    @Test
    public void testeWeeksAgo() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 27);
        date.set(Calendar.MONTH, 5);
        date.set(Calendar.YEAR, 2015);
        
        Calendar calculatedCalendar = Calendar.getInstance();
        calculatedCalendar.setTime(MyDateUtils.calculateAgo(date.getTime(), "2 weeks ago"));
        
        assertEquals(13, calculatedCalendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(5, calculatedCalendar.get(Calendar.MONTH));
        assertEquals(2015, calculatedCalendar.get(Calendar.YEAR));
        
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testExceptionInvalid() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, 10);
        date.set(Calendar.MONTH, 5);
        date.set(Calendar.YEAR, 2015);
        
        Calendar calculatedCalendar = Calendar.getInstance();
        calculatedCalendar.setTime(MyDateUtils.calculateAgo(date.getTime(), "2 dias atras"));
        
        
    }
    
    @Test
    public void testMergeDate() throws ParseException {
      Date day = new SimpleDateFormat("dd/MM/yyyy").parse("05/08/2015");
      Date dayTime = MyDateUtils.mergeDateAndTime(day, "15:12:11");
      
      assertEquals("05/08/2015 15:12:11", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dayTime));
    }

    
}
