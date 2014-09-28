/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomasborel
 * 
 * This class contains the mechanism for creating Rainbow tables. 
 * The principle is to compute large chains of passwords, each linking through a reduction function
 * Because the chains are precomputed, and the reduction function always returns the same passwords
 * for a given hash at the same chain position, only the first and last entries of a each chain need
 * to be stored. The passwords within the chains can be retrieved by simply recomputing the chain from 
 * the beginning
 * This class also contains the functionality for searching for passwords in a rainbow table, in addition
 * to reading and writing rainbow tables to and from files
 */
public class RainbowTable implements Serializable
{
    /**
     * reference to an Brute force class hashing object which is needed
     * call the SHA1 hashing function to calculate hash values of passwords
     */
    private BruteForce sha1 = new BruteForce();
    
    // these parameters are instantiated in the constructor
    private long searchSpace;
    private int maxPasswordLength;
    private int chainLength;
    private int numberOfChains;
    private char[] alphabet;
    
    // a random value generator used to create random password strings
    private Random random = new Random();
    
    /**
     * the HashMap object which contains the Rainbow table. this is instantiated
     * in the generateRainbowTable() method
     */
    private HashMap table;
    
    //the path to write the Rainbow table HashMap objects to
    private final String macPath = "ML3(4).ser";

    /**
     * Constructor for generating a RainbowTable object, which allows large tables of passwords to be generated
     * based on applying the SHA1 hashing algorithm to a password, followed by applying a reduction function 
     * the generated hash in order to obtain the next password in the table row, known as a chain
     * 
     * @param maxPasswordLength The maximum password length or any given password covered by the table
     * @param chainLength the length of a chain for a single row of the table, which is equivalent to 
     * the number of times the reduce function is applied per chain
     * @param numberOfChains The number of chains to compute for this table
     * @param alphabet The alphabet which is used to generate passwords for this table. Can either be 
     * a full alphabet of letters and numbers, or just letters and numbers separately
     */
    public RainbowTable(int maxPasswordLength, int chainLength, int numberOfChains, Alphabet alphabet)
    {
        this.alphabet = alphabet.getAlphabet().toCharArray();
        this.searchSpace = calculateSearchSpaceSize(maxPasswordLength, this.alphabet.length);
        this.maxPasswordLength = maxPasswordLength;
        this.chainLength = chainLength;
        this.numberOfChains = numberOfChains;
    }

    /**
     * 
     * @return a HashMap object containing the first and last passwords of each chain in the table
     */
    public HashMap getTable() 
    {
        return table;
    }

    /**
     * 
     * @return the alphabet of digits which a password in this table  may contain 
     */
    public char[] getAlphabet() 
    {
        return alphabet;
    }

    /**
     * 
     * @return the maximum length possible for any given password in this table
     */
    public int getMaxPasswordLength()
    {
        return maxPasswordLength;
    }
    
    /**
     * This algorithm provides the mechanism for searching for passwords within the table
     * The algorithm performs a chain reduce computation, which calculates all of the passwords 
     * of a chain starting from the before last chain position of the table. This is repeated
     * until the password generated is found in the last position of the table.At this point, 
     * the position of the password and chain are saved, and the chain is reduced starting from
     * its first password, until the recorded position is reached, allowing the password to be returned
     * 
     * @param hash The SHA1 hash value of the password to find. 
     * @param passwordToFind if the password is know and we simply wish to find it in the table, we can avoid
     * false positives returned due to collision
     * @param tableToUse
     * @return 
     */
    public String searchForPassword(String hash, String passwordToFind, HashMap tableToUse)
    {   
        int position = chainLength - 1;
        
        String currentPassword = "";
        String password = "";
        String firstInChain = "";
        HashMap<String, String> deletedChains = new HashMap();
        
        boolean foundPassword = false;
        
        while(!foundPassword)
        {
            while(!tableToUse.containsValue(currentPassword) && position >= 0)
            {
                currentPassword = chainReduce(hash, position, chainLength);
                position --;
            }

            firstInChain  = getFirstInChain(currentPassword, tableToUse);

            if(position < 0)
            {
                for(String key: deletedChains.keySet())
                    tableToUse.put(key, deletedChains.get(key));
                
                return "Not Found!";
            }

            password = chainReduce(sha1.applySHA1(firstInChain), 0, position);
            
            if(password.equals(passwordToFind))
                foundPassword = true;
            else
            {
                // if the found password is a false alarm, delete the table from the chain and keep searching
                tableToUse.remove(firstInChain);
                deletedChains.put(firstInChain, currentPassword);
                continue;
            }
            
            //add the deleted chains back into the table for the next search. 
            for(String key: deletedChains.keySet())
                tableToUse.put(key, deletedChains.get(key));
        

        }

        return "Found password : [" + password + "] in chain: [" + getTableRow(firstInChain, table) + "] at position: [" + position + "]";
    }
    
    /**
     * Performs a chain reduction of a hash value, by applying the reduce function 
     * to the value, and in turn applying the SHA1 algorithm to the reduced string. 
     * This is performed for the number of times specified
     * 
     * @param hash The original hash value to be reduced
     * @param position The position of the hash value within the table
     * @param endPosition The end position which represents the point in the table
     * at the reduction process should terminate
     * @return The password which the final hash of the process reduces to
     */
    public String chainReduce(String hash, int position, int endPosition)
    {
        String currentPassword = "";
        
        while(position <= endPosition)
        {
            currentPassword = reduce(hash, position);
            hash = sha1.applySHA1(currentPassword);
            position++;
        }
        
        return currentPassword;
    }
   
    /**
     * Searches through the tables last chain positions in order to return the 
     * corresponding password at the first position of the chain
     * 
     * @param lastInChain The password at the last position in the chain to search for
     * @param table the table HashMap object
     * @return The password at the first position of the correct chain 
     */
    private String getFirstInChain(String lastInChain, HashMap<String, String> table)
    {
        
        for(String key: table.keySet())
        {
            if(table.get(key).equals(lastInChain))
                return key;
        }
        
        return "Not found!";
    }
    
    /**
     * Returns the row number of a specified first password of a chain 
     * 
     * @param keyToFind The first password of the chain to find
     * @param table the table HashMap object
     * @return the chain number of the specified password at the first position
     */
    private int getTableRow(String keyToFind, HashMap<String, String> table)
    {
        int chain = 0;
        
        for(String key: table.keySet())
        {
            chain++;
            
            if(table.get(key).equals(keyToFind))
                break;
        }
        
        return chain;
    }
    
    /*
     * Method for instantiatin the HashMap object which contains the rainbow table
     * The function generates a random String to be used as the first password of a chain
     * This is then hashed and reduced agai for the number of times corresponding to the 
     * chain length specified by the user. 
     * Allows for some chain repetitions and collisions to be avoided by not adding any chains
     * containing first passwords which have already been generated, or last passwords which are
     * already contained in the table. 
     * This method also informs the user of the time taken to generate the table, and it's
     * actual size, which is obtained by multiplying the chain length by the number of rows
     */
    public void generateRainbowTable()
    {
        HashMap<String, String> mapToReturn = new HashMap();
        
        Timer timer = new Timer();
        
        timer.timeStart();
        
        int actualSize = 0;
        
        for(int size = 0; size < numberOfChains; size++)
        {
            String firstPassword = generateRandomString(maxPasswordLength);
            String lastPassword = chainReduce(sha1.applySHA1(firstPassword), 0, chainLength);
            
            if(mapToReturn.containsKey(firstPassword))
                continue;
            
            if(mapToReturn.containsValue(lastPassword))
                continue;
            
            actualSize++;
            
            mapToReturn.put(firstPassword, lastPassword);
        }
        
        timer.timeStop("min");
        
        System.out.println(" Table size: " + actualSize * chainLength);
        
        this.table = mapToReturn;
    }

    /**
     * Reduction function which reduces hash strings to passwords
     * The function has been designed to limit the amount of collisions which
     * can occur within a table. As the chain position is passed in as a parameter, 
     * a same hash will be reduced to different strings at different chain positions
     * @param hash The hash String to reduce
     * @param chainPosition The position of the hash in the chain
     * @return the reduced String
     */
    public String reduce(String hash, int chainPosition)
    {
        //Get a substring of the hash
        hash = hash.substring(0, 15).toLowerCase();
        
        //convert the subtring to an integer contained in a BigInteger object
        BigInteger bigIntFromHash = stringToBigInt(hash, alphabet);
        
        /**
         * Calculate the value to mod the integer by, which is obtained by 
         * adding the search space to the chain position. This will avoid returning
         * passwords which are outside of the search space
        **/
        BigInteger mod = BigInteger.valueOf(searchSpace + chainPosition);
        
        mod = mod.nextProbablePrime();
        
        bigIntFromHash = bigIntFromHash.mod(mod);
        
        //Convert the integer back to a String
        return bigIntToString(bigIntFromHash, alphabet);
    }
    
    /**
     * Converts a String to a BigInteger object. 
     * @param string The String to convert
     * @param alphabet The alphabet of possible digits that the string can contain
     * @return The converted BigInteger value
     */
    public BigInteger stringToBigInt(String string, char[] alphabet)
    {
        int base = alphabet.length;
        BigInteger valueToReturn = new BigInteger("0");
        int stringIndex = string.length();
        int index = 0;
        
        while(stringIndex != 0)
        {
            long valueToAdd = (long) (indexOf(alphabet, string.substring(index, index + 1)) * Math.pow(base, stringIndex - 1));
            valueToReturn = valueToReturn.add(BigInteger.valueOf(valueToAdd));
            index ++;
            stringIndex --;
        }
        
        return valueToReturn;
    }
    
    /**
     * Converts a BigInteger object to a String
     * @param number The BigInteger number to convert
     * @param alphabet The alphabet of digits that the converted String may contain
     * @return the Converted String value
     */
    public String bigIntToString(BigInteger number, char[] alphabet)
    {
        long base = alphabet.length;
        int value;
        String stringToReturn = "";
        
        while(number.intValue() >= 0)
        {
            // Mod by next biggest prime number 
            value = number.mod(BigInteger.valueOf(base)).intValue();
            number = number.divide(BigInteger.valueOf(base));
            stringToReturn = String.valueOf(alphabet[value]) + stringToReturn;
            number = number.subtract(BigInteger.valueOf(1));
        }
        
        return stringToReturn;
    }
    
    /**
     * Calculates the search space size for a given string length and alphabet. This 
     * corresponds to the total number of combinations of digits in the alphabet, of 
     * a length inferior or equal to the maximum length specified 
     * @param maxPasswordLength
     * @param alphabetLength
     * @return 
     */
    private long calculateSearchSpaceSize(int maxPasswordLength, int alphabetLength)
    {
        long value = 0;
        
        while(maxPasswordLength > 0)
        {
            value += (long) Math.pow(alphabetLength, maxPasswordLength);
            maxPasswordLength --;
        }
        
        return value; 
    }
    
    /**
     * Returns the index of a digit in the specified alphabet
     * @param alphabet The alphabet containing the digit to find
     * @param character The character to find
     * @return the index of the character found in the alphabet
     */
    private int indexOf(char[] alphabet, String character)
    {
        int indexToReturn = 0;
        
        for(int index = 0; index < alphabet.length; index ++)
        {
            if(character.equals(String.valueOf(alphabet[index])))
            {
                indexToReturn = index;
                break;
            }
        }
        
        return indexToReturn;
    }
    
    /**
     * Randomly selects digits from the alphabet class variable in order to generate
     * a string
     * 
     * Generates a random String of a the length specified
     * @param stringLength the length of the String to generate
     * @return The generated String
     */
    public String generateRandomString(int stringLength)
    {
        String stringToReturn = "";
        
        for(int index = 0; index < stringLength; index ++)
        {
            int randomInt = random.nextInt(alphabet.length);
            stringToReturn += String.valueOf(alphabet[randomInt]);
        }
        
        return stringToReturn;
    }
    
    /**
     * serializes the table HashMap object in order to save the file locally
     * This allows to save tables which may have taken a long time to compute
     * As the HashMap object implements the Serializable interface, this function simply
     * instantiates a new ObjectOutputStream in order to write the HashMap to a file
     */
    public void writeToFile()
    {
        FileOutputStream fileOut = null;
        
        try 
        {
            fileOut = new FileOutputStream(new File(macPath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(table);
            out.close();
            fileOut.close();
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(RainbowTable.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(RainbowTable.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try
            {
                fileOut.close();
            } 
            catch (IOException ex)
            {
                Logger.getLogger(RainbowTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Reads a table from the specified path, casting it to a HashMap object
     * and assigning it to the table class variable
     * @param pathToRead the path of the table to read
     */
    public void readFromfile(String pathToRead)
    {
        HashMap<String, String> table = null;
        
        try 
        {
            FileInputStream input = new FileInputStream(new File(pathToRead));
            ObjectInputStream objectInput = new ObjectInputStream(input);
            table = (HashMap<String, String>) objectInput.readObject();
            
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(RainbowTable.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(RainbowTable.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(RainbowTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.table = table;
    }  
}
