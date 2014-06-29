import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Date;
import Exceptions.*;

/**
 * Classe que trata arquivos de entrada no sistema
 * 
 * @author Luis Augusto
 */
public class InputFileHelper extends FileHelper
{
    // Quantidade de dados a serem lidos em um serviço de exame
    private static final int EXAME_DATA_LENGTH = 5;
    
    // Quantidade de dados a serem lidos em um serviço de consulta
    private static final int CONSULTA_DATA_LENGTH = 4;
    
    /**
     * Lê um arquivo de entrada do sistema
     * 
     * @param filePath Caminho do arquivo de entrada
     * @return Lista de serviços para serem agendados
     * @throws ReflectionException
     */
    public ArrayList<Servico> readFile(String filePath) throws ReflectionException
    {
        try
        {
            // Lista de serviços para agendamento
            ArrayList<Servico> servicoList = new ArrayList<Servico>();
            
            try (FileReader reader = new FileReader(filePath); BufferedReader buffer = new BufferedReader(reader)) // Try-with-resources
            {
                String line;

                while((line = buffer.readLine()) != null) // Lê o arquivo até o fim
                {
                    ArrayList<String> data = new ArrayList<String>(); // Lista com os dados de cada serviço
                
                    int dataRead = 0; // Quantidade de dados lidos
                    
                    // Define o tamanho dos dados que devem ser lidos de acordo com o serviço
                    int dataLength = (line.trim().equalsIgnoreCase(EXAME_PATTERN)) ? EXAME_DATA_LENGTH : CONSULTA_DATA_LENGTH;
                
                    while(dataRead <= dataLength) // Lê a quantidade de linhas de acordo com o serviço
                    {
                        if ((line != null) && !line.equals("")) // Ignora linhas vazias
                        {
                            data.add(line.trim()); // Adiciona um novo dado para o serviço
                            dataRead++; // Incrementa a quantidade de dados lidos para o serviço
                        }
                    
                        line = buffer.readLine(); // Lê uma nova linha
                    }

                    // Define qual é o tipo de serviço para instanciação
                    String tipoServico = (dataLength == EXAME_DATA_LENGTH) ? data.get(1) : "Inicial";
                    
                    // Define qual é o nome do paciente
                    String nomePaciente = (dataLength == EXAME_DATA_LENGTH) ? data.get(2) : data.get(1);
                    
                    // Define a idade do paciente
                    int idadePaciente = (dataLength == EXAME_DATA_LENGTH) ? Integer.parseInt(data.get(3)) : Integer.parseInt(data.get(2));
                    
                    // Define a data inicial do serviço
                    DateHelper dateHelp = new DateHelper();
                    Date dateInicio = (dataLength == EXAME_DATA_LENGTH) ?  dateHelp.stringToData(data.get(4), data.get(5)) : dateHelp.stringToData(data.get(3), data.get(4));

                    try
                    {
                        // Adiciona um novo serviço para agendamento
                        servicoList.add((Servico)ObjectHelper.createObject(tipoServico, new Object[] { null, new Paciente(nomePaciente, idadePaciente), dateInicio }));
                    }
                    catch(ClassNotFoundException ex)
                    {
                        throw new ReflectionClassNotFoundException(ex.getMessage());
                    }
                    catch(ReflectionInstantiationException ex)
                    {
                        throw new ReflectionInstantiationException(ex.getMessage());
                    }
                }
            }
            
            return servicoList;
        }
        catch(java.io.IOException ex)
        {
            System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
        }

        return null;
    }
}
