/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment2;

/**
 *
 * @author thomasborel
 */
public class BruteForceDriver 
{

    /**
     * @param args the command line arguments
     * Driver for the BruteForce exhaustive search class
     */
    public static void main(String[] args) 
    {
        BruteForce bruteForce = new BruteForce("min");
        
        bruteForce.bruteForceAttack("c2543fff3bfa6f144c2f06a7de6cd10c0b650cae", "a", "");
    }
}
