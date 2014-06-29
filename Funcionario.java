import java.util.Date;
import java.util.ArrayList;
import Exceptions.*;

/**
 * Classe abstrata que representa um funcionário
 * 
 * @author Luis Augusto
 */
public abstract class Funcionario
{
    private String nome; // Nome do funcionário
    private ArrayList<Agenda> agendaList; // Lista de agendas do funcionário
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
    
    /**
     * Faz o agendamento de um serviço para o funcionário
     * 
     * @param data Data da agenda
     * @param servico Serviço a ser agendado
     * @throws AgendaException
     */
    public boolean addServico(Date data, Servico servico) throws AgendaException
    {
        Agenda agenda = this.findAgenda(data); // Verifico se o funcionário já possui uma agenda com a data
        
        if(agenda == null) // Se não existir, crio uma nova
            agenda = new Agenda(data);
        
        // Isto deve ir para a classe Médico
        if(servico instanceof Consulta) // Verifico se o serviço é do tipo Consulta
            servico = isReturn(servico); // Se for, verifico se é uma consulta de Retorno
        
        // Faço o agendamento
        agenda.addServico(servico);
        return agendaList.add(agenda); // TODO
    }
    
    /**
     * Remove um serviço da agenda do funcionário
     * 
     * @param data Data da agenda
     * @param servico Serviço a ser removido
     * @return (true) Se o serviço foi removido com sucesso; (false) Se não foi removido
     */
    public boolean removeServico(Date data, Servico servico)
    {
        Agenda agenda = this.findAgenda(data); // Verifico se existe uma agenda com a data
        
        if(agenda != null) // Se a agenda existir
        {
            return agenda.getServicoList().remove(servico); // Removo o serviço se também existir
        }
        
        return false;
    }
    
    /**
     * Atualiza uma agenda
     * 
     * @param agenda Agenda a ser atualizada
     * @return (true) Se a agenda foi atualizada; (false) Se não foi
     */
    public boolean updateAgenda(Agenda agenda)
    {
        int index = this.agendaList.indexOf(agenda); // Busco o índice da agenda a ser atualizada
            
        if(index != -1) // Verifico se existe
        {
            this.agendaList.set(index, agenda); // Atualizo a agenda
            return true;
        }
        
        return false;
    }
    
    /**
     * Remove uma agenda
     * 
     * @param data Data da agenda
     */
    public boolean removeAgenda(Date data)
    {
        Agenda agenda = this.findAgenda(data); // Busco o índice da agenda a ser removida
        
        if(agenda != null) // Verifico se a agenda existe
        {
            return this.agendaList.remove(agenda); // Removo a agenda
        }
        
        return false;
    }
    
    /**
     * Busca uma agenda pela data
     * 
     * @param data Data da agenda
     * @return Agenda encontrada ou null se a agenda não for encontrada
     */
    public Agenda findAgenda(Date data)
    {
        for(Agenda agenda : agendaList) // Percorre a lista de agendas do funcionário
        {
            if(dateHelper.compareDay(agenda.getData(), data)) // Compara os dias das agendas
                return agenda;
        }
        
        return null;
    }
    
    /**
     * Retorna a lista de serviços de uma agenda do funcionário
     * 
     * @param data Data da agenda
     * @return Lista de serviços da agenda ou null se a agenda não existir
     */
    public ArrayList<Servico> getServicoList(Date data)
    {
        Agenda agenda = this.findAgenda(data); // Busco por uma agenda da data
        
        return (agenda != null) ? agenda.getServicoList() : null; // Verifico se existe uma agenda, se existir retorno a lista de serviços
    }
    
    /**
     * Retorna a quantidade de serviços para uma determinada agenda do funcionário
     * 
     * @param data Data da agenda
     * @return Quantidade de serviços da agenda ou -1 caso não exista uma agenda para a data
     */
    public int getServicoListCount(Date data)
    {
        Agenda agenda = this.findAgenda(data); // Busco por uma agenda da data
        
        return (agenda != null) ? agenda.getServicoList().size() : -1; // Verifico se existe uma agenda, se existir retorno a lista de serviços
    }
    
    // TODO - Problemas aqui!
    private Servico isReturn(Servico servico) // Este método deveria ir para a classe Médico
    {
        if(servico == null)
            return null;
            
        for(Agenda agenda : agendaList)
        {
            Servico s = agenda.findServicoByPaciente(servico.getPaciente());
            
            if(s != null){            
                if(dateHelper.isInRangeBefore(servico.getDataInicio(), s.getDataInicio(), 20)){
                    Servico retorno = new Retorno((Medico)servico.getFuncionario(), servico.getPaciente(), servico.getDataInicio());
                    return retorno;
                }
            }
        }
        
        return servico;
    }
}
