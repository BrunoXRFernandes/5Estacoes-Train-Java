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
public class Line {

    private int id;
    private String name;
    private char key;
    private int IDSubway;

    /**
     * Getter id
     *
     * @return integer
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

    /**
     * Getter key
     *
     * @return char
     */
    public char getKey() {
        return key;
    }

    /**
     * Getter IDSubway
     * @return int
     */
    public int getIDSubway(){
        return IDSubway;
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

    /**
     * Setter key
     *
     * @param key
     */
    public void setKey(char key) {
        this.key = key;
    }
    
    /**
     * Setter IDSubway
     * @param IDSubway 
     */
    public void setIDSubway(int IDSubway){
        this.IDSubway = IDSubway;
    } 

    /**
     * Constructor empty
     */
    public Line() {
    }

    /**
     * Constructor complete
     *
     * @param id
     * @param name
     * @param key
     * @param IDSubway
     */
    public Line(int id, String name, char key, int IDSubway) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.IDSubway = IDSubway;
    }
    
     /**
     * Constructor without database id
     *
     * @param name
     * @param key
     * @param IDSubway
     */
    public Line(String name, char key, int IDSubway) {
        this.name = name;
        this.key = key;
        this.IDSubway = IDSubway;
    }  

    /**
     * Default to string
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Line{" + "id=" + id + ", name=" + name + ", key=" + key + '}';
    }

}
