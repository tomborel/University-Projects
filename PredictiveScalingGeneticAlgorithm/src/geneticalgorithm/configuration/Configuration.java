/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomasborel
 */
public class Configuration 
{
    private String[] constraints;
    private String[] timeConstraints;
    private String[] resourceCOnstraints;
    private String[] objectives;
    
    public Configuration() 
    {
        try 
        {
            Properties properties = new Properties();
            properties.load(new FileInputStream(getClass().getResource("config.properties").getFile()));
            constraints = properties.getProperty("constraints").split("&");
            objectives = properties.getProperty("objectives").split("&");
            
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] getConstraints() 
    {
        return constraints;
    }

    public String[] getTimeConstraints() 
    {
        return timeConstraints;
    }

    public String[] getResourceConstraints() 
    {
        return resourceCOnstraints;
    }

    public String[] getObjectives() 
    {
        return objectives;
    }
    
    
    
}
