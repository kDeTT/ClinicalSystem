import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import Exceptions.*;

/**
 * Classe de gerenciamento da Agenda
 * 
 * @author Igor Pires
 * @version 1.0
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

    /**
     * Método que adiciona o serviço na agenda, fazendo o tratamento de todas as colisões
     *
     * @param  Servico servico serviço a ser adicionado
     * 
     * @return  boolean        true se o serviço foi adicionado e false caso contrário
     * 
     * @throws OutdatedException()  Quando o serviço a ser adicionado é anterior a data atual
     * @throws OutOfBusinessException()  Quando o serviço a ser adicionado está fora do horário comercial
     * @throws NoWindowException()  Quando o serviço precisa realocar outros e não há vagas no horário
     */
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
     * @param  servico Servico serviço que precisa ser encaixado na agenda
     * 
     * @return boolean         true se foi possível abrir espaço para o serviço e false caso contrário
     * 
     * @throws PriorityException() Quando o horário a ser realocado é tabém de um idoso, idosos não possuem vantagem sobre idosos
     */
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
        
        servico.setStatus(true);
        return this.servicoList.add(servico);
    }
    
     /**
     * Dado um serviço que precisa ser realocado, procura-se uma janela para este, se encontrado o serviço é realocado, caso contrário ele é cancelado.
     *
     * @param  Servico servico serviço a ser realocado
     */
    private void reallocate(Servico servico)
    {
        Date data = findWindow(servico.getDuracao());
        
        if(data != null)
        {
            
            servico.setDataInicio(data);
            
            int index = this.servicoList.indexOf(servico);
            this.servicoList.set(index, servico);
        } 
        else 
        {
            cancelServico(servico);
        }
    }
    
    /**
     * Dada uma duração de um serviço procura-se uma "janela" aonde será encaixado o serviço, precorrendo desde o começo do expediente ao final
     *
     * @param int  duracao   duração do serviço a ser encaixado
     * 
     * @return   Date window  janela no horário aonde caberá o serviço
     */
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
        
    }
    
    /**
     *  Dada uma duração de um serviço e um intervalo de tempo procura-se uma "janela" 
     *  aonde será encaixado o serviço, precorrendo desde o começo do expediente ao final
     *
     * @param int duracao  duração do serviço a ser encaixado
     * @param Date inicio  inicio do intervalo
     * @param Date fim     fim do intervalo
     * 
     * @return   Date window  janela no horário aonde caberá o serviço
     */
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
    
    /**
     * Cria uma lista dos serviços da agenda entre o intervalo de tempo dado
     *
     * @param  Date inicio inicio do intervalo
     * @param  Date fim    fim do intervalo
     */
    private List<Servico> servicosSubList(Date inicio, Date fim){
        ArrayList<Servico> subList = new ArrayList<Servico>();
        
        for(Servico s : servicoList){
            if(dateHelper.isBetween(inicio, fim, s.getDataInicio()))
                subList.add(s);
        }
        
        return subList;
    }
    
    /**
     * Dado um serviço, verifica-se se este causará conflito com outros serviços
     *
     * @param  Servico servico serviço que será verificado
     * 
     * @return boolean         true se houver algum conflito, false caso contrário
     */
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
    
    /**
     * Cancela um serviço e mostra na tela qual serviço foi cancelado
     *
     * @param  Servico servico
     * @return     the sum of x and y
     */
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
    
    /**
     * Procura na lista de serviços da agenda se o paciente
     *
     * @param  Paciente paciente    paciente a ser encontrado na agenda
     * @return  ArraList<Servico>   Servicos em que o paciente está agendado
     */
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
    
    /**
     * Checa se o intevalo [inicio -> fim] dado se encontra dentro do horário comercial
     *
     * @param  Date inicio início do intervalo
     * @param  Date fim    fim do intervalo
     * 
     * @return  boolean  
     */
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
    
    
    /**
     * Ordena a lista de serviços da agenda pela data
     *
     */
    public void sort(){
        servicoList = (ArrayList)MergeSort.sort(servicoList);
    }
}
