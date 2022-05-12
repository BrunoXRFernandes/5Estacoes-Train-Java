/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAOs.PassDAO;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import models.Client;

/**
 *
 * @author bruno
 */
public class PassBLL {
    static LocalDate currentDate = LocalDate.now();
    
    /**
     * calculate the age and receive the age as a parameter in the format 
     * "YYYY-MM-DD"
     * @param birthDate
     * @return 
     */
    public static int calculateClientAge(LocalDate birthDate) {
        if ((birthDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
    
    /**
     * checks if date is after today
     * @param expiration
     * @return 
     */
    public static boolean checkIfPassValid(LocalDate expiration){
       if(expiration.isAfter(currentDate)){
           return true;
       }
       return false;
    }
    /**
     * Check if date is the same as actual date
     * @param expiration
     * @return 
     */
    public static boolean checkLastYear(LocalDate expiration){
        int expirationYear = expiration.getYear();
        int currentYear = currentDate.getYear();
        
       if(expirationYear == currentYear){
           return true;
       }
       return false;
    }
    /**
     * Checks and updates all expired Passes of a list of clients
     * @param clients 
     */
    public static void validateALL(ArrayList<Client> clients){
        for (int i = 0; i < clients.size(); i++) {
            //only checks if client has pass
            if(clients.get(i).getPass().getTransportID() != null){
                //if pass expired updates it
                if(!(PassBLL.checkIfPassValid(clients.get(i).getPass().getExpirationDate()))){
                    PassDAO.updateTransportPass(clients.get(i), true);                          
                }
            }
        }
       
    }
}
