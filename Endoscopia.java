import java.util.Date;

/**
 * Classe que representa um exame do tipo Endoscopia
 * 
 * @author Luis Augusto
 */
public class Endoscopia extends Exame
{
    public Endoscopia(Tecnico tecnico, Paciente paciente, Date dataInicio)
    {
        super(tecnico, paciente, dataInicio, 30);
    }
}
