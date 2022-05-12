/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lp3_5estacoes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import models.Station;

/**
 * FXML Controller class
 *
 * @author Rui
 */
public class ImportedXMLViewController implements Initializable {

    @FXML
    private AnchorPane anchorTableImport;
    @FXML
    private TableView<Station> tableImported;
    @FXML
    private TableColumn<Station, String> colLine;
    @FXML
    private TableColumn<Station, String> colStation;
    @FXML
    private TableColumn<Station, String> colPosition;
    
    private ObservableList<Station> observableList;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * fills observable list with the list passed
     * @param stations 
     */
    public void getList(ArrayList<Station> stations){
        
        observableList = FXCollections.observableArrayList(stations);
        
        loadTable();
       
    }

    /**
     * Loads the table
     */
    private void loadTable() {
        
        colLine.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getLine().getName())));
        colStation.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getName())));
        colPosition.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getPosition() + 1)));
        
        tableImported.setItems(observableList);
    }
    
}
