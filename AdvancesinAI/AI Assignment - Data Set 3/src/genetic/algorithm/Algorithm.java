/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.algorithm;


/**
 *
 * @author thomasborel
 */
public class Algorithm 
{
    private double mutationRate;
    private double lengthMutation;
    private int numberOfGenerations = 0;
    private int individualsToAdd;
    private int tournamentSize;
    private String selectionType;
    private String crossoverType;
    private Population population;
    private SelectionMethod selection;
    private CrossoverMethod crossover;
    private float range;
    

    public Algorithm(int populationSize, int numberOfGenerations, int crossoverRate, double mutationRate,double lengthMutation,
                    int individualsToAdd, String selectionType, int tournamentSize, String crossoverType, float range) 
    {
       
       this.numberOfGenerations = numberOfGenerations;
       this.mutationRate = mutationRate;
       this.individualsToAdd = individualsToAdd;
       this.selectionType = selectionType;
       this.tournamentSize = tournamentSize;
       this.crossoverType = crossoverType;
       this.range = range;
       this.lengthMutation = lengthMutation;
      
       population = new Population(populationSize);
       crossover = new CrossoverMethod(crossoverRate);
       selection = new SelectionMethod();
    }
    
        
    public void execute()
    {
        Population currentGeneration = new Population();
        int generationCounter = 1;
        String message = "";
            
        //For each generation   
        for(int generation = 0; generation < numberOfGenerations; generation++)
        {
            for(Individual individual: population)
            {
                individual.fitnessFunction();
            }
            
            for(int index = 0; index < individualsToAdd; index++)
                currentGeneration.add(selection.tournamentSelection(population, tournamentSize));

            
            
            Individual individualToKeep = new Individual();
            Individual fittest = population.getFitest();
            
            individualToKeep.setRules(fittest.getRules());
            individualToKeep.setFitness(fittest.getFitness());
            individualToKeep.setLength(fittest.getLength());
            
            System.arraycopy(fittest.getLowerValues(), 0, individualToKeep.getLowerValues(), 0, fittest.getLowerValues().length);
            System.arraycopy(fittest.getUperValues(), 0, individualToKeep.getUperValues(), 0, fittest.getUperValues().length);
            
            //run crossover
            currentGeneration = copyIndividuals(crossover.uniform(currentGeneration));

            //run mutation
            for(Individual individual: currentGeneration)
            {
                individual.mutation(mutationRate, range); 
                individual.lengthMutation(lengthMutation);
            }
            
     
            //always keep the fitest individual
            currentGeneration.set(0, individualToKeep);
            
            
            //Output some stats each generation
            System.out.print(generation + " ");
            System.out.print(currentGeneration.getFitest().getFitness()+ " ");
            System.out.print((double)currentGeneration.getTotalFitness()/(double)currentGeneration.size() + " ");
            System.out.print(currentGeneration.getFitest().getLength() + " ");
            System.out.println(currentGeneration.getFitest().testError());
            
            
            //IF the termination criteria is satisfied, print the rules in a human readable format
            if(generation == numberOfGenerations - 1)
            {
                System.out.println(individualToKeep.toString());
                System.out.println("Errors: " + individualToKeep.testError());
            }

            
            //the children are now used as the parents in the next generation
            population.clear();
            
            for(int index = 0; index < currentGeneration.size(); index++)
                population.add(currentGeneration.get(index));
            
            currentGeneration.clear();
        }
        
        System.out.println(message);
    }

    public Population getPopulation() 
    {
        return population;
    }
    
    private Population copyIndividuals(Population individualsToCopy)
    {
        Population tempPopulation = new Population();
        
        for(Individual individualToCopy: individualsToCopy)
        {
            tempPopulation.add(individualToCopy);
        }
        
        return tempPopulation;
    }
}
