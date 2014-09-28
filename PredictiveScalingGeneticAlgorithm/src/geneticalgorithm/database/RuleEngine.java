/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.database;

import geneticalgorithm.mappers.RuleMapper;
import geneticalgorithm.objects.AbstractIndividual;
import geneticalgorithm.objects.BaldwinianPittsburghIndividual;

/**
 *
 * @author Tom
 */
public class RuleEngine
{
    private RuleMapper mapper = new RuleMapper();
    
    public String readRule(String input)
    {
        return mapper.selectQuery(input);
    }
    
    public void deleteRuleBase()
    {
        mapper.deleteQuery();
    }
    
    public void writeRules(AbstractIndividual individual)
    {
        if(mapper.selectCount() > 0)
            mapper.deleteQuery();
        
        BaldwinianPittsburghIndividual individualToAdd = (BaldwinianPittsburghIndividual) individual;
        
        int index = 0;
        
        while(index < individual.getGenes().length)
        {
            int ruleIndex = 0;
            String ruleToAdd = "";
            
            while(ruleIndex < individualToAdd.getRuleLength())
            {
                ruleToAdd += individualToAdd.getGenes()[index];
                index++;
                ruleIndex++;
                
                if(ruleIndex == individualToAdd.getRuleLength() - 2)
                    ruleToAdd += " : ";
            }
            
            mapper.insertQuery(ruleToAdd);
        }
    }
}
