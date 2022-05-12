/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.lp3_5estacoes;

import BLL.UserBLL;
import java.util.Date;
import DAOs.ClientDAO;
import auth.Auth;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import models.User;
import java.text.SimpleDateFormat;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author bruno
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField nameRegister;
    @FXML
    private TextField pswRegister;
    @FXML
    private TextField usernameRegister;
    @FXML
    private DatePicker dtBirthDate;
    @FXML
    private Button register;
    @FXML
    private Button cancel;
    @FXML
    private Label lbBrithDate;
    @FXML
    private Label labelPSW;
    @FXML
    private Label labelPSW2;
    @FXML
    private Label labelPSW3;

    String salt = Auth.createSalt().get();
    ClientDAO clientDao = new ClientDAO();
    User user = new User();
    Alerts alert = new Alerts();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * If BtnRegister is clicked execute the method register()
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    private void BtnRegister(ActionEvent event) throws SQLException {
        register();
    }

    /**
     * If the cancel button is clicked closes the scene
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void BtnCancel(ActionEvent event) throws IOException {
        closeRegister();
    }

    /**
     * Get the inserted texts, verify if they are empty and insert in database
     */
    private void register() throws SQLException {
        if (verifyInput() && Auth.verificationKey(pswRegister.getText())
                && Auth.verificationUsername(usernameRegister.getText())) {
            fillUser();
            saveUser();
            closeRegister();
        } else {
            dtBirthDate.setVisible(true);
            labelPSW.setVisible(true);
            labelPSW2.setVisible(true);
            labelPSW3.setVisible(true);
        }
    }

    /**
     * Verify input register
     *
     * @return
     */
    public boolean verifyInput() {
        if (nameRegister.getText().isEmpty() || usernameRegister.getText().isEmpty()
                || pswRegister.getText().isEmpty() || !UserBLL.verifyAge(dtBirthDate.getValue())) {
            alert.showError("Preencha os campos");
            return false;
        }
        return true;
    }

    /**
     * Fill object user with all datas
     */
    public void fillUser() {
        try {
            user.setName(nameRegister.getText());
            user.setUserName(usernameRegister.getText());
            user.setBirthDate(dtBirthDate.getValue());
            String psw = pswRegister.getText();
            user.setSalt(salt);
            user.setHash(Auth.hashPassword(psw, salt).get());
        } catch (Exception ex) {
            alert.showError("Erro ao registar user");

        }
    }

    /**
     * Save user in database
     *
     * @throws SQLException
     */
    public void saveUser() throws SQLException {
        if (clientDao.insertClientDB(user)) {
            alert.showInformation("Registo efetudo com sucesso!");
        } else {
            alert.showError("Não foi possivel efetuar o registo");
        }
    }

    /**
     * Close scene Register
     */
    public void closeRegister() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

}
