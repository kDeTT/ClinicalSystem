
/**
 * Write a description of class Funcionarios here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public abstract class Funcionario
{
    private String nome;
    private ArrayList<Agenda> agendaList;
    
    public Funcionario(String nome)
    {
        this.nome = nome;
        this.agendaList = new ArrayList<Agenda>();
    }
    
    public String getNome()
    {
        return this.nome;
    }
    
    public ArrayList<Agenda> getAgendaList()
    {
        return this.agendaList;
    }
    
    public boolean addServico(int data, Servico servico)
    {
        Agenda agenda = this.findAgendaByData(data);
        
        if(agenda == null)
            agenda = new Agenda(data);
            
        agenda.addServico(servico);
        return agendaList.add(agenda);
    }
    
    public boolean removeServico(int data, Servico servico)
    {
        Agenda agenda = this.findAgendaByData(data);
        
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
    
    public boolean removeAgenda(int data)
    {
        Agenda agenda = this.findAgendaByData(data);
        
        if(agenda != null)
        {
            this.agendaList.remove(agenda);
            return true;
        }
        
        return false;
    }
    
    public Agenda findAgendaByData(int data)
    {
        for(Agenda agenda : agendaList)
        {
            if(agenda.getData() == data)
                return agenda;
        }
        
        return null;
    }
    
    public ArrayList<Servico> findServicoListByData(int dataInicio)
    {
        return this.findAgendaByData(dataInicio).getServicoList();
    }
}
