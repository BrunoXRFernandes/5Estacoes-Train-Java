/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAOs.MachineChangeDAO;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Alert;
import utils.Parser;

/**
 *
 * @author Rui
 */
public class PaymentBLL {

    static MachineChangeDAO machineChangeDAO = new MachineChangeDAO();
    static ArrayList<Integer> machine = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    static ArrayList<Integer> coins;
    Alert a = new Alert(Alert.AlertType.NONE);
    static double changeDue;
    
    static final double TWOEURO = 2;
    static final double ONEEURO = 1;
    static final double FIFTYCENT = 0.5;
    static final double TWENTYCENT = 0.2;
    static final double TENCENT = 0.1;
    static final double FIVECENT = 0.05;
    static final double TWOCENT = 0.02;
    static final double ONECENT = 0.01;

    public static ArrayList<Integer> getCoins() {
        return coins;
    }

    public static ArrayList<Integer> getMachine() {
        return machine;
    }
    
    public static void setChangeDue(double change){       
        changeDue = change; 
    }

    /**
     * Receives the value and calculates the number of coins to the change
     * @param change
     * @return 
     */
    public static boolean countChange(double change){
        
        //gets the coins in the machine
        machine = machineChangeDAO.getMachineMoney();
        //Round the change to 2 decimal numbers
        change = Parser.round(change, 2);
        changeDue = change;
        
        //Goes from the biggest to the smallest coin to see if the machine has them
        int twoEuros = change(TWOEURO, 0);
        int oneEuro = change(ONEEURO, 1);
        int fiftyCents = change(FIFTYCENT, 2);
        int twentyCents = change(TWENTYCENT, 3);
        int tenCents = change(TENCENT, 4);
        int fiveCents = change(FIVECENT, 5);
        int twoCents = change(TWOCENT, 6);
        int oneCent = change(ONECENT, 7);
        
        //if the change is bigger than 0 means that the machine has not enough coins
        if (changeDue>0){
            return false;
        }
        //fills the array with the coins to be given as change
        coins = new ArrayList<>(Arrays.asList(twoEuros, oneEuro, fiftyCents, twentyCents, tenCents, fiveCents, twoCents, oneCent));
        return true;
    }
    
    /**
     * Receives the actual value and the position of the array to see if their is coins
     * @param money
     * @param pos
     * @return 
     */
    public static int change(double money, int pos){
        int count = 0;
        //while the change is bigger than the actual coin and the machine has coins
        while(changeDue >= money && machine.get(pos) > 0){
            //subtracts the actual coin to the change due (ex. 10-2)
            changeDue -= money;
            changeDue = Parser.round(changeDue, 2);
            //takes one coin from the actual position
            machine.set(pos, (machine.get(pos) - 1));
            //how many coins from the actual position are been given
            count ++;
        }
        return count;
    }
    
    /*
    calculates and retrives the total acording with the array given
    */
    public static double totalChange(ArrayList<Integer> listCoins){
        double total = 0;
        total += listCoins.get(0) * TWOEURO;
        total += listCoins.get(1);
        total += listCoins.get(2) * FIFTYCENT;
        total += listCoins.get(3) * TWENTYCENT;
        total += listCoins.get(4) * TENCENT;
        total += listCoins.get(5) * FIVECENT;
        total += listCoins.get(6) * TWOCENT;
        total += listCoins.get(7) * ONECENT;
        return total;
    }
    
    /**
     * returns the new price with the discount
     * @param price
     * @param discount
     * @return 
     */
    public static double applyDiscount(double price, double discount){
        double value = Parser.round((price - price * discount), 2);
        //This was used to round to the nearest five
        //return Math.round(value * 20) / 20.0;
        return value;
    }

}
