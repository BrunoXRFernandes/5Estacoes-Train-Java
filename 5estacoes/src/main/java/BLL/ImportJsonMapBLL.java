/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAOs.LineDAO;
import DAOs.StationDAO;
import DAOs.TripDAO;
import com.mycompany.lp3_5estacoes.AdminViewController;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import javafx.scene.control.Alert;
import models.Line;
import models.Station;
import models.StationLine;
import models.Trip;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.TimeParse;

/**
 *
 * @author pcoelho
 */
public class ImportJsonMapBLL {

    static StationDAO stationDAO = new StationDAO();
    static LineDAO lineDAO = new LineDAO();
    static TripDAO tripDAO = new TripDAO();

    // ArrayList of line name and Key
    public static ArrayList<Line> lines = new ArrayList<>();
    // ArrayList of StationLine (station name  and lines )
    public static ArrayList<StationLine> stationLine = new ArrayList<>();
    //ArrayList of station and position and line
    public static ArrayList<Station> stationInOrderInLine = new ArrayList<>();
    //ArrayList of trips wit stations and duration
    public static ArrayList<Trip> trips = new ArrayList<>();

    // Alert 
    private static final Alert a = new Alert(Alert.AlertType.NONE);

    // url de API
    private static final String MAP_GET_API = "https://services.inapa.com/Subway2Feira/api/map";
    // API username
    private static final String API_USERNAME = "G3";
    // API password
    private static final String API_PASSWORD = "PtM#fh?R8o";
    
    
    /**
     * Connects to API Map Enpoint end gets map in JSON
     *
     *
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws org.json.simple.parser.ParseException
     */
    public static void ImportJsonMap() throws URISyntaxException, IOException, InterruptedException, ParseException, Exception {

        // clear all Arrays
        lines.clear();
        stationLine.clear();
        stationInOrderInLine.clear();

        // credential
        String credentialsString = API_USERNAME + ":" + API_PASSWORD;
        // credential Base64
        String credentialsBase64 = "Basic " + Base64.getEncoder().encodeToString(credentialsString.getBytes());

        //create Client
        HttpClient client = HttpClient.newHttpClient();

        // create a request
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", credentialsBase64)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .uri(URI.create(MAP_GET_API))
                .GET()
                .timeout(Duration.ofSeconds(4))
                .build();

        // Reposta http
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        //Process file
        if (response.statusCode() == 200) {

            readJsonMap(response.body());

        } else {

            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Erro da comunicar com a API: " + response.statusCode());
            a.show();
            throw new Exception("Error connect ");

        }

    }
    
    /**
     * Reads Json MAP and process lines, stations and trips
     *
     * @param jsonString
     * @throws org.json.simple.parser.ParseException
     * @throws java.sql.SQLException
    **/
     public static void readJsonMap(String jsonString) throws ParseException, SQLException, Exception {

        try {

            //New Parser JSON
            JSONParser parser = new JSONParser();

            //Parse JSON to object
            Object obj = parser.parse(jsonString);

            //Cast to JSON Object
            JSONObject jsonObject = (JSONObject) obj;

            //Get the Lines from JSON Array
            JSONArray linesFromJSON = (JSONArray) jsonObject.get("Lines");

            //Process Lines
            readJsonLines(linesFromJSON);

            //DEBUG
            for (Line linha : lines) {
                System.out.println(linha.toString());
            }

            //Get the Lines Stations JSON Array
            JSONArray stations = (JSONArray) jsonObject.get("Stations");

            //Process Stations
            readJsonStations(stations);

            //DEBUG
            for (StationLine stl : stationLine) {
                System.out.println(stl.toString());
            }

            //Get the Trips from Trips Json Array
            JSONArray tripsFomJSON = (JSONArray) jsonObject.get("Trips");

            //Process Trips
            readJsonTrips(tripsFomJSON);

            //DEBUG
            for (Station stOrderInLine : stationInOrderInLine) {
                System.out.println(stOrderInLine.getName() + ";" + stOrderInLine.getPosition() + ";" + stOrderInLine.getLine().getKey());
            }

        } catch (SQLException | ParseException e) {

            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Erro Parsing JSON");
            a.show();
            throw new Exception("Erro Parsing JSON");

        }

    }
     
     /**
     * Reads Json Lines and save to DB
     *
     * @param linesFromJSON
     * @throws org.json.simple.parser.ParseException
    **/
    public static void readJsonLines(JSONArray linesFromJSON) throws ParseException, Exception {

        try {

            for (Object line : linesFromJSON) {

                JSONObject jsonLine = (JSONObject) line;
                //Line Key
                String lineKey = (String) jsonLine.get("Line");
                //Line Name
                String lineName = (String) jsonLine.get("Name");

                //Line key in char
                char key = lineKey.charAt(0);

                //Check for duplicate line name or key
                if (!checkIfLinesContainsNameorKey(lineName, key)) {

                    lines.add(new Line(lineName, key, 1));

                } else {

                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("JSON Line consistency do not exist.");
                    a.show();

                    throw new ParseException(1);

                }

            }

        } catch (Exception e) {

            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("JSON Line consistency do not exist.");
            a.show();
            throw new Exception("JSON Line consistency do not exist");


        }

        //Add to Database
        lineToDB(lines);

    }

     /**
     * Reads Json Stations and save stations to DB
     *
     * @param stations
     * @throws org.json.simple.parser.ParseException
    **/
    public static void readJsonStations(JSONArray stations) throws ParseException, Exception {

        try {

            for (Object station : stations) {

                // new Arrylist of caracter to add to Station name
                ArrayList<Line> listLinesOfStation = new ArrayList();

                JSONObject jsonStation = (JSONObject) station;
                //name of station
                String stationName = (String) jsonStation.get("Name");
                //Price of station
                String stationPrice = (String) jsonStation.get("Price");
                // key og line station
                JSONArray stationLines = (JSONArray) jsonStation.get("Lines");

                for (Object stationLinekey : stationLines) {

                    char key = ((String) stationLinekey).charAt(0);

                    // check if line key exist in Line and check if the listLinesofStation doesnt duplicate line)
                    if (checkIfLinesContainsKey(key) && !listLinesOfStation.contains(getLineOfLinesByKey(key))) {

                        listLinesOfStation.add(getLineOfLinesByKey(key));

                    } else {

                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setContentText("JSON Station consistency Station do not exist.");
                        a.show();
                        throw new ParseException(1);

                    }

                }

                //add Station to array to be inserted into DB
                stationLine.add(new StationLine(stationName, Double.parseDouble(stationPrice), listLinesOfStation));

            }

        } catch (Exception e) {

            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("JSON Station consistency do not exist.");
            a.show();
            throw new Exception("JSON Station consistency do not exist.");
            

        }


        //Inserts the Stations in the DB 
        stationToDB(stationLine);

    }
    
    
     /**
     * Reads Json Trips and save Trips to DB
     *
     * @param tripsFomJSON
     * @throws org.json.simple.parser.ParseException
     * @throws java.sql.SQLException
    **/
    public static void readJsonTrips(JSONArray tripsFomJSON) throws ParseException, SQLException, IOException {

        try {
             //position in the line
            int position = 0;
            for (Object tripJSON : tripsFomJSON) {
                JSONObject jsonTrip = (JSONObject) tripJSON;
                String tripDeparture = (String) jsonTrip.get("Departure");
                String tripArrival = (String) jsonTrip.get("Arrival");
                String tripLine = (String) jsonTrip.get("Line");
                String tripDuration = (String) jsonTrip.get("Duration");

                Station station = new Station();
                Station arrivalStation = new Station();

                // get the line
                Line currentLine = getLineOfLinesByKey(tripLine.charAt(0));

                //Line to add in the Station
                Line newLine = new Line();

                //Trip to add in the trips
                Trip trip = new Trip();

                // check if departure and arrival station and line exist in station line, check also if is not duplicate 
                if (stationInLineExist(tripDeparture, currentLine) && stationInLineExist(tripArrival, currentLine)
                        && !CheckStationAndLineExistInStationInOrderInLine(tripArrival, currentLine) && !CheckStationAndLineExistInStationInOrderInLine(tripArrival, currentLine)) {

             
                    newLine = lineDAO.getLine(currentLine.getKey());
                    station.setName(tripDeparture);
                    station.setPosition(position);
                    station.setLine(newLine);

                    stationInOrderInLine.add(station);

                    //set the station departure in this trip
                    trip.setDeparture(station);

                    //set the station arrival in this trip
                    arrivalStation.setName(tripArrival);
                    arrivalStation.setPosition(position);
                    arrivalStation.setLine(newLine);

                    trip.setArrival(arrivalStation);

                    //calculate the duration of trip in seconds for DB
                    int duration = TimeParse.timeToInt(tripDuration);
                    trip.setDuration(duration);

                    //sets the key of line of this trip
                    trip.setKeyLine(tripLine.charAt(0));

                    trips.add(trip);

                    // In case that is the last station of the line add the arrival
                    if (position == numberOfStationsInLine(currentLine) - 2) {
                        Station lastStation = new Station();
                        newLine = lineDAO.getLine(currentLine.getKey());

                        position++;

                        lastStation.setName(tripArrival);
                        lastStation.setLine(newLine);
                        lastStation.setPosition(position);

                        stationInOrderInLine.add(lastStation);

                        position = -1;
                    }

                    // increment position
                    position++;

                } else {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("JSON TRIP consistency do not exists do not exist.");
                    a.show();
                    throw new ParseException(1);
                    
                }

            }

        } catch (ParseException e) {
            
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("JSON TRIP consistency do not exists do not exist.");
            a.show();
            throw new ParseException(1);
            

        }
        
        //Inserts station in the Line_Station table in the DB
        lineStationToDB(stationInOrderInLine);
        
        //Inserts trips into DB
        tripToDB(trips);
        
        //Show success
        AdminViewController ac = new AdminViewController();
        ac.showTable(stationInOrderInLine);

    }


    /**
     * Check if key is already in the lines ArrayList
     *
     * @param key
     * @return boolean
     */
    public static boolean checkIfLinesContainsKey(char key) {

        return lines.stream().anyMatch(line -> (line.getKey() == key));
    }

    /**
     * Check if key or name is already in the lines ArrayList
     *
     * @param name
     * @param key
     * @return boolean
     */
    public static boolean checkIfLinesContainsNameorKey(String name, char key) {

        return lines.stream().anyMatch(line -> (line.getKey() == key || line.getName().contains(name)));
    }

    /**
     * Get a line Object of arraylist Line
     *
     * @param key
     * @return line
     */
    public static Line getLineOfLinesByKey(char key) {

        for (Line line : lines) {
            if (line.getKey() == key) {
                return line;
            }

        }
        return null;
    }

    /**
     * Check if the Station and that line is in stationline arrayList
     *
     *
     * @param nameStation
     * @param line
     * @return boolean
     */
    public static boolean stationInLineExist(String nameStation, Line line) {

        return stationLine.stream().anyMatch(st -> (st.getNameOfStation().equals(nameStation) && st.getLines().contains(line)));
    }

    /**
     * Returns the number of station in a line
     *
     * @param line
     * @return int
     */
    public static int numberOfStationsInLine(Line line) {
        int numberOfStationInLine = 0;

        for (StationLine st : stationLine) {

            if (st.getLines().contains(line)) {
                numberOfStationInLine++;
            }
        }
        return numberOfStationInLine;
    }

    /**
     * Check if the Station and that line exist in stationInOrderInline (checks
     * duplicate)
     *
     *
     * @param nameStation
     * @param line
     * @return boolean
     */
    public static boolean CheckStationAndLineExistInStationInOrderInLine(String nameStation, Line line) {

        return stationInOrderInLine.stream().anyMatch(st -> (st.getName().equals(nameStation) && st.getLine().equals(line)));
    }
    
        /**
     * Receives an array of stations checks if Line_Station exists and DB and if not, inserts into DB
     * @param stationInOrderInLine 
     */
    public static void lineStationToDB(ArrayList<Station> stationInOrderInLine) {

        for(Station s : stationInOrderInLine){
             stationDAO.insertLineStation(s);
        }
        
    }

    /**
     * Receives a Map of Characters and Strings, checks if line exists with key
     * in Map and inserts Lines into DB
     *
     * @param lines
     */
    private static void lineToDB(ArrayList<Line> lines) {
        for (Line line : lines) {
            if (!lineDAO.lineExist(line.getKey())) {
                lineDAO.insertLine(line);
            }
        }
    }

    /**
     * Receives an Array of StationLine and inserts into DB
     *
     * @param stations
     */
    private static void stationToDB(ArrayList<StationLine> stations) {
        for (StationLine stationLine : stations) {
            Station station = new Station(stationLine.getNameOfStation(), stationLine.getPrice());
            stationDAO.insertStation(station);
        }
    }

    /**
     * Receives an Array of trips and inserts into DB wit 2 stations and
     * duration of trip
     *
     * @param trips
     * @throws SQLException
     */
    private static void tripToDB(ArrayList<Trip> trips) throws SQLException {
        for (Trip trip : trips) {
            tripDAO.insertTrip(trip);
        }
    }

}
