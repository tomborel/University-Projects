/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment2;

/**
 *
 * @author thomasborel
 * 
 * This enumeration has been created in order to maintain any changes to the 
 * alphabet consistent throughout the software
 * Contains references to a full alphabet (a-9), letters only (a-z) and
 * numbers only (0-9)
 * 
 */
public enum Alphabet 
{
    FULL("abcdefghijklmnopqrstuvwxyz0123456789"), LETTERS("abcdefghijklmnopqrstuvwxyz"), NUMBERS("0123456789");
    
    private String alphabet;
    
    private Alphabet(String alphabet)
    {
        this.alphabet = alphabet;
    }
    
    /**
     * 
     * @return the String value of the alphabet
     */
    public String getAlphabet()
    {
        return alphabet;
    }
}
