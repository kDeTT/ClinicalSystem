
/**
 * Write a description of class ObjectHelper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectHelper
{
    public static <T> T createObject(String className, Object... params) throws ClassNotFoundException
    {
        try
        {
            Class objClass = Class.forName(className);
            return (T)getConstructor(objClass, params);
        }
        catch(InstantiationException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch(IllegalAccessException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch(InvocationTargetException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    private static Object getConstructor(Class objClass, Object[] params) throws InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Constructor[] constructorList = objClass.getConstructors();
        
        for(int i = 0; i < constructorList.length; i++)
        {
            Constructor ctor = constructorList[i];
            
            if(constructorList[i].getParameterTypes().length == params.length)
                return constructorList[i].newInstance(params);
        }
        
        return null;
    }
}
