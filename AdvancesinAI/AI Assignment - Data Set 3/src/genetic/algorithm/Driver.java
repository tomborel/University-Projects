/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.algorithm;

/**
 *
 * @author thomasborel
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {  
        Algorithm algorithm = new Algorithm(100, 1000, 90, 0.005, 0.01, 100, "Tournament", 10, "Uniform", 0.1f);
        
        algorithm.execute();
    }
}
