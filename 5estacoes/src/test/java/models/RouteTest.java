/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
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
public class RouteTest {
    
    private Route route;
    
    public RouteTest() {
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
     * Test of getId method, of class Route.
     */
    @Test
    public void testGetId() {
        route = new Route();
        route.setId(1);
        int expResult = 1;
        int result = route.getId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getName method, of class Route.
     */
    @Test
    public void testGetName() {

        route = new Route();
        route.setName("teste");
        String expResult = "teste";
        String result = route.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getPosition method, of class Route.
     */
    @Test
    public void testGetPosition() {

        route = new Route();
        route.setPosition(0);
        int expResult = 0;
        int result = route.getPosition();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStations method, of class Route.
     */
    @Test
    public void testGetStations() {
        route = new Route();

        ArrayList<Station> aSt = new ArrayList<>();
        Station st1 = new Station();
        st1.setName("teste1");
        Station st2 = new Station();
        st2.setName("teste2");
        aSt.add(st1);
        aSt.add(st2);
        route.setStations(aSt);
        String expResult = "teste1";
        String result = route.getStations().get(0).getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDuration method, of class Route.
     */
    @Test
    public void testGetDuration() {
        route = new Route();
        route.setDuration(10);
        String expResult = "00:10";
        String result = route.getDuration();
        assertEquals(expResult, result);

    }
    
        /**
     * Test of getPrice method, of class Route.
     */
    @Test
    public void testGetPrice() {
        route = new Route();
        route.setPrice(3.00);
        double expResult = 3.00;
        double result = route.getPrice();
        assertEquals(expResult, result,0);

    }



    /**
     * Test of getNumberOfStations method, of class Route.
     */
    @Test
    public void testGetNumberOfStations() {
        route = new Route();
        route.setNumberOfStations(10);
        int expResult = 10;
        int result = route.getNumberOfStations();
        assertEquals(expResult, result);

    }

    /**
     * Test of getChangesOfLine method, of class Route.
     */
    @Test
    public void testGetChangesOfLine() {
        
        route = new Route();
        route.setChangesOfLine(10);
        int expResult = 10;
        int result = route.getChangesOfLine();
        assertEquals(expResult, result);   
 
    }

    /**
     * Test of setId method, of class Route.
     */
    @Test
    public void testSetId() {   
        route = new Route();
        route.setId(1);
        int expResult = 1;
        int result = route.getId();
        assertEquals(expResult, result);
     
    }

    /**
     * Test of setName method, of class Route.
     */
    @Test
    public void testSetName() {

        route = new Route();
        route.setName("teste");
        String expResult = "teste";
        String result = route.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of setPosition method, of class Route.
     */
    @Test
    public void testSetPosition() {
        
        route = new Route();
        route.setPosition(0);
        int expResult = 0;
        int result = route.getPosition();
        assertEquals(expResult, result);

    }

    /**
     * Test of setStations method, of class Route.
     */
    @Test
    public void testSetStations() {
        
         route = new Route();
        ArrayList<Station> aSt = new ArrayList<>();
        Station st1 = new Station();
        st1.setName("teste1");
        Station st2 = new Station();
        st2.setName("teste2");
        aSt.add(st1);
        aSt.add(st2);
        route.setStations(aSt);
        String expResult = "teste1";
        String result = route.getStations().get(0).getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of setDuration method, of class Route.
     */
    @Test
    public void testSetDuration() {
        route = new Route();
        route.setDuration(10);
        String expResult = "00:10";
        String result = route.getDuration();
        assertEquals(expResult, result);

    }

    /**
     * Test of setPrice method, of class Route.
     */
    @Test
    public void testSetPrice() {
        
        route = new Route();
        route.setPrice(3.00);
        double expResult = 3.00;
        double result = route.getPrice();
        assertEquals(expResult, result, 0);
    
    }

    /**
     * Test of setNumberOfStations method, of class Route.
     */
    @Test
    public void testSetNumberOfStations() {
        route = new Route();
        route.setNumberOfStations(10);
        int expResult = 10;
        int result = route.getNumberOfStations();
        assertEquals(expResult, result);

    }

    /**
     * Test of setChangesOfLine method, of class Route.
     */
    @Test
    public void testSetChangesOfLine() {
        route = new Route();
        route.setChangesOfLine(10);
        int expResult = 10;
        int result = route.getChangesOfLine();
        assertEquals(expResult, result);   

    }

    /**
     * Test of populateMetroAndFindRoute method, of class Route.
     */
    @Test
    public void testPopulateMetroAndFindRoute() throws Exception {

    }

    /**
     * Test of upTheLineOnePosition method, of class Route.
     */
    @Test
    public void testUpTheLineOnePosition() {

    }

    /**
     * Test of downTheLineOnePosition method, of class Route.
     */
    @Test
    public void testDownTheLineOnePosition() {

    }

    /**
     * Test of startAlgoFindRoute method, of class Route.
     */
    @Test
    public void testStartAlgoFindRoute() {

    }

    /**
     * Test of checkIfLineContainsStation method, of class Route.
     */
    @Test
    public void testCheckIfLineContainsStation() {

    }

    /**
     * Test of positionOfStationInLine method, of class Route.
     */
    @Test
    public void testPositionOfStationInLine() {
      
    }

    /**
     * Test of checkIfStationIsLastStation method, of class Route.
     */
    @Test
    public void testCheckIfStationIsLastStation() {
;
    }

    /**
     * Test of deepCopyOfRota method, of class Route.
     */
    @Test
    public void testDeepCopyOfRota() {

    }

    /**
     * Test of showRoutesFixtStartStation method, of class Route.
     */
    @Test
    public void testShowRoutesFixtStartStation() throws Exception {
    }


}
