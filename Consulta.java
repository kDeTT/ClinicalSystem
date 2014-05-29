
/**
 * Write a description of class Consulta here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Consulta extends Servico
{
    private Medico medico;
    private Paciente paciente;
    
    public Consulta(Medico medico, Paciente paciente, int dataInicio, int duracao)
    {
        super(dataInicio, duracao);
        
        this.medico = medico;
        this.paciente = paciente;
    }
    
    public Funcionario getFuncionario()
    {
        return this.medico;
    }
    
    public Paciente getPaciente()
    {
        return this.paciente;
    }
}
