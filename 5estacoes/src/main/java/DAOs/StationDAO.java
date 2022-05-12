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
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Line;
import models.Station;

/**
 *
 * @author nelso
 */

public class StationDAO {
    
    //BD Factory that calls our MSSql Database
    private final Database database = DatabaseFactory.getDatabase(CurrentDatabase.database);
    private final Connection conn = database.conectar();
    
    LineDAO lineDAO = new LineDAO();
 
    /**
     * gets the ids for a line station in a line
     * @param key
     * @param station
     * @return 
     */
    public int getIDLineStation(char key, Station station) {
        int idStation = getIDStation(station.getName());
        int idLine = lineDAO.getIDLine(key);
        int id = 0;
        String sql = "SELECT * FROM LINE_STATION WHERE IDStation= " + idStation + " and IDLine= " + idLine;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt("IDLineStation");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    /**
     * Gets ID of line with name passed as Parameter
     * @param name
     * @return int
     */
    public int getIDStation(String name) {
        int id=0;
        String sql = "SELECT IDStation as 'id' FROM STATION WHERE NameStation= ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
       
             ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * get station with name only
     * @param IDStation
     * @return station
     */
    public Station getStation(int IDStation) {
        Station station = new Station();
        String sql = "SELECT * FROM STATION WHERE IDStation= " + IDStation;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                station.setId(IDStation);
                station.setName(rs.getString("NameStation"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return station;
    }
    
    /**
     * get station with name only
     * @param stationName
     * @param line
     * @return station
     */
    public Station getStation(String stationName, Line line) {
        Station station = new Station();
        String sql = "SELECT * from All_Lines_Stations where NameStation = '" + stationName + "' and LineKey = '" +  line.getKey() + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                station.setId(rs.getInt("IDLineStation"));
                station.setName(rs.getString("NameStation"));
                station.setPrice(rs.getDouble("Price"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return station;
    }
    
    /**
     * gets the station matching the lineStation id
     * @param IDLineStation
     * @return station
     */
    public Station getStationFromLineStation(int IDLineStation) {
        Station station = new Station();
        //view in database that returns the name, id and price from a station representing a lineStation
        String sql = "select * from view_getStationFromLineStation where idLineStation = " + IDLineStation;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                station.setId(rs.getInt("iDStation"));
                station.setName(rs.getString("NameStation"));
                station.setPrice(rs.getDouble("Price"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return station;
    }
    
     /**
     * Get stations in database
     *
     * @return Station
     */
    public ArrayList<Station> getStations() {
        ArrayList<Station> stations = new ArrayList<>();
        String sql = "SELECT * FROM Station";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Station station = new Station();
                station.setId(rs.getInt("idStation"));
                station.setName(rs.getString("namestation"));
                
                stations.add(station);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stations;
    }   
    
    /**
     * Inserts Line_Station in DB for the Station passed as parameter
     * @param station
     * @return boolean
     */
    public boolean insertLineStation(Station station){
        int stationID = getIDStation(station.getName());
        String sql = "insertLineStation ?, ?, ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, stationID);
            stmt.setInt(2, station.getLine().getId());
            stmt.setInt(3, station.getPosition());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }       
    }
    
    /**
     * Inserts Station in DB Name + ID
     * @param station
     * @return boolean
     */
    public boolean insertStation(Station station) {
        //procedure in database that inserts station with name and price(set to 0 if empty)
        String sql = "insertStation ?, ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, station.getName());
            stmt.setDouble(2, station.getPrice());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Returns the number of BD results with the String passed as parameter
     * @param name
     * @return 
     */
    public boolean stationExist(String name) {
        int result = 0;
        String sql = "SELECT COUNT(NameStation) as 'total' FROM STATION WHERE NameStation='" + name + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result > 0;
    }
    
    /**
    * Returns the number of BD results with the String passed as parameter
     * @param station
    * @return 
    */
    public boolean stationLineExist(Station station) {
        int result = 0;
        int lineID = station.getLine().getId();
        int stationID = getIDStation(station.getName());
        String sql = "SELECT COUNT(IDLine) as 'total' FROM LINE_STATION WHERE IDStation=" + stationID + " and IDLine="+ lineID;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result > 0;
    }      
    
    /**
    * Returns an Arraylist of Arraylist of station that represent a subway
     * @return listStationByLines
    */
    public ArrayList<ArrayList<Station>> getLinesAndStation() {
        ArrayList<ArrayList<Station>> listStationByLines = new ArrayList<>();
        String nameOfCurrentLine = "";
        ArrayList<Station> listOfStationOfLine = new ArrayList<>();

        String sql = "SELECT * from All_Lines_Stations order by IDLine, position, idstation desc";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Station station = new Station();
                Line line = new Line();
                station.setId(rs.getInt("IDLineStation"));
                station.setName(rs.getString("NameStation"));
                station.setPosition(rs.getInt("Position"));
                station.setPrice(rs.getDouble("Price"));
                line.setName(rs.getString("NameLine"));
                line.setKey(rs.getString("LineKey").charAt(0));
                line.setId(rs.getInt("IdLine"));
                station.setLine(line);

                // if diferent that currentLineName and Empty (first station)
                if (!station.getLine().getName().equals(nameOfCurrentLine) && nameOfCurrentLine.equals("")) {
                    nameOfCurrentLine = station.getLine().getName();

                    //add station to line
                    listOfStationOfLine.add(station);

                    // if the line name is diferent that the current Line    
                } else if (!station.getLine().getName().equals(nameOfCurrentLine)) {
                    nameOfCurrentLine = station.getLine().getName();

                    // Add previous Line to list of Line
                    listStationByLines.add(listOfStationOfLine);
                    // new line array
                    listOfStationOfLine = new ArrayList<>();
                    //add station tonew  line
                    listOfStationOfLine.add(station);

                } else {
                    //add station to line
                    listOfStationOfLine.add(station);

                }

            }

            // if not empty, add last line
            if (!rs.next()) {
                listStationByLines.add(listOfStationOfLine);
            }

        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listStationByLines;
    }
}
