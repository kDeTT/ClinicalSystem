import java.util.Date;

/**
 * Write a description of class Exame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Exame extends Servico
{
    public Exame(Tecnico tecnico, Paciente paciente, Date dataInicio, int duracao)
    {
        super(dataInicio, duracao, tecnico, paciente);
    }
}
