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
        
        if(isConflicted(servico)){
            if(servico.getPaciente().getIdade() >= 60){
                delayServicos(servico);
                return this.servicoList.add(servico);
            }
            return false;
        }
        
        return this.servicoList.add(servico);
    }
    
    /**
     * O metodo recebe o servico que precisa ser encaixado na agenda e abre espaço para este serviço
     *
     * @param  servico Servico servico que precisa ser encaixado na agenda
     */
    private void delayServicos(Servico servico)
    {
        for(Servico s : servicoList){
            if(s.getDataInicio().equals(servico.getDataInicio())){
                s.setDataInicio(dateHelper.addMinutes(servico.getDataInicio(),servico.getDuracao()));
                s.setDataFim(dateHelper.addMinutes(servico.getDataFim(),servico.getDuracao()));
            } else if(s.getDataInicio().before(servico.getDataInicio())){
                if(s.getDataFim().after(servico.getDataInicio())){
                    s.setDataFim(servico.getDataInicio());
                    s.setDataInicio(dateHelper.addMinutes(s.getDataFim(),-s.getDuracao());
                }
            } else {
                if(s.getDataInicio().before(servico.getDataFim())){
                    return true;
                }
            }
        }
    }
    
    private Date findNextWindow(Date inicio, int duracao){
        Date previousEnd = inicio;
        Date nextBegin;
        boolean finished = false;
        while(true){
            Servico closestService;
            for(Servico s : servicoList){
                if(s.getDataInicio().after(previousEnd)){
                    if(nextBegin != null){
                        if(s.getDataInicio.before(nextBegin)){
                            closestService = s;
                            nextBegin = s.getDataInicio();
                        }
                    } else {
                        closestService = s;
                        nextBegin = s.getDataInicio();
                    }
                }
            }
            
            if(nextBegin == null)
                break;
            if(dateHelper.timeFits(previousEnd, nextBegin, duracao)
                break;
                
            previousEnd = s.getDataFim();
        }
        
        return previousEnd;
    }
    
    private boolean isConflicted(Servico servico){
        for(Servico s : servicoList){
            if(s.getDataInicio().equals(servico.getDataInicio())){
                return true;
            } else if(s.getDataInicio().before(servico.getDataInicio())){
                if(s.getDataFim().after(servico.getDataInicio())){
                    return true;
                }
            } else {
                if(s.getDataInicio().before(servico.getDataFim())){
                    return true;
                }
            }
        }
        
        return false;
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
