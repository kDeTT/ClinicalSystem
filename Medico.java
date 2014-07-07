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
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  Date data       data da agenda
     * @param  Servico servico serviço a ser agendado
     * 
     * @return  boolean     true se o serviço foi adicionado, false caso contrário
     */
    @Override
    public boolean addServico(Date data, Servico servico) throws AgendaException
    {
        if(servico instanceof Consulta) // Verifico se o serviço é do tipo Consulta
        {
            if(isReturn(servico)) // Verifico se é uma consulta de Retorno
                servico = new Retorno((Medico)servico.getFuncionario(), servico.getPaciente(), servico.getDataInicio());
        }

        return super.addServico(data, servico);
    }
    
    /**
     * Dado um serviço é verificado se com o mesmo médico há registro de visitas em até 20 dias anteriores, se sim a consulta é de retorno e vice-versa
     *
     * @param  Servico servico serviço a ser interpretado
     * 
     * @return boolean  true se a consulta for de retorno
     */
    public boolean isReturn(Servico servico)
    {
        if(servico == null)
            return false;
            
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
                        if(dateHelper.isInRangeBefore(s.getDataInicio(), servico.getDataInicio(), 20))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
}
