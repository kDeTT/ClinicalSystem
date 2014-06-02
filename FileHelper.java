
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
    private final int CONSULTA_DATA_LENGTH = 6;
    
    private final String EXAME_PATTERN = "EXAME";
    private final int EXAME_DATA_LENGTH = 7;
    
    private DateHelper dateHelper = new DateHelper();
    
    public ArrayList<Funcionario> readFile(String filePath, ArrayList<Funcionario> funcionarioList)
    {
        Servico servico = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        String line = null;
        
        try
        {
            reader = new FileReader(filePath);
            buffer = new BufferedReader(reader);
            line = buffer.readLine();
            int dataLength = (((line != null) && line.toUpperCase().equals(CONSULTA_PATTERN)) ? CONSULTA_DATA_LENGTH : EXAME_DATA_LENGTH);
            
            while(line != null) // Lê o arquivo até o fim
            {
                ArrayList<String> data = new ArrayList<String>();
                    
                for(int i = 0; i < dataLength; i++) // Lê os dados do serviço
                {
                    line = buffer.readLine();

                    if((line != null) && (line.trim().length() != 0))
                    {
                        System.out.println(line);
                        data.add(line);
                    }
                }
                
                if(data.size() == CONSULTA_DATA_LENGTH) // É um serviço do tipo Consulta
                {
                    String nomePaciente = data.get(1);
                    int idadePaciente = Integer.parseInt(data.get(2));
                    Date date = dateHelper.stringToData(data.get(3), data.get(4));
                    
                    
                    servico = new ConsultaInicial(new Medico("Nome do médico"), new Paciente(nomePaciente, idadePaciente), 0);
                }
                else if(data.size() == EXAME_DATA_LENGTH)
                {
                    System.out.println(data);
                    String nomePaciente = data.get(2);
                    int idadePaciente = Integer.parseInt(data.get(3));
                    Date date = dateHelper.stringToData(data.get(4), data.get(5));
                    
                    servico = new Endoscopia(new Tecnico("Nome do técnico"), new Paciente(nomePaciente, idadePaciente), 0);
                }

                line = buffer.readLine();
                dataLength = (((line != null) && line.toUpperCase().equals(CONSULTA_PATTERN)) ? CONSULTA_DATA_LENGTH : EXAME_DATA_LENGTH);
            }
            
            buffer.close();
            reader.close();
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
                    if(!containsAgendaByData(funcAgendaList.get(j).getData(), filtredAgendaList))
                    {
                        Agenda filtredAgenda = filterAgendaByData(funcAgendaList.get(j).getData(), funcionarioList);
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
                        buffer.write(appendLine("Consulta", 1));
                    else
                        buffer.write(appendLine("Exame", 1));
                    
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
    
    private Agenda filterAgendaByData(int data, ArrayList<Funcionario> funcionarioList)
    {
        Agenda filtredAgenda = new Agenda(data);
        
        for(Funcionario funcionario : funcionarioList)
        {
            Agenda agenda = funcionario.findAgendaByData(data);
            
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
    
    private boolean containsAgendaByData(int data, ArrayList<Agenda> agendaList)
    {
        for(Agenda agenda : agendaList)
        {
            if(agenda.getData() == data)
                return true;
        }
        
        return false;
    }
}
