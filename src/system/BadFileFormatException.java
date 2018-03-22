package system;

public class BadFileFormatException extends Exception{
    private final String errorMsg;
    public BadFileFormatException(){
        errorMsg = "Format expected in File is not respected.";
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    public String toString(){
        return errorMsg;
    }
}
