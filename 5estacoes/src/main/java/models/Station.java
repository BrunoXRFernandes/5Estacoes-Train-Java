/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author nelso
 */
public class Station {

    private int id;
    private Integer position;
    private String name;
    private double price;
    private Line line;
 
    /**
     * Getter id
     *
     * @return Integer
     */
    public int getId() {
        return id;
    }

    /**
     * Getter position
     *
     * @return Integer
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Getter name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter price
     * @return double
     */
    public double getPrice() {
        return price;
    }  
    
    /**
     * Getter line
     *
     * @return char
     */
    public Line getLine() {
        return line;
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
     * Setter position
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Setter name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter price
     * @param price 
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Setter line
     *
     * @param line
     */
    public void setLine(Line line) {
        this.line = line;
    }

    /**
     * Constructor empty
     */
    public Station() {
    }

    /**
     * Constructor complete
     *
     * @param id
     * @param position
     * @param name
     * @param line
     */
    public Station(int id, int position, String name, Line line) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.line = line;
    }
    
    
    /**
     * Constructor without database id
     *
     * @param position
     * @param name
     * @param line
     */
     public Station( int position, String name, Line line) {
      
        this.position = position;
        this.name = name;
        this.line = line;
    }
     
    /**
     * Constructor with name only
     * @param name 
     */
    public Station(String name){
        this.name = name;
    }
    
    /**
     * Constructor name and price
     * @param name
     * @param price 
     */
    public Station(String name, double price){
        this.name = name;
        this.price = price;
    }
    
    /**
     * Default to string
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.getName();
    }


    

}
