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
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import models.Client;
import models.PassType;
import models.TransportPass;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import BLL.PassBLL;
import java.util.UUID;

/**
 *
 * @author nelso
 */
public class PassDAO {

    // Alert 
    private static final Alert a = new Alert(Alert.AlertType.NONE);

    public static ArrayList<TransportPass> getTransportPass() throws Exception {
        String url = Subway2Feira.TRANSPORT_PASS;
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
            ArrayList<TransportPass> transportPasses = responseNewPass(response.body());
            
            return getPassTypeByAge(transportPasses);
        } else {
            throw new Exception("Integer already added.");
        }
    }

    public static TransportPass getTransportPassById(String id) throws Exception {
        String url = Subway2Feira.TRANSPORT_PASS + id;
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
            
            ArrayList<TransportPass> transportPasses = responseNewPass(response.body());

            return getPassTypeByAge(transportPasses).get(0);
        } else {
            throw new Exception("Integer already added.");
        }
    }

    public static ArrayList<TransportPass> getPassTypeByAge(ArrayList<TransportPass> transportPasses) throws Exception {
        
        for (PassType passTypeDao : PassTypeDAO.getTransportTypes()) {
            for (TransportPass transportPass : transportPasses) {
                if (passTypeDao.getIdType().equals(transportPass.getPassType().getIdType())) {
                    transportPass.setPassType(passTypeDao);
                }
            }
        }
        
        return transportPasses;
    }

    public static TransportPass deleteTransportPass(UUID id, int user) throws Exception {
        String url = Subway2Feira.TRANSPORT_PASS + id;
        HttpClient httpclient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", Subway2Feira.CREDENTIAL)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .DELETE()
                .timeout(Duration.ofSeconds(4))
                .build();

        // Reposta http
        HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        //Process file
        if (response.statusCode() == 202) {
            
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.deletePassUser(user);
            return new TransportPass();
        } else {
            throw new Exception("Não foi possivel eliminar");
        }
    }

    public static boolean updateTransportPass(Client user, boolean active){

        try {
            String url = Subway2Feira.TRANSPORT_PASS + user.getPass().getTransportID();
            //create Client
            HttpClient httpclient = HttpClient.newHttpClient();
            String requestBody = "{\"ClientId\": \"" + user.getUserName() + "\",\n"
                    + "\"PassTypeId\": \""+ PassTypeBLL.calculateTypePassByAge(PassBLL.calculateClientAge(user.getBirthDate())).getIdType() + "\",\n"
                    + "\"ExpirationDate\": \"" +  PassTypeBLL.dateExpirationPass(user).toString() + "\",\n"
                    + "\"Active\": \"" +  String.valueOf(active) + "\""
                    + "}";

            // create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", Subway2Feira.CREDENTIAL)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .uri(URI.create(url))
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(4))
                    .build();

            // Reposta http
            HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
            //Process file
            if (response.statusCode() == 202) {
                return true;
            } else {
                throw new Exception("Error updating.");
            }
        } catch (Exception ex) {

        }
        return false;
    }

    /**
     * Connects to API Transport Pass Endpoint , POST user to generate ticket
     *
     *
     * @param user
     * @return
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws org.json.simple.parser.ParseException
     */
    //TODO pass user
    public static TransportPass createPass(Client user) throws Exception {

        String url = Subway2Feira.TRANSPORT_PASS;

        //create Client
        HttpClient client = HttpClient.newHttpClient();

        // create a request
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", Subway2Feira.CREDENTIAL)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .POST(requestIdAPI(user))
                .timeout(Duration.ofSeconds(4))
                .build();

        // Reposta http
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Pass id is created
        if (response.statusCode() == 201) {

            ArrayList<TransportPass> newPass = responseNewPass(response.body());
            //Add TransportID to database
            UserDAO userDB = new UserDAO();
            userDB.addPassToUser(newPass.get(0), user);
            
            return newPass.get(0);

        } else {

            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Erro da comunicar com a API: " + response.statusCode());
            a.show();
            throw new Exception("Error connect ");
        }

    }

    /**
     * Create the body json for the post to create *
     */
    //TODO pass user
    private static HttpRequest.BodyPublisher requestIdAPI(Client user) throws Exception {
        JSONObject json = new JSONObject();

        // json.put("ClientId", user.getId());
        json.put("ClientId", user.getUserName());
        json.put("PassTypeId", PassTypeBLL.calculateTypePassByAge(PassBLL.calculateClientAge(user.getBirthDate())).getIdType());
        json.put("ExpirationDate", PassTypeBLL.dateExpirationPass(user).toString());
        json.put("Active", "true");

        return HttpRequest.BodyPublishers.ofString(json.toString());
    }

    /**
     * Reads Json from POST to create PassId
     *
     * @param jsonString
     * @return
     * @throws org.json.simple.parser.ParseException
     * @throws java.sql.SQLException
     *
     */
    public static ArrayList<TransportPass> responseNewPass(String jsonString) throws ParseException, Exception {
        UserDAO userDAO = new UserDAO();
        ArrayList<TransportPass> arrayTransportPass = new ArrayList<>();

        try {
            //New Parser JSON
            JSONParser parser = new JSONParser();

            //Parse JSON to object
            Object obj = parser.parse(jsonString);

            //Cast to JSON Object
            JSONObject jsonObject = (JSONObject) obj;

            //Get the Lines from JSON Array
            JSONArray transportPassJson = new JSONArray();
            if (jsonObject.get("TransportPasses") != null) {
                transportPassJson = (JSONArray) jsonObject.get("TransportPasses");
            } 
            
            if(jsonObject.get("TransportPass") != null){
                transportPassJson = (JSONArray) jsonObject.get("TransportPass");
            }

            for (Object pass : transportPassJson) {
                TransportPass newPass = new TransportPass();
                PassType newPassType = new PassType();
                
                JSONObject jsonPass = (JSONObject) pass;

                //Set attributes to new Pass
                newPass.setTransportID(UUID.fromString((String) jsonPass.get("Id")));
                newPass.setGroupId((String) jsonPass.get("GroupId"));

                //PassType in Pass           
                newPassType.setIdType((String) jsonPass.get("PassTypeId"));
                newPass.setPassType(newPassType);
                //Experition date
                String expDate = ((String) jsonPass.get("ExpirationDate"));
                LocalDate expDateLD = LocalDate.parse(expDate.subSequence(0, 10));
                newPass.setExpirationDate(expDateLD);

                newPass.setStatus((boolean) jsonPass.get("Active"));
                
                newPass.setUser(userDAO.getUser((String) jsonPass.get("ClientId")));

                //Initial date
                String initDate = ((String) jsonPass.get("InsertDate"));
                LocalDate initDateLD = LocalDate.parse(initDate.subSequence(0, 10));
                newPass.setInsertDate(initDateLD);

                //add to array
                arrayTransportPass.add(newPass);
            }
            
            return arrayTransportPass;

        } catch (ParseException e) {

            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Erro Parsing JSON");
            a.show();
            throw new ParseException(1);

        } catch (Exception e) {

            System.out.println(e);
            return null;
        }
    }
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    public static ArrayList<Client> getAllPasses() throws Exception{
        ClientDAO clientDAO = new ClientDAO();
        ArrayList<Client> clientPasses = new ArrayList<>();
        ArrayList<Client> clients = clientDAO.getAllClients();
        ArrayList<TransportPass> passes = getTransportPass();

        for (int i = 0; i < passes.size(); i++) {
            for (int j = 0; j < clients.size(); j++) {
                if(passes.get(i).getUser().equals(clients.get(j))){
                    clientPasses.add(clients.get(j));
                }
            }
        }
        return clientPasses;
    }
}
