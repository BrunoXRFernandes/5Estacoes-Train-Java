/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pcoelho
 */
public class TimeParseTest {
    
    private TimeParse timeParse;
    
    public TimeParseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        timeParse = new TimeParse();
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of timeToInt method, of class TimeParse.
     */
    @Test
    public void testTimeToInt() {
        assertEquals(83, timeParse.timeToInt("01:23"));
    }

    /**
     * Test of timeToString method, of class TimeParse.
     */
    @Test
    public void testTimeToString() {
            assertEquals("01:23",timeParse.timeToString(83));
    }
    
}
