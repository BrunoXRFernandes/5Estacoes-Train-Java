package utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rui
 */
public class Alerts {

    Alert alert = new Alert(Alert.AlertType.NONE);

    public void showError(String message) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText("Erro");
        alert.setContentText(message);
        alert.show();
    }

    public void showInformation(String message) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Informação");
        alert.setContentText(message);
        alert.show();
    }
    
    public boolean showConfirmation(String title, String content){
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
	alert.setTitle(title);
	alert.setContentText(content + " ?");
	
	Optional<ButtonType> result = alert.showAndWait();
	if(!result.isPresent() || result.get() != ButtonType.OK) {
		return false;
	} else {
		return true;
	}
    }

}
