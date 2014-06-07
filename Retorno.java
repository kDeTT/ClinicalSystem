/**
 * Write a description of class ConsultaRetorno here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Retorno extends Consulta
{
    public Retorno(Medico medico, Paciente paciente, int dataInicio)
    {
        super(medico, paciente, dataInicio, 20);
    }
}