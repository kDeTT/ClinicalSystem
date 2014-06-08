
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
        
        c.cadastrarFuncionario(new Medico("Medico1"));
        c.cadastrarFuncionario(new Medico("Medico2"));
        
        c.saveAgenda();
    }
}
