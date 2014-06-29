import java.util.Date;


/**
 * Classe abstrata que representa um serviço do tipo exame
 * 
 * @author Luis Augusto
 */
public abstract class Exame extends Servico
{
    public Exame(Tecnico tecnico, Paciente paciente, Date dataInicio, int duracao)
    {
        super(dataInicio, duracao, tecnico, paciente);
    }
}
