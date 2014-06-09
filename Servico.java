import java.util.Date;

/**
 * Write a description of class Servico here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Servico
{
    private Date dataInicio; // Data de início do serviço
    private int duracao; // Duração do serviço
    private Date dataFim; // Data do fim do serviço
    private boolean status; // Se o serviço está agendado (true), senão (false)
    private DateHelper dateHelper;
    
    public abstract Funcionario getFuncionario();
    
    public abstract Paciente getPaciente();
    
    public Servico(Date dataInicio, int duracao)
    {
        dateHelper = new DateHelper();
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.dataFim = dateHelper.addMinutes(dataInicio, duracao);
    }
    
    public Date getDataInicio()
    {
        return this.dataInicio;
    }
    
    public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
    }
    
    public Date getDataFim()
    {
        return this.dataFim;
    }
    
    public void setDataFim(Date dataFim)
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
