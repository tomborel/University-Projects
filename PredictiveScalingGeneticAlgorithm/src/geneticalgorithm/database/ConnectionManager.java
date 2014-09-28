/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tom
 */
public class ConnectionManager 
{
    private String url = "jdbc:mysql://localhost:3306/rulebase";
    private String username = "root";
    private String password = "brocel22";

    public ConnectionManager() 
    {
    }

    public Connection establishConnection()
    {
        Connection connection = null;
        
        try 
        {
            connection = DriverManager.getConnection(url, username, password);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }
}
