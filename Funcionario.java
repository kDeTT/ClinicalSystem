
/**
 * Write a description of class Funcionarios here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public abstract class Funcionario
{
    private String nome;
    private ArrayList<Agenda> agendaList;
    
    public Funcionario(String nome)
    {
        this.nome = nome;
        this.agendaList = new ArrayList<Agenda>();
    }
}
