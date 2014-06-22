
/**
 * Write a description of class Principal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Principal
{
    public static void main(String args[])
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
    }
}
