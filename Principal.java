/**
 * Classe principal do sistema
 * 
 * @author Luis Augusto
 */
public class Principal
{
    public static void main(String args[])
    {
        try
        {
            Clinica c = new Clinica();
        
            c.cadastrarFuncionario(new Medico("Sandra"));
            c.cadastrarFuncionario(new Medico("André"));
            c.cadastrarFuncionario(new Medico("Gustavo"));
            c.cadastrarFuncionario(new Medico("Ana"));
        
            c.cadastrarFuncionario(new Tecnico("Jonas"));
            c.cadastrarFuncionario(new Tecnico("Henrique"));
        
            c.agendarByFile("Dados/dados.txt");
            c.saveAgenda();
            c.saveCancelamento();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
