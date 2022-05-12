/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lp3_5estacoes;

import DAOs.RouteDAO;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import models.Route;
import models.Trip;
import BLL.QrCodeBLL;
import java.io.UnsupportedEncodingException;

/**
 * FXML Controller class
 *
 * @author Rui
 */
public class CourseClientViewController implements Initializable {

    @FXML
    private TableView<Route> tableCourse;
    @FXML
    private TableColumn<Route, String> colID;
    @FXML
    private TableColumn<Route, String> colStation;
    @FXML
    private TableColumn<Route, String> colPrice;
    @FXML
    private TableColumn<Route, String> colNrEstacoes;
    @FXML
    private TableColumn<Route, String> colDuration;
    @FXML
    private TableColumn<Route, String> colChangeOfLine;

    RouteDAO routeDAO = new RouteDAO();

    private Trip trip;
    Route route = new Route();

    ArrayList<Route> routeList;
    ObservableList<Route> observableList;
    @FXML
    private ImageView qrcode;

    /**
     * Receives the trip from the previous table
     *
     * @param course
     */
    public void getTrip(Trip course) throws UnsupportedEncodingException {
        this.trip = course;
        loadTableCourse();
    }

    /**
     * Presents the Route from this trip
     */
    public void loadTableCourse() throws UnsupportedEncodingException {
        if (this.trip != null) {
            String image = trip.getTicket();

            //Format the price so that it has 2 decimals max
            DecimalFormat df = new DecimalFormat("#.##");

            colID.setCellValueFactory(date -> new SimpleStringProperty(
                    String.valueOf(date.getValue().getId())));
            colStation.setCellValueFactory(date -> new SimpleStringProperty(
                    String.valueOf(date.getValue().toStringRouteStations())));
            colPrice.setCellValueFactory(date -> new SimpleStringProperty(
                    String.valueOf(df.format(date.getValue().getPrice()) + " €")));
            colNrEstacoes.setCellValueFactory(date -> new SimpleStringProperty(
                    String.valueOf(date.getValue().getNumberOfStations())));
            colDuration.setCellValueFactory(date -> new SimpleStringProperty(
                    String.valueOf(date.getValue().getDuration())));
            colChangeOfLine.setCellValueFactory(date -> new SimpleStringProperty(
                    String.valueOf(date.getValue().getChangesOfLine())));
            //set qrcode in select trip client
            if (image != null) {
                qrcode.setImage(QrCodeBLL.transformToImage(image));
            }

            //sets a fixed witdh to the tables so that they stay regular when user maximizes window
            colID.prefWidthProperty().bind(tableCourse.widthProperty().divide(38));
            colStation.prefWidthProperty().bind(tableCourse.widthProperty().divide(1.4));
            colPrice.prefWidthProperty().bind(tableCourse.widthProperty().divide(15));
            colNrEstacoes.prefWidthProperty().bind(tableCourse.widthProperty().divide(25));
            colDuration.prefWidthProperty().bind(tableCourse.widthProperty().divide(12));
            colChangeOfLine.prefWidthProperty().bind(tableCourse.widthProperty().divide(16));

            route = routeDAO.getRouteTrip(trip);
            observableList = FXCollections.observableArrayList(route);
            tableCourse.setItems(observableList);
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
