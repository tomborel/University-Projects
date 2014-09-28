/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.fitness;

import geneticalgorithm.configuration.Configuration;
import geneticalgorithm.objects.AbstractIndividual;
import geneticalgorithm.objects.MichiganIndividual;
import geneticalgorithm.objects.PittsburghIndividual;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author thomasborel
 */
public class FitnessCalculator 
{ 

    private Configuration configuration;
    private double penaltyThreshold;
    private double penaltyCoefficient;
    String[] objectives = scanRuleSfromFile();
    
    public FitnessCalculator(Configuration configuration, double penaltyThreshold, double penaltyCoefficient) 
    {
        this.configuration = configuration;
        this.penaltyThreshold = penaltyThreshold;
        this.penaltyCoefficient = penaltyCoefficient;
    }

    public double getPenaltyCoefficient() 
    {
        return penaltyCoefficient;
    }

    public void setPenaltyCoefficient(int penaltyCoefficient) 
    {
        this.penaltyCoefficient = penaltyCoefficient;
    }
    
    public void calculateFitness(AbstractIndividual individual)
    {
        if(individual instanceof MichiganIndividual)
            calculateMichiganFitness((MichiganIndividual)individual);
        else if(individual instanceof PittsburghIndividual)
            calculatePittsburghFitness((PittsburghIndividual) individual);
    }
    
    private void calculateMichiganFitness(MichiganIndividual individual)
    {
        double fitness = 0;
        
        for(String objective: configuration.getObjectives())
        {
            char[] objectiveBits = objective.toCharArray();
            char[] individualGenes = individual.getGenes();
            int length = individualGenes.length;
            
            if(!compareIndividuals(0, objectiveBits.length - 2, objectiveBits, individualGenes))
                continue;

            if(individualGenes[length - 2] == '1' && individualGenes[length - 1] == '1')
                continue;

            if(individualGenes[length - 2] == objectiveBits[length - 2] && individualGenes[length - 1] == objectiveBits[length - 1])
            {
                fitness++;
                break;
            }
        }
        
    }
    
    private void calculatePittsburghFitness(PittsburghIndividual individual)
    {
        int fitness = 0;
       
        for(String objective: configuration.getObjectives())
        {
            char[] objectiveBits = objective.toCharArray();
            boolean isEqual = true;
            
            for(int geneIndex = 0; geneIndex < individual.getGenes().length; geneIndex = geneIndex + individual.getRuleLength())
            {
                int length = individual.getRuleLength();
                char[] individualGenes = new char[length];
                System.arraycopy(individual.getGenes(), geneIndex, individualGenes, 0, individual.getRuleLength());
                
                if(!compareIndividuals(0, objectiveBits.length - 2, objectiveBits, individualGenes))
                    continue;
                
                if(individualGenes[length - 2] == '1' && individualGenes[length - 1] == '1')
                    continue;
                
                if(individualGenes[length - 2] == objectiveBits[length - 2] && individualGenes[length - 1] == objectiveBits[length - 1])
                {
                    fitness++;
                    break;
                }
            }
        }
        
        individual.setFitness(fitness);
    }
    
    private int calculateHammingDistance(String firstString, String secondString, int startIndex, int endIndex)
    {
        if(firstString.length() != secondString.length())
            return Integer.MAX_VALUE;
        
        int hammingDistance = 0;
        
        for(int index = startIndex; index < endIndex; index ++)
        {
            if(firstString.substring(index, index + 1).equals("#") || secondString.substring(index, index + 1).equals("#"))
                continue;
            
            if(!firstString.substring(index, index + 1).equals(secondString.substring(index, index + 1)))
                hammingDistance ++;
        }
        
        return hammingDistance;
    }
    
//    private void deathPenalty(AbstractIndividual individual)
//    {
//        for(String timeConstraint: constraints.getTimeConstraints())
//        {
//            if(individual.getGenes().substring(7, 12).equals(timeConstraint))
//                individual.setFitness(individual.getFitness() - 100);
//        }
//        
//        for(String resourceConstraint : constraints.getResourceConstraints())
//        {
//            if(individual.getGenes().substring(23, 27).equals(resourceConstraint))
//                individual.setFitness(individual.getFitness() - 100);
//            
//            if(individual.getGenes().substring(27, 31).equals(resourceConstraint))
//                individual.setFitness(individual.getFitness() - 100);
//            
//            if(individual.getGenes().substring(31, 35).equals(resourceConstraint))
//                individual.setFitness(individual.getFitness() - 100);
//        }
//        
//        if(individual.getGenes().substring(35, 37).equals("11"))
//            individual.setFitness(individual.getFitness() - 100);
//    }
    
    private boolean compareIndividuals(int startIndex, int endIndex, char[] individualOne, char[] individualTwo)
    {
        for(int index = startIndex; index <= endIndex; index ++)
        {
            if(individualOne[index] == '#')
                continue;
            
            if(individualTwo[index] == '#')
                continue;
            
            if(individualOne[index] != individualTwo[index])
                return false;
        }
        
        return true;
    }
    
    private String[] scanRuleSfromFile()
    {
        String[] testRules = new String[64];
        
        try 
        {
            Scanner fileScanner = new Scanner(new File("data2.txt"));
            
            int index = 0;
            
            while(fileScanner.hasNext())
            {
                testRules[index] = fileScanner.nextLine().replaceAll(" ", "");
                index ++;
            }
        } 
        catch (FileNotFoundException ex) 
        {

        }
        
//        int median = testRules.size()/2; 
//       
//        ArrayList trainingRules = new ArrayList<String>();
//        ArrayList selectedIndexes = new ArrayList();
//        
//        while(trainingRules.size() < median)
//        {
//            int index = random.nextInt(median);
//
//            while(selectedIndexes.contains(index))
//                index = random.nextInt(median);
//            
//            selectedIndexes.add(index);
//            trainingRules.add(testRules.get(index));
//            testRules.remove(index);
//            
//        }

        return testRules;
    }
}
