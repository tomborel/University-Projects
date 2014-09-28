/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment2;

/**
 *
 * @author Tom
 * A Timer class for measuring time taken for testing purposes
 * the time can be specified as minutes, seconds or milliseconds
 * 
 * Provided by Rong Yang
 */
public class Timer 
{

    private long timer = 0;

    //Default constructor
    public Timer()
    {
    }

    // Start the timer
    public void timeStart() 
    {
        timer = System.currentTimeMillis();
    }

    //Stop the timer, and print the time taken in the format specified
    public void timeStop(String s) 
    {
        timer = System.currentTimeMillis() - timer;
        switch (s) 
        {
            case "ms":
            case "":
                System.out.println("Time taken is " + timer + "  milliseconds");
                break;
            case "sec":
                System.out.println("Time taken is " + timer / 1000 + " seconds");
                break;
            case "min":
                System.out.println("Time taken is " + timer / 60000 + " minutes and "
                        + (timer % 60000) / 1000 + " seconds");
                break;
        }
    }
}
