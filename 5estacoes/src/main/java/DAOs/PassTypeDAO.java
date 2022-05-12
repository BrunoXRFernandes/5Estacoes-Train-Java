/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import BLL.PassTypeBLL;
import Interfaces.Subway2Feira;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import models.PassType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author pcoelho
 */
public class PassTypeDAO {

    // Alert 
    private static final Alert a = new Alert(Alert.AlertType.NONE);
    
    /**
     * Reads Json from GET API TransportPassType to array of Pass types
     *
     * @return
     * @throws org.json.simple.parser.ParseException
     * @throws Exception
     *
     */
    public static ArrayList<PassType> getTransportTypes() throws Exception {

        // create array of pass types
        ArrayList<PassType> arrayTransportType = new ArrayList<>();

        String url = Subway2Feira.TRANSPORT_TYPE;
        //create Client
        HttpClient httpclient = HttpClient.newHttpClient();

        // create a request
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", Subway2Feira.CREDENTIAL)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .GET()
                .timeout(Duration.ofSeconds(4))
                .build();

        // Reposta http
        HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        //Process file
        if (response.statusCode() == 200) {
            //Process response

            arrayTransportType = responseGetPassTypes(response.body());
            
          //  System.out.println(arrayTransportType);

            return arrayTransportType;
        } else {
            throw new Exception("Error connecting to API. " + response.statusCode());
        }
    }

    /**
     * Reads Json from GET to arraay of Pass types
     *
     * @param jsonString
     * @return
     * @throws org.json.simple.parser.ParseException
     * @throws java.sql.SQLException
     *
     */
    public static ArrayList<PassType> responseGetPassTypes(String jsonString) throws ParseException, Exception {

        // create array of pass types
        ArrayList<PassType> arrayTransportType = new ArrayList<>();

        try {

            //New Parser JSON
            JSONParser parser = new JSONParser();

            //Parse JSON to object
            Object obj = parser.parse(jsonString);

            //Cast to JSON Object
            JSONObject jsonObject = (JSONObject) obj;

            //Get the Lines from JSON Array
            JSONArray transportPassJson = (JSONArray) jsonObject.get("TransportPassTypes");

            for (Object eachPassType : transportPassJson) {

                //initialize passType
                PassType newPassType = new PassType();

                JSONObject jsonPassType = (JSONObject) eachPassType;

                newPassType.setIdType((String) jsonPassType.get("PassTypeId"));

               
                  newPassType.setDiscount(((Double) jsonPassType.get("Discount")));
                  
                  
                 newPassType.setDescription((String) jsonPassType.get("Description"));
                 long minAge = (Long) jsonPassType.get("MinAge");
                 newPassType.setMinAge((int) minAge);
                 long maxAge = (Long) jsonPassType.get("MaxAge");
                 newPassType.setMaxAge((int) maxAge);
                 

                //add to array
                arrayTransportType.add(newPassType);
                
                
                //PassTypeBLL.calculateTypePassByAge(newPassType.getMaxAge(), newPassType.getMinAge());

            }

            return arrayTransportType;

        } catch (ParseException e) {

            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Erro Parsing JSON");
            a.show();
            throw new ParseException(1);

        } catch (Exception e) {

            System.out.println("erro no parse");
            System.out.println(e);
            return null;
        }

    }
    
}
