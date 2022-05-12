/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author bruno
 */
public class TransportPass {

    private UUID transportID;
    private User user;
    private PassType passType;
    private LocalDate expirationDate;
    private boolean status;
    private LocalDate insertDate;
    private String groupId;
    

    /**
     * Empty constructor
     */
    public TransportPass() {
    }

    public TransportPass(UUID transportID, User user, PassType passType, LocalDate expirationDate, boolean status) {
        this.transportID = transportID;
        this.user = user;
        this.passType = passType;
        this.expirationDate = expirationDate;
        this.status = status;
    }
    
        public TransportPass(PassType passType, LocalDate expirationDate, boolean status) {
        this.passType = passType;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public UUID getTransportID() {
        return transportID;
    }

    public void setTransportID(UUID transportID) {
        this.transportID = transportID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "TransportPass{" + "transportID=" + transportID.toString() +
                ", user=" + user + ", passType=" + passType + 
                ", expirationDate=" + expirationDate + ", status=" + status + 
                ", insertDate=" + insertDate + ", groupId=" + groupId + '}';
    }
    
    
}
