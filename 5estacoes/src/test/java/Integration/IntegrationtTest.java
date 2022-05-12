/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Integration;

import BLL.PassBLL;
import BLL.PassTypeBLL;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Client;
import models.PassType;
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
public class IntegrationtTest {
    
    public IntegrationtTest() {
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

    // TODO Class Client and 
    //
     @Test
     public void newClientAge() { 
     
     Client client  = new Client();
     client.setBirthDate(LocalDate.now().minusYears(10));
     
     int result = PassBLL.calculateClientAge(client.getBirthDate());
     
     int expResult = 10;
      
     assertEquals(expResult, result);

     
     
     
     }
}
