/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import models.Line;
import models.Route;
import models.Station;
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
public class RouteBLLTest {

    public RouteBLLTest() {
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
     * Test of calculatePrice method, of class RouteBLL.
     */
    @Test
    public void testCalculatePrice() throws SQLException {

        Route route = new Route();
        ArrayList<Station> aSt = new ArrayList<>();
        Station st1 = new Station();
        st1.setPrice(1);
        Station st2 = new Station();
        st2.setPrice(2);
        aSt.add(st2);
        route.setStations(aSt);
        aSt.add(st1);

        RouteBLL instance = new RouteBLL();
        instance.route = route;

        instance.calculatePrice();

        double expResult = instance.route.getPrice();

        assertEquals(expResult, 3, 0);
    }

    /**
     * Test of calculateChangesOfLine method, of class Route.
     */
    @Test
    public void testCalculateChangesOfLine() {

        ArrayList<Station> stations;

        stations = new ArrayList();

        Station st1 = new Station();
        Line l1 = new Line();
        l1.setKey('B');

        st1.setLine(l1);

        Station st2 = new Station();
        Line l2 = new Line();
        l2.setKey('B');
        st2.setLine(l2);

        Station st3 = new Station();
        Line l3 = new Line();
        l3.setKey('V');
        st3.setLine(l3);

        stations.add(st1);
        stations.add(st2);
        stations.add(st3);

        int expResult = 2;

        Route route = new Route();
        route.setStations(stations);

        RouteBLL instance = new RouteBLL();
        instance.route = route;

        instance.calculateChangesOfLine();

        int result = route.getChangesOfLine();

        assertEquals(expResult, result);

    }

    /**
     * Test of calculateTime method, of class Route.
     */
    @Test
    public void testCalculateTime() throws Exception {

        // Test com o ID da base de dados se a base de dados 1for novo tem de ser corrigido, nova usa o id do line station
        ArrayList<Station> stations;
        stations = new ArrayList();

        // Aveleda id 1159 Line Station id 7 Linha verde
        Station st1 = new Station();
        st1.setId(1159);
        Line l1 = new Line();
        l1.setId(11);
        st1.setLine(l1);

        //Casal Garcia id 1160 Line Station id 7 Linha verde
        Station st2 = new Station();
        st2.setId(1160);
        Line l2 = new Line();
        l2.setId(11);
        st2.setLine(l2);

        //Casal Gatão id 1161 Line Station id 7 Linha verde
        Station st3 = new Station();
        st3.setId(1161);
        Line l3 = new Line();
        l3.setId(11);
        st3.setLine(l3);

        stations.add(st1);
        stations.add(st2);
        stations.add(st3);

        String expResult = "04:25";

        Route route = new Route();
        route.setStations(stations);

        RouteBLL instance = new RouteBLL();
        instance.route = route;

        instance.calculateTime();

        String result = route.getDuration();

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
