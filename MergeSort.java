
/**
 * Write a description of class MergeSort here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.List;
import java.util.ArrayList;

public class MergeSort
{
    public static List<Servico> sort(List<Servico> servico) 
    {
        if (servico == null) {
            return null;
        }
        if (servico.size() <= 1) {
            return servico;
        }

        final int TAM = servico.size();
        final int HALF = TAM / 2;

        List<Servico> l1 = sort(servico.subList(0, HALF));
        List<Servico> l2 = sort(servico.subList(HALF, TAM));

        List<Servico> merge = merge(l1, l2);

        return merge;
    }

    private static List<Servico> merge(List<Servico> vetor1, List<Servico> vetor2) 
    {
        if (vetor1 == null || vetor1.isEmpty()) {
            return vetor2;
        }

        if (vetor2 == null || vetor2.isEmpty()) {
            return vetor1;
        }

        //Iterador dos vetores
        int it1 = 0;
        int it2 = 0;
        //Tamanho do vetor
        final int TAM1 = vetor1.size();
        final int TAM2 = vetor2.size();
        //Lista que será a união dos dois vetores
        ArrayList<Servico> merged = new ArrayList<>();
        //Servico a ser inserida
        Servico sv1;
        Servico sv2;

        while (it1 < TAM1 && it2 < TAM2) {
            sv1 = vetor1.get(it1);
            sv2 = vetor2.get(it2);
            if (sv2.getDataInicio().before(sv1.getDataInicio())) {
                merged.add(sv2);
                it2++;
            } else {
                merged.add(sv1);
                it1++;
            }
        }

        while (it1 < TAM1) {
            sv1 = vetor1.get(it1);
            merged.add(sv1);
            it1++;
        }

        while (it2 < TAM2) {
            sv2 = vetor2.get(it2);
            merged.add(sv2);
            it2++;
        }

        return merged;
    }
}
