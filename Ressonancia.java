import java.util.Date;

/**
 * Classe que representa um exame do tipo Ressonancia
 * 
 * @author Luis Augusto
 */
public class Ressonancia extends Exame
{
    public Ressonancia(Tecnico tecnico, Paciente paciente, Date dataInicio)
    {
        super(tecnico, paciente, dataInicio, 30);
    }
}
