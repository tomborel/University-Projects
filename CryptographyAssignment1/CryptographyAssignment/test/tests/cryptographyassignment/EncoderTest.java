/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.cryptographyassignment;

import cryptographyassignment.Encoder;
import cryptographyassignment.Utilities;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thomasborel
 */
public class EncoderTest {
    
    public EncoderTest() {
    }

    @Test
    public void TestEncoder()
    {
       Encoder encoder = new Encoder();
       
       assertTrue(Utilities.isPalindrome("0018228100"));
       
       assertFalse(Utilities.isPalindrome("18382291919"));
    }
    
    @Test
    public void TestGenerateNextUseable()
    {
        Encoder encoder = new Encoder();
        
        System.out.println(encoder.generateNextUsable("000000", 100000));
    }
    
    @Test
    public void LuckyIds()
    {
        Encoder encoder = new Encoder();
        
        encoder.generateNextUsable("000000", 100000);
        
        System.out.println(encoder.getPalindromes());
    }
}