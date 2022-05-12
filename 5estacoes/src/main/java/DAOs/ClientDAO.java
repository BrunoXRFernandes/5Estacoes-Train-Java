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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Client;
import models.TransportPass;
import models.User;

/**
 *
 * @author Rui
 */
public class ClientDAO {

    //BD Factory that calls our MSSql Database
    private final Database database = DatabaseFactory.getDatabase(CurrentDatabase.database);
    private final Connection conn = database.conectar();

    /**
     * Insert client in database
     *
     * @param user
     * @return boolean
     * @throws SQLException
     */
    public boolean insertClientDB(User user) throws SQLException {
        String sql = "INSERT INTO PERSON(Name, UserName, BirthDate, Permission, "
                + "Active, HashPassword, Salt) VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUserName());
            stmt.setDate(3, Date.valueOf(user.getBirthDate()));
            stmt.setInt(4, User.CLIENT_PERMISSION);
            stmt.setBoolean(5, User.USER_ACTIVE);
            stmt.setString(6, user.getHash());
            stmt.setString(7, user.getSalt());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Get all active clients from BD to ArrayList
     *
     * @return
     */
    public ArrayList<Client> getAllClients() throws Exception {
        //TODO: CHANGE METHOD getTransportPass to PASSDAO
        String sql = "SELECT * FROM PERSON where PERMISSION = 2";
        ArrayList<Client> clients = new ArrayList<>();
          
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Client client = new Client();
                client.setId(result.getInt("IDPerson"));
                client.setName(result.getString("Name"));
                client.setUserName(result.getString("UserName"));
                client.setBirthDate(result.getDate("BirthDate").toLocalDate());
                client.setStatus(result.getBoolean("Active"));
                client.setPermission(result.getInt("Permission"));
                client.setPass(getTransportPass(client.getUserName()));

                clients.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clients;
    }

    /**
     * Get all active clients from BD to ArrayList
     *
     * @return
     */
    public ArrayList<Client> getClients() throws Exception {
        //TODO: CHANGE METHOD getTransportPass to PASSDAO
        String sql = "SELECT * FROM PERSON where PERMISSION = 2 ";//AND ACTIVE = 1
        ArrayList<Client> clients = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Client client = new Client();
                client.setId(result.getInt("IDPerson"));
                client.setName(result.getString("Name"));
                client.setUserName(result.getString("UserName"));
                client.setBirthDate(result.getDate("BirthDate").toLocalDate());
                client.setStatus(result.getBoolean("Active"));
                client.setPermission(result.getInt("Permission"));
                client.setPass(getTransportPass(client.getUserName()));

                clients.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clients;
    }
    
        public ArrayList<Client> getClientsWithoutPass() throws Exception {
        //TODO: CHANGE METHOD getTransportPass to PASSDAO
        String sql = "SELECT * FROM PERSON where PERMISSION = 2 AND ACTIVE = 1 AND PASS IS NULL";
        ArrayList<Client> clients = new ArrayList<>();
          
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Client client = new Client();
                client.setId(result.getInt("IDPerson"));
                client.setName(result.getString("Name"));
                client.setUserName(result.getString("UserName"));
                client.setBirthDate(result.getDate("BirthDate").toLocalDate());
                client.setStatus(result.getBoolean("Active"));
                client.setPermission(result.getInt("Permission"));

                clients.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clients;
    }

    public TransportPass getTransportPass(String username) throws Exception {
        TransportPass transportPass = new TransportPass();
        String sql = "select pass from PERSON where UserName = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("Pass") != null) {
                    transportPass = PassDAO.getTransportPassById(rs.getString("Pass"));
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransportPass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transportPass;
    }

    /**
     *
     *
     * @param pass
     *
     * @return boolean
     */
    public boolean deletePassUser(int client) {
        String sql = "UPDATE PERSON SET PASS = NULL WHERE IDPerson = ? ";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, client);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean statusPerson(String userName, boolean status) {
        String sql = "UPDATE PERSON SET ACTIVE = ? WHERE UserNAme like ? ";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, status);
            stmt.setString(2, userName);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(StationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
