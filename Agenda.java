import java.util.Date;

/**
 * Write a description of class Agendamento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class Agenda
{
    private Date data;
    private ArrayList<Servico> servicoList;
    private DateHelper dateHelper;
    
    public Agenda(Date data)
    {
        this.data = data;
        this.servicoList = new ArrayList<Servico>();
        dateHelper = new DateHelper();
    }
    
    public Date getData()
    {
        return this.data;
    }
    
    public ArrayList<Servico> getServicoList()
    {
        return this.servicoList;
    }
    
    public boolean addServico(Servico servico) // TODO
    {
        if(!dateHelper.compareDate(servico.getDataInicio())){
                return false;
        }
        for(Servico s : servicoList){
            if(s.getDataInicio().equals(servico.getDataInicio())){
                return false;
            } else if(s.getDataInicio().before(servico.getDataInicio())){
                if(s.getDataFim().after(servico.getDataInicio())){
                    return false;
                }
            } else {
                if(s.getDataInicio().before(servico.getDataFim())){
                    return false;
                }
            }
        }
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
