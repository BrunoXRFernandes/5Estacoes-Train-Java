/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.Base64;

/**
 *
 * @author nelso
 */
public interface Subway2Feira {
    // url de API
    static final String QR_POST_API = "https://services.inapa.com/Subway2Feira/api/trip";
    //TRNSPORT PASS
    static final String TRANSPORT_PASS = "https://services.inapa.com/Subway2Feira/api/transportPass/";
     //TRNSPORT TYPE PASS
    static final String TRANSPORT_TYPE = "https://services.inapa.com/subway2feira/api/transportPassType"; 
    // API username
    static final String API_USERNAME = "G3";
    // API password
    static final String API_PASSWORD = "PtM#fh?R8o";

    String credentialsString = Subway2Feira.API_USERNAME + ":" + Subway2Feira.API_PASSWORD;
    
    static final String CREDENTIAL = "Basic " + Base64.getEncoder().encodeToString(credentialsString.getBytes());
}
