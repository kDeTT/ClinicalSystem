
/**
 * Write a description of class FileHelper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public abstract class FileHelper
{
    public final String CONSULTA_PATTERN = "CONSULTA";
    public final String EXAME_PATTERN = "EXAME";
    
    public String appendLine(String text, int lineNumber)
    {
        StringBuilder strBuilder = new StringBuilder();
        
        for(int i = 0; i < lineNumber; i++)
            strBuilder.append(System.getProperty("line.separator"));
        
        return String.format("%s%s", text, strBuilder.toString());
    }
}
