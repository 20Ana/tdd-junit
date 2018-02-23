package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Created by ana on 22/02/18.
 */
public class TesteDoAvaliador {
    private Avaliador leiloeiro;
    private Usuario maria;
    private Usuario jose;
    private Usuario joao;
    private Leilao leilao;

    @Before
    public void setUp() {
        leilao = new Leilao("IPhone 10");
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        leilao.propoe(new Lance(maria, 400.0));
        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 403.0));

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        leilao.propoe(new Lance(joao, 200.0));

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMenorLance(), equalTo(leiloeiro.getMaiorLance()));
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        leilao.propoe(new Lance(joao, 1100.0));
        leilao.propoe(new Lance(joao, 2010.0));
        leilao.propoe(new Lance(joao, 2010.0));
        leilao.propoe(new Lance(joao, 2001.0));


        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());

        assertThat(maiores, hasItems(
                new Lance(maria, 400),
                new Lance(joao, 300),
                new Lance(maria, 200)
        ));
    }

    @Test(expected=RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {

        leiloeiro.avalia(leilao);
    }
}
