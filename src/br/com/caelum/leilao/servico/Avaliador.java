package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

import java.util.*;

/**
 * Created by ana on 22/02/18.
 */
public class Avaliador {
    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private int soma = 0;
    private double media = Double.NEGATIVE_INFINITY;
    private List<Lance> maiores;

    public void avalia(Leilao leilao){
        if(leilao.getLances().size() ==0)
            throw new RuntimeException("Não é possível avaliar um leilão sem lances");

        for (Lance lance : leilao.getLances()){
            soma += lance.getValor();
            if(lance.getValor() > maiorDeTodos) maiorDeTodos = lance.getValor();
            if(lance.getValor() < menorDeTodos) menorDeTodos = lance.getValor();
        }

        if(soma == 0) {
            media = 0;
        } else {
            media = soma / 3;
        }

        pegaOsMaioresNo(leilao);
    }

    private void pegaOsMaioresNo(Leilao leilao) {
            maiores = new ArrayList<Lance>(leilao.getLances());
            Collections.sort(maiores, new Comparator<Lance>() {
                public int compare(Lance o1, Lance o2) {
                    if(o1.getValor() < o2.getValor()) return 1;
                    if(o1.getValor() > o2.getValor()) return -1;
                    return 0;
                }
            });
            maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
    }

    public List<Lance> getTresMaiores() {
        return maiores;
    }

    public double getMaiorLance() {
        return maiorDeTodos;
    }

    public double getMenorLance() {
        return menorDeTodos;
    }

    public double getMediaLance() {
        return media;
    }
}
