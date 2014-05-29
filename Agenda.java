
/**
 * Write a description of class Agendamento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class Agenda
{
    private int data; // Data da agenda
    private ArrayList<Servico> servicoList;
    
    public Agenda()
    {
        this.servicoList = new ArrayList<Servico>();
    }
    
    public void addServico(Servico servico) // TODO
    {
        this.servicoList.add(servico); // Tratar conflitos de horário
    }
    
    public int getData()
    {
        return this.data;
    }
    
    public ArrayList<Servico> getServicoList()
    {
        return this.servicoList;
    }
    
    public void setServicoList(ArrayList<Servico> servicoList)
    {
        this.servicoList = servicoList;
    }
}
