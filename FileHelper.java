
/**
 * Write a description of class FileHelper here.
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

public class FileHelper
{
    private final String CONSULTA_PATTERN = "CONSULTA";
    private final String EXAME_PATTERN = "EXAME";
    
    private DateHelper dateHelper = new DateHelper();
    
    public ArrayList<Servico> readFile(String filePath)
    {
        try
        {
            ArrayList<Servico> servicoList = new ArrayList<Servico>();
            
            FileReader reader = new FileReader(filePath);
            BufferedReader buffer = new BufferedReader(reader);
            //String line = buffer.readLine();
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
                Date dateInicio = (dataLength == 5) ?  dateHelper.stringToData(data.get(4), data.get(5)) : dateHelper.stringToData(data.get(3), data.get(4));

                try
                {
                    servicoList.add((Servico)ObjectHelper.createObject(tipoServico, new Object[] { null, new Paciente(nomePaciente, idadePaciente), dateInicio }));
                }
                catch(ClassNotFoundException ex)
                {
                    System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
                }
            }
            
            buffer.close();
            reader.close();
            
            return servicoList;
        }
        catch(java.io.IOException ex)
        {
            System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
        }

        return null;
    }
    
    public boolean writeFile(String filePath, ArrayList<Funcionario> funcionarioList)
    {
        FileWriter writer = null;
        BufferedWriter buffer = null;
        
        try
        {
            writer = new FileWriter(filePath);
            buffer = new BufferedWriter(writer);
            
            ArrayList<Agenda> filtredAgendaList = new ArrayList<Agenda>();
            
            for(int i = 0; i < funcionarioList.size(); i++)
            {
                ArrayList<Agenda> funcAgendaList = funcionarioList.get(i).getAgendaList();
                
                for(int j = 0; j < funcAgendaList.size(); j++)
                {
                    if(!containsAgenda(funcAgendaList.get(j).getData(), filtredAgendaList))
                    {
                        Agenda filtredAgenda = filterAgenda(funcAgendaList.get(j).getData(), funcionarioList);
                        filtredAgendaList.add(filtredAgenda);
                    }
                }
            }
 
            for(Agenda agenda : filtredAgendaList)
            {
                buffer.write(appendLine(String.valueOf(agenda.getData()), 2));
                ArrayList<Servico> servicoList = agenda.getServicoList();
                System.out.println("Agenda filtrada!!! Serviços: " + servicoList.size());
                
                for(Servico servico : servicoList)
                {
                    System.out.println("Salvando lista de serviços!!!");
                    
                    if(servico instanceof Consulta)
                        buffer.write(appendLine(CONSULTA_PATTERN, 1));
                    else
                        buffer.write(appendLine(EXAME_PATTERN, 1));
                    
                    buffer.write(appendLine(servico.getPaciente().getNome(), 1));
                    buffer.write(appendLine(String.valueOf(servico.getPaciente().getIdade()), 1));
                    buffer.write(appendLine(servico.getClass().getName(), 1));
                    buffer.write(appendLine(servico.getFuncionario().getNome(), 1));
                    buffer.write(appendLine("<Hora do serviço>", 2));
                }
            }
            
            buffer.flush();
            buffer.close();
            writer.close();
            
            return true;
        }
        catch(java.io.IOException ex)
        {
            System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
        }
        
        return false;
    }
    
    private String appendLine(String text, int lineNumber)
    {
        StringBuilder strBuilder = new StringBuilder();
        
        for(int i = 0; i < lineNumber; i++)
            strBuilder.append(System.getProperty("line.separator"));
        
        return String.format("%s%s", text, strBuilder.toString());
    }
    
    private Agenda filterAgenda(Date data, ArrayList<Funcionario> funcionarioList)
    {
        Agenda filtredAgenda = new Agenda(data);
        
        for(Funcionario funcionario : funcionarioList)
        {
            Agenda agenda = funcionario.findAgenda(data);
            
            if(agenda != null)
            {
                ArrayList<Servico> servicoList = agenda.getServicoList();
                
                for(Servico servico : servicoList)
                {
                    filtredAgenda.addServico(servico);
                }
            }
        }
        
        return filtredAgenda;
    }
    
    private boolean containsAgenda(Date data, ArrayList<Agenda> agendaList)
    {
        for(Agenda agenda : agendaList)
        {
            if(dateHelper.compareDay(agenda.getData(), data))
                return true;
        }
        
        return false;
    }
}
