/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.exceptiontype;

/**
 *
 * @author thomasborel
 */
public enum ExceptionType 
{
    ArithmeticException(0){@Override public String toString(){return "0000";}},
    ArrayIndexOutOfBoundsException(1){@Override public String toString(){return "0001";}},
    ArrayStoreException(2){@Override public String toString(){return "0011";}},
    ClassCastException(3){@Override public String toString(){return "0010";}},
    IllegalArgumentException(4){@Override public String toString(){return "0110";}},
    IllegalMonitorStateException(5){@Override public String toString(){return "0111";}},
    IllegalStateException(6){@Override public String toString(){return "0101";}},
    IllegalThreadStateException(7){@Override public String toString(){return "0100";}},
    IndexOutOfBoundsException(8){@Override public String toString(){return "1100";}},
    NegativeArraySizeException(9){@Override public String toString(){return "1101";}},
    NullPointerException(10){@Override public String toString(){return "1111";}},
    NumberFormatException(11){@Override public String toString(){return "1110";}},
    SecurityException(12){@Override public String toString(){return "1010";}},
    StringIndexOutOfBounds(13){@Override public String toString(){return "1011";}},
    UnsupportedOperationException(14){@Override public String toString(){return "1001";}};
    
    private int exceptionType;
    
    private ExceptionType(int exceptionType)
    {
        this.exceptionType = exceptionType;
    }
    
    public static ExceptionType getExceptionTypeFromIndex(int index)
    {
        for(ExceptionType exception: ExceptionType.values())
        {
            if(exception.exceptionType == index)
                return exception;
        }
        
        return null;
    }
    
    public static ExceptionType getExceptionTypeFromEncoding(String encoding)
    {
        for(ExceptionType exception: ExceptionType.values())
        {
            if(exception.toString().equals(encoding))
                return exception;
        }
        
        return null;
    }
    
    
}
