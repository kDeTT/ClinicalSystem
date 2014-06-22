package Exceptions;


/**
 * Write a description of class ClinicalException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ClinicalSystemException extends Exception
{
    public ClinicalSystemException(String errorMessage)
    {
        super(errorMessage);
    }
}
