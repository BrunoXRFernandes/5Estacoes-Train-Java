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
 * MSSql Database connects and disconnects to MSSql Database
 * @author Rui
 */
public class DatabaseSQLServer implements Database {

    public Connection conectar() {
          String connectionUrl =
                "jdbc:sqlserver://CTESPBD.DEI.ISEP.IPP.PT:1433;"
                        + "database=LP3_G3_2122;"
                        + "user=LP3_G3_2122;"
                        + "password=5estacoes;"
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
