/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import DAOs.LineDAO;
import DAOs.StationDAO;
import DAOs.TripDAO;
import com.mycompany.lp3_5estacoes.AdminViewController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import models.Line;
import models.Station;
import models.StationLine;
import models.Trip;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author pcoelho
 */
public class ImportXML {
    
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

    /**
     * Method receives a file and read the XML
     *
     * @param file
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @throws java.sql.SQLException
     *
     */
    public static void importXMLFile(File file) throws ParserConfigurationException, SAXException, IOException, SQLException {
        Boolean xmlProcessed = false;

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse File
            Document doc = db.parse(file);

            // optional, but recommended, normalize 
            doc.getDocumentElement().normalize();

            // get list of nodes child of Data
            NodeList listOfNodes = doc.getDocumentElement().getChildNodes();

            // go through nodes
            for (int i = 0; i < listOfNodes.getLength(); i++) {

                // node from list of nodes
                Node node = listOfNodes.item(i);

                // test if node is an element
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    //Cast to element
                    Element element = (Element) node;

                    //System.out.println(element.getNodeName());

                    if (element.getNodeName().contains("Lines")) {

                        // read the line of Lines node
                        readLinesXml(node);
                    } else if (element.getNodeName().contains("Stations")) {

                        //read the Staions 
                        readStationsXml(node);
                    } else {
                        // read the order of the line
                        readLineOrderXml(node);
                    }
                }
            }

            xmlProcessed = true;

        } catch (ParserConfigurationException | SAXException | IOException e) {

            e.printStackTrace();
            //TODO alert user

        } finally {

            if (xmlProcessed) {

                //System.out.println("FILE WAS READ SUCCESFULLY");
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("XML Importado com sucesso");
                a.show();
                
                AdminViewController ac = new AdminViewController();
                ac.showTable(stationInOrderInLine);

                //System.out.println(lines);
                //stationLine.forEach(st -> {
                    //System.out.println(st.getNameOfStation() + st.getLines());
                //});

                //stationInOrderInLine.forEach(st -> {
                    //System.out.println(st.getName() + " " + st.getPosition() + " " + st.getLine());
                //});

            } else {

                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("XML não é um ficheiro válido");
                a.show();

                //System.out.println("ERROR!!!!!!!!!!!");
            }

        }

    }

    /**
     * Method receives receive the node of lines and reads the name of lines and
     * ke
     *
     * @param node
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    public static void readLinesXml(Node node) throws ParserConfigurationException, SAXException, IOException {

        // Get the list of nodes of line
        NodeList listOfNodes = node.getChildNodes();
        for (int i = 0; i < listOfNodes.getLength(); i++) {

            // get node from each item of lines
            Node node1 = listOfNodes.item(i);

            if (node1.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node1;

                // Get key attribute
                char key = element.getAttribute("Key").charAt(0);

                // Get name of line
                String name = element.getElementsByTagName("Name").item(0).getTextContent();
                //System.out.println("Line--> key: " + key + " name: " + name);

                // check values are unique and add to lines ArrayList
                if (!checkIfLinesContainsNameorKey(name, key)) {

                    lines.add(new Line(name, key, 1));

                } else {

                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("XML Line consistency do not exist.");
                    a.show();
                    throw new ParserConfigurationException("XML Line consistency do not exist.");

                }
            }
        }
        //Insert Lines into DB
        lineToDB(lines);
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
     * Method receives receive the node of starions and reads the name of the
     * station and the lines keys
     *
     * @param node
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     *
     */
    public static void readStationsXml(Node node) throws ParserConfigurationException, SAXException, IOException {

        // Get the list of nodes of Station
        NodeList listOfNodes = node.getChildNodes();

        for (int i = 0; i < listOfNodes.getLength(); i++) {

            Node node1 = listOfNodes.item(i);

            if (node1.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node1;

                //Station name
                String name = element.getElementsByTagName("Name").item(0).getTextContent();
                
                //Station price
                double price = Double.parseDouble(element.getElementsByTagName("Price").item(0).getTextContent());

                // number of lines
                int numberLines = element.getElementsByTagName("Line").getLength();

                // new Arrylist of caracter to add to Station name
                ArrayList<Line> listLinesOfStation = new ArrayList();

                // each line
                for (int j = 0; j < numberLines; j++) {

                    char key = element.getElementsByTagName("Line").item(j).getTextContent().charAt(0);

                    // check if line key exist in Line and check if the listLinesofStation doesnt duplicate line)
                    if (checkIfLinesContainsKey(key) && !listLinesOfStation.contains(getLineOfLinesByKey(key))) {
                        listLinesOfStation.add(getLineOfLinesByKey(key));

                    } else {

                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setContentText("XML Station consistency Line do not exist.");
                        a.show();
                        throw new ParserConfigurationException("XML Station consistency Line do not exist.");

                    }

                    //System.out.println("Station name --> " + name + " line: " + element.getElementsByTagName("Line").item(j).getTextContent());

                }
                //add Station to array to be inserted into DB
                stationLine.add(new StationLine(name, price, listLinesOfStation));
            }
        }
        
        //Inserts the Stations in the DB
        stationToDB(stationLine);
    }

    /**
     * Check if the Station is in stationline arrayList
     *
     * @param nameStation
     * @return boolean
     */
    public static boolean stationExist(String nameStation) {

        return stationLine.stream().anyMatch(st -> (st.getNameOfStation().equals(nameStation)));

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
     * Method receives receive the node of Trips and reads the name of staion,
     * line and create the order
     *
     * @param node
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     *
     */
    public static void readLineOrderXml(Node node) throws ParserConfigurationException, SAXException, IOException, SQLException {
        // TODo check if is in new stationIn OrderLine
        NodeList listOfNodes = node.getChildNodes();

        // position in the line
        int position = 0;
        for (int i = 0; i < listOfNodes.getLength(); i++) {

            Node node1 = listOfNodes.item(i);

            Station station = new Station();
            Station arrivalStation = new Station();

            if (node1.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node1;
                String departure = element.getElementsByTagName("Departure").item(0).getTextContent();

                String arrival = element.getElementsByTagName("Arrival").item(0).getTextContent();
                char key = element.getElementsByTagName("Line").item(0).getTextContent().charAt(0);
                Line line = getLineOfLinesByKey(key);
                
                String time = element.getElementsByTagName("Duration").item(0).getTextContent();

                //System.out.println("Departure: " + departure + " Arrival: " + arrival + " line: " + line);

                //Line to add in the Station
                Line newLine = new Line();
                
                //Trip to add in the trips
                Trip trip = new Trip();
                
                // check if departure and arrival station and line exist in station line, check also if is not duplicate 
                if (stationInLineExist(departure, line) && stationInLineExist(arrival, line)
                        && !CheckStationAndLineExistInStationInOrderInLine(departure, line) && !CheckStationAndLineExistInStationInOrderInLine(arrival, line)) {
                    
                    newLine = lineDAO.getLine(line.getKey());
                    
                    station.setName(departure);
                    station.setPosition(position);
                    station.setLine(newLine);
                    
                    stationInOrderInLine.add(station);
                    
                    //set the station departure in this trip
                    trip.setDeparture(station);
                    
                    //set the station arrival in this trip
                    arrivalStation.setName(arrival);
                    arrivalStation.setPosition(position);
                    arrivalStation.setLine(newLine);
                    
                    trip.setArrival(arrivalStation);
                    
                    //calculate the duration of trip in seconds for DB
                    int duration = TimeParse.timeToInt(time);
                    trip.setDuration(duration);
                    
                    //sets the key of line of this trip
                    trip.setKeyLine(key);
                    
                    trips.add(trip);
                    
                    // In case that is the last station of the line add the arrival
                    if (position == numberOfStationsInLine(line) - 2) {
                        Station lastStation = new Station();
                        newLine = lineDAO.getLine(line.getKey());

                        position++;
                        
                        lastStation.setName(arrival);
                        lastStation.setLine(newLine);
                        lastStation.setPosition(position);
                        
                        stationInOrderInLine.add(lastStation);
                        
                        position = -1;
                    }

                    // increment position
                    position++;

                } else {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("XML Station consistency Line do not exist.");
                    a.show();
                    throw new ParserConfigurationException("XML Station consistency Line do not exist.");
                }
            }
        }
        
        //Inserts station in the Line_Station table in the DB
        lineStationToDB(stationInOrderInLine);
        
        //Inserts trips into DB
        tripToDB(trips);
    }

    /**
     * Choose an XML File in project root directory
     *
     *
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static void fileChooseXML() throws ParserConfigurationException, SAXException, IOException, SQLException {

        // Initialize FileChoose Object  
        FileChooser fc = new FileChooser();

        // Get The Path of the appilcation
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();

        // Set the title of the popup window
        fc.setTitle("Select a xml File to import a subway");

        // Set the initial directory
        fc.setInitialDirectory(new File(currentPath));

        // filter XML file
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        // Select file
        File selectedFile = fc.showOpenDialog(null);

        // if exist send file to testXML method
        if (selectedFile != null) {

            //clear arraylists
            lines.clear();
            stationLine.clear();
            stationInOrderInLine.clear();

            //System.out.println(selectedFile.getAbsolutePath());
            importXMLFile(new File(selectedFile.getAbsolutePath()));

        } else {

            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("File was not selected.");
            a.show();
        }
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
     * Receives a Map of Characters and Strings, checks if line exists with key in Map and inserts Lines into DB
     * @param lines 
     */
    private static void lineToDB(ArrayList<Line> lines) {
        for(Line line : lines){
            if(!lineDAO.lineExist(line.getKey())){          
                lineDAO.insertLine(line);
            }
        }
    }

    /**
     * Receives an Array of StationLine and inserts into DB
     * @param stations 
     */
    private static void stationToDB(ArrayList<StationLine> stations) {
        for(StationLine stationLine : stations){
                Station station = new Station(stationLine.getNameOfStation(), stationLine.getPrice());
                stationDAO.insertStation(station);
        }
    }
    
    /**
     * Receives an Array of trips and inserts into DB wit 2 stations and duration of trip
     * @param trips
     * @throws SQLException 
     */
    private static void tripToDB(ArrayList<Trip> trips) throws SQLException {
        for(Trip trip : trips){
                tripDAO.insertTrip(trip);
        }
    } 
}
