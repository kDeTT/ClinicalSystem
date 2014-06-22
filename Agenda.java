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
    private Date[] expediente;
    
    public Agenda(Date data)
    {
        this.data = data;
        this.servicoList = new ArrayList<Servico>();
        dateHelper = new DateHelper();
        expediente = dateHelper.comercialTime(data);
    }
    
    public Date getData()
    {
        return this.data;
    }
    
    public ArrayList<Servico> getServicoList()
    {
        return this.servicoList;
    }
    
    //TODO eu preciso imprimir em arquivo qual a consulta que foi marcada e/ou desmarcada
    public boolean addServico(Servico servico)
    {
        if(!dateHelper.compareDate(servico.getDataInicio()))
        {
            System.out.println("Estou aqui!! Não agendando!");
            return false;
        }
        
        if(!isComercialTime(servico.getDataInicio(), servico.getDataFim()))
        {
            System.out.println("Estou aqui!! Não agendando 2!");
            return false;
        }
        
        if(isConflicted(servico))
        {
            if(servico.getPaciente().getIdade() >= 60)
            {//Idosos
                return delayServicos(servico);
            }
            
            Date window = findWindow(servico.getDuracao());
            if(window != null)
            {
                servico.setDataInicio(window);
                return this.servicoList.add(servico);
            }
            
            System.out.println("Estou aqui!! Não agendando 3!");
            return false;
        }

        return this.servicoList.add(servico);
    }
    
    /**
     * O metodo recebe o servico que precisa ser encaixado na agenda e abre espaço para este serviço
     *
     * @param  servico Servico que precisa ser encaixado na agenda
     */
    private boolean delayServicos(Servico servico)
    {
        for(Servico s : servicoList){
            if(s.getStatus()){
                if(s.getDataInicio().equals(servico.getDataInicio())){
                    if(s.getPaciente().getIdade() >= 65)
                        return false;
                    else
                        reallocate(s);
                } else if(s.getDataInicio().before(servico.getDataInicio())){
                    if(s.getDataFim().after(servico.getDataInicio())){
                        if(s.getPaciente().getIdade() >= 65)
                            return false;
                        else
                            reallocate(s);
                    }
                } else {
                    if(s.getDataInicio().before(servico.getDataFim())){
                        if(s.getPaciente().getIdade() >= 65)
                            return false;
                        else
                            reallocate(s);
                    }
                }
            }
        }
        
        return this.servicoList.add(servico);
    }
    
    private void reallocate(Servico servico){
        Date data = findWindow(servico.getDuracao());
        if(data != null){
            cancelServico(servico);
            servico.setDataInicio(findWindow(servico.getDuracao()));
            this.servicoList.add(servico);
        } else {
            cancelServico(servico);
        }
    }
    
    private Date findWindow(int duracao){
        Date previousEnd = expediente[0];
        Date nextBegin;
        while(true){
            Servico closestService = null;
            nextBegin = null;
            
            //Procura o servico mais proximo e seta nextBegin
            for(Servico s : servicoList){
                if(s.getDataInicio().after(previousEnd) && s.getStatus()){
                    if(nextBegin != null){
                        if(s.getDataInicio().before(nextBegin)){
                            closestService = s;
                            nextBegin = s.getDataInicio();
                        }
                    } else {
                        closestService = s;
                        nextBegin = s.getDataInicio();
                    }
                }
            }
            
            //Se não houver um próximo servico seta o fim do expediente(matutino ou vespertino)
            if(nextBegin == null){
                if(previousEnd.before(expediente[1]))
                    nextBegin = expediente[1];
                else
                    nextBegin = expediente[3];
            }
            
            //Verifica se o servico cabe e se está no horário comercial
            if(dateHelper.timeFits(previousEnd, nextBegin, duracao) && isComercialTime(previousEnd, nextBegin))
                break;
            
            //Verifica se já passou do limite do expediente, se não, seta a variavel previousEnd
            if(nextBegin.equals(expediente[1])){
                previousEnd = expediente[2];
            } else if(nextBegin.equals(expediente[3])){
                return null;
            } else {
                previousEnd = closestService.getDataFim();
            }
            
            nextBegin = null;
        }
        
        return previousEnd;
    }
    
    private boolean isConflicted(Servico servico){
        for(Servico s : servicoList){
            if(s.getStatus()){
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
        }
        
        return false;
    }
    
    //TODO
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
            if(servico.getStatus())
                if(servico.getPaciente().getNome().equals(paciente.getNome()))
                    return servico;
        }
        
        return null;
    }
    
    public boolean isComercialTime(Date inicio, Date fim)
    {
        if(inicio.after(expediente[0]) || inicio.equals(expediente[0]))
        {
            if(fim.before(expediente[1]) || inicio.equals(expediente[1]))
                return true;
        }
                
        if(inicio.after(expediente[2]) || inicio.equals(expediente[2]))
        {
            if(fim.before(expediente[3]) || inicio.equals(expediente[3]))
                return true;
        }
                
        return false;
    }
}
