
/**
 * Write a description of class FileHelper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class FileHelper
{
    private final String CONSULTA_PATTERN = "CONSULTA";
    private final int CONSULTA_DATA_LENGTH = 4;
    
    private final String EXAME_PATTERN = "EXAME";
    private final int EXAME_DATA_LENGTH = 5;
    
    public ArrayList<Servico> readFile(String filePath)
    {
        ArrayList<Servico> servicoList = new ArrayList<Servico>();
        
        Servico servico = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        String line = null;
        
        try
        {
            reader = new FileReader(filePath);
            buffer = new BufferedReader(reader);
            line = buffer.readLine();
            int dataLength = (((line != null) && line.toUpperCase().equals(CONSULTA_PATTERN)) ? CONSULTA_DATA_LENGTH : EXAME_DATA_LENGTH);
            
            while(line != null) // Lê o arquivo até o fim
            {
                String data[] = new String[dataLength];
                    
                for(int i = 0; i < data.length; i++) // Lê os dados do serviço
                {
                    data[i] = buffer.readLine();
                }
                
                if(data.length == CONSULTA_DATA_LENGTH) // É um serviço do tipo Consulta
                {
                    String nomePaciente = data[0];
                    int idadePaciente = Integer.parseInt(data[1]);
                    
                    servico = new ConsultaInicial(new Medico("Nome do médico"), new Paciente(nomePaciente, idadePaciente), 0);
                }
                else // É um serviço do tipo Exame
                {
                    String nomePaciente = data[1];
                    int idadePaciente = Integer.parseInt(data[2]);
                    
                    servico = new Endoscopia(new Tecnico("Nome do técnico"), new Paciente(nomePaciente, idadePaciente), 0);
                }
                
                servicoList.add(servico);

                line = buffer.readLine();
                dataLength = (((line != null) && line.toUpperCase().equals(CONSULTA_PATTERN)) ? CONSULTA_DATA_LENGTH : EXAME_DATA_LENGTH);
            }
            
            buffer.close();
            reader.close();
            
            return servicoList;
        }
        catch(java.io.IOException ex)
        {
            System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
        }
        
        return null;
    }
    
    public boolean writeFile(String filePath, ArrayList<Agenda> agendaList)
    {
        FileWriter writer = null;
        BufferedWriter buffer = null;
        
        try
        {
            writer = new FileWriter(filePath);
            buffer = new BufferedWriter(writer);
            
            for(Agenda agenda : agendaList)
            {
                buffer.write("<Data do serviço>");
                buffer.newLine();
                buffer.newLine();
                
                ArrayList<Servico> servicoList = agenda.getServicoList();
                
                for(Servico servico : servicoList)
                {
                    if(servico instanceof Consulta)
                        buffer.write("Consulta");
                    else
                        buffer.write("Exame");
                    
                    buffer.newLine();
                    buffer.write(servico.getPaciente().getNome());
                    buffer.newLine();
                    buffer.write(String.valueOf(servico.getPaciente().getIdade()));
                    buffer.newLine();
                    buffer.write(servico.getClass().getName());
                    buffer.newLine();
                    buffer.write(servico.getFuncionario().getNome());
                    buffer.newLine();
                    buffer.write("<Hora do serviço>");
                    buffer.newLine();
                    buffer.newLine();
                }
                
                buffer.newLine();
                buffer.newLine();
            }
            
            buffer.flush();
            buffer.close();
            writer.close();
            
            return true;
        }
        catch(java.io.IOException ex)
        {
            System.out.println(String.format("Não foi possível ler o arquivo. Mensagem: %s", ex.getMessage()));
        }
        
        return false;
    }
    
    public boolean testWrite(String filePath, Agenda agenda)
    {
        ArrayList<Agenda> agendaList = new ArrayList<Agenda>();
        agendaList.add(agenda);
        
        return this.writeFile(filePath, agendaList);
    }
}
