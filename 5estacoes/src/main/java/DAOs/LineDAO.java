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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Line;

/**
 *
 * @author Rui
 */
public class LineDAO {
    
    //BD Factory that calls our MSSql Database
    private final Database database = DatabaseFactory.getDatabase(CurrentDatabase.database);
    private final Connection conn = database.conectar();
    
    /**
     * Gets the Line from the DB with given Key
     * @param key
     * @return Line
     */
    public Line getLine(Character key){
        //Line to be inserted into DB
        Line line = new Line();
        String sql = "SELECT * FROM LINE WHERE LineKey='" + key + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                line.setId(rs.getInt("IDLine"));
                line.setName(rs.getString("NameLine"));
                //Convert nvarchar into char
                line.setKey(rs.getString("LineKey").charAt(0));
                line.setIDSubway(rs.getInt("IDSubway"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }
    
    /**
     * Gets the Line from the DB with given Key
     * @param IDLineStation
     * @return Line
     */
    public Line getLineFromLineStation(int IDLineStation){
        Line line = new Line();
        String sql = "SELECT l.* from LINE as l join LINE_STATION as ls on l.IDLine = ls.IDLine "
                + "where ls.IDLineStation =" + IDLineStation;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                line.setId(rs.getInt("IDLine"));
                line.setName(rs.getString("NameLine"));
                line.setKey(rs.getString("LineKey").charAt(0));
                line.setIDSubway(rs.getInt("IDSubway"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }
    
    /**
     * Gets ID of Line with given key
     * @param key
     * @return int
     */
    public int getIDLine(Character key){
        int id=0;
        String sql = "SELECT IDLine as 'id' FROM LINE WHERE LineKey='" + key + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * Returns the number of BD results with the character passed as parameter
     * @param lineKey
     * @return 
     */
    public boolean lineExist(Character lineKey) {
        int result = 0;
        String sql = "SELECT COUNT(lineKey) as 'total' FROM LINE WHERE LineKey='" + lineKey + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //If theres more than 0 results return false meaning that the line exists in the DB
        return result > 0;
    }
    
    /**
     * Inserts Line into DB
     * @param line
     * @return boolean
     */
    public boolean insertLine(Line line){
        String sql = "INSERT INTO LINE(NameLine, LineKey, IDSubway) "
                + "VALUES(?,?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, line.getName());
            //char into nvarchar
            stmt.setString(2, String.valueOf(line.getKey()));
            stmt.setInt(3, line.getIDSubway());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LineDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }       
    }
    
}
