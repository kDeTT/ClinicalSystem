
/**
 * Write a description of class Servico here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Servico
{
    private int dataInicio; // Data de início do serviço
    private int dataFim; // Data do fim do serviço
    private boolean status; // Se o serviço está agendado (true), senão (false)
    
    public abstract Funcionario getFuncionario();
    public abstract Paciente getPaciente();
    
    public Servico(int dataInicio, int duracao)
    {
        this.dataInicio = dataInicio;
        this.dataFim = dataInicio + duracao;
    }
    
    public int getDataInicio()
    {
        return this.dataInicio;
    }
    
    public int getDataFim()
    {
        return this.dataFim;
    }
    
    public boolean getStatus()
    {
        return this.status;
    }
    
    public void setStatus(boolean status)
    {
        this.status = status;
    }
}
