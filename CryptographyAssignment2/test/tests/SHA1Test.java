package tests;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import cryptographyassignment2.BruteForce;
import org.junit.Test;

/**
 *
 * @author thomasborel
 */
public class SHA1Test 
{
    private BruteForce sha1 = new BruteForce("min");
    
    @Test
    public void testSHA1()
    {
        System.out.println(sha1.applySHA1("uwe.ac.ukwork").toLowerCase());
    }
    
    @Test
    public void testBruteForceOne()
    {
       sha1.bruteForceAttack("c2543fff3bfa6f144c2f06a7de6cd10c0b650cae", "a", "");
    }
    
    @Test
    public void testBruteForceTwo()
    {
       sha1.bruteForceAttack("b47f363e2b430c0647f14deea3eced9b0ef300ce", "a", "");
    }
    
    @Test
    public void testBruteForceThree()
    {
       sha1.bruteForceAttack("e74295bfc2ed0b52d40073e8ebad555100df1380", "a", "");
    }
    
    @Test
    public void testBruteForceFour()
    {
       sha1.bruteForceAttack("0f7d0d088b6ea936fb25b477722d734706fe8b40", "a", "");
    }
    
    @Test
    public void testBruteForceFive()
    {
       sha1.bruteForceAttack("77cfc481d3e76b543daf39e7f9bf86be2e664959", "a", "");
    }
    
    @Test
    public void testBruteForceSix()
    {
       sha1.bruteForceAttack("5cc48a1da13ad8cef1f5fad70ead8362aabc68a1", "a", "");
    }
    
    @Test
    public void testBruteForceSeven()
    {
       sha1.bruteForceAttack("4bcc3a95bdd9a11b28883290b03086e82af90212", "a", "");
    }
    
    @Test
    public void testBruteForceEight()
    {
       sha1.bruteForceAttack("7302ba343c5ef19004df7489794a0adaee68d285", "a", "");
    }
    @Test
    public void testBruteForceNine()
    {
       sha1.bruteForceAttack("21e7133508c40bbdf2be8a7bdc35b7de0b618ae4", "a", "");
    }
    @Test
    public void testBruteForceTen()
    {
       sha1.bruteForceAttack("6ef80072f39071d4118a6e7890e209d4dd07e504", "a", "");
    }
    @Test
    public void testBruteForceEleven()
    {
       sha1.bruteForceAttack("02285af8f969dc5c7b12be72fbce858997afe80a", "a", "");
    }
    @Test
    public void testBruteForceTwelve()
    {
       sha1.bruteForceAttack("02285af8f969dc5c7b12be72fbce858997afe80a", "a", "");
    }
    
    @Test
    public void testBruteForceSalt()
    {
       sha1.bruteForceAttack("2cb3c01f1d6851ac471cc848cba786f9edf9a15b", "a", "uwe.ac.uk");
    }
    @Test
    public void testBruteForceSaltOne()
    {
       sha1.bruteForceAttack("a20cdc214b652b8f9578f7d9b7a9ad0b13021aef", "a", "uwe.ac.uk");
    }
    @Test
    public void testBruteForceSaltTwo()
    {
       sha1.bruteForceAttack("76bcb777cd5eb130893203ffd058af2d4f46e495", "a", "uwe.ac.uk");
    }
    @Test
    public void testBruteForceSaltThree()
    {
       sha1.bruteForceAttack("9208c87b4d81e7026f354e63e04f7a6e9ca8b535", "a", "uwe.ac.uk");
    }
    @Test
    public void testBruteForceSaltFour()
    {
       sha1.bruteForceAttack("d5e694e1182362ee806e4b03eee9bb453a535482", "a", "uwe.ac.uk");
    }
    @Test
    public void testBruteForceSaltFive()
    {
       sha1.bruteForceAttack("120282760b8322ad7caed09edc011fc8dafb2f0b", "a", "uwe.ac.uk");
    }
}