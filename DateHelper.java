import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Write a description of class DateHelper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DateHelper
{
    private Date data;
    
    public Date stringToData(String date, String time)
    {
        try{
            String format = "dd/MM/yyyy,HH:mm";
            String texto = date + "," + time;
            SimpleDateFormat dt = new SimpleDateFormat(format);
            
            data = dt.parse(texto);
            
            return data;
        } 
        catch(ParseException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }
}
