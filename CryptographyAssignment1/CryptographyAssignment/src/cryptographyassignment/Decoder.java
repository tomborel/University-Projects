/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thomasborel
 */

/* 
 * BCH DECODER. 
 * 
 * This class allows Strings to be decoded which have been encoded using BCH (10, 6)
 * Full codewords can bee decoded, as well as codewords with 1 or 2 digits missing 
 * 
 */
public class Decoder 
{
    private Map errorMap;

    public Decoder() 
    {
    }
    
    /**
     * 
     * @param stringToDecode The String to decode
     * @return the decoded String
     */
    public String decode(String stringToDecode)
    {
        switch(stringToDecode.length())
        {
            //Two digits are missing
            case 8:
                return decodeWithTwoDigitsMissing(stringToDecode);
                
            //One digit is missing
            case 9:
                return decodeWithSingleDigitMissing(stringToDecode);
                
            //No digits missing
            case 10:
                return decodeFullCode(stringToDecode);
            
            //Any other length can not be decoded
            default:
                return "Cannot decode";
        }
    }
    
    /*
     * Method for decoding Strings with two digits missing
     * This method uses Trial and Error two decode an incomplete code
     * Two 0s are inserted at each possible index of the incomplete 
     * String, which is then decoded. If the decode message returns two errors at
     * the correct position, then the String is correct
     */
    private String decodeWithTwoDigitsMissing(String incompleteCode)
    {
        String message = "";
        String messageToReturn ="";
        
        //Loop through each position of the string and insert 0
        for(int position = 0; position <= incompleteCode.length(); position++)
        {
            StringBuilder builder = new StringBuilder(incompleteCode);
            builder.insert(position, "0");

            //Loop through each position of the string and insert an additional 0
            for(int secondPosition = 0; secondPosition <= incompleteCode.length(); secondPosition++)
            {
                StringBuilder secondBuilder = new StringBuilder(builder.toString());
                secondBuilder.insert(secondPosition, "0");
                
                //decode
                message = decodeFullCode(secondBuilder.toString());

                //If the code has two errors, and the positions returned in the 
                //message correspond to the positions at which the two 0s were inserted
                //Then the String has succesfuly been decoded
                
                if(message.contains("One Error") && ((position + 1 == errorMap.get("Position")) || (secondPosition + 1 == errorMap.get("Position"))))
                    messageToReturn += message + "\n";
                
                if(message.contains("Two Errors") && (position + 1 == errorMap.get("PositionA") && secondPosition + 1 == errorMap.get("PositionB")))
                    messageToReturn += message + "\n";

                if(message.contains("Two Errors") && (secondPosition + 1 == errorMap.get("PositionA") && position + 1 == errorMap.get("PositionB")))
                    messageToReturn += message + "\n";
            } 
        }
        //return error message if the String can not be decoded
        if(messageToReturn.isEmpty())
            return "Cannot Decode";
        
        return messageToReturn;
    }
    /*
     * Method for decoding Strings with one digit missing
     * Loops through each position of the incomplete code, inserts a 0 at 
     * the position and attempts to decode it. If the decode message contains a single
     * error, then the String has been decoded succesfuly. 
     */
    private String decodeWithSingleDigitMissing(String incompleteCode)
    {
        String message = "";
        
        //Loop through each position of the string and insert 0
        for(int position = 0; position <= incompleteCode.length(); position++)
        {
            StringBuilder builder = new StringBuilder(incompleteCode);
            builder.insert(position, "0");
            
            //decode
            message = decodeFullCode(builder.toString());
           
            //if the message returns a single error, the String hase been decoded sucessfuly
            if(message.contains("No Errors") || message.contains("One Error"))
                break;
        }
        
        return message;
    }
    
    //primary decode method for Strings with 10 digits
    private String decodeFullCode(String code)
    {
        //calculate the syndromes from the code which has been passed to the method
        int[] syndromes = calculateSyndromes(code);
        
        //calculate the errors using the syndromes
        errorMap = calculateErrors(syndromes);
        StringBuilder builder = new StringBuilder(code);
             
        String message = "";
        
        //Switch statement which builds a message to return to the user depending on the amount of errors found
        switch(Integer.valueOf(errorMap.get("Errors").toString()))
        {
            //No erros were found
            case 0:
                message = "No Errors";
                break;
            
            // One error was found
            case 1:               
                int position = Integer.valueOf(errorMap.get("Position").toString());
                int magnitude = Integer.valueOf(errorMap.get("Magnitude").toString()); 
                int error = (int) Integer.valueOf(code.substring(position - 1, position));                    
                
                //replace the incorrect value at the returned position with the corrected value
                builder.replace(position - 1, position, String.valueOf(Utilities.calculateNegativeInt(error - magnitude) % 11));
                
                message = "One Error at position: " + position
                        + " and magnitude: " +  magnitude + ""
                        + "\n Corrected code: " + builder.toString();
                break;
            
            //two errors were found
            case 2:
                int positionA = Integer.valueOf(errorMap.get("PositionA").toString());
                int positionB = Integer.valueOf(errorMap.get("PositionB").toString());
                
                int magnitudeA = Integer.valueOf(errorMap.get("MagnitudeA").toString()); 
                int magnitudeB = Integer.valueOf(errorMap.get("MagnitudeB").toString());
                
                int errorA = (int) Integer.valueOf(code.substring(positionA - 1, positionA));      
                int errorB = (int) Integer.valueOf(code.substring(positionB - 1, positionB));   
                
                //replace the incorrect values at the returned positions with the corrected values
                builder.replace(positionA - 1, positionA, String.valueOf(Utilities.calculateNegativeInt(errorA - magnitudeA) % 11));
                builder.replace(positionB - 1, positionB, String.valueOf(Utilities.calculateNegativeInt(errorB - magnitudeB) % 11));

                message = "Two Errors at positions " + positionA + 
                        " and " + positionB + "\n with magnitudes "
                        + magnitudeA + " and " + magnitudeB+ ""
                        + "\n Corrected code: " + builder.toString();
                break;
             
            //At least three errors were found, the code is impossible to correct
            case 3: 
                message = "At least 3 Errors";
                break;
        }
        
        /*
         * The length of the corrected code is not valid (i.e one off the errors 
         * was calculated as being 10, the input code is not useable
        */
        if(!vaildLength(builder.toString()))
            message = "Unusable Number";
       
        return message;
    }
    
    /*
     * Calculates the syndromes of a given String, and returns an integer array 
     * of 4 syndromes
     */
    private int[] calculateSyndromes(String code)
    {
        int[] codeArray = new int[10];
        
        //Convert String to int array
        for(int index = 0; index <= code.length() - 1; index++)
        {
            codeArray[index] =  Character.getNumericValue(code.charAt(index));
        }
        
        int[] syndromes = new int[4];
        
        // s1= (d1+d2+d3+d4+d5+d6+d7+d8+d9+d10) mod 11
        syndromes[0] = (codeArray[0] + codeArray[1] + codeArray[2] + codeArray[3] + 
                codeArray[4] + codeArray[5] + codeArray[6] + codeArray[7] + 
                codeArray[8] + codeArray[9]) % 11; 
        
        // s2 = (d1+2*d2+3*d3+4*d4+5*d5+6*d6+7*d7+8*d8+9*d9+10*d10) mod 11
        syndromes[1] = (codeArray[0] + 2*codeArray[1] + 3*codeArray[2] + 4*codeArray[3] + 
                5*codeArray[4] + 6*codeArray[5] + 7*codeArray[6] + 8*codeArray[7] + 
                9*codeArray[8] + 10*codeArray[9]) % 11; 
        
        //s3 = (d1+4*d2+9*d3+5*d4+3*d5+3*d6+5*d7+9*d8+4*d9+d10) mod 11
        syndromes[2] = (codeArray[0] + 4*codeArray[1] + 9*codeArray[2] + 16*codeArray[3] + 
                25*codeArray[4] + 36*codeArray[5] + 49*codeArray[6] + 64*codeArray[7] + 
                81*codeArray[8] + 100*codeArray[9]) % 11; 
        
        //s4 = (d1+8*d2+5*d3+9*d4+4*d5+7*d6+2*d7+6*d8+3*d9+10*d10) mod 11 
        syndromes[3] = (codeArray[0] + 8*codeArray[1] + 27*codeArray[2] + 64*codeArray[3] + 
                125*codeArray[4] + 216*codeArray[5] + 343*codeArray[6] + 512*codeArray[7] + 
                729*codeArray[8] + 1000*codeArray[9]) % 11; 
        
        return syndromes;
    }
    
    /*
     * This method calculates the errors in the code based on the four syndromes
     * Returns a Map containing the number of errors, and the position and magnitudes
     * of the errors if there are any
     */
    private Map calculateErrors(int[] syndromes)
    {
        Map mapToReturn = new HashMap();
        
        //If all of the syndromes are equal to 0 then there are no errors in the code
        if(syndromes[0] == 0 && syndromes[1] == 0 && syndromes[2] == 0 && syndromes[3] == 0)      
        {
            mapToReturn.put("Errors", 0);
            return mapToReturn;
        }
        
        //There's at least one error so calculate P, Q and R
        int P = Utilities.calculateNegativeInt((Utilities.square(syndromes[1]) - syndromes[0] * syndromes[2]) % 11);
        
        int Q = Utilities.calculateNegativeInt((syndromes[0] * syndromes[3] - syndromes[1] * syndromes[2]) % 11);
        
        int R = Utilities.calculateNegativeInt((Utilities.square(syndromes[2]) - syndromes[1] * syndromes[3]) % 11);
        
        //if P, Q and R are equal to 0, there is only one error
        if(P == 0 && Q == 0 && R == 0)
        {
            mapToReturn.put("Errors", 1);
            
            //position = s2/s1 and the magnitude is s1
            int position = (syndromes[1] * Utilities.inverse(syndromes[0])) % 11;
            
            if(position == 0)
            {
                mapToReturn.clear();
                mapToReturn.put("Errors", 3);
            }
            
            mapToReturn.put("Position", position);
            mapToReturn.put("Magnitude", syndromes[0]);
            return mapToReturn;
        }
        
        //There are now at least two errors, so apply the Quadratic equasion
        int root = (Utilities.squareRoot(
                        Utilities.calculateNegativeInt(
                        Utilities.square(Q) - 4*P*R)) % 11);
        
        int quadratic = Utilities.calculateNegativeInt(root % 11);
        
        
        //If the quadratic equasion returns 0, or its square root doesn't exist
        //then there are least 3 errors
        if(quadratic == 0 || root == -1)
        {
            mapToReturn.put("Errors", 3);
            return mapToReturn;
        }
        
        //Calculate the positions of the two errors based on the quadratic equasion
        int positionI = Utilities.calculateNegativeInt(((-Q + quadratic) * Utilities.inverse(2*P % 11)) % 11);
        int positionJ = Utilities.calculateNegativeInt(((-Q - quadratic) * Utilities.inverse(2*P % 11)) % 11);
        
        //if the positions are 0, there are at least 3 errors
        if(positionI == 0 || positionJ == 0)
            mapToReturn.put("Errors", 3);
        
        else
            mapToReturn.put("Errors", 2);
        
        //calculate the magnitude of the errors
        int magnitudeB = Utilities.calculateNegativeInt((positionI * syndromes[0] - syndromes[1]) * Utilities.inverse((Utilities.calculateNegativeInt((positionI - positionJ) % 11))) % 11);
        int magnitudeA = Utilities.calculateNegativeInt((syndromes[0] - magnitudeB) % 11);
        
        mapToReturn.put("PositionA", positionI);
        mapToReturn.put("MagnitudeA", magnitudeA);
        mapToReturn.put("PositionB", positionJ);
        mapToReturn.put("MagnitudeB", magnitudeB);
            
        return mapToReturn;
    }
    
    //Method which returns true if the length of the BCH(10, 6) code is valid
    public boolean vaildLength(String stringToCheck)
    {
        if(stringToCheck.length() != 10)
            return false;
        
        return true;
    }
    
}
