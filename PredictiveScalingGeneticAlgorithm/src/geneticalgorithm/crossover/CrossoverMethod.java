/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.crossover;

import geneticalgorithm.objects.AbstractIndividual;
import geneticalgorithm.objects.BaldwinianPittsburghIndividual;
import geneticalgorithm.objects.Population;
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
    
    public Population singlePoint(Population individuals)
    {
        for(int index = 0; index < individuals.size(); index = index + 2)
        {
            if(generator.nextInt(100) >= crossoverRate)
                continue;

            AbstractIndividual individualOne = individuals.get(index);
            AbstractIndividual individualTwo = individuals.get(index + 1);
            
            int pointOfCrossover = generator.nextInt(individualOne.getGenes().length);
            
            for(int i = pointOfCrossover; i < individualOne.getGenes().length; i++)
            {              
                    char tempGene = individualOne.getGenes()[i];
                    individualOne.getGenes()[i] = individualTwo.getGenes()[i];
                    individualTwo.getGenes()[i] = tempGene;
             }
        }
        
        return individuals;
    }
    
    public void uniform(Population individuals)
    {
        for(int index = 0; index < individuals.size(); index = index + 2)
        {
            AbstractIndividual individualOne = individuals.get(index);
            AbstractIndividual individualTwo = individuals.get(index + 1);
            
            for(int i = 0; i < individualOne.getGenes().length; i++)
            {              
                int random = generator.nextInt(100);

                if(random <= crossoverRate)
                {
                    char tempGene = individualOne.getGenes()[i];
                    individualOne.getGenes()[i] = individualTwo.getGenes()[i];
                    individualTwo.getGenes()[i] = tempGene;
                }    
             }
        }
    }
    
    public void baldwinianUniform(Population individuals)
    {
        for(int index = 0; index < individuals.size(); index = index + 2)
        {
            BaldwinianPittsburghIndividual individualOne = (BaldwinianPittsburghIndividual)individuals.get(index);
            BaldwinianPittsburghIndividual individualTwo = (BaldwinianPittsburghIndividual)individuals.get(index);
            
            
            for(int i = 0; i < individualOne.getGenes().length; i++)
            {              
                int random = generator.nextInt(100);

                if(random <= crossoverRate)
                {
                    char tempGene = individualOne.getGenes()[i];
                    individualOne.getGenes()[i] = individualTwo.getGenes()[i];
                    individualTwo.getGenes()[i] = tempGene;
                }    
             }
            
            if(individualOne.getPlasticity() > individualTwo.getPlasticity())
                individualTwo.setPlasticity(individualOne.getPlasticity());
            else
                individualOne.setPlasticity(individualTwo.getPlasticity());
            
        }
        
    }
    
//    
//    private Population crossover(int beginningIndex, int endIndex)
//    {
//        Population populationToReturn = new Population()
//    }
}

