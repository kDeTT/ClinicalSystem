
/**
 * Write a description of class Agendamento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public abstract class Agenda
{
    private int data; // Data da agenda
    private ArrayList<Servico> servicoList;
    
    public Agenda()
    {
        this.servicoList = new ArrayList<Servico>();
    }
}
