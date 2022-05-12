/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lp3_5estacoes;

import DAOs.ClientDAO;
import DAOs.MachineChangeDAO;
import DAOs.TripDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import models.Admin;
import models.Client;
import models.Station;
import models.Trip;
import models.User;
import org.xml.sax.SAXException;
import static utils.ImportXML.fileChooseXML;
import utils.Parser;
import static BLL.ImportJsonMapBLL.ImportJsonMap;
import BLL.PassBLL;
import BLL.PaymentBLL;
import java.net.URISyntaxException;
import org.json.simple.parser.ParseException;
import DAOs.PassDAO;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;
import models.PassType;
import models.TransportPass;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author Rui
 */
public class AdminViewController implements Initializable {

    private Admin admin;
    @FXML
    private Button clients;
    @FXML
    private Button importXML;
    @FXML
    private Button configuration;
    @FXML
    private Button perfil;
    @FXML
    private Button logOut;

    @FXML
    private AnchorPane anchorClients;
    @FXML
    private Label labelTrips;
    @FXML
    private Label labelClients;
    @FXML
    private AnchorPane anchorImportXML;
    @FXML
    private AnchorPane anchorPerfil;
    @FXML
    private Button ImportFile;
    @FXML
    private Button ImportJSONMAP;
    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, String> colIDClient;
    @FXML
    private TableColumn<Client, String> colNameClient;
    @FXML
    private TableColumn<Client, String> colUserNameClient;
    @FXML
    private TableView<Trip> tableTrips;
    @FXML
    private TableColumn<Trip, String> colIDTrip;
    @FXML
    private TableColumn<Trip, String> colDeparture;
    @FXML
    private TableColumn<Trip, String> colArrival;
    @FXML
    private TableColumn<Trip, String> colData;
    @FXML
    private TextField clienttxt;

    private static final boolean USER_ACTIVE = true;
    private static final boolean USER_INACTIVE = false;

    ClientDAO clientDAO = new ClientDAO();
    TripDAO tripDAO = new TripDAO();
    MachineChangeDAO machineChangeDAO = new MachineChangeDAO();
    TransportPass transportPass = new TransportPass();
    private Alerts alert = new Alerts();

    ArrayList<Client> clientsList;
    ArrayList<Trip> tripsList;
    ArrayList<Client> clientPasses;
    ArrayList<Client> clientsWithoutPasses;
    ObservableList<Client> observableListClient;
    ObservableList<Trip> observableListTrip;
    ObservableList<Station> observableList;
    ObservableList<Client> observableListClientPasses;
    ObservableList<Client> observableListClientWithoutPass;
    
    @FXML
    private AnchorPane anchorMachine;
    @FXML
    private Button twoEuro;
    @FXML
    private Button fiveCent;
    @FXML
    private Button tenCent;
    @FXML
    private Button twentyCent;
    @FXML
    private Button fiftyCent;
    @FXML
    private Button oneEuro;
    @FXML
    private Label lblTwoEuro;
    @FXML
    private Label lblTenCent;
    @FXML
    private Label lblTwentyCent;
    @FXML
    private Label lblFiftyCent;
    @FXML
    private Label lblOneEuro;
    @FXML
    private Label lblFiveCent;
    @FXML
    private Button add;
    @FXML
    private Label lblTotal;

    ArrayList<Integer> coins = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    ArrayList<Integer> currentCoins = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    ArrayList<Double> currentMoney = new ArrayList<>(Arrays.asList((double) 0, (double) 0));

    static final double TWOEURO = 2;
    static final double ONEEURO = 1;
    static final double FIFTYCENT = 0.5;
    static final double TWENTYCENT = 0.2;
    static final double TENCENT = 0.1;
    static final double FIVECENT = 0.05;
    static final double TWOCENT = 0.02;
    static final double ONECENT = 0.01;
    
    private Button btnDelete;
  
    Client user = new Client();

    @FXML
    private Button post;
    @FXML
    private Button put;
    @FXML
    private Button delete;
    @FXML
    private Label lblType;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblPassId;
    @FXML
    private TableColumn<Client, String> colExpiration;
    @FXML
    private AnchorPane anchorPassInfo;
    @FXML
    private Button twoCent;
    @FXML
    private Label lblTwoCent;
    @FXML
    private Button oneCent;
    @FXML
    private Label lblOneCent;
    @FXML
    private TableColumn<Client, String> colExpired;
    @FXML
    private Button updateAll;
    @FXML
    private Button active;
    @FXML
    private TableColumn<Client, String> colStatus;

    @FXML
    private TableView<Client> tablePasses;
    @FXML
    private TableColumn<Client, String> colPassId;
    @FXML
    private TableColumn<Client, String> colPassExpiration;
    @FXML
    private TableColumn<Client, String> colPassExpirated;
    @FXML
    private TableColumn<Client, String> colPassType;
    @FXML
    private TableColumn<Client, String> colPassClient;
    @FXML
    private TableColumn<Client, String> colPassBirth;
    @FXML
    private TableColumn<Client, String> colPassActive;
    @FXML
    private ChoiceBox<Client> cbClient;

    /**
     * Fills the local admin with user passed as parameter
     *
     * @param user
     */
    public void getUser(User user) {

        admin = new Admin();

        admin.setId(user.getId());
        admin.setUserName(user.getUserName());
        admin.setName(user.getName());
        admin.setPermission(user.getPermission());
        admin.setStatus(USER_ACTIVE);
        admin.setHash(user.getHash());
        admin.setSalt(user.getSalt());

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTableClients();
        //if the user click in a client from the tble fills the table trips with all the trips of the selected client
        tableClients.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> loadTableTrips(newValue));
       
        tablePasses.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> getClientPass(newValue));

        //if the user click in a trip from the table calls the method to show the route
        tableTrips.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        loadTableClientCourse(newValue);
                    } catch (IOException ex) {
                        Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }

    /**
     * Change to anchorClients
     *
     * @param event
     */
    @FXML
    private void btnClients(ActionEvent event) {
        user = new Client();
        
        anchorClients.setVisible(true);
        anchorImportXML.setVisible(false);
        anchorMachine.setVisible(false);
        anchorPassInfo.setVisible(false);
        labelTrips.setVisible(true);
        tableTrips.setVisible(true);
        active.setVisible(true);
        labelClients.setVisible(true);
        clienttxt.setVisible(true);

        loadTableClients();
    }

    /**
     * Change to anchorImportXML
     *
     * @param event
     */
    @FXML
    private void btnImportXML(ActionEvent event) {
        anchorImportXML.setVisible(true);
        anchorClients.setVisible(false);
        anchorMachine.setVisible(false);
        anchorPassInfo.setVisible(false);
        labelClients.setVisible(false);
        clienttxt.setVisible(false);
    }

    @FXML
    private void btnMachine(ActionEvent event) {
        anchorImportXML.setVisible(false);
        anchorClients.setVisible(false);
        anchorMachine.setVisible(true);
        anchorPassInfo.setVisible(false);
        labelClients.setVisible(false);
        clienttxt.setVisible(false);

        fillMachineLabels();
    }

    /**
     * Fills the labels and the total with coins in the database
     */
    public void fillMachineLabels() {
        //gets the coins in the machine
        currentCoins = machineChangeDAO.getMachineMoney();

        lblTwoEuro.setText("" + currentCoins.get(0));
        lblOneEuro.setText("" + currentCoins.get(1));
        lblFiftyCent.setText("" + currentCoins.get(2));
        lblTwentyCent.setText("" + currentCoins.get(3));
        lblTenCent.setText("" + currentCoins.get(4));
        lblFiveCent.setText("" + currentCoins.get(5));
        lblTwoCent.setText("" + currentCoins.get(6));
        lblOneCent.setText("" + currentCoins.get(7));

        //calculate the total
        totalMachine();
    }

    /**
     * Change to anchorPerfil
     *
     * @param event
     */
    @FXML
    private void btnPerfil(ActionEvent event) {
        // anchorPerfil.setVisible(true);
    }

    /**
     * Log out to aplication
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void btnLogOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    private void btnImport(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, SQLException {

        fileChooseXML();

    }

    /**
     * Populates the table with id, name and username and filters depending on
     * user input
     */
    public void loadTableClients() {

        try {
            colIDClient.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getId())));
            colNameClient.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getName())));
            colUserNameClient.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getUserName())));
            colExpiration.setCellValueFactory(date -> new SimpleStringProperty(
                    String.valueOf(date.getValue().getPass().getExpirationDate() != null
                            ? date.getValue().getPass().getExpirationDate() : "")));
            colStatus.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().isStatus() ? "Ativo" : "Inativo")));
            
            tableColorModifier(tableClients, colExpiration);
            
            colExpired.setCellValueFactory(date -> 
                    new SimpleStringProperty(
                            String.valueOf(date.getValue().getPass().getExpirationDate()!= null ? 
                                    (PassBLL.checkIfPassValid(date.getValue().getPass().getExpirationDate()) ? "Não" : "Sim") : "")));
            clientsList = clientDAO.getClients();
            observableListClient = FXCollections.observableArrayList(clientsList);
            tableClients.setItems(observableListClient);
            
            searchClient(tableClients, observableListClient);
            
        } catch (Exception ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Populates the table trips with all trips made by the client
     *
     * @param client
     */
    public void loadTableTrips(Client client) {
        user = client;
        if (client != null) {
            colIDTrip.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getId())));
            colDeparture.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getDeparture().getName())));
            colArrival.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getArrival().getName())));
            colData.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getTripDate())));
            tripsList = tripDAO.getTripsClient(client.getId());
            observableListTrip = FXCollections.observableArrayList(tripsList);
            tableTrips.setItems(observableListTrip);
            
        }
    }
    
    public void searchClient(TableView<Client> table, ObservableList<Client> list){
                    //Initial filtered List
            FilteredList<Client> filteredData = new FilteredList<>(list, b -> true);
            
            clienttxt.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Client -> {
                    
                    //If no search value then display all records or whatever records it current have. No changes.
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    
                    String searchClient = newValue.toLowerCase();
                    if(Client.getName() != null){
                            if (Client.getName().toLowerCase().indexOf(searchClient) > -1) {
                            return true;
                        } else if (Client.getUserName().toLowerCase().indexOf(searchClient) > -1) {
                            return true;
                        } else {
                            return false; // No match found
                        }                       
                    }else{
                        if (Client.getUserName().toLowerCase().indexOf(searchClient) > -1) {
                        return true;
                    } else if (Client.getPass().getPassType().getIdType().indexOf(searchClient) > -1) {
                        return true;
                    } else if (Client.getPass().getExpirationDate().toString().indexOf(searchClient) > -1) {
                        return true;
                    }else {
                        return false; // No match found
                    }
                    }
                    
                });
            });
            
            SortedList<Client> sortedData = new SortedList<>(filteredData);
            
            //Bind sorted result with table view
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            
            //Aply filtered and sorted data to the table view
            table.setItems(sortedData);
    }
    
    /**
     * Fills the table with all clients with passes, actives or not
     * @throws Exception 
     */
    public void loadTablePasses() throws Exception{
        
        colPassId.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getPass().getTransportID())));
        colPassExpiration.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getPass().getExpirationDate())));
        colPassExpirated.setCellValueFactory(date -> 
                            new SimpleStringProperty(
                                String.valueOf(date.getValue().getPass().getExpirationDate()!= null ? 
                                    (PassBLL.checkIfPassValid(date.getValue().getPass().getExpirationDate()) ? "Não" : "Sim") : "")));
        colPassType.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getPass().getPassType().getIdType())));
        colPassClient.setCellValueFactory(date -> new SimpleStringProperty(String.valueOf(date.getValue().getUserName())));
        colPassBirth.setCellValueFactory(date -> 
                                            new SimpleStringProperty(String.valueOf(date.getValue().getBirthDate()) 
                                                    + " (" + PassBLL.calculateClientAge(date.getValue().getBirthDate()) + ")"));
        colPassActive.setCellValueFactory(date -> 
                            new SimpleStringProperty(
                                String.valueOf(date.getValue().isStatus()== true ? "Sim" : "Nao")));
        
        tableColorModifier(tablePasses, colPassExpiration);
        
        clientPasses = PassDAO.getAllPasses();
        observableListClientPasses = FXCollections.observableArrayList(clientPasses);
        tablePasses.setItems(observableListClientPasses);
        
        searchClient(tablePasses, observableListClientPasses);
    }
    
    /**
     * Modifies the color of the Expiration data according with the time left: Expired = red, 1 year left = orange active= blue
     * @param table
     * @param column 
     */
    public void tableColorModifier(TableView<Client> table, TableColumn<Client, String> column){
                    //Modifies row colors depending on Expirated = Red Last Year = Orange Valid = Blue
            table.setRowFactory(tv -> new TableRow<Client>() {
                
                @Override
                protected void updateItem(Client item, boolean empty) {
                    table.refresh();
                    super.updateItem(item, empty);
                    //Ii date is null or empty stays the same
                    if (item == null || item.getPass().getTransportID() == null){
                        column.setStyle("");
                    } else if (PassBLL.checkIfPassValid(item.getPass().getExpirationDate())){
                        column.setStyle("-fx-background-color: #89cff0;");
                    }else if (!PassBLL.checkIfPassValid(item.getPass().getExpirationDate())){
                        column.setStyle("-fx-background-color: #d82342;");
                    }
                    else if ( PassBLL.checkLastYear(item.getPass().getExpirationDate())){
                        column.setStyle("-fx-background-color: #fea925;");
                   }
                }
            });
    }

    /**
     * Opens the view for the trip selected course
     *
     * @param trip
     * @throws IOException
     */
    public void loadTableClientCourse(Trip trip) throws IOException {
        if (trip != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/courseClientView.fxml"));
            Parent root = loader.load();

            CourseClientViewController controller = loader.getController();
            controller.getTrip(trip);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Viagem " + trip.getDeparture() + " -> " + trip.getArrival());
            stage.showAndWait();
        }
    }

    /**
     * Call the view and the method to fill the table with the stations passed
     *
     * @param stations
     * @throws IOException
     */
    public void showTable(ArrayList<Station> stations) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/importedXMLView.fxml"));
        Parent root = loader.load();

        ImportedXMLViewController controller = loader.getController();
        controller.getList(stations);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Items importados");
        stage.showAndWait();
    }

    @FXML
    private void onClick(MouseEvent event) {
        //actual value of coins to insert
        int actual = 0;
        if (event.getSource() == twoEuro) {
            //gets the actual coins of this value to insert
            actual = Integer.parseInt(lblTwoEuro.getText());
            //adds one to the value of coins inserted
            lblTwoEuro.setText("" + (actual + 1));
            //sets the array of coins to be inserted in DB
            coins.set(0, (coins.get(0) + 1));
        } else if (event.getSource() == oneEuro) {
            actual = Integer.parseInt(lblOneEuro.getText());
            lblOneEuro.setText("" + (actual + 1));
            coins.set(1, (coins.get(1) + 1));
        } else if (event.getSource() == fiftyCent) {
            actual = Integer.parseInt(lblFiftyCent.getText());
            lblFiftyCent.setText("" + (actual + 1));
            coins.set(2, (coins.get(2) + 1));
        } else if (event.getSource() == twentyCent) {
            actual = Integer.parseInt(lblTwentyCent.getText());
            lblTwentyCent.setText("" + (actual + 1));
            coins.set(3, (coins.get(3) + 1));
        } else if (event.getSource() == tenCent) {
            actual = Integer.parseInt(lblTenCent.getText());
            lblTenCent.setText("" + (actual + 1));
            coins.set(4, (coins.get(4) + 1));
        } else if (event.getSource() == fiveCent) {
            actual = Integer.parseInt(lblFiveCent.getText());
            lblFiveCent.setText("" + (actual + 1));
            coins.set(5, (coins.get(5) + 1));
        } else if (event.getSource() == twoCent) {
            actual = Integer.parseInt(lblTwoCent.getText());
            lblTwoCent.setText("" + (actual + 1));
            coins.set(6, (coins.get(6) + 1));
        } else if (event.getSource() == oneCent) {
            actual = Integer.parseInt(lblOneCent.getText());
            lblOneCent.setText("" + (actual + 1));
            coins.set(7, (coins.get(7) + 1));
        }
        totalMachine();
    }

    /**
     * Fills the coins Array to be updated in the DB with the current DB coins
     * and the coins to be inserted
     */
    public void fillCoins() {
        currentCoins = machineChangeDAO.getMachineMoney();
        for (int i = 0; i < coins.size(); i++) {
            coins.set(i, (coins.get(i) + currentCoins.get(i)));
        }

    }

    @FXML
    private void btnAdd(ActionEvent event) {

        fillCoins();
        machineChangeDAO.chargeMachine(coins);
    }

    @FXML
    private void btnConfiguration(ActionEvent event) {
    }

    /**
     * Calculates the total money to be inserted
     */
    public void totalMachine() {
        //this array will get all the values to be sum
        ArrayList<Integer> total = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
        double totalMachineValue = 0;

        //total of coins * their value
        total.set(0, Integer.parseInt(lblTwoEuro.getText()));
        total.set(1, Integer.parseInt(lblOneEuro.getText()));
        total.set(2, Integer.parseInt(lblFiftyCent.getText()));
        total.set(3, Integer.parseInt(lblTwentyCent.getText()));
        total.set(4, Integer.parseInt(lblTenCent.getText()));
        total.set(5, Integer.parseInt(lblFiveCent.getText()));
        total.set(6, Integer.parseInt(lblTwoCent.getText()));
        total.set(7, Integer.parseInt(lblOneCent.getText()));

        totalMachineValue = PaymentBLL.totalChange(total);

        //this will format the value to only have 2 decimal places
        String totalValue = Parser.format(totalMachineValue);
        lblTotal.setText(totalValue + "€");
    }

    /**
     * Import Json via API
     */
    @FXML
    private void btnImportJsonMap(ActionEvent event) throws URISyntaxException, IOException, InterruptedException, ParseException, Exception {

        ImportJsonMap();

    }

    @FXML
    private void btnPass(ActionEvent event) throws Exception {
        user = new Client();

        anchorClients.setVisible(false);
        anchorImportXML.setVisible(false);
        anchorMachine.setVisible(false);
        anchorPassInfo.setVisible(true);
        labelTrips.setVisible(false);
        tableTrips.setVisible(false);
        active.setVisible(false);

        labelClients.setVisible(true);
        clienttxt.setVisible(true);
        
        loadTablePasses();
        fillChoiceBoxClients();
    }
    
    /**
     * Fills the choice box with active clients without pass
     * @throws Exception 
     */
    private void fillChoiceBoxClients() throws Exception{
        clientsWithoutPasses = clientDAO.getClientsWithoutPass();
        observableListClientWithoutPass = FXCollections.observableArrayList(clientsWithoutPasses);
        if(observableListClientWithoutPass.size()>0){
           cbClient.setValue(observableListClientWithoutPass.get(0)); 
        }
        
        cbClient.setItems(observableListClientWithoutPass);
    }

    @FXML
    private void btnPost(ActionEvent event) throws Exception {
        try {
            
            Client newClient = cbClient.getValue();
            
            if (newClient.isStatus() == true) {
                if(newClient.getUserName() != null){
                    PassDAO.createPass(newClient);
                    loadTablePasses();
                    fillChoiceBoxClients();
                    alert.showInformation("O passe do utilizar " + newClient.getUserName()+ " foi criado com sucesso");
                }else{
                    alert.showInformation("Selecione um utilizador da lista");
                }
            } else {
                alert.showInformation("Utilizador não está ativo");
            }
        } catch (Exception e) {
            alert.showInformation("Erro ao atualizar o passe");

        }
    }

    @FXML
    private void btnPut(ActionEvent event) throws Exception {
        try {
            
            if (user.isStatus() == true) {
                if(user.getUserName() != null){
                    PassDAO.updateTransportPass(user, true);
                    loadTablePasses();
                    alert.showInformation("O passe do utilizar " + user.getUserName()+ " foi atualizado com sucesso");
                }else{
                    alert.showInformation("Selecione um utilizador da lista");
                }
            } else {
                alert.showInformation("Utilizador não está ativo");
            }
        } catch (Exception e) {
            alert.showInformation("Erro ao atualizar o passe");
        }
    }

    @FXML
    private void btnDelete(ActionEvent event) throws Exception {
        
        try {
            
            if (user.isStatus() == true){
                if(user.getUserName() != null){
                PassDAO.deleteTransportPass(transportPass.getTransportID(), user.getId());
                loadTablePasses();
                fillChoiceBoxClients();

                alert.showInformation("O passe do utilizar " + user.getUserName()+ " foi eliminado com sucesso");
                }else{
                    alert.showInformation("Selecione um utilizador da tabela");
                }
            }else{
                alert.showInformation("Utilizador não está ativo");
            }
                
        }catch(Exception e){
                alert.showError("Não foi possivel eliminar o passe do utilizador " + user.getUserName());
                }       
            
            user = new Client();

    }

    /**
     * Checks if user has pass and fills the labels according
     *
     * @param client
     */
    private void getClientPass(Client client) {
        if (client != null) {
            try {
                //gloabl user to be used in other places
                user = client;
                transportPass = clientDAO.getTransportPass(user.getUserName());
                //if he has no pass
                if(transportPass.getTransportID() == null){
                    lblPassId.setText("");
                    lblDescription.setText("");
                    lblType.setText("");
                }else{
                    user.setPass(transportPass);
                    //Label passes                    
                    lblPassId.setText(transportPass.getTransportID().toString());
                    lblDescription.setText(transportPass.getPassType().getDescription());
                    lblType.setText(transportPass.getPassType().getIdType());

                }
                lblAge.setText(String.valueOf(PassBLL.calculateClientAge(client.getBirthDate())));
            } catch (Exception ex) {
                Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * enables or disables the buttons to CRUD the pass depending on if user has
     * pass or not
     *
     * @param created
     */
    public void passButtonsUpdate(boolean created) {

        if (created) {
            post.setDisable(false);
            put.setDisable(true);
            delete.setDisable(true);
        } else {
            post.setDisable(true);
            put.setDisable(false);
            delete.setDisable(false);
        }

    }

    @FXML
    private void btnUpdateAll(ActionEvent event) throws Exception {
        PassBLL.validateALL(clientPasses);
        loadTablePasses();       
    }

    /**
     * Modify status from user
     * @param event 
     */
    @FXML
    private void activeAction(ActionEvent event) {
        boolean flag = true;
        try {
            if (user.isStatus()) {
                flag = false;
            }
            clientDAO.statusPerson(user.getUserName(), flag);
            PassDAO.updateTransportPass(user,flag);
            alert.showInformation("Estado do utilizador atualizado");
            loadTableClients();
        } catch (Exception e) {
            alert.showError("Não foi possivel alterar o estado do utilizador");
        }
    }
}
