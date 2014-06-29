import java.util.Date;

/**
 * Classe que representa uma consulta do tipo Inicial
 * 
 * @author Luis Augusto
 */
public class Inicial extends Consulta
{
    public Inicial(Medico medico, Paciente paciente, Date dataInicio)
    {
        super(medico, paciente, dataInicio, 30);
    }
}