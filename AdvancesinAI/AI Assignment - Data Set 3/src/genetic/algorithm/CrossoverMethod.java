/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.algorithm;

import java.util.Random;

/**
 *
 * @author thomasborel
 */
public class CrossoverMethod 
{
    private Random generator = new Random();
    private int crossoverRate;

    public CrossoverMethod(int crossoverRate) 
    {
        this.crossoverRate = crossoverRate;
    }
    
    public Population uniform(Population individuals)
    {
        for(int index = 0; index < individuals.size(); index = index + 2)
        {
            Individual firstIndividual = individuals.get(index);
            Individual secondIndividual = individuals.get(index + 1);
            
            Individual individualToCopy;
            
            int length;
            
            //The children keep the length of the shortest parent
            if(firstIndividual.getLength() < secondIndividual.getLength())
            {
               length = firstIndividual.getLength();
               individualToCopy = firstIndividual;
            }
            else
            {
                length = secondIndividual.getLength();
                individualToCopy = secondIndividual;
            }
           
            for(int i = 0; i <firstIndividual.getLowerValues().length; i++)
            {
                int random = generator.nextInt(100);

                //each gene has a separate probabilty of crossing over
                if(random <= crossoverRate)
                {
                    float firstLower = firstIndividual.getLowerValues()[i];
                    float firstUper = firstIndividual.getUperValues()[i];
                    
                    float secondLower = secondIndividual.getLowerValues()[i];
                    float secondUper = secondIndividual.getUperValues()[i];
                    
                    firstIndividual.getLowerValues()[i] = secondLower;
                    firstIndividual.getUperValues()[i] = secondUper;
                    
                    secondIndividual.getLowerValues()[i] = firstLower;
                    secondIndividual.getUperValues()[i] = firstUper;

                }
            }
            
            firstIndividual.setLength(length);
            secondIndividual.setLength(length);
            
            for(int i = length * individualToCopy.getRuleLength(); i < individualToCopy.getNumberOfGenes(); i ++)
            {
                firstIndividual.getLowerValues()[i] = individualToCopy.getLowerValues()[i];
                firstIndividual.getUperValues()[i] = individualToCopy.getUperValues()[i];
                secondIndividual.getLowerValues()[i] = individualToCopy.getLowerValues()[i];
                secondIndividual.getUperValues()[i] = individualToCopy.getUperValues()[i];
            }

        }
        return individuals;
    }
}

