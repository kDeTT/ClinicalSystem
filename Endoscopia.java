import java.util.Date;

/**
 * Write a description of class Endoscopia here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Endoscopia extends Exame
{
    public Endoscopia(Tecnico tecnico, Paciente paciente, Date dataInicio)
    {
        super(tecnico, paciente, dataInicio, 30);
    }
}
