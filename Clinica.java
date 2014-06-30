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
        
        ArrayList<Servico> servicoList = fileHelp.readFile(filePath);
        
        for(Servico servico : servicoList)
        {
            if(servico instanceof Consulta)
            {
                Funcionario servFunc = null;
                
                ArrayList<Funcionario> medicoList = this.filterFuncionarioList(Medico.class);
                
                for(Funcionario func : medicoList)
                {
                    ArrayList<Servico> servicoListByPaciente = func.findAllServicoByPaciente(servico.getPaciente());
                    
                    // Médicos só fazem consultas, se encontrou algo, é porque já tenho consultas marcadas para este médico e este paciente
                    if(servicoListByPaciente != null) 
                    {
                        servFunc = func;
                        break;
                    }
                }

                if(servFunc == null)
                {
                    servFunc = this.getMedicoMinServicoByAgenda(servico.getDataInicio());
                }
                
                if(servFunc != null)
                {
                    servico.setFuncionario(servFunc);
                    servFunc.addServico(servico.getDataInicio(), servico);
                }
                else
                    System.out.println("Nenhum médico encontrado para a consulta!");
            }
            else if(servico instanceof Exame)
            {
                Tecnico tecnico = this.getTecnicoMinServicoByAgenda(servico.getDataInicio());
                
                if(tecnico != null)
                {
                    servico.setFuncionario(tecnico);
                    tecnico.addServico(servico.getDataInicio(), servico);
                }
            }
        }
                
    }

    /**
     * Retorna o médico com a menor quantidade de serviços de uma agenda
     * 
     * @param date Data da agenda
     * @return Médico com a menor quantidade de serviços de uma agenda
     */
    public Medico getMedicoMinServicoByAgenda(Date date)
    {
        return (Medico)this.getFuncionarioMinServicoByAgenda(date, Medico.class);
    }
    
    /**
     * Retorna o técnico com a menor quantidade de serviços de uma agenda
     * 
     * @param date Data da agenda
     * @return Técnico com a menor quantidade de serviços de uma agenda
     */
    public Tecnico getTecnicoMinServicoByAgenda(Date date)
    {
        return (Tecnico)this.getFuncionarioMinServicoByAgenda(date, Tecnico.class);
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
            if(f.getClass().isAssignableFrom(filter)) // Verifico se a classe do funcionário confere com a do filtro
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
    private Funcionario getFuncionarioMinServicoByAgenda(Date date, Class filter)
    {
        Funcionario min = null;
        
        for(Funcionario f : funcionarioList) // Percorro a lista de funcionários
        {
            if(f.getClass().isAssignableFrom(filter)) // Verifico se a classe do funcionário confere com a do filtro
            {
                if(min != null)
                {
                    if(f.getServicoListCount(date) < min.getServicoListCount(date)) // Verifico a quantidade de serviços
                        min = f;
                }
                else
                    min = f;
            }
        }
        
        return min;
    }
}
