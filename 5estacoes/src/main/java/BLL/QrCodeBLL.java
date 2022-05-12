/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;
import Interfaces.Subway2Feira;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import javafx.scene.image.Image;
import models.Route;
import models.User;
import org.json.simple.JSONObject;

/**
 *
 * @author nelso
 */
public class QrCodeBLL {
    public static HashMap<String, Object> getQrCodeTrip(Route route, User client) throws Exception {
        
        // credential
        String credentialsString = Subway2Feira.API_USERNAME + ":" + Subway2Feira.API_PASSWORD;
       // credential Base64
        String credentialsBase64 = "Basic " + Base64.getEncoder().encodeToString(credentialsString.getBytes());
 
         //create Client
         HttpClient httpclient = HttpClient.newHttpClient();
 
         // create a request
         HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", credentialsBase64)
                .header("accept","application/json")
                .header("Content-Type", "application/json")
                .uri(URI.create(Subway2Feira.QR_POST_API))
                .POST(requesQrCodeAPI(route, client))
                .timeout(Duration.ofSeconds(4))
                .build();
        
         // Reposta http
        HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        //Process file
        if (response.statusCode() == 200){
            
            HashMap<String, Object> qrcode = new HashMap<>();
            
            //exclude parts of string
            String stringbase = response.body().split(",")[2];
            String base64Image = stringbase.substring(0, stringbase.length() -2);
            
            
            qrcode.put("Base64", base64Image);
            qrcode.put("QrCode", transformToImage(base64Image));
            
            return qrcode;
        }else{
            throw new Exception("Integer already added.");
        }
    }
    
    public static Image transformToImage(String image) throws UnsupportedEncodingException{
        byte[] decodeImg =  Base64.getMimeDecoder().decode(image.getBytes("UTF-8"));
        ByteArrayInputStream arrayImg = new ByteArrayInputStream(decodeImg);
        return new Image(arrayImg);
    }
    
    private static HttpRequest.BodyPublisher requesQrCodeAPI(Route route, User client) {
        JSONObject json = new JSONObject();
        
        json.put("Customer", client.getName());
        json.put("Begin",    route.getStations().get(0).getName());
        json.put("End",      route.getStations().get(route.getStations().size() -1).getName());
        json.put("Price",    route.getPrice());
        json.put("Duration", route.getDuration());
        
        return HttpRequest.BodyPublishers.ofString(json.toString());
    }
}
