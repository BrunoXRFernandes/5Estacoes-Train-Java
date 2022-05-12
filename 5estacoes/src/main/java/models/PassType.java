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
public class PassType {
    
    private String idType;
    private double discount;
    private String description;
    private int minAge;
    private int maxAge;

    public PassType() {
    }

    public PassType(String idType, double discount, String description, int minAge, int maxAge) {
        this.idType = idType;
        this.discount = discount;
        this.description = description;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }
    
        public PassType(String idType) {
        this.idType = idType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String toString() {
        return "PassType{" + "idType=" + idType + ", discount=" + discount + ", description=" + description + ", minAge=" + minAge + ", maxAge=" + maxAge + '}';
    }
    
    
}
