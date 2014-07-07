
/**
 * Write a description of class TestGenerator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;

public class TestGenerator extends FileHelper
{
    public boolean writeFile(String filePath, int length)
    {
        try
        {
            try (FileWriter writer = new FileWriter(filePath); BufferedWriter buffer = new BufferedWriter(writer);) // Try-with-resources
            {
                for(int i = 0; i < length; i++)
                {
                    if((i % 2) == 0)
                    //if(true)
                        buffer.write(appendLine(CONSULTA_PATTERN, 1));
                    else
                    {
                        buffer.write(appendLine(EXAME_PATTERN, 1));
                        buffer.write(appendLine("Endoscopia", 1));
                    }
                    
                    buffer.write(appendLine("Paciente " + i, 1)); // Nome do paciente
                    
                    Random  rnd = new Random();
                    int idade = rnd.nextInt(100) + 1;
                    
                    buffer.write(appendLine(String.valueOf(idade), 1)); // Idade do paciente

                    long ms = 1404581592853L + (Math.abs(rnd.nextLong()) % (1 * 365 * 24 * 60 * 60 * 1000));
                    Date date = new Date(ms);
                    
                    //Date date = new DateHelper().addMinutes(new DateHelper().getNow(), 1440);

                    buffer.write(appendLine(DateHelper.getDate(date), 1)); // Data inicial do serviço
                    buffer.write(appendLine(DateHelper.getTime(date), 2)); // Data inicial do serviço
                }
            
                buffer.flush();
            }
            
            return true;
        }
        catch(java.io.IOException ex)
        {
            System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
        }
        
        return false;
    }
}
