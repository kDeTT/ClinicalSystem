
/**
 * Write a description of class Servico here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Servico
{
    private int dataInicio; // Data de início do serviço
    private int duracao; // Duração do serviço
    private int dataFim; // Data do fim do serviço
    private boolean status; // Se o serviço está agendado (true), senão (false)
    
    public abstract Funcionario getFuncionario();
    
    public abstract Paciente getPaciente();
    
    public Servico(int dataInicio, int duracao)
    {
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.dataFim = dataInicio + this.duracao;
    }
    
    public int getDataInicio()
    {
        return this.dataInicio;
    }
    
    public void setDataInicio(int dataInicio)
    {
        this.dataInicio = dataInicio;
    }
    
    public int getDataFim()
    {
        return this.dataFim;
    }
    
    public void setDataFim(int dataFim)
    {
        this.dataFim = dataFim;
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
