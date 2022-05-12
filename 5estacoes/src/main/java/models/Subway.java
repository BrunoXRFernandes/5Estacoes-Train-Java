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
public class Subway {

    private int id;
    private String name;

    /**
     * Getter id
     *
     * @return Interger
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
     * Constructor empty
     */
    public Subway() {
    }

    /**
     * constructor complete
     *
     * @param id
     * @param name
     */
    public Subway(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Default to string
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Subway{" + "id=" + id + ", name=" + name + '}';
    }
}
