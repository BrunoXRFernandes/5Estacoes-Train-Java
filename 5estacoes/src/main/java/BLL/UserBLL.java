/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.time.LocalDate;
import models.User;

/**
 *
 * @author nelso
 */
public class UserBLL {
     private static final int MIN_AGE = 3;
     private static final int MAX_AGE = 100;
    
     /**
      * Verify age user if diferent null and if within the range allowed
      * @param date
      * @return boolean
      */
    public static boolean verifyAge(LocalDate date){
        if(date == null || PassBLL.calculateClientAge(date) <= MIN_AGE || PassBLL.calculateClientAge(date) >= MAX_AGE){
            return false;
        }
        return true;
    }
    
    
}
