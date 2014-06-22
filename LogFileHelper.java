
/**
 * Write a description of class LogHelper here.
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

public class LogFileHelper extends FileHelper
{
    public boolean writeFile(String filePath, ArrayList<Funcionario> funcionarioList)
    {
        try
        {
            try (FileWriter writer = new FileWriter(filePath); BufferedWriter buffer = new BufferedWriter(writer);)
            {
                buffer.write(appendLine("Cancelamentos", 2));
                
                ArrayList<Servico> cancelamentoList = this.getAllCancelamentoList(funcionarioList);
                
                for(Servico service : cancelamentoList)
                {
                    if(service instanceof Consulta)
                        buffer.write(appendLine(CONSULTA_PATTERN, 1));
                    else
                        buffer.write(appendLine(EXAME_PATTERN, 1));
                    
                    buffer.write(appendLine(service.getPaciente().getNome(), 1));
                    buffer.write(appendLine(String.valueOf(service.getPaciente().getIdade()), 1));
                    buffer.write(appendLine(service.getClass().getName(), 1));
                    buffer.write(appendLine(service.getDataInicio().toString(), 2));
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
    
    private ArrayList<Servico> getAllCancelamentoList(ArrayList<Funcionario> funcionarioList)
    {
        ArrayList<Servico> cancelamentoList = new ArrayList<Servico>();
            
        for(int i = 0; i < funcionarioList.size(); i++)
        {
            ArrayList<Agenda> funcAgendaList = funcionarioList.get(i).getAgendaList();
                
            for(int j = 0; j < funcAgendaList.size(); j++)
            {
                ArrayList<Servico> agendaServicoList = funcAgendaList.get(j).getServicoList();
                        
                for(Servico service : agendaServicoList)
                {
                    if(!service.getStatus())
                    {
                        cancelamentoList.add(service);
                    }
                }
            }
        }
        
        return cancelamentoList;
    }
}
