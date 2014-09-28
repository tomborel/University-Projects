/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment;

import java.util.ArrayList;

/**
 *
 * @author thomasborel
 */

/*
 * UTILITIES:
 * 
 * Class which contains static methods for general operations which are reused
 * in the encoder/decoder
 * Contains general utilities such as operations using Modular 11 amongst others
 * 
 * The methods in this class are seperate to the general encoding and decoding methods
 *
 */

public class Utilities 
{
    //Calculates a negative number in mod 11
    public static int calculateNegativeInt(int numberToCalculate)
    {
        while(numberToCalculate < 0)
            numberToCalculate = 11 + numberToCalculate;
        
        return numberToCalculate;
    }
    
    //Returns the inverse of a number in mod 11
    public static int inverse(int number)
    {
        switch(number)
        {
            case 1:
                number = 1;
                break;
                
            case 2:
                number = 6;
                break;
                    
            case 3:
                number = 4;
                break;
                
            case 4:
                number = 3;
                break;
                
            case 5:
                number = 9;
                break;
                
            case 6:
                number = 2;
                break;
                
            case 7:
                number = 8;
                break;
                
            case 8:
                number = 7;
                break;
                
            case 9:
                number = 5;
                break;
                
            case 10:
                number = 10;
                break;
        }
        
        return number;
    }
    
    //Calculates the square root of a number in mod 11
    public static int squareRoot(int number)
    {
        switch(number)
        {
            case 1:
                number = 1;
                break;
                
            case 2:
                number = -1;
                break;
                    
            case 3:
                number = 5;
                break;
                
            case 4:
                number = 2;
                break;
                
            case 5:
                number = 4;
                break;
                
            case 6:
                number = -1;
                break;
                
            case 7:
                number = -1;
                break;
                
            case 8:
                number = -1;
                break;
                
            case 9:
                number = 3;
                break;
                
            case 10:
                number = -1;
                break;
        }
        
        return number;
    }
    
    //Calculates the square of a number in mod 11
    public static int square(int number)
    {
        switch(number)
        {
            case 1:
                number = 1;
                break;
                
            case 2:
                number = 4;
                break;
                    
            case 3:
                number = 9;
                break;
                
            case 4:
                number = 5;
                break;
                
            case 5:
                number = 3;
                break;
                
            case 6:
                number = 3;
                break;
                
            case 7:
                number = 5;
                break;
                
            case 8:
                number = 9;
                break;
                
            case 9:
                number = 4;
                break;
                
            case 10:
                number = 1;
                break;
        }
        
        return number;
    }
    
    //Adds an array list of Strings to a single String
    public static String listToString(ArrayList<String> list)
    {
        String stringToReturn = "";
        
        for(String item: list)
        {
            stringToReturn += item + "\n";
        }
        
        return stringToReturn;
    }
    
    //Method which returns true if the given string is a palindrome
     public static boolean isPalindrome(String stringToCheck)
     {
         if(stringToCheck.length() != 10)
             return false;
         
         if(stringToCheck.charAt(0) != stringToCheck.charAt(9))
             return false;
         
         if(stringToCheck.charAt(1) != stringToCheck.charAt(8))
             return false;
         
         if(stringToCheck.charAt(2) != stringToCheck.charAt(7))
             return false;
         
         if(stringToCheck.charAt(3) != stringToCheck.charAt(6))
             return false;
         
         if(stringToCheck.charAt(4) != stringToCheck.charAt(5))
             return false;
         
         return true;
     }
    
}
