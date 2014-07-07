import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

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
    
    public String dataToString(Date data){
        if(data == null)
            return null;
        
        String dt;
        String format = "dd/MM/yyyy,HH:mm";
        SimpleDateFormat dt2 = new SimpleDateFormat(format);
        
        dt = dt2.format(data);
        
        return dt;
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
        return (getNow().before(date));
    }
    
    /** Método que compara o dia de duas datas
     * 
     * @param date Date a ser comparada com seconDate
     * @param seconDate Date a ser comparada com date
     * @return (true) se a 1ª data é igual que a 2ª data comparada (false) se a 1ª data é diferente que a 2ª data comparada
     */
    public boolean compareDay(Date date, Date seconDate)
    {
        Calendar dateOne = Calendar.getInstance(new Locale("pt", "BR"));
        Calendar dateTwo = Calendar.getInstance(new Locale("pt", "BR"));
        dateOne.setTime(date);
        dateTwo.setTime(seconDate);
        
        if(dateOne.get(Calendar.YEAR) == dateOne.get(Calendar.YEAR))
            if(dateOne.get(Calendar.MONTH) == dateTwo.get(Calendar.MONTH))
                if(dateOne.get(Calendar.DATE) == dateTwo.get(Calendar.DATE))
                    return true;
                    
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
    
    public static String getDate(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
    
    public static String getTime(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }
    
    public boolean timeFits(Date begin, Date end, int minutes){
        if(begin.before(end)){
            Calendar b = Calendar.getInstance();
            Calendar e = Calendar.getInstance();
            b.setTime(begin);
            e.setTime(end);
            
            int min = (int)((end.getTime()/60000) - (begin.getTime()/60000));
            
            if(min > minutes){
                return true;
            }
        }
        return false;
    }
    
    public Date[] comercialTime(Date date)
    {
        int TAM = 4;
        Date[] comercialTime = new Date[TAM];
        Calendar cal = Calendar.getInstance(new Locale("pt", "BR"));
        Calendar commercial = Calendar.getInstance(new Locale("pt", "BR"));
        
        cal.setTime(date);
        
        commercial.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 8, 0, 0);
        comercialTime[0] = commercial.getTime();
        
        commercial.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 12, 0, 0);
        comercialTime[1] = commercial.getTime();
        
        commercial.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 14, 0, 0);
        comercialTime[2] = commercial.getTime();
        
        commercial.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 18, 0, 0);
        comercialTime[3] = commercial.getTime();
        
        return comercialTime;
    }
    
    public boolean isInRangeBefore(Date date, Date inicio, int range)
    {
        if(date.before(inicio))
        {
              Calendar a = Calendar.getInstance();
              a.setTime(date);
              int dias = 0; 
              
              while (a.before(inicio)) 
              {  
                a.add(Calendar.DAY_OF_MONTH, 1);  
                dias++;  
              }  
              
              if(dias <= 20)
                return true;
        }
        
        return false;
    }
    
    public boolean isBetween(Date inicio, Date fim, Date data){
        if(data.after(inicio) || equals(data,inicio))
            if(data.before(fim) || equals(data,fim))
                return true;
                
        return false;
    }
    
    public boolean equals(Date d1, Date d2){
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        
        a.setTime(d1);
        b.setTime(d2);
        
        if(a.get(Calendar.MINUTE) != b.get(Calendar.MINUTE))
            return false;
            
        if(a.get(Calendar.HOUR) != b.get(Calendar.HOUR))
            return false;
            
        if(a.get(Calendar.DAY_OF_MONTH) != b.get(Calendar.DAY_OF_MONTH))
            return false;
            
        if(a.get(Calendar.MONTH) != b.get(Calendar.MONTH))
            return false;
            
        if(a.get(Calendar.YEAR) != b.get(Calendar.YEAR))
            return false;
        
        return true;
    }
}
