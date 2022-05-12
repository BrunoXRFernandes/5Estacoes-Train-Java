/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAOs.StationDAO;
import DAOs.TripDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Line;
import models.Route;
import models.Station;

/**
 *
 * @author nelso
 */
public class RouteBLL {

    public Route route;
    private static ArrayList<Route> allRoutes = new ArrayList<>();
    public static ArrayList<ArrayList<Station>> routes;

    StationDAO stationDAO = new StationDAO();
    TripDAO tripDAO = new TripDAO();

    public RouteBLL() {
        route = new Route();
    }
    

    /**
     * Calculates the price of the Route, adding all the stations price
     */
    public void calculatePrice() {
        double price = 0;
        for (int i = 0; i < route.getStations().size(); i++) {
            price += route.getStations().get(i).getPrice();
        }
        route.setPrice(price);
    }

    /**
     * Calculates how many changes of line in this route
     */
    public void calculateChangesOfLine() {
        // set the number of station to 0
        route.setNumberOfStations(0);

        //ArrayList of char lines not repeated
        ArrayList<Character> countLines = new ArrayList<Character>();
     
        for (int i = 0; i < route.getStations().size(); i++) {
            //current line
            char lineCurrent = route.getStations().get(i).getLine().getKey();
            //if current line is different from line A that means there was a change
            if (!countLines.contains(lineCurrent)) {
               
                countLines.add(lineCurrent);
                
            }

        }
        route.setChangesOfLine(countLines.size());
        route.setNumberOfStations(route.getStations().size());
    }

    /**
     * Calculates the time of the Route
     */
    public void calculateTime() throws SQLException {
        int time = 0;
        for (int i = 1; i < route.getStations().size(); i++) {
            time += tripDAO.getDurationTrip(route.getStations().get(i - 1), route.getStations().get(i));
        }
        route.setDuration(time);
    }

    /**
     * Get Arraylist of Arraylist of Station and Finds Route
     *
     * @param nameOfStartStation
     * @param nameOfEndSation
     *
     */
    public ArrayList<Route> populateMetroAndFindRoute(String nameOfStartStation, String nameOfEndSation) throws SQLException {
        // Initialize Metro
        ArrayList<ArrayList<Station>> metro = new ArrayList<>();

        //Populate metro
        metro = stationDAO.getLinesAndStation();

        //Initialize start and end station
        Station startingStation = new Station();
        startingStation.setName(nameOfStartStation);
        Station endingStation = new Station();
        endingStation.setName(nameOfEndSation);

        //Initialize Arraylist of routes
        routes = new ArrayList<>();

        //Inilializa ArrayList of rota
        ArrayList<Station> rota = new ArrayList<>();
        // get station by name
        rota.add(startingStation);
        //Start the algorithm
        startAlgoFindRoute(metro, startingStation, endingStation, rota);

        //System.out.println("FIX FIRST STATION");
        //Show each rota in routes
        showRoutesFixtStartStation();

        return allRoutes;
    }

    /**
     * Go one Position Up the Line and if not in route add to the route
     *
     * @param metro
     * @param line
     * @param position
     * @param endStation
     * @param rota
     *
     */
    public static void upTheLineOnePosition(ArrayList<ArrayList<Station>> metro, int line, int position, Station endStation, ArrayList<Station> rota) {

        // Check against edge case tha te line only as one station
        if (position + 1 <= metro.get(line).size()) {

            Station nextStation = metro.get(line).get(position + 1);
            ArrayList<Station> route = deepCopyOfRota(rota);

            //
            if ((!checkIfLineContainsStation(route, nextStation) && !checkIfStationIsLastStation(nextStation, endStation))) {
                route.add(nextStation);
                startAlgoFindRoute(metro, nextStation, endStation, route);
            }

            if (checkIfStationIsLastStation(nextStation, endStation)) {
                route.add(nextStation);
                routes.add(route);
            }
        }
    }

    /**
     * Go one Position Down the Line and Add to route
     *
     * @param metro
     * @param line
     * @param position
     * @param endStation
     * @param rota
     *
     */
    public static void downTheLineOnePosition(ArrayList<ArrayList<Station>> metro, int line, int position, Station endStation, ArrayList<Station> rota) {

        //    if (position - 1 >= 0) {
        Station nextStation = metro.get(line).get(position - 1);
        ArrayList<Station> route = deepCopyOfRota(rota);

        if ((!checkIfLineContainsStation(route, nextStation) && !checkIfStationIsLastStation(nextStation, endStation))) {
            route.add(nextStation);
            startAlgoFindRoute(metro, nextStation, endStation, route);

        }

        //check if the Station name is the same as end Station
        if (checkIfStationIsLastStation(nextStation, endStation)) {
            route.add(nextStation);
            routes.add(route);
        }

        //  }
    }

    /**
     * Find Route with submay map, start Station endStation, And current route
     *
     * @param metro
     * @param startStation
     * @param endStation
     * @param rota
     */
    public static void startAlgoFindRoute(ArrayList<ArrayList<Station>> metro, Station startStation, Station endStation, ArrayList<Station> rota) {

        // check every line of the metro
        for (int line = 0; line < metro.size(); line++) {

            // if the line contains the stating station
            if (checkIfLineContainsStation(metro.get(line), startStation)) {

                //what is is position of the station in the line
                int position = positionOfStationInLine(metro.get(line), startStation);

                // if the stating station position is at the start of line
                if (position == 0) {

                    // System.out.println("Seguir para cima");
                    // Move up the line
                    upTheLineOnePosition(metro, line, position, endStation, rota);

                    // if the stating station position is at the end of line
                } else if (position == metro.get(line).size() - 1) {

                    // System.out.println("Seguir para baixo");
                    //Move down the line
                    downTheLineOnePosition(metro, line, position, endStation, rota);

                } else {

                    // System.out.println("Seguir para cima  +");
                    // Move up the line
                    upTheLineOnePosition(metro, line, position, endStation, rota);

                    // System.out.println("Seguir para baixo -");
                    //Move down the line
                    downTheLineOnePosition(metro, line, position, endStation, rota);
                }
            }
        }
    }

    /**
     * Check if a line or Route contains the station name
     *
     * @param line
     * @param station
     * @return boolean
     */
    public static boolean checkIfLineContainsStation(ArrayList<Station> line, Station station) {

        for (Station st : line) {

            if (st.getName().equals(station.getName())) {

                return true;
            }
        }
        return false;
    }

    /**
     * Gets the position of the station name int a line
     *
     * @param line
     * @param station
     * @return boolean
     */
    public static int positionOfStationInLine(ArrayList<Station> line, Station station) {
        int pos = 0;
        for (Station st : line) {

            if (st.getName().equals(station.getName())) {

                return pos;
            }
            pos++;
        }

        return pos;

    }

    /**
     * Check if next Station is the end station
     *
     * @param nextStation
     * @param endStation
     * @return boolean
     */
    public static boolean checkIfStationIsLastStation(Station nextStation, Station endStation) {

        if (nextStation.getName().equals(endStation.getName())) {

            return true;
        }

        return false;
    }

    /**
     * Create a copy of the route to Find new routes
     *
     * @param rota
     * @return boolean
     */
    public static ArrayList<Station> deepCopyOfRota(ArrayList<Station> rota) {

        ArrayList<Station> newRota = new ArrayList<>();

        for (Station st : rota) {
            Station newSt = new Station();
            newSt = st;
            newRota.add(newSt);

        }

        return newRota;
    }

    /**
     * Create a copy of the route to Find new routes
     */
    public void showRoutesFixtStartStation() throws SQLException {
        if (routes != null) {
            for (int i = 0; i < routes.size(); i++) {
                route.setStations(routes.get(i));

                for (int j = 0; j < routes.get(i).size(); j++) {
                    if (j == 0) {
                        //FIX lack of line
                        Line line = routes.get(i).get(j + 1).getLine();
                        routes.get(i).get(j).setLine(line);
                        Station firstStation = stationDAO.getStation(routes.get(i).get(j).getName(), line);
                        routes.get(i).get(j).setId(firstStation.getId());
                        routes.get(i).get(j).setPrice(firstStation.getPrice());
                    }
                }

                RouteBLL newRoute = new RouteBLL();
                newRoute.route.setStations(route.getStations());
                newRoute.calculatePrice();
                newRoute.calculateChangesOfLine();
                newRoute.calculateTime();
                allRoutes.add(newRoute.route);
            }
        }
    }
}
