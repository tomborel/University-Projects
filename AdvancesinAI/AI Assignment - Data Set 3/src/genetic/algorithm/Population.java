/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.algorithm;

import java.util.ArrayList;

/**
 *
 * @author thomasborel
 */
public class Population extends ArrayList<Individual>
{
    private int populationSize;

    public Population(int populationSize) 
    {
        this.populationSize = populationSize;
        
        for(int i = 0; i < populationSize; i++)
        {
            Individual individual = new Individual();
            individual.generateRandomGenes();
            this.add(individual);
        }
    }
    
    public Population() 
    {
    }
    
    public int getTotalFitness()
    {
        int fitnessToReturn = 0;
        
            for(Individual individual : this)
            {
                fitnessToReturn += individual.getFitness();
            }
        
        return fitnessToReturn;
    }
    
    public Individual getFitest()
    {
        Individual individualToReturn = this.get(0);
        
        for(Individual individual: this)
        {
            if(individual.getFitness()> individualToReturn.getFitness())
                individualToReturn = individual;
        }
        
        return individualToReturn;
    }
    
    public Individual getShortest()
    {
        Individual individualToReturn = this.get(0);
        
        for(Individual individual: this)
        {
            if(individual.getLength() < individualToReturn.getLength())
                individualToReturn = individual;
        }
        
        return individualToReturn;
    }
    
    public Individual getFitestAndShortest()
    {
        int highestFitness = this.getFitest().getFitness();
        
        Population populationOfFittest = new Population();
        
        for(Individual individual: this)
        {
            if(individual.getFitness() == highestFitness)
                populationOfFittest.add(individual);
        }
        
        return populationOfFittest.getShortest();
        
    }

    public int getPopulationSize() 
    {
        return populationSize;
    } 
    
    @Override
    public boolean add(Individual e) 
    {
        add(0, e);
        
        return true;
    }
}
