/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.encoding;

import geneticalgorithm.exceptiontype.ExceptionType;

/**
 *
 * @author thomasborel
 */
public class ConstraintEncoder 
{
    private String requestsPerSecond;
    private String hourOfDay;
    private String exceptionCounter;
    private int exceptionType; 
    private String cpu;
    private String ram;
    private String diskWrite;

    public ConstraintEncoder(int requestsPerSecond, int hourOfDay, int exceptionCounter,
                            int exceptionType, int cpu, int ram, int diskWrite) 
    {
        this.requestsPerSecond = Integer.toBinaryString(requestsPerSecond);
        this.hourOfDay = Integer.toBinaryString(hourOfDay);
        this.exceptionCounter = Integer.toBinaryString(exceptionCounter);
        this.exceptionType = exceptionType;
        this.cpu = Integer.toBinaryString(cpu);
        this.ram = Integer.toBinaryString(ram);
        this.diskWrite = Integer.toBinaryString(diskWrite);
    }
    
    //Minimal constructor for demo purposes
    public ConstraintEncoder(int requestsPerSecond, int hourOfDay, int cpu) 
    {
        this.requestsPerSecond = Integer.toBinaryString(requestsPerSecond);
        this.hourOfDay = Integer.toBinaryString(hourOfDay);
        this.cpu = Integer.toBinaryString(cpu);
    }
    
    public String encode(String action)
    {       
        String stringToReturn = align(requestsPerSecond, 7) + 
                                align(hourOfDay, 5) +
                                //align(exceptionCounter, 7) +
                                //ExceptionType.getExceptionTypeFromIndex(exceptionType).toString() +
                                align(cpu, 4);
                                //align(ram, 4) +
                                //align(diskWrite, 4);
        
        //stringToReturn = GrayEncoder.encode(stringToReturn);
        
        switch(action)
        {
            case "Add":
                return stringToReturn += "10";
                
            case "Delete":
                return stringToReturn += "01";
                
            case "Nothing":
                return stringToReturn += "00";
                
            default:
                return "Error";
        }
    }
    
    public static String decode(String stringToDecode)
    {
        String requests = stringToDecode.substring(0, 7).replaceAll("#", "");
        String time = stringToDecode.substring(7, 12).replaceAll("#", "");
        String exceptions = stringToDecode.substring(12, 19).replaceAll("#", "");
        ExceptionType type = ExceptionType.getExceptionTypeFromEncoding(stringToDecode.substring(19, 23).replaceAll("#", ""));
        String cpuLevel = stringToDecode.substring(23, 27).replaceAll("#", "");
        String ramLevel = stringToDecode.substring(27, 31).replaceAll("#", "");
        String diskLevel = stringToDecode.substring(31, 35).replaceAll("#", "");
        String action = stringToDecode.substring(35, 37);
        
        String stringToreturn = "Requests per second: " + Integer.parseInt(requests, 2) + "\n" +
                                "Time: " + Integer.parseInt(time, 2) + "\n" +
                                "Exceptions: " + Integer.parseInt(exceptions, 2) + "\n" +
                                "Exception type: " + type + "\n" +
                                "CPU: " + Integer.parseInt(cpuLevel, 2) + 0 +  "\n" +
                                "RAM: " + Integer.parseInt(ramLevel, 2) + 0 +  "\n" +
                                "Disk: " + Integer.parseInt(diskLevel, 2) + 0 +  "\n" +
                                "Action: ";
        
        switch(action)
        {
            case "01":
                stringToreturn += "Delete";
                break;
                
            case "10":
                stringToreturn += "Add";
                break;
                
            case "00":
                stringToreturn += "Nothing";
                break;
                
            case "11":
                stringToreturn += "Impossible";
                break;
        }
                
        return stringToreturn;                     
    }
    
    String align(String string, int length)
    {
        if(string.isEmpty())
            return "";
        
        while(string.length() != length)
        {
            string += "0";
        }
        
        return string;
    }
    
}
