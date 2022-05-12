package database;

import java.sql.Connection;

/**
 * Database Interface
 * @author Rui
 */
public interface Database {
    
    public Connection conectar();
    public void desconectar(Connection conn);
    
}
