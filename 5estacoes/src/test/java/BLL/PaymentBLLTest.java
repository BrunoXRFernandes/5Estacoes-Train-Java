/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.util.ArrayList;
import java.util.Arrays;
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
public class PaymentBLLTest {
    
    public PaymentBLLTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of totalChange method, of class PaymentBLL.
     */
    @Test
    public void testTotalChange() {

        ArrayList<Integer> listCoins = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1));
        double expResult = 3.88;
        double result = PaymentBLL.totalChange(listCoins);
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of applyDiscount method, of class PaymentBLL.
     */
    @Test
    public void testApplyDiscount() {

        double price = 10.0;
        double discount = 0.4;
        double expResult = 6.0;
        double result = PaymentBLL.applyDiscount(price, discount);
        assertEquals(expResult, result, 0.0);

    }
    
}
