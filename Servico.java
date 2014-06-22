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
    
    private Funcionario funcionario;
    private Paciente paciente;
    
    private boolean status; // Se o serviço está agendado (true), senão (false)
    private DateHelper dateHelper;
    
    public Servico(Date dataInicio, int duracao, Funcionario funcionario, Paciente paciente)
    {
        dateHelper = new DateHelper();
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.dataFim = dateHelper.addMinutes(dataInicio, duracao);
        
        this.funcionario = funcionario;
        this.paciente = paciente;
    }
    
    public Date getDataInicio()
    {
        return this.dataInicio;
    }
    
    public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
        dataFim = dateHelper.addMinutes(dataInicio, duracao);
    }
    
    public int getDuracao()
    {
        return this.duracao;
    }
    
    public void setDuracao(int duracao)
    {
        this.duracao = duracao;
    }
    
    public Date getDataFim()
    {
        return this.dataFim;
    }
    
    public void setDataFim(Date dataFim)
    {
        this.dataFim = dataFim;
        dataInicio = dateHelper.addMinutes(dataFim, -duracao);
    }
    
    public Funcionario getFuncionario()
    {
        return this.funcionario;
    }
    
    public void setFuncionario(Funcionario f)
    {
        this.funcionario = f;
    }
    
    public Paciente getPaciente()
    {
        return this.paciente;
    }
    
    public void setPaciente(Paciente p)
    {
        this.paciente = p;
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
