/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.cryptographyassignment;

import cryptographyassignment.Decoder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thomasborel
 */
public class DecoderTest 
{
    Decoder decoder = new Decoder();

    
    @Test
    public void TestDecodeWithSignleDigitMissing()
    {
        assertTrue(decoder.decode("3745195876").contains("No Errors"));
        
        assertTrue(decoder.decode("3945195876").contains("3745195876"));
        
        assertTrue(decoder.decode("3715195076").contains("3745195876"));
        
        assertTrue(decoder.decode("0743195876").contains("3745195876"));
        
        assertTrue(decoder.decode("3745195840").contains("3745195876"));
        
        assertTrue(decoder.decode("2745795878").contains("3 Errors"));
    }
}