/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Interfaces.CurrentDatabase;
import database.Database;
import database.DatabaseFactory;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Client;
import models.Route;
import models.Station;
import models.Trip;
import utils.TimeParse;

/**
 *
 * @author Rui
 */
public class TripDAO {

    //BD Factory that calls our MSSql Database
    private final Database database = DatabaseFactory.getDatabase(CurrentDatabase.database);
    private final Connection conn = database.conectar();
    StationDAO stationDAO = new StationDAO();
    RouteDAO routeDAO = new RouteDAO();

    /**
     * Gets the last id from a trip made by the client send as parameter
     *
     * @param client
     * @return
     */
    public int getLastId(Client client) {
        int id = 0;
        String sql = "select max(idTriphistory) as 'IDTrip' from triphistory where idclient = " + client.getId();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt("IDTrip");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TripDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * gets departure and arrival stations from all the trips from a client
     *
     * @param IDPerson
     * @return ArrayList
     */
    public ArrayList<Trip> getTripsClient(int IDPerson) {
        ArrayList<Trip> trips = new ArrayList<>();
        //we get all the id of all the trips of this client
        ArrayList<Integer> idTrips = getTripClientID(IDPerson);

        for (int i = 0; i < idTrips.size(); i++) {
            //procedure in database that gets the departure and arrival of a trip for a client
            String sql = "proc_getTripsClient " + IDPerson + ", " + idTrips.get(i);
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    Trip trip = new Trip();
                    trip.setId(rs.getInt("idtriphistory"));
                    //gets the station that represents the LineStation in the DB
                    trip.setDeparture(stationDAO.getStationFromLineStation(rs.getInt("Departure")));
                    trip.setArrival(stationDAO.getStationFromLineStation(rs.getInt("Arrival")));
                    trip.setTripDate(rs.getDate("DateTrip"));
                    trip.setTicket(rs.getString("ticket"));
                    trips.add(trip);
                }
            } catch (SQLException ex) {
                Logger.getLogger(TripDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return trips;
    }

    /**
     * Gets all the ids of all the trips for this client
     *
     * @param IDPerson
     * @return
     */
    public ArrayList<Integer> getTripClientID(int IDPerson) {
        ArrayList<Integer> idTrips = new ArrayList<>();
        //we only need one id for trip so we call the first positions
        String sql = "select * from view_TripCourse where idclient = " + IDPerson + " and position = 0";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                idTrips.add(rs.getInt("IDTripHistory"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TripDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idTrips;
    }

    /**
     * inserts a trip into DB if trip exists updates duration
     *
     * @param trip
     * @return
     * @throws SQLException
     */
    public boolean insertTrip(Trip trip) throws SQLException {
        //calls procedure in database departure, arrival and price(set to 0 if empty
        String sql = "insertTrip ?, ?, ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //gets station from lineStation table getting the id of station and the key of the line
            stmt.setInt(1, stationDAO.getIDStation(trip.getDeparture().getName()));
            stmt.setInt(2, stationDAO.getIDStation(trip.getArrival().getName()));
            //duration is set in seconds
            stmt.setInt(3, trip.getDuration());
            stmt.execute();
            return true;
        } catch (SQLException ex) {

            Logger.getLogger(Trip.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * inserts a trip into DB if trip exists updates duration
     *
     * @param route
     * @param client
     * @return
     * @throws SQLException
     */
    public boolean insertTripClient(Route route, Client client, String base64) throws SQLException {
        //calls procedure in database departure, arrival and price(set to 0 if empty
        String sql = "INSERT INTO TRIPHISTORY(TimeCourse, Price, ChangeLine, NrStations, IDClient,DateTrip, ticket) "
                + "values(?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, TimeParse.timeToInt(route.getDuration()));
            stmt.setDouble(2, route.getPrice());
            stmt.setInt(3, route.getChangesOfLine());
            stmt.setInt(4, route.getNumberOfStations());
            stmt.setInt(5, client.getId());
            stmt.setString(6, base64);

            stmt.execute();
            int idTrip = getLastId(client);
            routeDAO.insertRoute(route, idTrip);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Trip.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Get time in trip
     *
     * @param stationA
     * @param stationB
     * @return
     */
    public int getDurationTrip(Station stationA, Station stationB) throws SQLException {
        try ( CallableStatement function = conn.prepareCall(
                "{? = call func_getTripDuration(?,?)}")) {
            function.registerOutParameter(1, Types.INTEGER);
            function.setInt(2, stationA.getId());
            function.setInt(3, stationB.getId());
            function.execute();
            return function.getInt(1);
        }
    }

}
