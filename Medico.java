import java.util.ArrayList;
import java.util.Date;
import Exceptions.*;

/**
 * Classe que representa um funcionário do tipo Médico
 * 
 * @author Luis Augusto
 */
public class Medico extends Funcionario
{
    public Medico(String nome)
    {
        super(nome);
    }
    
    @Override
    public boolean addServico(Date data, Servico servico) throws AgendaException
    {
        if(servico instanceof Consulta) // Verifico se o serviço é do tipo Consulta
        {
            servico = isReturn(servico); // Se for, verifico se é uma consulta de Retorno
        }

        return super.addServico(data, servico);
    }
    
    private Servico isReturn(Servico servico)
    {
        if(servico == null)
            return null;
            
        for(Agenda agenda : this.getAgendaList())
        {
            ArrayList<Servico> servicoList = agenda.findServicoListByPaciente(servico.getPaciente());
            
            if(servicoList!= null)
            {
                DateHelper dateHelper = new DateHelper();
                
                for(Servico s : servicoList)
                {
                    if(s instanceof Inicial)
                    {
                        if(dateHelper.isInRangeBefore(servico.getDataInicio(), s.getDataInicio(), 20))
                        {
                            return new Retorno((Medico)servico.getFuncionario(), servico.getPaciente(), servico.getDataInicio());
                        }
                    }
                }
            }
        }
        
        return servico;
    }
}
