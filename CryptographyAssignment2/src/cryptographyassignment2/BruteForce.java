/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment2;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomasborel
 * 
 * This class provides functions for applying the SHA1 hashing function to Strings
 * it also allows for passwords to be cracked from a hash using the exhaustive brute force search method
 * Two different search strategies are provided
 */
public class BruteForce 
{
   // the hexadceimal alphabet, should not be changed
    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();
    
    /**
     * The alphabet to be used for the brute force search algorithm, which represents 
     * all the different digits which a password may contain. The alphabet has been stored 
     * here for ease of configuration, in order to change it's order and provided varied
     * search strategies
     */
    private static char[] alphabet = Alphabet.FULL.getAlphabet().toCharArray();
    
    //The maximum password length this program can break; 
    final private int MAX_PASSWORD_LENGTH = 6;
    
    /*
     * a boolean flag used by the brute force search algorithm. It is stored here
     * as the brute force algorithms employs a recursive method, meaning this flag
     * is accessed by multiple methods
     */ 
    private boolean addDigit = false;
    
    // A reference to the Timer class for testing purposes
    private Timer timer = new Timer();
    
    //instantiated in constructor
    private String time;

   /**
    * The default constructor for this class
    */
   public BruteForce() 
   {
   }

   /**
    * A constructor which allows strings to be passed in. These should specify
    * the time format required when using this class for testing purposes
    * @param time the time format needed. This can be either min, sec or ms
    */
   public BruteForce(String time) 
   {
       this.time = time;
   }

   /**
    * Applies the SHA1 hashing algorithm to the specified string, by calling
    * Instantiating an SHA-1 instance of MessageDigest object, and converting 
    * the returning Hexadecimal value to a string
    * @param String to apply the SHA1 hashing algorithm to
    * @return the hashed String
    */
   public String applySHA1(String text) 
   {
        byte[] sha1hash = new byte[40];

        try 
        {
            MessageDigest messageDigest  = MessageDigest.getInstance("SHA-1");

            messageDigest.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = messageDigest.digest();
        } 
        catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(BruteForce.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (UnsupportedEncodingException ex) 
        {
            Logger.getLogger(BruteForce.class.getName()).log(Level.SEVERE, null, ex);
        }

        return convertToHexString(sha1hash).toLowerCase();
     }

     /**
      * public method for calculating the value of a hash value of a password through brute force
      * also allows for an optional salt String
      * this method also calls the Timer class object in order to print the time
      * taken for calculating the password
      * @param hash the hash value of the password to calculate
      * @param testValue the first value of the alphabet to begin the brute force attack with
      * @param salt the optional salt the password has been hashed with
      */
     public void bruteForceAttack(String hash, String testValue, String salt)
     {
         System.out.println("Calculating : [" + hash + "]");
         timer.timeStart();

         System.out.println(bruteForce(hash, testValue, salt));

         timer.timeStop(time);

     }
     
     /**
      * This method uses recursion to increment through each combination of digits of the alphabet variable
      * until either the password has been discovered or the maximum number of digits has been reached
      * The incrementForwards method used here can be changed to incrementBackwards in order to change the
      * search strategy
      * This algorithm can discover any password within the maximum range given enough time
      * 
      * @param hash the hash  value of the password to calculate
      * @param testValue the initial value to start the brute force process with
      * @param salt the optional salt value the password has been hashed with
      * @return the cracked string
      */
     private String bruteForce(String hash, String testValue, String salt)
     {
         while(testValue.length() <= MAX_PASSWORD_LENGTH && !hash.equals(applySHA1(salt + testValue)))
         {       
             testValue = incrementForwards(testValue);

             if(addDigit)
             {
                 addDigit = false;
                 testValue = bruteForce(hash, testValue + String.valueOf(alphabet[0]), salt); 
             }
         }

         return testValue;
     }

     /**
      * Increments the specified String the the next character in the alphabet
      * if the end of the alphabet has been reached for all digits in the string
      * a new digit is added and the other digits return to the beginning of the alphabet. 
      * The incrementation then continue from the left foremost digit
      * @param stringToIncrement the value to increment
      * @return the incremented value
      */
     private String incrementForwards(String stringToIncrement)
     {
         StringBuilder builder = new StringBuilder(stringToIncrement);
         int position = 0;

         for(int character = position; character < stringToIncrement.length(); character ++)
         {
             if(builder.substring(position, position + 1).equals(String.valueOf(alphabet[alphabet.length - 1])))
             {
                 builder.replace(position, position + 1, String.valueOf(alphabet[0]));
                  position ++;
             }
         }

         if(position == builder.length())
         {
             addDigit = true;
             return builder.toString();
         }

         int index = getNextIndex(builder.substring(position, position + 1));

         builder.replace(position, position + 1, String.valueOf(alphabet[index]));

         return builder.toString();
     }

          /**
      * Increments the specified String the the next character in the alphabet
      * if the end of the alphabet has been reached for all digits in the string
      * a new digit is added and the other digits return to the beginning of the alphabet. 
      * The incrementation then continue from the right foremost digit
      * @param stringToIncrement the value to increment
      * @return the incremented value
      */
     private String incrementBackwards(String stringToIncrement)
     {
         StringBuilder builder = new StringBuilder(stringToIncrement);

         int position = builder.length();

         for(int character = 0; character < stringToIncrement.length(); character ++)
         {
             if(builder.substring(position - 1, position).equals(String.valueOf(alphabet[alphabet.length - 1])))
             {
                 builder.replace(position - 1, position, String.valueOf(alphabet[0]));
                  position --;
             }
         }

         if(position == 0)
         {
             addDigit = true;
             return builder.toString();
         }

         int index = getNextIndex(builder.substring(position - 1, position));

         builder.replace(position - 1, position, String.valueOf(alphabet[index]));

         return builder.toString();
     }

     /**
      * returns the next index of the alphabet from the specified character
      * if the final character of the alphabet has been returned, the first
      * index of the alphabet is returned (0)
      * @param string the character at the current index
      * @return the next index from the specified character
      */
     private int getNextIndex(String string)
     {
         if(string.length() > 1)
             return - 1;

         for(int index = 0; index < alphabet.length; index ++)
         {
             if(index == alphabet.length - 1)
                 return 0;

             if(String.valueOf(alphabet[index]).equals(string))
               return index + 1;

         }

         return - 1;
     }

     /**
      * Method for converting a hexadecimal byte array to a hexadecimal String
      * @param hash the byte array to convert 
      * @return the converted array returned as a String
      */
     private String convertToHexString(byte[] hash)
     {
       char[] hexChars = new char[hash.length * 2];
       for ( int index = 0; index < hash.length; index++ ) 
       {
           //bitwise AND operator
           int v = hash[index] & 0xFF;

           hexChars[index * 2] = hexArray[v >>> 4];
           hexChars[index * 2 + 1] = hexArray[v & 0x0F];
       }
       return new String(hexChars);
     }

}
