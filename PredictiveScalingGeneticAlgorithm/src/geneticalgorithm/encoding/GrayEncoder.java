/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.encoding;

/**
 *
 * @author thomasborel
 */
public class GrayEncoder 
{
    public static char[] encode(char[] genesToEncode)
    {
        String stringToEncode = convertGenesToString(genesToEncode);
        
        StringBuilder builder = new StringBuilder(stringToEncode.substring(0, 1));
        
        for(int index = 1; index < stringToEncode.length(); index++)
        {           
            if(stringToEncode.substring(index, index + 1).equals("#"))
            {
                builder.insert(index, "#");
            }
            
            else if(stringToEncode.substring(index - 1, index).equals("#"))
            {
                builder.insert(index, stringToEncode.substring(index, index + 1));
            }
            
            else
            {
                String bitToAdd =  XOR(stringToEncode.substring(index - 1, index), stringToEncode.substring(index, index + 1));
                builder.insert(index, bitToAdd);
            }
        }
        
        return convertGenesToArray(builder.toString());
    }
    
    public static char[] decode(char[] genesToDecode)
    {
        String stringToDecode = convertGenesToString(genesToDecode);
        
        StringBuilder builder = new StringBuilder(stringToDecode.substring(0, 1));
        
        for(int index = 1; index < stringToDecode.length(); index ++)
        {
            if(stringToDecode.substring(index, index + 1).equals("#"))
            {
                builder.insert(index, "#");
            }
            
            else if(builder.substring(index - 1, index).equals("#"))
            {
                builder.insert(index, stringToDecode.substring(index, index + 1));
            }
            
            else
            {
                String bitToAdd = XOR(builder.substring(index - 1, index), stringToDecode.substring(index, index +1));

                builder.insert(index, bitToAdd);
            }
        }
        
        return convertGenesToArray(builder.toString());
    }
    
    private static String XOR(String binaryBit, String grayBit)
    {
        switch(binaryBit + grayBit)
        {
            case "00":
                return "0";
                
            case "01":
                return "1";
                
            case "10":
                return "1";
                
            case "11":
                return "0";
                
            default:
                return "Error";
        }
    }
    
    private static char[] convertGenesToArray(String genes)
    {
        char arrayToReturn[] = new char[genes.length()];
        
        for(int index = 0; index < genes.length(); index++)
        {
            arrayToReturn[index] = genes.charAt(index);
        }
        
        return arrayToReturn;
    }
    
    private static String convertGenesToString(char[] genes)
    {
        String stringToReturn = "";
        
        for(int index = 0; index < genes.length; index ++)
        {
            stringToReturn += genes[index];
        }
        
        return stringToReturn;
    }
}
