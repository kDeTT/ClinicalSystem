import java.util.Date;

/**
 * Classe que representa um exame do tipo Biopsia
 * 
 * @author Luis Augusto
 */
public class Biopsia extends Exame
{
    public Biopsia(Tecnico tecnico, Paciente paciente, Date dataInicio)
    {
        super(tecnico, paciente, dataInicio, 20);
    }
}
