/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.crossover;

/**
 *
 * @author thomasborel
 */
public enum CrossoverType 
{
    SinglePoint(0),
    Uniform(1);
    
    private int selectionIndex;

    private CrossoverType(int selectionIndex) 
    {
        this.selectionIndex = selectionIndex;
    }
}
