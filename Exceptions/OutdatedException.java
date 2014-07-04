package Exceptions;


/**
 * Write a description of class OldDateException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OutdatedException extends AgendaException
{
    public OutdatedException()
    {
        super("Agendamento > Não é possível fazer um agendamento para datas anteriores as atuais!");
    }
}
