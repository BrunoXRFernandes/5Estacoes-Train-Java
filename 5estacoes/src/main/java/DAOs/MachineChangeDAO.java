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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rui
 */
public class MachineChangeDAO {
    
    //BD Factory that calls our MSSql Database
    private final Database database = DatabaseFactory.getDatabase(CurrentDatabase.database);
    private final Connection conn = database.conectar();
    
    static ArrayList<Integer> machine = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0));
    
    public ArrayList<Integer> getMachineMoney() {
        String sql = "SELECT * FROM MACHINECHANGE";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                machine.set(0, (rs.getInt("twoEuro")));
                machine.set(1, (rs.getInt("oneEuro")));
                machine.set(2, (rs.getInt("fiftyCent")));
                machine.set(3, (rs.getInt("twentyCent")));
                machine.set(4, (rs.getInt("tenCent")));
                machine.set(5, (rs.getInt("fiveCent")));
                machine.set(6, (rs.getInt("twoCent")));
                machine.set(7, (rs.getInt("OneCent")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MachineChangeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return machine;
    }
    
    public boolean chargeMachine(ArrayList<Integer> coins) {
        String sql = "UPDATE machinechange\n" +
                    "SET twoEuro = ?,\n" +
                    "	oneEuro = ?,\n" +
                    "	fiftyCent = ?,\n" +
                    "	twentycent = ?,\n" +
                    "	tenCent = ?,\n" +
                    "	fiveCent = ?,\n" +
                    "	twoCent = ?,\n" +
                    "	oneCent = ?";
       try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, coins.get(0));
            stmt.setInt(2, coins.get(1));
            stmt.setInt(3, coins.get(2));
            stmt.setInt(4, coins.get(3));
            stmt.setInt(5, coins.get(4));
            stmt.setInt(6, coins.get(5));
            stmt.setInt(7, coins.get(6));
            stmt.setInt(8, coins.get(7));
            stmt.execute();
            return true;
        } catch (SQLException ex) {

            Logger.getLogger(MachineChangeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
