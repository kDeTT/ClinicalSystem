import java.util.Date;

/**
 * Write a description of class Consulta here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Consulta extends Servico
{
    public Consulta(Medico medico, Paciente paciente, Date dataInicio, int duracao)
    {
        super(dataInicio, duracao, medico, paciente);
    }
}
