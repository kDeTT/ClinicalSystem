
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
    
    private final int DATA_LENGTH = 5;
    
    private DateHelper dateHelper = new DateHelper();
    
    public ArrayList<Servico> readFile(String filePath)
    {
        try
        {
            ArrayList<Servico> servicoList = new ArrayList<Servico>();
            
            FileReader reader = new FileReader(filePath);
            BufferedReader buffer = new BufferedReader(reader);
            String line = buffer.readLine();
            
            while(line != null) // Lê o arquivo até o fim
            {
                ArrayList<String> data = new ArrayList<String>();
                    
                for(int i = 0; i < DATA_LENGTH; i++) // Lê os dados do serviço
                {
                    line = buffer.readLine();

                    if((line != null) && (line.trim().length() != 0))
                        data.add(line);
                }

                String tipoServico = data.get(0);
                String nomePaciente = data.get(1);
                int idadePaciente = Integer.parseInt(data.get(2));
                Date date = dateHelper.stringToData(data.get(3), data.get(4));
                    
                try
                {
                    if(tipoServico.toUpperCase().equals(CONSULTA_PATTERN))
                        tipoServico = "Inicial";
                    
                    servicoList.add((Servico)ObjectHelper.createObject(tipoServico, new Object[] { null, new Paciente(nomePaciente, idadePaciente), -1 }));
                }
                catch(ClassNotFoundException ex)
                {
                    System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
                }
                
                line = buffer.readLine();
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
                
                for(Servico servico : servicoList)
                {
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
    
    private Agenda filterAgenda(int data, ArrayList<Funcionario> funcionarioList)
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
    
    private boolean containsAgenda(int data, ArrayList<Agenda> agendaList)
    {
        for(Agenda agenda : agendaList)
        {
            if(agenda.getData() == data)
                return true;
        }
        
        return false;
    }
}
