
/**
 * Write a description of class Agendamento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class Agenda
{
    private int data;
    private ArrayList<Servico> servicoList;
    
    public Agenda(int data)
    {
        this.data = data;
        this.servicoList = new ArrayList<Servico>();
    }
    
    public int getData()
    {
        return this.data;
    }
    
    public ArrayList<Servico> getServicoList()
    {
        return this.servicoList;
    }
    
    public boolean addServico(Servico servico) // TODO
    {
        return this.servicoList.add(servico); // Tratar conflitos de horário
    }
    
    public boolean cancelServico(Servico servico)
    {
        int index = this.servicoList.indexOf(servico);
        
        if(index != -1)
        {
            servico.setStatus(false);
            this.servicoList.set(index, servico);
            return true;
        }
        
        return false;
    }
    
    public Servico findServicoByPaciente(Paciente paciente)
    {
        for(Servico servico : servicoList)
        {
            if(servico.getPaciente().getNome().equals(paciente.getNome()))
                return servico;
        }
        
        return null;
    }
    
}
