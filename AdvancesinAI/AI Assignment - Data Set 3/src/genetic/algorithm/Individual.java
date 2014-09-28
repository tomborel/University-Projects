/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomasborel
 */
public class Individual 
{
    private int numberOfGenes = 224; //Max length 20
    private int ruleLength = 7;
    private float lowerValues[] = new float[numberOfGenes];
    private float uperValues[] = new float[numberOfGenes];
    private Random random = new Random();
    private int fitness;
    private int length = generateLength();
    private String path = "data2.txt";
    private ArrayList<String> rules = scanRuleSfromFile();
    private ArrayList<String> testRules;
    private ArrayList<String> trainingRules;
    
    public Individual() 
    {    
    }

    public ArrayList<String> getRules()
     {
        return rules;
    } 

    public void setRules(ArrayList<String> rules)
    {
        this.rules = rules;
    }

    public float[] getLowerValues() 
    {
        return lowerValues;
    }

    public float[] getUperValues() 
    {
        return uperValues;
    } 

    public void setFitness(int fitness)
    {
        this.fitness = fitness;
    }
    
    public int getLength() 
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public int getRuleLength()
    {
        return ruleLength;
    }

    public int getNumberOfGenes() 
    {
        return numberOfGenes;
    }

    public void fitnessFunction() 
    {
        fitness = 0;
       
        //Loop over each data entry
        for(int ruleIndex = 0; ruleIndex < rules.size(); ruleIndex ++)
        {
            String rule = rules.get(ruleIndex);
            String[] values = rule.split(" ");
            
            //Loop over each rule
            for(int index = 0; index < length * getRuleLength(); index = index + getRuleLength())
            {
                boolean validRule = true;

                float[] uperRuleValues  = new float[getRuleLength()];
                float [] lowerRuleValues   = new float[getRuleLength()];
                
                System.arraycopy(uperValues, index, uperRuleValues, 0, getRuleLength());
                System.arraycopy(lowerValues, index, lowerRuleValues, 0, getRuleLength());
                
                for(int bitIndex = 0; bitIndex < values.length - 1; bitIndex ++)
                {
                    float ruleValue = Float.valueOf(values[bitIndex]);
                    
                    if(ruleValue < lowerRuleValues[bitIndex] || ruleValue > uperRuleValues[bitIndex])
                    {
                        //The rule does not match the data entry
                        validRule = false;
                        break;
                    }              
                }
                
                //Continue to the next rule
                if(!validRule)
                    continue;
                
                //Compare action bits, if they match increment fitness, otherwise move on to the next data entry
                if((lowerRuleValues[lowerRuleValues.length - 1]) == Float.valueOf(values[values.length - 1]))
                { 
                    fitness++;
                    break;
                }
                
            }
            
        }
    }
    
    public int getFitness()
    {
        return fitness;
    }
      
    public void generateRandomGenes()
    {
        Random randomGenerator = new Random();
        int counter = 1;
        
        for(int index = 0; index < lowerValues.length; index ++)
        {
            //action bits can't be generalised
            if(counter == getRuleLength())
            {
                uperValues[index] = randomGenerator.nextInt(2);
                lowerValues[index] = uperValues[index];
                counter = 1;
                continue;
            }
            
            float valueOne = random.nextFloat();
            float valueTwo = random.nextFloat();
            
            //make sure the boundaries are consistant 
            if(valueOne>= valueTwo)
            {
                uperValues[index] = valueOne;
                lowerValues[index] = valueTwo;
            }
            else
            {
                uperValues[index] = valueTwo;
                lowerValues[index] = valueOne;
            }
            
            counter++;
        }
    }
    
    private int generateLength()
    {
        int lengthToReturn = 0;
        
        while(lengthToReturn < 7)
        {
            lengthToReturn = random.nextInt(numberOfGenes/ruleLength);
        }
        
        return lengthToReturn;
    }

    public void mutation(double mutationRate, float range)
    {
        int counter = 1;
        float value = ((float)random.nextDouble() * range);
        
        for(int index = 0; index < uperValues.length; index ++)
        {
            if(counter == getRuleLength())
            {
                counter = 1;
                continue;
            }
            
            counter++;
        
            //if the probability is not satisfied, return 
            if(random.nextDouble() > mutationRate)
                continue;
            
            //randomly add or subtract a value to either an upper or lower boundary
            int randomInt = random.nextInt(4);

            if(randomInt == 0 && lowerValues[index] - value > 0)
                lowerValues[index] = lowerValues[index] - value;
            
            if(randomInt == 1 && lowerValues[index] + value < 1 && lowerValues[index] + value < uperValues[index])
                lowerValues[index] = lowerValues[index] + value;
            
            if(randomInt == 2 && uperValues[index] - value > 0 && uperValues[index] - value > lowerValues[index])
                uperValues[index] = uperValues[index] - value;
            
            if(randomInt == 3 && uperValues[index] + value < 1)
                uperValues[index] = uperValues[index] + value;

    }
}
    //mutate the length of the individual according to it's own porbability
    public void lengthMutation(double mutationRate)
    {
        if(random.nextDouble() <= mutationRate)
        {
            int rand = random.nextInt(2);
            
            if(rand == 0 && length > 8)
                length--;
            if(rand == 1 && length < numberOfGenes / ruleLength)
                length++;
        }
    }
    
    //Print the rules in a human readable format
    @Override
    public String toString()
    {
        String toString = "";
        
        for(int index = 0; index < lowerValues.length; index++)
        {
            toString += "( " + String.valueOf(lowerValues[index]) + ", " + String.valueOf(uperValues[index]) + ")   " + "\n";
        }
        return toString;
    }
    
    public int testError()
    {
        int errors = 0;
                
        //Loop over each rule in the individual
        for(int index = 0; index < length * getRuleLength(); index = index + getRuleLength())
        {
            //Loop over each line of the data set
            for(int ruleIndex = 0; ruleIndex < rules.size(); ruleIndex ++)
            {
                String rule = rules.get(ruleIndex);
                String[] values = rule.split(" ");

                    boolean validRule = true;

                    float[] uperRuleValues  = new float[getRuleLength()];
                    float [] lowerRuleValues   = new float[getRuleLength()];

                    System.arraycopy(uperValues, index, uperRuleValues, 0, getRuleLength());
                    System.arraycopy(lowerValues, index, lowerRuleValues, 0, getRuleLength());

                    for(int bitIndex = 0; bitIndex < values.length - 1; bitIndex ++)
                    {
                        float ruleValue = Float.valueOf(values[bitIndex]);

                        if(ruleValue < lowerRuleValues[bitIndex] || ruleValue > uperRuleValues[bitIndex])
                        {
                            validRule = false;
                            break;
                        }              
                    }

                    if(!validRule)
                        continue;

                    //if the data entry has been matched but the action bit is incorrect, the rule has an error
                    if((lowerRuleValues[lowerRuleValues.length - 1]) != Float.valueOf(values[values.length - 1]))
                    { 
                        errors++;
                        break;
                    }

                }
        }
        
        return errors;
    }
    
    //Method for reading the data entry form a file
    private ArrayList<String> scanRuleSfromFile()
    {
        trainingRules = new ArrayList<String>();
        testRules = new ArrayList<String>();
        
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader("data3.txt"));
            String line = reader.readLine();
            
            while(line != null)
            {
                testRules.add(line);
                line = reader.readLine();
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Individual.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Halve data into separate training and testing data
        int median = testRules.size() / 2; 
       
        ArrayList selectedIndexes = new ArrayList();
        
        while(trainingRules.size() < median)
        {
            int index = random.nextInt(median);

            while(selectedIndexes.contains(index))
                index = random.nextInt(median);
            
            selectedIndexes.add(index);
            trainingRules.add(testRules.get(index));
           testRules.remove(index);     
        }

        return trainingRules;
    }
}

