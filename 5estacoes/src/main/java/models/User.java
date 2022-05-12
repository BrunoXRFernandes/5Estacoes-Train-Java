/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Optional;
import java.time.LocalDate; 
import java.util.Objects;

/**
 *
 * @author nelso
 */
public class User {

    private String name;
    private String userName;
    private LocalDate birthDate;
    private int id;
    private int permission;
    private boolean status;
    private String hash;
    private String salt;
    private TransportPass pass;

    //type user
    public static final int ADMIN_PERMISSION  = 1;
    public static final int CLIENT_PERMISSION = 2;
    
    //active/disable
    public static final boolean USER_ACTIVE = true;
    public static final boolean USER_INATIVE = false;
    
    
    /**
     * Getter name
     *
     * @return String
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter userName
     * @return String
     */
    public String getUserName() {
        return userName;
    }

   /**
     * Getter birthDate
     * @return Date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Getter id
     *
     * @return integer
     */
    public int getId() {
        return id;
    }

    /**
     * Getter status
     *
     * @return boolean
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Getter permission
     *
     * @return integer
     */
    public int getPermission() {
        return permission;
    }
    
    /**
     * Getter hash
     * @return String
     */
    public String getHash() {
        return hash;
    }
    
    /**
     * Getter salt
     * @return String
     */
    public String getSalt() {
        return salt;
    }

    public TransportPass getPass() {
        return pass;
    }

    /**
     * Setter name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Setter userName
     * @param userName
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Setter birthDate
     * @param birthDate
     */
    public void setBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
    }

    /**
     * Setter id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter status
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Setter permission
     * @param permission
     */
    public void setPermission(int permission) {
        this.permission = permission;
    }
    
        /**
     * Setter hash
     * @param hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }
    
    /**
     * Setter salt
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPass(TransportPass pass) {
        this.pass = pass;
    }

    /**
     * Constructor empty
     */
    public User() {
    }

    /**
     * Constructor complete
     *
     * @param name
     * @param userName
     * @param birthDate
     * @param id
     * @param status
     * @param permission
     * @param hash
     * @param salt
     */
    public User(String name,String userName, LocalDate birthDate, boolean status, int permission, String hash, String salt) {
        this.name = name;
        this.userName = userName;
        this.birthDate = birthDate;
        this.status = status;
        this.permission = permission;
        this.hash = hash;
        this.salt = salt;
    }

    /**
     * Default to string
     *
     * @return String
     */
    @Override
    public String toString() {
        return "User{" + "name=" + name + ", User Name=" + userName + ", id=" + id + ", permission=" + permission + ", status=" + status + '}';
    }

    public void setSalt(Optional<String> salt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User)o;
        return user.userName.equals(userName);
    }
    
}
