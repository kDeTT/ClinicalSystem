package Exceptions;


/**
 * Write a description of class OutOfBussinessException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OutOfBusinessException extends AgendaException
{
    public OutOfBusinessException()
    {
        super("OutOfBusinessException > Não é possível fazer um agendamento para horários não comerciais!");
    }
}
