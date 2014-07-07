import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Write a description of class DateHelper here.
 * 
 * @author Igor Pires 
 * @version 1.0
 */
public class DateHelper
{
    private Date data;
    
    /**
     * Transforma duas Strings no formato "dd/MM/yyyy" e "HH:mm" em um objeto Date
     *
     * @param  String date data no formato "dd/MM/yyyy"
     * @param  String time horário no formato "HH:mm"
     * 
     * @return Date   data da união das Strings
     * 
     * @throws ParseException() Quando o String não está no devido formato
     */
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
    
    /**
     * Transforma uma data em uma String no formato "dd/MM/yyyy,HH:mm"
     *
     * @param  Date data a ser formatada
     * 
     * @return  String data no formato "dd/MM/yyyy,HH:mm"
     */
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
     * 
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
     * 
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
     * 
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
    
    /**
     * Recebe um intervalo de datas e verifica se entre estas cabe uma dada quantidade inteira de minutos 
     *
     * @param  Date begin  início do intervalo
     * @param  Date end    fim do intervalo
     * @param  int minutes tempo a ser verificado, em minutos
     * 
     * @return  boolean true se houver espaço, false caso contrário.
     * 
     */
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
    
    /**
     * Dado um certo dia retorna um vetor de tamanho 4 com a dupla de intervalos do horário comercial do mesmo dia
     *
     * @param  Date date dia em que se cria o vetor
     * 
     * @return  Date[]   vetor com os intervalos
     */
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
    
    /**
     * Verifica a partir de uma data se a outra está anteriormente dentro do alcance
     *
     * @param  Date data   data a ser verificada
     * @param  Date inicio data que será usada como referência
     * @param  int range   alcance que será verificado
     * 
     * @return   boolean se a data está anteriormente dentro do alcance
     */
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
              
              if(dias <= range)
                return true;
        }
        
        return false;
    }
    
    /**
     * Dado um intervalo, é verificado se a data está contida dentre deste intervalo
     *
     * @param Date inicio inicio do intervalo
     * @param Date fim    fim do intervalo
     * @param Date data   data a ser verificada
     * 
     * @return boolean    se a data esta dentro do intervalo
     */
    public boolean isBetween(Date inicio, Date fim, Date data){
        if(data.after(inicio) || equals(data,inicio))
            if(data.before(fim) || equals(data,fim))
                return true;
                
        return false;
    }
    
    /**
     * Verifica se uma data é igual a outra ignorando os milisegundos e o fuso horário
     *
     * @param  Date d1 primeira data
     * @param  Date d2 segunda data
     * 
     * @return  boolean   true se as datas forem iguais, false caso contrário
     */
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
