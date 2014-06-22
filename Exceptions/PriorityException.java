package Exceptions;


/**
 * Write a description of class PriorityException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PriorityException extends AgendaException
{
    public PriorityException()
    {
        super("Um idoso não tem prioridade sobre outro idoso!");
    }
}
