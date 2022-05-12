package database;

/**
 *  Database Factory that connect to each DB
 *  MSSql = "SQLServer"
 * @author Rui
 */
public class DatabaseFactory {
    
        //Receives a String with the name of the Database to call
        public static Database getDatabase(String nome){
            //Calls our MSSql Database
        if(nome.equals("SQLServer")){
            return new DatabaseSQLServer();
        }else if(nome.equals("Test")){
            return new DatabaseTest();
        }
        return null;
    }
    
}
