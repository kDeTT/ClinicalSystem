
/**
 * Write a description of class Exame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Exame extends Servico
{
    private Tecnico tecnico;
    private Paciente paciente;
    
    public Exame(Tecnico tecnico, Paciente paciente, int inicio, int duracao)
    {
        super(inicio, duracao);
        
        this.tecnico = tecnico;
        this.paciente = paciente;
    }
    
    public Funcionario getFuncionario()
    {
        return this.tecnico;
    }
    
    public Paciente getPaciente()
    {
        return this.paciente;
    }
}
