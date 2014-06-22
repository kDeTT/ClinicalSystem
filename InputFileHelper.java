
/**
 * Write a description of class InputFileHelper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Date;

public class InputFileHelper extends FileHelper
{
    public ArrayList<Servico> readFile(String filePath)
    {
        try
        {
            ArrayList<Servico> servicoList = new ArrayList<Servico>();
            
            try (FileReader reader = new FileReader(filePath); BufferedReader buffer = new BufferedReader(reader))
            {
                String line;

                while((line = buffer.readLine()) != null) // Lê o arquivo até o fim
                {
                    ArrayList<String> data = new ArrayList<String>();
                
                    int dataRead = 0;
                    int dataLength = (line.trim().toUpperCase().equals(EXAME_PATTERN)) ? 5 : 4;
                
                    while(dataRead <= dataLength)
                    {
                        if ((line != null) && !line.equals(""))
                        {
                            data.add(line.trim());
                            dataRead++;
                        }
                    
                        line = buffer.readLine();
                    }

                    String tipoServico = (dataLength == 5) ? data.get(1) : "Inicial";
                    String nomePaciente = (dataLength == 5) ? data.get(2) : data.get(1);
                    int idadePaciente = (dataLength == 5) ? Integer.parseInt(data.get(3)) : Integer.parseInt(data.get(2));
                
                    DateHelper dateHelp = new DateHelper();
                    Date dateInicio = (dataLength == 5) ?  dateHelp.stringToData(data.get(4), data.get(5)) : dateHelp.stringToData(data.get(3), data.get(4));

                    try
                    {
                        servicoList.add((Servico)ObjectHelper.createObject(tipoServico, new Object[] { null, new Paciente(nomePaciente, idadePaciente), dateInicio }));
                    }
                    catch(ClassNotFoundException ex)
                    {
                        System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
                    }
                }
            }
            
            return servicoList;
        }
        catch(java.io.IOException ex)
        {
            System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
        }

        return null;
    }
}
