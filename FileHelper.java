/**
 * Classe abstrata que contém constantes e métodos que auxiliam no processamento dos arquivos do sistema
 * 
 * @author Luis Augusto
 */
public abstract class FileHelper
{
    public final String CONSULTA_PATTERN = "CONSULTA";
    public final String EXAME_PATTERN = "EXAME";
    
    /**
     * Faz quebras de linha após o texto
     * 
     * @param text Linha de texto
     * @param lineNumber Quantidade de quebras de linha que serão adicionadas ao final do texto
     * @return Texto com quebras de linha
     */
    public String appendLine(String text, int lineNumber)
    {
        StringBuilder strBuilder = new StringBuilder();
        
        for(int i = 0; i < lineNumber; i++)
            strBuilder.append(System.getProperty("line.separator")); // Adiciono quebras de linha ao final do texto
        
        return String.format("%s%s", text, strBuilder.toString()); // Retorno o texto com as quebras de linha
    }
}
