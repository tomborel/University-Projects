/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import geneticalgorithm.encoding.GrayEncoder;
import geneticalgorithm.objects.AbstractIndividual;
import geneticalgorithm.objects.Population;

/**
 *
 * @author thomasborel
 */
public class DarwinianAlgorithm extends AbstractAlgorithm
{
    public DarwinianAlgorithm(int numberOfGenerations, int crossoverRate, double mutationRate, double actionMutationRate, int populationSize,
            int tournamentSize, double penaltyThreshold, double penaltyCoefficient, String individualType) {
        
        super(numberOfGenerations, crossoverRate, mutationRate, actionMutationRate, populationSize, tournamentSize,
                penaltyThreshold, penaltyCoefficient, individualType);

    }

    @Override
    public void execute()
    {    
        for (int index = 0; index < numberOfGenerations; index++)
        {
            for (AbstractIndividual individual: population) 
            {
                fitness.calculateFitness(individual);
            }

            while (currentGeneration.size() < population.size())
            {
                AbstractIndividual individualToAdd = selection.tournamentSelection(population, tournamentSize);
                currentGeneration.add(individualToAdd);
            }
            
            instantiateIndividualToKeep();

            for (AbstractIndividual individualToEncode : currentGeneration) 
            {
                char[] decodedGenes = GrayEncoder.encode(individualToEncode.getGenes());
                individualToEncode.setGenes(decodedGenes);
            }

            crossover.uniform(currentGeneration);

            for (AbstractIndividual individualToEncode : currentGeneration) 
            {
                individualToEncode.mutation(mutationRate);
                individualToEncode.actionMutation(actionMutationRate);
                
                char[] encodedGenes = GrayEncoder.decode(individualToEncode.getGenes());
                individualToEncode.setGenes(encodedGenes);
            }

            double averageFitness = currentGeneration.getTotalFitness() / (double) currentGeneration.getPopulationSize();

            System.out.println(averageFitness + "  " + population.getFitest().getFitness());

            if(index == numberOfGenerations - 1)
                System.out.println(currentGeneration.getFitest().toString());
            
            population.clear();
            
            for (AbstractIndividual individual: currentGeneration)
            {
                //fitness.calculateFitness(individual);
                population.add(individual);
            }
            
            population.remove(population.getLeastFit());
            population.add(individualToKeep);
            
            currentGeneration.clear();
        }
    }
}
