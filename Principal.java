/**
 * Classe principal do sistema
 * 
 * @author Luis Augusto
 */

import Exceptions.*;

public class Principal
{
    private static Clinica c;
    
    static
    {
        c = new Clinica();
    }

    public static void main(String args[])
    {
        try
        {
            c.cadastrarFuncionario(new Medico("Sandra"));
            /*c.cadastrarFuncionario(new Medico("André"));
            c.cadastrarFuncionario(new Medico("Gustavo"));
            c.cadastrarFuncionario(new Medico("Ana"));
        
            c.cadastrarFuncionario(new Tecnico("Jonas"));
            c.cadastrarFuncionario(new Tecnico("Henrique"));*/
            
            c.agendarByFile("Dados/dados.txt");
            c.saveAgenda();
            c.saveCancelamento();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void cadastrarFuncionarios() throws FuncionarioExistsException
    {
        c.cadastrarFuncionario(new Medico("Sandra"));
        c.cadastrarFuncionario(new Medico("André"));
        c.cadastrarFuncionario(new Medico("Gustavo"));
        c.cadastrarFuncionario(new Medico("Ana"));
        
        c.cadastrarFuncionario(new Tecnico("Jonas"));
        c.cadastrarFuncionario(new Tecnico("Henrique"));
    }
    
    public void agendarByFile(String filePath) throws AgendaException, ReflectionException
    {
        c.agendarByFile(filePath);
        c.saveAgenda();
        c.saveCancelamento();
    }
}
