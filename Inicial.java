import java.util.Date;

/**
 * Write a description of class ConsultaInicial here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inicial extends Consulta
{
    public Inicial(Medico medico, Paciente paciente, Date dataInicio)
    {
        super(medico, paciente, dataInicio, 30);
    }
}