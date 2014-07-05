import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import Exceptions.*;

/**
 * Write a description of class Agendamento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
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
    public boolean addServico(Servico servico) throws AgendaException
    {
        if(!dateHelper.compareDate(servico.getDataInicio()))
        {
            throw new OutdatedException();
        }
        
        if(!isComercialTime(servico.getDataInicio(), servico.getDataFim()))
        {
            throw new OutOfBusinessException();
        }
        
        if(isConflicted(servico))
        {
            if(servico.getPaciente().getIdade() >= 65)
            {//Idosos
                return delayServicos(servico);
            }
            
            Date window = findWindow(servico.getDuracao());
            
            if(window != null)
            {
                servico.setDataInicio(window);
                servico.setStatus(true);
                return this.servicoList.add(servico);
            }
            else
                throw new NoWindowException();
        }
        
        servico.setStatus(true);
        return this.servicoList.add(servico);
    }
    
    /**
     * O metodo recebe o servico que precisa ser encaixado na agenda e abre espaço para este serviço
     *
     * @param  servico Servico que precisa ser encaixado na agenda
     */
    
    //TODO - Mandar só o serviço que está conflitando ao invés de percorrer a lista de novo
    private boolean delayServicos(Servico servico) throws PriorityException
    {
        for(Servico s : servicoList)
        {
            if(s.getStatus())
            {
                if(s.getDataInicio().equals(servico.getDataInicio()))
                {
                    if(s.getPaciente().getIdade() >= 65)
                        throw new PriorityException();
                    else
                        reallocate(s);
                } 
                else if(s.getDataInicio().before(servico.getDataInicio()))
                {
                    if(s.getDataFim().after(servico.getDataInicio()))
                    {
                        if(s.getPaciente().getIdade() >= 65)
                            throw new PriorityException();
                        else
                            reallocate(s);
                    }
                } 
                else 
                {
                    if(s.getDataInicio().before(servico.getDataFim()))
                    {
                        if(s.getPaciente().getIdade() >= 65)
                            throw new PriorityException();
                        else
                            reallocate(s);
                    }
                }
            }
        }

        return this.servicoList.add(servico);
    }
    
    private void reallocate(Servico servico)
    {
        Date data = findWindow(servico.getDuracao());
        
        if(data != null)
        {
            //cancelServico(servico);
            //servico.setDataInicio(findWindow(servico.getDuracao()));
            //this.servicoList.add(servico);
            
            servico.setDataInicio(data);
            
            int index = this.servicoList.indexOf(servico);
            this.servicoList.set(index, servico);
        } 
        else 
        {
            cancelServico(servico);
        }
    }
    
    private Date findWindow(int duracao)
    {
        Date window;
        
        //Ordena a lista de serviços de acordo com a data de início 
        sort();
        
        //Procura a janela no expediente da manhã
        window = findWindow(expediente[0], expediente[1], duracao);
        
        if(window != null)
            return window;
            
        //Procura a janela no exédiente da tarde
        window = findWindow(expediente[2], expediente[3], duracao);
        
        return window;
        
        /*
        while(true)
        {
            Servico closestService = null;
            nextBegin = null;
            
            //Procura o servico mais proximo e seta nextBegin
            for(Servico s : servicoList)
            {
                if(s.getDataInicio().after(previousEnd) && s.getStatus())
                {
                    if(nextBegin != null)
                    {
                        if(s.getDataInicio().before(nextBegin))
                        {
                            closestService = s;
                            nextBegin = s.getDataInicio();
                        }
                    } 
                    else 
                    {
                        closestService = s;
                        nextBegin = s.getDataInicio();
                    }
                }
            }
            
            //Se não houver um próximo servico seta o fim do expediente(matutino ou vespertino)
            if(nextBegin == null)
            {
                if(previousEnd.before(expediente[1]))
                    nextBegin = expediente[1];
                else
                    nextBegin = expediente[3];
            }
            
            //Verifica se o servico cabe e se está no horário comercial
            if(dateHelper.timeFits(previousEnd, nextBegin, duracao) && isComercialTime(previousEnd, nextBegin))
                break;
            
            //Verifica se já passou do limite do expediente, se não, seta a variavel previousEnd
            if(nextBegin.equals(expediente[1]))
            {
                previousEnd = expediente[2];
            } 
            else if(nextBegin.equals(expediente[3]))
            {
                return null;
            } 
            else 
            {
                previousEnd = closestService.getDataFim();
            }
            
            nextBegin = null;
        }
        */
    }
    
    private Date findWindow(Date inicio, Date fim, int duracao){
        //Cria uma lista com os serviços que estão entre o inicio e o fim
        List<Servico> servicos = servicosSubList(inicio, fim);
        final int TAM = servicos.size();
        
        //Se não houver serviços checa se cabe entre o inicio e o fim
        if(servicos.isEmpty())
            if(dateHelper.timeFits(inicio, fim, duracao))
                return inicio;
            else
                return null;
        
        //Checa se cabe entre o inicio do intervalo e o começo primeiro serviço
        if(dateHelper.timeFits(inicio, servicos.get(0).getDataInicio(), duracao) && isComercialTime(inicio, servicos.get(0).getDataInicio()))
            return inicio;
            
        //Checa se cabe entre os serviços
        for(int i = 0; i < TAM - 1; i ++){
            Servico s1 = servicos.get(i);
            Servico s2 = servicos.get(i + 1);
            
            if(dateHelper.timeFits(s1.getDataFim(), s2.getDataInicio(), duracao) && isComercialTime(s1.getDataFim(), s2.getDataInicio()))
                return s1.getDataFim();
        }
        
        //Checa se cabe entre o fim do último serviço e o fim do intervalo
        if(dateHelper.timeFits(servicos.get(TAM - 1).getDataFim(), fim, duracao) && isComercialTime(servicos.get(TAM - 1).getDataFim(), fim))
            return servicos.get(TAM - 1).getDataFim();
            
        return null;
    }
    
    private List<Servico> servicosSubList(Date inicio, Date fim){
        ArrayList<Servico> subList = new ArrayList<Servico>();
        
        for(Servico s : servicoList){
            if(dateHelper.isBetween(inicio, fim, s.getDataInicio()))
                subList.add(s);
        }
        
        return subList;
    }
    
    private boolean isConflicted(Servico servico)
    {
        for(Servico s : servicoList)
        {
            if(s.getStatus())
            {
                if(s.getDataInicio().equals(servico.getDataInicio()))
                {
                    return true;
                } 
                else if(s.getDataInicio().before(servico.getDataInicio()))
                {
                    if(s.getDataFim().after(servico.getDataInicio()))
                    {
                        return true;
                    }
                } 
                else 
                {
                    if(s.getDataInicio().before(servico.getDataFim()))
                    {
                        return true;
                    }
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
            
            String tipoServico = (servico.getClass().getName().equals("Exame")) ? servico.getClass().getName() : String.format("Consulta %s", servico.getClass().getName());
            System.out.println(String.format("O serviço de %s do paciente %s foi cancelado!", tipoServico, servico.getPaciente().getNome()));
            return true;
        }
        
        return false;
    }
    
    public ArrayList<Servico> findServicoListByPaciente(Paciente paciente)
    {
        ArrayList<Servico> serviceListForPaciente = new ArrayList<Servico>();
        
        for(Servico servico : servicoList)
        {
            if(servico.getStatus())
                if(servico.getPaciente().getNome().equals(paciente.getNome()))
                    serviceListForPaciente.add(servico);
        }
        
        return serviceListForPaciente;
    }
    
    public boolean isComercialTime(Date inicio, Date fim)
    {
        if(dateHelper.isBetween(expediente[0], expediente[1], inicio))
            if(dateHelper.isBetween(expediente[0], expediente[1], fim))
                return true;
                
        if(dateHelper.isBetween(expediente[2], expediente[3], inicio))
            if(dateHelper.isBetween(expediente[2], expediente[3], fim))
                return true;
                
        return false;
    }
    
    public void sort(){
        servicoList = (ArrayList)MergeSort.sort(servicoList);
    }
}
