/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import cryptographyassignment2.Alphabet;
import cryptographyassignment2.RainbowTable;
import org.junit.Test;

/**
 *
 * @author thomasborel
 */
public class RainbowTableTests 
{
    
    private RainbowTable table = new RainbowTable(3, 500, 1500, Alphabet.FULL);
    private final String path = "/Users/thomasborel/Documents/Uni/CryptographyAssignment2/table.ser";
    
    
    @Test
    public void testWriteToFile()
    {
        table.generateRainbowTable();
        
        table.writeToFile();
    }
    
    @Test
    public void testReadFromFile()
    {
         table.readFromfile(path);
         
         table.getTable();
    }
    
    
    
}