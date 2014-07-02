import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe que trata arquivos de saída para cancelamentos
 * 
 * @author Luis Augusto
 */
public class LogFileHelper extends FileHelper
{
    /**
     * Escreve o arquivo de cancelamentos
     * 
     * @param filePath Caminho do arquivo de saída
     * @param funcionarioList Lista de funcionários do sistema
     * @return (true) Se o arquivo é salvo com sucesso; (false) Se o processo falha
     */
    public boolean writeFile(String filePath, ArrayList<Funcionario> funcionarioList)
    {
        try
        {
            try (FileWriter writer = new FileWriter(filePath); BufferedWriter buffer = new BufferedWriter(writer);) // Try-with-resources
            {
                buffer.write(appendLine("Cancelamentos", 2)); // Adiciona título ao arquivo de saída
                
                // Pega a lista de todos os serviços cancelados no sistema
                ArrayList<Servico> cancelamentoList = this.getAllCancelamentoList(funcionarioList);
                
                for(Servico service : cancelamentoList)
                {
                    // Adiciona o tipo de serviço que foi cancelado
                    if(service instanceof Consulta)
                        buffer.write(appendLine(CONSULTA_PATTERN, 1));
                    else
                        buffer.write(appendLine(EXAME_PATTERN, 1));
                    
                    // Adiciona informações do serviço que cancelado
                    buffer.write(appendLine(service.getPaciente().getNome(), 1)); // Nome do paciente
                    buffer.write(appendLine(String.valueOf(service.getPaciente().getIdade()), 1)); // Idade do paciente
                    buffer.write(appendLine(service.getClass().getName(), 1)); // Tipo de serviço
                    buffer.write(appendLine(DateHelper.getDate(service.getDataInicio()), 1)); // Data inicial do serviço
                    buffer.write(appendLine(DateHelper.getTime(service.getDataInicio()), 2)); // Data inicial do serviço
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
     * Retorna a lista de todos os serviços cancelados
     * 
     * @param funcionarioList Lista de funcionários do sistema
     * @return Lista de serviços cancelados
     */
    private ArrayList<Servico> getAllCancelamentoList(ArrayList<Funcionario> funcionarioList)
    {
        ArrayList<Servico> cancelamentoList = new ArrayList<Servico>(); // Lista de cancelamentos
            
        for(int i = 0; i < funcionarioList.size(); i++) // Percorre todos os funcionários
        {
            ArrayList<Agenda> funcAgendaList = funcionarioList.get(i).getAgendaList(); // Pega a lista de agendas do funcionário
                
            for(int j = 0; j < funcAgendaList.size(); j++) // Percorre a lista de agendas do funcionário
            {
                // Pega a lista de serviços de uma agenda do funcionário
                ArrayList<Servico> agendaServicoList = funcAgendaList.get(j).getServicoList();
                        
                for(Servico service : agendaServicoList) // Percorre todos os serviços da agenda
                {
                    if(!service.getStatus()) // Verifica se o status do serviço está como (false), ou seja, cancelado
                    {
                        cancelamentoList.add(service); // Se estiver, adiciona na lista de cancelados
                    }
                }
            }
        }
        
        return cancelamentoList;
    }
}
