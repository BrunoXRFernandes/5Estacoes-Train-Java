/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAOs.PassTypeDAO;
import java.time.LocalDate;
import java.time.Month;
import models.PassType;
import models.Client;

/**
 *
 * @author bruno
 */
public class PassTypeBLL {

    public PassType passType = new PassType();
    
    /**
     * calculates the type of pass for the user
     * @param age
     * @return
     * @throws Exception 
     */
    public static PassType calculateTypePassByAge(int age) throws Exception {
        for (PassType passTypeDao : PassTypeDAO.getTransportTypes()) {
            if (age >= passTypeDao.getMinAge() && age <= passTypeDao.getMaxAge()) {
                //checks if the age is within the minimum and maximum
                return passTypeDao;
            }
        }
        return null;
    }
    
    
    public static LocalDate dateExpirationPass(Client client) throws Exception{
        int clientAge = PassBLL.calculateClientAge(client.getBirthDate());

        PassType date = calculateTypePassByAge(clientAge);

        return client.getBirthDate().plusYears(date.getMaxAge()+1);
    }
}
