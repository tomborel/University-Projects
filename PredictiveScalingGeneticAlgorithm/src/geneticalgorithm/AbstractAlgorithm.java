/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import geneticalgorithm.configuration.Configuration;
import geneticalgorithm.crossover.CrossoverMethod;
import geneticalgorithm.encoding.GrayEncoder;
import geneticalgorithm.fitness.FitnessCalculator;
import geneticalgorithm.objects.AbstractIndividual;
import geneticalgorithm.objects.BaldwinianPittsburghIndividual;
import geneticalgorithm.objects.MichiganIndividual;
import geneticalgorithm.objects.PittsburghIndividual;
import geneticalgorithm.objects.Population;
import geneticalgorithm.selection.SelectionMethod;
import java.util.Random;

/**
 *
 * @author thomasborel
 */
public abstract class AbstractAlgorithm 
{
    protected int numberOfGenerations;
    protected int populationSize;
    protected double mutationRate;
    protected double actionMutationRate;
    protected int tournamentSize;
    protected Configuration configuration;
    protected Population population;
    protected CrossoverMethod crossover;
    protected FitnessCalculator fitness;
    protected AbstractIndividual individualToKeep;
    protected SelectionMethod selection;
    protected double penaltyThreshold;
    protected double penaltyCoefficient;
    protected String individualType;  
    protected Random random = new Random();
    protected int numberOfRules = 6;
    Population currentGeneration = new Population();

    public AbstractAlgorithm (int numberOfGenerations, int crossoverRate, double mutationRate, double actionMutationRate, int populationSize,
            int tournamentSize, double penaltyThreshold, double penaltyCoefficient, String individualType) 
    {
        this.numberOfGenerations = numberOfGenerations;
        this.mutationRate = mutationRate;
        this.tournamentSize = tournamentSize;
        this.penaltyThreshold = penaltyThreshold;
        this.penaltyCoefficient = penaltyCoefficient;
        this.actionMutationRate = actionMutationRate;
        this.individualType = individualType;
        this.populationSize = populationSize;

        selection = new SelectionMethod();
        configuration = new Configuration();
        
        if (individualType.equals("Michigan"))
            population = new Population(populationSize);
        else if(individualType.equals("Pittsburgh"))
            population = new Population(populationSize, numberOfRules);
        else if(individualType.equals("Baldwinian"))
            population = new Population(populationSize, numberOfRules, 100);


        crossover = new CrossoverMethod(crossoverRate);
        fitness = new FitnessCalculator(configuration, penaltyThreshold, penaltyCoefficient);

    }
    
    
    public abstract void execute();
    
    
    public Population getPopulation()
    {
        return population;
    }

    public double getPenaltyThreshold()
    {
        return penaltyThreshold;
    }
    
    public void localSearch(AbstractIndividual individual)
    {
        if(individual instanceof PittsburghIndividual)
            localSearchPittsburgh((PittsburghIndividual)individual);
        else
            localSearchMichigan((MichiganIndividual)individual);
    }
        protected void instantiateIndividualToKeep()
    {
        switch (individualType) 
        {
                case "Pittsburgh":
                    individualToKeep = new PittsburghIndividual(population.getFitest().getGenes(), numberOfRules);
                    break;
                case "Michigan":
                    individualToKeep = new MichiganIndividual(population.getFitest().getGenes());
                    break;
                case "Baldwinian":
                    individualToKeep = new BaldwinianPittsburghIndividual(population.getFitest().getGenes(), numberOfRules);
                    int plasticity = ((BaldwinianPittsburghIndividual)population.getFitest()).getPlasticity();
                    ((BaldwinianPittsburghIndividual)individualToKeep).setPlasticity(plasticity);
                    break;
        }
            
            individualToKeep.setFitness(population.getFitest().getFitness());
            individualToKeep.setConstraintViolations(population.getFitest().getConstraintViolations());
    }
    //make private 
    private void localSearchPittsburgh(PittsburghIndividual individual)
    {
        double originalFitness = individual.getFitness();
        final char[] originalGenes = individual.getGenes();
        int count = 0;
        int ruleLength = ((PittsburghIndividual)individual).getRuleLength();

        for(int index = 0; index < individual.getGenes().length; index++)
        {
            count ++;
            
            if(count == ruleLength - 1)
                continue;
            
            if(count == ruleLength)
            {
                count = 0;
                continue;
            }
            
            char[] modifiedGenes = flipSingleBit(individual.getGenes(), index);

            individual.setGenes(modifiedGenes);

            fitness.calculateFitness(individual);

            if(individual.getFitness() > originalFitness)
            {
               individual.setGenes(modifiedGenes);
               fitness.calculateFitness(individual);
               break;
            }
            else
            {
                individual.setGenes(originalGenes);
                individual.setFitness(originalFitness);
            }
        }
    }
    
    private void localSearchMichigan(MichiganIndividual individual)
    {
        double originalFitness = individual.getFitness();
        
        for(int index = 0; index < individual.getGenes().length - 2; index ++)
        {
            char bit = individual.getGenes()[index];
            char[] modifiedGenes = flipSingleBit(GrayEncoder.encode(individual.getGenes()), index);
            individual.setGenes(modifiedGenes);
            
            fitness.calculateFitness(individual);
            
            if(individual.getFitness() > originalFitness)
            {
               individual.setGenes(GrayEncoder.decode(modifiedGenes));
               break;
            }
            else
            {
                modifiedGenes[index] = bit;
                individual.setGenes(GrayEncoder.decode(modifiedGenes));
                individual.setFitness(originalFitness);
            }
        }
        
    }
    
    private char[] flipSingleBit(char[] genesToModify, int index)
    {
        int rand = random.nextInt(2);
        
        switch(genesToModify[index])
        {
            case '0':
            {
                if(rand == 0)
                    genesToModify[index] = '1';
                else
                    genesToModify[index] = '#';
                break;
            }
            case '1':
            {
                if(rand == 0)
                    genesToModify[index] = '0';
                else
                    genesToModify[index] = '#';
                break;
            }
            case '#':
            {
                if(rand == 0)
                    genesToModify[index] = '0';
                else
                    genesToModify[index] = '1';
                break;
            }
        }  
            
        return genesToModify;
    }
    
    public void reset()
    {
        switch (individualType) {
            case "Michigan":
                population = new Population(populationSize);
                break;
            case "Pittsburgh":
                population = new Population(populationSize, numberOfRules);
                break;
            case "Baldwinian":
                population = new Population(populationSize, numberOfRules, 100);
                break;
        }
    }
    
}
