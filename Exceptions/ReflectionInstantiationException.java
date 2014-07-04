package Exceptions;


/**
 * Write a description of class ReflectionInstantiationException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ReflectionInstantiationException extends ReflectionException
{
    public ReflectionInstantiationException(String errorMsg)
    {
        super(String.format("ReflectionInstantiationException > %s", errorMsg));
    }
}
