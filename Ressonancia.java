import java.util.Date;

/**
 * Write a description of class Ressonancia here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ressonancia extends Exame
{
    public Ressonancia(Tecnico tecnico, Paciente paciente, Date dataInicio)
    {
        super(tecnico, paciente, dataInicio, 30);
    }
}
