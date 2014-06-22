import java.util.Date;

/**
 * Write a description of class Funcionarios here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import Exceptions.*;

public abstract class Funcionario
{
    private String nome;
    private ArrayList<Agenda> agendaList;
    private DateHelper dateHelper;
    
    public Funcionario(String nome)
    {
        this.nome = nome;
        this.agendaList = new ArrayList<Agenda>();
        dateHelper = new DateHelper();
    }
    
    public String getNome()
    {
        return this.nome;
    }
    
    public ArrayList<Agenda> getAgendaList()
    {
        return this.agendaList;
    }
    
    public boolean addServico(Date data, Servico servico) throws AgendaException
    {
        Agenda agenda = this.findAgenda(data);
        
        if(agenda == null)
            agenda = new Agenda(data);
        
        if(servico instanceof Consulta)
            servico = isReturn(servico);
            
        agenda.addServico(servico);
        return agendaList.add(agenda);
    }
    
    public boolean removeServico(Date data, Servico servico)
    {
        Agenda agenda = this.findAgenda(data);
        
        if(agenda != null)
        {
            return agenda.getServicoList().remove(servico);
        }
        
        return false;
    }
    
    public boolean updateAgenda(Agenda agenda)
    {
        int index = this.agendaList.indexOf(agenda);
            
        if(index != -1)
        {
            this.agendaList.set(index, agenda);
            return true;
        }
        
        return false;
    }
    
    public boolean removeAgenda(Date data)
    {
        Agenda agenda = this.findAgenda(data);
        
        if(agenda != null)
        {
            this.agendaList.remove(agenda);
            return true;
        }
        
        return false;
    }
    
    public Agenda findAgenda(Date data)
    {
        for(Agenda agenda : agendaList)
        {
            if(dateHelper.compareDay(agenda.getData(), data))
                return agenda;
        }
        
        return null;
    }
    
    public ArrayList<Servico> getServicoList(Date dataInicio)
    {
        Agenda agenda = this.findAgenda(dataInicio);
        
        return (agenda == null) ? null : agenda.getServicoList();
    }
    
    public int getServicoListCount(Date dataInicio)
    {
        Agenda agenda = this.findAgenda(dataInicio);
        
        return (agenda == null) ? -1 : agenda.getServicoList().size();
    }
    
    // TODO - Problemas aqui!
    private Servico isReturn(Servico servico)
    {
        for(Agenda agenda : agendaList)
        {
            Servico s = agenda.findServicoByPaciente(servico.getPaciente());
            
            if(servico != null)            
                if(dateHelper.isInRangeBefore(servico.getDataInicio(), s.getDataInicio(), 20))
                    servico.setDuracao(20);
        }
        
        return servico;
    }
}
