/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *dB FOR TESTING, PLEASE CHANGE CONNECTION URL AS YOU WISH BUT ERASE IT AFTER USE SO YOU DATA IS SECURE
 * @author Rui
 */
public class DatabaseTest implements Database{
    
        public Connection conectar() {
          String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "database=estacoes;"
                        + "user=dev;"
                        + "password=dev;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";
          
          Connection conn = null;
        
        try {
            
           conn = DriverManager.getConnection(connectionUrl);
           
        } 
        catch (SQLException ex)           {
            System.err.println(ex.getMessage());
        }      
            
        return conn; 
    }

    @Override
    public void desconectar(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            
        }
    }     
}
