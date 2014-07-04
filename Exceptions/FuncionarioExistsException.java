package Exceptions;


/**
 * Write a description of class FuncionarioExistsException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FuncionarioExistsException extends FuncionarioException
{
    public FuncionarioExistsException(String funcName)
    {
        super(String.format("Cadastramento de funcionário > %s já está cadastrado(a)!", funcName));
    }
}
