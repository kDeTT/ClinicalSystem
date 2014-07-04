package Exceptions;


/**
 * Write a description of class NoWindowException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NoWindowException extends AgendaException
{
    public NoWindowException()
    {
        super("NoWindowException > Não há um espaço vago para realocar o serviço!");
    }
}
