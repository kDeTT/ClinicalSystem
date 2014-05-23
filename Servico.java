
/**
 * Write a description of class Servico here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Servico
{
    private int inicio;
    private int duracao; // Duração do exame em minutos
    private int fim;
    private boolean status;
    
    public Servico(int inicio, int duracao)
    {
        this.inicio = inicio;
        this.duracao = duracao;
    }
}
