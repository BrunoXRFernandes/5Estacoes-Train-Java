package com.mycompany.lp3_5estacoes;

import BLL.PassTypeBLL;
import database.Database;
import database.DatabaseFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    //Call BD
    private Connection connection;
    //BD Factory that calls our MSSql Database
    private final Database database = DatabaseFactory.getDatabase("SQLServer");
    private final Connection conn = database.conectar();

    @Override
    public void start(Stage stage) throws IOException, Exception {
        scene = new Scene(loadFXML("fxml/login"));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.show();      
    }
    
    static void setRoot(String fxml) throws IOException {
         scene.setRoot(loadFXML(fxml));

     }

     private static Parent loadFXML(String fxml) throws IOException {
         FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
         return fxmlLoader.load();
     }

    public static void main(String[] args) {
        launch();
    }

}
