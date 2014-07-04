import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Date;
import Exceptions.*;

/**
 * Classe que trata arquivos de saída para agendamentos
 * 
 * @author Luis Augusto
 */
public class AgendaFileHelper extends FileHelper
{
    /**
     * Escreve o arquivo de agendamentos
     * 
     * @param filePath Caminho do arquivo de saída
     * @param funcionarioList Lista de funcionários do sistema
     * @return (true) Se o arquivo é salvo com sucesso; (false) Se o processo falha
     */
    // TODO - Devo tratar essas exceções e não lançar para o nível acima!
    public boolean writeFile(String filePath, ArrayList<Funcionario> funcionarioList) throws AgendaException
    {
        try
        {
            try (FileWriter writer = new FileWriter(filePath); BufferedWriter buffer = new BufferedWriter(writer);) // Try-with-resources
            {
                // Lista de agendas filtradas
                ArrayList<Agenda> filtredAgendaList = new ArrayList<Agenda>();
                
                for(int i = 0; i < funcionarioList.size(); i++) // Percorro a lista de funcionários
                {
                    // Pego a lista de agendas do funcionário
                    ArrayList<Agenda> funcAgendaList = funcionarioList.get(i).getAgendaList();
                
                    for(int j = 0; j < funcAgendaList.size(); j++) // Percorro a lista de agendas
                    {
                        // Verifico se a lista de agendas filtradas já não contém uma agenda da data da agenda do funcionário
                        if(!containsAgenda(funcAgendaList.get(j).getData(), filtredAgendaList))
                        {
                            // Se não contém, adiciono uma nova agenda com os serviço de todos os funcionários que tem a mesma agenda
                            Agenda filtredAgenda = filterAgenda(funcAgendaList.get(j).getData(), funcionarioList);
                            filtredAgendaList.add(filtredAgenda);
                        }
                        
                        // Se já contém, eu não preciso fazer nada, porque todos os serviços já foram filtrados para aquela agenda
                    }
                }

                // Percorro a lista das agendas filtradas
                for(Agenda agenda : filtredAgendaList)
                {
                    buffer.write(appendLine(String.format("Dia %s", DateHelper.getDate(agenda.getData())), 2)); // Adiciono a data da agenda no arquivo
                    ArrayList<Servico> servicoList = agenda.getServicoList(); // Pego a lista de serviços da agenda
                    
                    for(Servico servico : servicoList) // Percorro a lista de serviços
                    {
                        // Adiciona o tipo de serviço
                        if(servico instanceof Consulta)
                            buffer.write(appendLine(CONSULTA_PATTERN, 1));
                        else
                            buffer.write(appendLine(EXAME_PATTERN, 1));
                    
                        // Adiciona informações do serviço
                        buffer.write(appendLine(servico.getPaciente().getNome(), 1)); // Nome do paciente
                        buffer.write(appendLine(String.valueOf(servico.getPaciente().getIdade()), 1)); // Idade do paciente
                        buffer.write(appendLine(servico.getClass().getName(), 1)); // Tipo de serviço
                        buffer.write(appendLine(servico.getFuncionario().getNome(), 1)); // Nome do funcionário
                        buffer.write(appendLine(DateHelper.getTime(servico.getDataInicio()), 2)); // Data de início do serviço
                    }
                }
            
                buffer.flush();
            }
            
            return true;
        }
        catch(java.io.IOException ex)
        {
            System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
        }
        
        return false;
    }
    
    /**
     * Retorna uma agenda com todos os serviços de funcionários que tem a mesma agenda
     * 
     * @param data Data da agenda a ser filtrada
     * @param funcionarioList Lista de funcionários
     * @return Agenda filtrada
     * @throws AgendaException
     */
    private Agenda filterAgenda(Date data, ArrayList<Funcionario> funcionarioList) throws AgendaException
    {
        Agenda filtredAgenda = new Agenda(data); // Crio uma nova agenda
        
        for(Funcionario funcionario : funcionarioList) // Percorro a lista de funcionários
        {
            Agenda agenda = funcionario.findAgenda(data); // Busco uma agenda do funcionário com a data que eu quero
            
            if(agenda != null) // Se existir uma agenda
            {
                ArrayList<Servico> servicoList = agenda.getServicoList(); // Pego a lista de serviços da agenda encontrada
                
                for(Servico servico : servicoList) // Adiciono todos os serviços para a agenda filtrada
                {
                    filtredAgenda.addServico(servico);
                }
            }
        }
        
        return filtredAgenda;
    }
    
    /**
     * Verifica se uma agenda está contida em uma lista de agendas
     * 
     * @param data Data para busca
     * @param agendaList Lista de agendas para busca
     * @return (true) Se a lista de agendas contém uma agenda com a data buscada; (false) Se não contém a agenda
     */
    private boolean containsAgenda(Date data, ArrayList<Agenda> agendaList)
    {
        DateHelper dateHelp = new DateHelper();
        
        for(Agenda agenda : agendaList) // Percorro a lista de agendas
        {
            if(dateHelp.compareDay(agenda.getData(), data)) // Comparo se os dias das agendas conferem
                return true;
        }
        
        return false;
    }
}
