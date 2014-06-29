import java.util.Date;

/**
 * Classe que representa um serviço do tipo Consulta
 * 
 * @author Luis Augusto
 */
public abstract class Consulta extends Servico
{
    public Consulta(Medico medico, Paciente paciente, Date dataInicio, int duracao)
    {
        super(dataInicio, duracao, medico, paciente);
    }
}
