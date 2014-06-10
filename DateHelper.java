import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

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
    
    /** Método que retorna a data atual
     * 
     * @return String nowDate; 
     */
    public Date getNow()
    {
        return Calendar.getInstance().getTime();
    }
    
    /** Método que compara a data atual com a data do parâmetro
     * 
     * @param date Data a ser comparada com a atual
     * @return (true) se a data atual é menor que a data comparada (false) se a data atual é maior que a data comparada
     */
    public boolean compareDate(Date date)
    {
        return (getNow().compareTo(date) < 0);
    }
    
    /** Método que compara o dia de duas datas
     * 
     * @param date Date a ser comparada com seconDate
     * @param seconDate Date a ser comparada com date
     * @return (true) se a data atual é menor que a data comparada (false) se a data atual é maior que a data comparada
     */
    public boolean compareDay(Date date, Date seconDate)
    {
        String format = "dd/MM/yyyy";
        SimpleDateFormat dt = new SimpleDateFormat(format);
        String dateOne = dt.format(date);
        String dateTwo = dt.format(date);
        
        if(dateOne == dateTwo){
            return true;
        }
        return false;
    }
    
    /** Método que adiciona minutos à data passada como parâmetro
     * 
     * @param date Data em que será adicionado minutos
     * @param minutes Quantidade de minutos que será adicionado à data
     * @return Date newDate 
     */
    public Date addMinutes(Date date, int minutes)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        
        return cal.getTime();
    }
    
    public int getMinutes(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }
    
    public boolean timeFits(Date begin, Date end, int minutes){
        if(begin.before(end)){
            Calendar b = Calendar.getInstance();
            Calendar e = Calendar.getInstance();
            b.setTime(begin);
            e.setTime(end);
            
            int min = (e.get(Calendar.HOUR) - b.get(Calendar.HOUR)) * 60;
            min = min + (e.get(Calendar.MINUTE) - b.get(Calendar.MINUTE));
            
            if(min > minutes){
                return true;
            }
        }
        return false;
    }
}
