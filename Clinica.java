import java.util.ArrayList;
import java.util.Date;
import Exceptions.*;

/**
 * Classe que representa uma Clínica
 * 
 * @author Luis Augusto
 */
public class Clinica
{
    // Constantes que indicam caminhos padrões para os arquivos de saída da clínica
    private static final String AGENDA_FILE_PATH = "Dados/agenda.txt";
    private static final String CANCELAMENTO_FILE_PATH = "Dados/cancelamento.txt";

    private ArrayList<Funcionario> funcionarioList; // Lista de funcionários da clínica
    
    public Clinica()
    {
        this.funcionarioList = new ArrayList<Funcionario>();
    }
    
    /**
     * Cadastra um novo funcionário
     * 
     * @param funcionario Funcionário a ser cadastrado
     * @throws FuncionarioExistsException
     */
    public void cadastrarFuncionario(Funcionario funcionario) throws FuncionarioExistsException
    {
        if(!funcionarioList.contains(funcionario))
        {
            this.funcionarioList.add(funcionario);
            System.out.println(String.format("Funcionário(a) %s cadastrado(a) com sucesso!", funcionario.getNome()));
        }
        else
            throw new FuncionarioExistsException(funcionario.getNome());
    }
    
    /**
     * Faz o agendamento de serviços a partir de um arquivo de entrada
     * 
     * @param filePath Caminho do arquivo de entrada
     * @throws AgendaException
     * @throws ReflectionException
     */
    public void agendarByFile(String filePath) throws AgendaException, ReflectionException
    {
        InputFileHelper fileHelp = new InputFileHelper();
        
        try
        {
            ArrayList<Servico> servicoList = fileHelp.readFile(filePath);
            
            for(Servico service : servicoList)
            {
                Funcionario servFunc = this.selectFuncionarioToService(service);
                
                if(servFunc != null)
                {
                    try
                    {
                        service.setFuncionario(servFunc);
                        servFunc.addServico(service.getDataInicio(), service);
                    }
                    catch(AgendaException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
                else
                    System.out.println("Nenhum funcionário disponível para o serviço!");
                }
        }
        catch(ReflectionException ex)
        {
            System.out.println(ex.getMessage());
        }     
    }

    /**
     * Retorna o médico com a menor quantidade de serviços de uma agenda
     * 
     * @param date Data da agenda
     * @return Médico com a menor quantidade de serviços de uma agenda
     */
    public Medico getMedicoMinServico(Date date)
    {
        return (Medico)this.getFuncionarioMinServico(date, filterFuncionarioList(Medico.class));
    }
    
    /**
     * Retorna o técnico com a menor quantidade de serviços de uma agenda
     * 
     * @param date Data da agenda
     * @return Técnico com a menor quantidade de serviços de uma agenda
     */
    public Tecnico getTecnicoMinServico(Date date)
    {
        return (Tecnico)this.getFuncionarioMinServico(date, filterFuncionarioList(Tecnico.class));
    }
    
    /**
     * Salva o arquivo de agendamentos da clínica
     * 
     * @throws AgendaException
     */
    public void saveAgenda() throws AgendaException
    {
        this.saveAgenda(AGENDA_FILE_PATH);
    }
    
    /**
     * Salva o arquivo de agendamentos da clínica em um caminho fora do caminho padrão do sistema
     * 
     * @param filePath Caminho do arquivo de agendamentos
     * @throws AgendaException
     */
    public void saveAgenda(String filePath) throws AgendaException
    {
        AgendaFileHelper agendaFileHelp = new AgendaFileHelper();
        
        if(agendaFileHelp.writeFile(filePath, funcionarioList))
            System.out.println("Agenda da clínica salva com sucesso!");
    }
    
    /**
     * Salva o arquivo de cancelamentos da clínica
     */
    public void saveCancelamento()
    {
        this.saveCancelamento(CANCELAMENTO_FILE_PATH);
    }
    
    /**
     * Salva o arquivo de cancelamentos da clínica em um caminho fora do caminho padrão do sistema
     * 
     * @param filePath Caminho do arquivo de cancelamentos
     */
    public void saveCancelamento(String filePath)
    {
        LogFileHelper logFileHelp = new LogFileHelper();
        
        if(logFileHelp.writeFile(filePath, funcionarioList))
            System.out.println("Cancelamentos da clínica salvos com sucesso!");
    }
    
    private Funcionario selectFuncionarioToService(Servico service)
    {
        Class filter = null;
        ArrayList<Funcionario> funcionarioLivreList = new ArrayList<Funcionario>();
        
        if(service instanceof Consulta)
            filter = Medico.class;
        else if(service instanceof Exame)
            filter = Tecnico.class;
        
        ArrayList<Funcionario> filtredFuncionarioList = this.filterFuncionarioList(filter);
        
        if(service instanceof Consulta)
        {
            // Se for uma consulta, preciso encontrar o médico da consulta inicial
            for(Funcionario f : filtredFuncionarioList)
            {
                if(((Medico)f).isReturn(service)) // Verifico se é uma consulta de retorno para algum médico
                    return f;
            }
        }

        for(Funcionario f : filtredFuncionarioList) // Percorro a lista filtrada de funcionários para decidir quem tem horários disponíveis
        {
            Agenda agendaFunc = f.findAgenda(service.getDataInicio());
            
            if(agendaFunc == null) // O funcionário não tem nenhum serviço para este dia, logo, pode ser ele
                funcionarioLivreList.add(f);
            else
            {
                for(Servico s : agendaFunc.getServicoList())
                {
                    if(!s.getDataInicio().equals(service.getDataInicio())) // Funcionário está com o horário livre
                        funcionarioLivreList.add(f);
                }
            }
        }
        
        if(funcionarioLivreList.size() == 1)
        {
            // Se eu só tenho um funcionário com esse horário livre, retorno ele
            return funcionarioLivreList.get(0);
        }
        else
        {
            if(funcionarioLivreList.size() > 1)
            {
                // Preciso definir quem tem a menor quantidade de serviços para o dia
                return this.getFuncionarioMinServico(service.getDataInicio(), funcionarioLivreList);
            }
            else
            {
                // Nenhum funcionário tem o horário livre, escolho um com menor quantidade de serviços para realocar o horário
                return this.getFuncionarioMinServico(service.getDataInicio(), filtredFuncionarioList);
            }
        }
    }
    
    /**
     * Filtra a lista de todos os funcionários de acordo com a classe do filtro
     * 
     * @param filter Classe do tipo de funcionário buscado
     * @return Lista de funcionários filtrada pelo tipo
     */
    private ArrayList<Funcionario> filterFuncionarioList(Class filter)
    {
        ArrayList<Funcionario> filterList = new ArrayList<Funcionario>();
        
        for(Funcionario f : funcionarioList) // Percorro a lista de funcionários
        {
            if(f.getClass().isAssignableFrom(filter)) // Verifico se o funcionário é do tipo do funcionário desejado
            {
                filterList.add(f);
            }
        }

        return filterList;
    }
    
    /**
     * Retorna um funcionário de acordo com o filtro especificado com a menor quantidade de serviços para uma dada agenda
     * 
     * @param date Data da agenda
     * @param filter Classe do tipo de funcionário buscado
     * @return Funcionário com a menor quantidade de serviços para uma dada agenda
     */
    private Funcionario getFuncionarioMinServico(Date date, ArrayList<Funcionario> funcList)
    {
        System.out.println("Count: " + funcList.size());
        
        Funcionario min = (funcList.size() > 0) ? funcList.get(0) : null;
        
        for(Funcionario f : funcList) // Percorro a lista de funcionários
        {
            if(f.getServicoListCount(date) < min.getServicoListCount(date)) // Verifico a quantidade de serviços
                min = f;
        }
        
        return min;
    }
}
