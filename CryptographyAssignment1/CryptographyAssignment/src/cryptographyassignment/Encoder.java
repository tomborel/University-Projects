/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author thomasborel
*/

/* 
 * BCH ENCODER. 
 * 
 * This class allows Strings to be encoded using BCH(10, 6)
 * Functionality is also provided for generating n consecutive BCH (10, 6) 
 * codewords in a row, as well as checking if given codewords are palindromes
 * 
 */
public class Encoder 
{
    private String codeToCalculate;
    private String palindromes = "";
    private int palindromeCount = 0;
    
    public Encoder() 
    {
    }
    
    //Getters and setters
    
    public String getPalindromes() 
    {
        return palindromes;
    }

    public void setPalindromes(String palindromes) 
    {
        this.palindromes = palindromes;
    }

    public int getPalindromeCount() 
    {
        return palindromeCount;
    }

    public void setPalindromeCount(int palindromeCount) 
    {
        this.palindromeCount = palindromeCount;
    }
    
    public String encode(String stringToEncode)
    {
        //if the given string can not be encoded, generate a random one
        if(stringToEncode.length() < 6)
            return "Generated Random: " + generateRandomCode();
        
        return calulateParityBits(stringToEncode);
    }
    
    //Method which generates random BCH(10,6) codes
    private String generateRandomCode()
    {
        int code[] = new int[10];
        String codeToReturn = "";
        
        // Generate random first 6 digits
        for(int index = 0; index < code.length - 4; index++)
        {
            int value =  new Random().nextInt(9);
            codeToReturn += String.valueOf(value);
        }
        
        return calulateParityBits(codeToReturn);
    }
    
    /**
     * 
     * @param partialCode the initial first six digits from which to calculate 
     * the first BCH (10, 6) codeword. This is incremented by one until the number
     * of codewords specified has been reached
     * 
     * @param amountToGenerate The number of BCH (10, 6) codewords to generate, 
     * this includes any unusable numbers specified by the given problem
     * @return return an array list of usable codes
     */
    public ArrayList<String> generateNextUsable(String partialCode, int amountToGenerate)
    {
        ArrayList<String> codes = new ArrayList<String>();
        String codeToAdd;
        
        codeToCalculate = partialCode;
       
        //generate n consecutive codewords 
        for(int index = 0; index < amountToGenerate; index++)
        { 
            if(Integer.parseInt(codeToCalculate) >= 999999)
                break;
            
            //calculate the parity bits
            codeToAdd = calulateParityBits(codeToCalculate);
            
            /*
             * if the calculated code is unuseable (i.e a parity bit is equal to 10)
             * recursively increment and recalculate until the returned
             * number is useable
            */
            while(codeToAdd.contains("Unusable Number"))
            {
                codeToCalculate = incrementOne(codeToCalculate);
                codeToAdd = calulateParityBits(codeToCalculate);
            }
            
            codes.add(codeToAdd);
            
            //increment the first six digits by 1
            codeToCalculate = incrementOne(codeToCalculate);   
            
            //if the code is a plaindrome then add it to the string of palindromes
            if(Utilities.isPalindrome(codeToAdd))
            {
                palindromes += codeToAdd + "\n";
                palindromeCount ++;
            }
                
        }
        
        //return the list of all codewords generated
        return codes;

    }
    
    private String calulateParityBits(String codeToCalculate)
    {
        int digits[] = new int[10];
        
        //Convert the given code into an integer array, this will allow the parity bits
        //to be calculated easily
        for(int index = 0; index <= codeToCalculate.length() - 1; index++)
        {
            digits[index] =  Character.getNumericValue(codeToCalculate.charAt(index));
        }
        
        //Calculate the ramaining four digits from the first six digits using the following forumlae
        
        //d7 = (4*d1+10*d2+9*d3+2*d4+d5+7*d6) mod 11
        digits[6] = (4*digits[0] + 10*digits[1] + 9*digits[2] + 2*digits[3] + digits[4] + 7*digits[5]) % 11;
              
        //d8 = (7*d1+8*d2+7*d3+d4+9*d5+6*d6) mod 11
        digits[7] = (7*digits[0] + 8*digits[1] + 7*digits[2] + digits[3] + 9*digits[4] + 6*digits[5]) % 11;
        
        //d9 = (9*d1+d2+7*d3+8*d4+7*d5+7*d6) mod 11
        digits[8] = (9*digits[0] + digits[1] + 7*digits[2] + 8*digits[3] + 7*digits[4] + 7*digits[5]) % 11;
        
        //d10 = (d1+2*d2+9*d3+10*d4+4*d5+d6) mod 11
        digits[9] = (digits[0] + 2*digits[1] + 9*digits[2] + 10*digits[3] + 4*digits[4] + digits[5]) % 11;
        
        //Check whether the calculated codeword is useable or not
        if(digits[6] == 10 || digits[7] == 10 || digits[8] == 10 || digits[9] == 10)
            return codeToCalculate + ": Unusable Number";
        
        
        //return the codeword if it is valid
        String codeToReturn = "";
          
        //convert the integer array back to a String
        for(int index = 0; index <digits.length; index++)
        {
            codeToReturn += String.valueOf(digits[index]);
        }
        
        
        return codeToReturn;
     }
    
    //Increments any String which is passed in by one (from right to left)
     private String incrementOne(String stringToIncrement)
     {
        // Cast the string to an integer and increment it
        int code = Integer.valueOf(stringToIncrement);
        code ++;
        String isbnToGenerate = String.valueOf(code);
        StringBuilder builder = new StringBuilder(isbnToGenerate);

        //adds 0s to the incremented String until it is back to its original length
        while(builder.length() < stringToIncrement.length())
        {
            builder.insert(0, "0");
        }

        return builder.toString();
     }
}
