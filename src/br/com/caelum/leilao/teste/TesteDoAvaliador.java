package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ana on 22/02/18.
 */
public class TesteDoAvaliador {
    Usuario maria, jose, joao;
    Leilao leilao1, leilao2;
    Avaliador leiloeiro;

    double maiorEsperado = 400.0;
    double menorEsperado = 250.0;

    @Before
    public void setUp(){
        maria = new Usuario("Maria");
        jose = new Usuario("José");
        joao = new Usuario("João");

        leilao1 = new Leilao("IPhone 6");

        leiloeiro = new Avaliador();
    }

    @Test
    public void maiorEMenorLanceDado(){
        leilao1.propoe(new Lance(maria, 400.0));
        leilao1.propoe(new Lance(jose, 250.0));
        leilao1.propoe(new Lance(joao, 300.0));

        leiloeiro.avalia(leilao1);

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void valorMedioDeLances(){
        leilao1.propoe(new Lance(maria, 400.0));
        leilao1.propoe(new Lance(jose, 250.0));
        leilao1.propoe(new Lance(joao, 300.0));

        leiloeiro.avalia(leilao1);

        double media = (400+250+300)/3;

        assertEquals(media, leiloeiro.getMediaLance(), 0.0001);
    }

    @Test
    public void valorMedioLancesZero(){
        leiloeiro.avalia(leilao1);

        assertEquals(0, leiloeiro.getMediaLance(), 0.0001);
    }

    @Test
    public void leilaoComApenasUmLance(){
        leilao1.propoe(new Lance(maria, 1000.0));

        leiloeiro.avalia(leilao1);

        assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void encontrarTresMaiores(){
        leilao1.propoe(new Lance(maria, 400.0));
        leilao1.propoe(new Lance(jose, 250.0));
        leilao1.propoe(new Lance(joao, 300.0));

        leiloeiro.avalia(leilao1);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(400.0, maiores.get(0).getValor(), 0.0001);
        assertEquals(300.0, maiores.get(1).getValor(), 0.0001);
        assertEquals(250.0, maiores.get(2).getValor(), 0.0001);
    }

    @Test
    public void lancesOrdemAleatoria(){
        leilao1.propoe(new Lance(maria, 200.0));
        leilao1.propoe(new Lance(maria, 450.0));
        leilao1.propoe(new Lance(maria, 120.0));
        leilao1.propoe(new Lance(maria, 700.0));
        leilao1.propoe(new Lance(maria, 630.0));
        leilao1.propoe(new Lance(maria, 230.0));

        leiloeiro.avalia(leilao1);

        List<Lance> lances = leilao1.getLances();

        assertEquals(6, lances.size());
        assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
    }

    @Test
    public void lancesOredemDecrescente(){
        leilao1.propoe(new Lance(maria, 400.0));
        leilao1.propoe(new Lance(maria, 300.0));
        leilao1.propoe(new Lance(maria, 200.0));
        leilao1.propoe(new Lance(maria, 100.0));

        leiloeiro.avalia(leilao1);

        List<Lance> lances = leilao1.getLances();

        assertEquals(4, lances.size());
        assertEquals(400.0, lances.get(0).getValor(), 0.0001);
        assertEquals(300.0, lances.get(1).getValor(), 0.0001);
        assertEquals(200.0, lances.get(2).getValor(), 0.0001);
        assertEquals(100.0, lances.get(3).getValor(), 0.0001);
    }

    @Test
    public void devolver5Lances(){
        leilao1.propoe(new Lance(maria, 500.0));
        leilao1.propoe(new Lance(maria, 100.0));
        leilao1.propoe(new Lance(maria, 300.0));
        leilao1.propoe(new Lance(maria, 200.0));
        leilao1.propoe(new Lance(maria, 400.0));

        leiloeiro.avalia(leilao1);

        List<Lance> tresMaiores = leiloeiro.getTresMaiores();

        assertEquals(3, tresMaiores.size());
        assertEquals(500.0, tresMaiores.get(0).getValor(), 0.0001);
        assertEquals(400.0, tresMaiores.get(1).getValor(), 0.0001);
        assertEquals(300.0, tresMaiores.get(2).getValor(), 0.0001);
    }

    @Test
    public void devolver1Leilao(){
        leilao1.propoe(new Lance(maria, 500.0));
        leilao1.propoe(new Lance(maria, 5000.0));

        leiloeiro.avalia(leilao1);

        assertEquals(2, leilao1.getLances().size());
        assertEquals(500.0, leilao1.getLances().get(0).getValor(), 0.0001);
        assertEquals(5000.0, leilao1.getLances().get(1).getValor(), 0.0001);
    }

    @Test
    public void leilaoSemLancesDevolverZeroLances(){
        leiloeiro.avalia(leilao1);

        assertEquals(0, leilao1.getLances().size());
    }
}
