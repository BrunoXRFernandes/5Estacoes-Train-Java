/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DAOs.StationDAO;
import DAOs.TripDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.TimeParse;

/**
 *
 * @author nelso
 */
public class Route {

    private int id;
    private String name;
    private int position;
    // All routes
   
    private ArrayList<Station> stations;
    private String duration;
    private double price;
    private int numberOfStations;
    private int changesOfLine;
    

   

    /**
     * Constructor empty
     */
    public Route() {
    }

    /**
     * Constructor complete
     *
     * @param id
     * @param name
     * @param position
     */
    public Route(int id, String name, int position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Route(ArrayList<Station> stations) {
        this.stations = stations;
    }

    /**
     * Getter id
     *
     * @return Integer
     */
    public int getId() {
        return id;
    }

    /**
     * Getter name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    /**
     * Getter stations
     *
     * @return ArrayList
     */
    public ArrayList<Station> getStations() {
        return stations;
    }

    /**
     * getter duration
     *
     * @return String
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Getter Price
     *
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter numberOfStations
     *
     * @return int
     */
    public int getNumberOfStations() {
        return numberOfStations;
    }

    /**
     * Getter ChangesOfLine
     * @return int
     */
    public int getChangesOfLine() {
        return changesOfLine;
    }
    
    /**
     * Setter id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Setter stations
     *
     * @param stations
     */
    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    /**
     * setter Duration
     *
     * @param time
     */
    public void setDuration(int time) {
        String duration = TimeParse.timeToString(time);
        this.duration = duration;
    }

    /**
     * Setter price
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter numberOfStations
     *
     * @param numberOfStations
     */
    public void setNumberOfStations(int numberOfStations) {
        this.numberOfStations = numberOfStations;
    }

    /**
     * Setter changesOfLine
     * @param changesOfLine 
     */
    public void setChangesOfLine(int changesOfLine) {
        this.changesOfLine = changesOfLine;
    }
    
    



    /**
     * Presents all the station in this route separated by '->'
     *
     * @return
     */
    public String toStringRouteStations() {

        String course = "";
        for (int i = 0 ; i < stations.size() ; i++) {
            course += stations.get(i).getName() + " (" + stations.get(i).getLine().getKey() + ")" + " -> ";
            //if this route has 14 stations we might need to break the line to show in the table
            if(i % 14 == 0 && i > 0){
                course += "\n";
            }
        }

        //return the string appended, eliminating the last separator
        return course.substring(0, course.length() - 4);
    }

    /**
     * Default to string
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Route{" + "id=" + id + ", name=" + name
                + ", position=" + position + '}';
    }


}
