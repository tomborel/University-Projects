/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author thomasborel
 * 
 * Driver class for evaluating a Rainbow tables coverage of passwords, and time taken to generate
 * The main method reads in a table from the specified file path, randomly generates 100 strings to search
 * for in the table, and informs the user of the number of passwords which have been discovered.
 * Finally the time taken for the entire process is printed
 */
public class RainbowTableDriver 
{
    /*
     * A reference to the RainbowTable class, which should specifiy the maximum password length,
     * the chain length and number of tables, and the alphabet used
     */
    private static RainbowTable table = new RainbowTable(4, 1000, 20000, Alphabet.FULL);
    
    //This object is needed in order to calculate the hashes of passwords to search for
    private static BruteForce sha1 = new BruteForce();
    
    //Used to calculate the time taken to search for 100 passwords
    private static Timer timer = new Timer();
    
    //The path at which the serialized rainbow table can be found
    private static String path = "/Users/thomasborel/Documents/Uni/CryptographyAssignment2/";
    
    public static void main(String[] args) 
    {
        table.readFromfile(path + "ML4CL1000.ser");

        HashMap<String, String> tests = new HashMap();
        
        for(int index = 0; index < 100; index ++)
        {
            String password = table.generateRandomString(table.getMaxPasswordLength());
            String hash = sha1.applySHA1(password);
            
            tests.put(password, hash);
        }
        
        
        int numberPassed = 0;   
        
        timer.timeStart();
        
        for(String test: tests.keySet())
        {
            System.out.println("Searching For: " + tests.get(test));
            
            String password = table.searchForPassword(tests.get(test), test, table.getTable());
            
            if(password.equals("Not Found!"))
                continue;
                
            numberPassed++;
        }
        
        timer.timeStop("min");
        
        System.out.println("Found " + numberPassed + "% of passwords");
    }
}

