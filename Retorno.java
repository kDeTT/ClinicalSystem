import java.util.Date;

/**
 * Classe que representa uma consulta do tipo Retorno
 * 
 * @author Luis Augusto
 */
public class Retorno extends Consulta
{
    public Retorno(Medico medico, Paciente paciente, Date dataInicio)
    {
        super(medico, paciente, dataInicio, 20);
    }
}