/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.sql.Date;


/**
 *
 * @author nelso
 */
public class Trip {
    
    //TODO Estation departure and arrival
    public int id;
    public Route route;
    public Station departure;
    public Station arrival;
    public int duration;
    public char keyLine;
    public String tripDate;
    public String ticket;

    /**
     * Constructor empty
     * @param id
     * @param departure
     * @param arrival
     */
    public Trip(int id, Station departure, Station arrival) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
    }
    
     /**
     * Constructor empty
     * @param departure
     * @param arrival
     */
    public Trip(Station departure, Station arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }
    
     /**
     * Constructor trip stations + duartion
     * @param departure
     * @param arrival
     * @param duration
     * @param keyLine
     */
    public Trip(Station departure, Station arrival, int duration, char keyLine) {
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        this.keyLine = keyLine;
    }

    public Trip() {
    }

    public int getId() {
        return id;
    }

    public Route getRoute() {
        return route;
    }

    public Station getDeparture() {
        return departure;
    }

    public Station getArrival() {
        return arrival;
    }

    public int getDuration() {
        return duration;
    }

    public char getKeyLine() {
        return keyLine;
    }

    public String getTripDate() {
        return tripDate;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setDeparture(Station departure) {
        this.departure = departure;
    }

    public void setArrival(Station arrival) {
        this.arrival = arrival;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setKeyLine(char keyLine) {
        this.keyLine = keyLine;
    }

    public void setTripDate(Date tripDate) {
        
        this.tripDate = tripDate.toString();
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

 
    
    

    /**
     * Default to string
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Trip{" + '}';
    }
}
