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
public class SelectionMethod 
{
    private Random generator = new Random();

    public SelectionMethod() 
    {
    }
    
    
    public Individual tournamentSelection(Population population, int tournamentSize)
    {
        Population tempPopulation = new Population();
        
        //randomly add the individuals to the tournament
        for(int index = 0; index < tournamentSize; index++)
        {
            int individualIndex = generator.nextInt(population.getPopulationSize());
            tempPopulation.add(population.get(individualIndex));
        }

        //select the fittest of the tournament
        return tempPopulation.getFitestAndShortest();
    }
}
