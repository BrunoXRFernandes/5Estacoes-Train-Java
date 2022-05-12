module com.mycompany.lp3_5estacoes {
 
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.net.http;
    requires json.simple;

   

    opens com.mycompany.lp3_5estacoes to javafx.fxml;
    exports com.mycompany.lp3_5estacoes;
    
    

}
