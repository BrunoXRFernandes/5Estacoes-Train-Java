/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Interfaces.CurrentDatabase;
import database.Database;
import database.DatabaseFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Route;
import models.Station;
import models.Trip;

/**
 *
 * @author Rui
 */
public class RouteDAO {
    
    //BD Factory that calls our MSSql Database
    private final Database database = DatabaseFactory.getDatabase(CurrentDatabase.database);
    private final Connection conn = database.conectar();
    StationDAO stationDAO = new StationDAO();
    LineDAO lineDAO = new LineDAO();
    
    /**
     * Gets the Route for the selected trip saved in the history
     * @param trip
     * @return Route
     */
    public Route getRouteTrip(Trip trip) {       
        ArrayList<Station> stations = new ArrayList<>();
        Route route = new Route();
        //gets all the results from the course table from the trip history
        String sql = "select * from view_tripHistory where IDTripHistory = " + trip.getId() + " ORDER BY position";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Station station = stationDAO.getStationFromLineStation(rs.getInt("IDLineStation"));
                station.setLine(lineDAO.getLineFromLineStation(rs.getInt("IDLineStation")));
                stations.add(station);
                //route id is the same as the trip
                route.setId(trip.getId());
                //adds the station to the course
                route.setStations(stations);
                //details saved in the trip history table
                route.setPrice(rs.getDouble("Price"));
                route.setDuration(rs.getInt("TimeCourse"));
                route.setChangesOfLine(rs.getInt("ChangeLine"));
                route.setNumberOfStations(rs.getInt("NrStations"));
                route.setPosition(rs.getInt("Position"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RouteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return route;
    }

    /**
     * insert route in database for client
     * @param route 
     * @param idTrip 
     */
    public void insertRoute(Route route, int idTrip){
        //gets the next id for courses
        int IDCourse = getNextID();
        if(IDCourse > 0){
            String sql = "insert into course(IDCourse, IDLineStation, Position, IDTripHistory)"
                    + "values(?, ?, ?, ?)";
            //we need to run the array of stations in the current course
            for (int i = 0; i < route.getStations().size(); i++) {
                try {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    //the id is the same in all the course
                    stmt.setInt(1, IDCourse);
                    stmt.setInt(2, route.getStations().get(i).getId());
                    //the position is the index in the array
                    stmt.setInt(3, i);
                    stmt.setInt(4, idTrip);
                    stmt.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
    }
    
    /**
     * returns the next id from table courses
     * @return 
     */
    public int getNextID(){
        int id = 0;
        
        //this function return the last id from the table course
        String sql = "select dbo.func_idCourse() as id";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt("id");
            }
            //return the last id + 1
            return id+1;
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }       
    }
    
}
