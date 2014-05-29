
/**
 * Write a description of class Clinica here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class Clinica
{
    private final String AGENDA_FILE_PATH = "Dados/agenda.txt";
    
    private ArrayList<Funcionario> funcionarioList;
    
    public Clinica()
    {
        this.funcionarioList = new ArrayList<Funcionario>();
    }
    
    public void cadastrarFuncionario(Funcionario funcionario)
    {
        if(!funcionarioList.contains(funcionario))
        {
            this.funcionarioList.add(funcionario);
            System.out.println("Funcionário cadastrado com sucesso!");
        }
        else
            System.out.println("Funcionário já está cadastrado!");
    }
    
    public void saveAgenda()
    {
        this.saveAgenda(AGENDA_FILE_PATH);
    }
    
    public void saveAgenda(String filePath)
    {
        FileHelper helper = new FileHelper();
        
        if(helper.writeFile(filePath, funcionarioList))
            System.out.println("Agenda da clínica salva com sucesso!");
    }
}
