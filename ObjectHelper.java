import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import Exceptions.*;

/**
 * Classe que instancia objetos pelo nome da classe
 * 
 * @author Luis Augusto
 */
public class ObjectHelper
{
    /**
     * Retorna um objeto da classe instanciada caso ela seja encontrada e se existir parâmetros do construtor compatíveis. 
     * Faz uso de Reflection e genéricos.
     * 
     * @param className Nome da classe a ser instanciada
     * @param params Parâmetros do construtor da classe a ser instanciada
     * @return Objeto da classe
     * @throws ClassNotFoundException
     */
    public static <T> T createObject(String className, Object... params) throws ClassNotFoundException, ReflectionInstantiationException
    {
        try
        {
            Class objClass = Class.forName(className); // Procuro uma classe pelo nome
            return (T)getConstructor(objClass, params); // Busco por um construtor compatível e retorno um objeto instanciado
        }
        catch(InstantiationException | IllegalAccessException | InvocationTargetException ex) // Multicatch
        {
            throw new ReflectionInstantiationException(ex.getMessage());
        }
    }
    
    /**
     * Busca por um construtor da classe compatível com os parâmetros
     * 
     * @param objClass Tipo da classe
     * @param params Parâmetros do construtor da classe
     * @return Objeto instanciado
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static Object getConstructor(Class objClass, Object[] params) throws InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Constructor[] constructorList = objClass.getConstructors(); // Pego todos os construtores da classe

        for(int i = 0; i < constructorList.length; i++) // Percorro a lista de construtores da classe
        {
            Constructor ctor = constructorList[i];
            
            if(constructorList[i].getParameterTypes().length == params.length) // Se a quantidade de parâmetros confere
                return constructorList[i].newInstance(params); // Tento instanciar o objeto
        }
        
        return null; // Se não encontrar nenhum construtor compatível, retorno nulo
    }
}
