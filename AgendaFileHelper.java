
/**
 * Write a description of class AgendaHelper here.
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
import Exceptions.*;

public class AgendaFileHelper extends FileHelper
{
    // TODO - Devo tratar essas exceções e não lançar para o nível acima!
    public boolean writeFile(String filePath, ArrayList<Funcionario> funcionarioList) throws AgendaException
    {
        try
        {
            try (FileWriter writer = new FileWriter(filePath); BufferedWriter buffer = new BufferedWriter(writer);)
            {
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
                    //System.out.println("Agenda filtrada!!! Serviços: " + servicoList.size());
                
                    for(Servico servico : servicoList)
                    {
                        //System.out.println("Salvando lista de serviços!!!");
                    
                        if(servico instanceof Consulta)
                            buffer.write(appendLine(CONSULTA_PATTERN, 1));
                        else
                            buffer.write(appendLine(EXAME_PATTERN, 1));
                    
                            buffer.write(appendLine(servico.getPaciente().getNome(), 1));
                            buffer.write(appendLine(String.valueOf(servico.getPaciente().getIdade()), 1));
                            buffer.write(appendLine(servico.getClass().getName(), 1));
                            buffer.write(appendLine(servico.getFuncionario().getNome(), 1));
                            buffer.write(appendLine(servico.getDataInicio().toString(), 2));
                    }
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

    private Agenda filterAgenda(Date data, ArrayList<Funcionario> funcionarioList) throws AgendaException
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
        DateHelper dateHelp = new DateHelper();
        
        for(Agenda agenda : agendaList)
        {
            if(dateHelp.compareDay(agenda.getData(), data))
                return true;
        }
        
        return false;
    }
}
