package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ana on 22/02/18.
 */
public class TestesLeilao {
    private static Leilao leilao;
    private static Usuario maria;
    private static Usuario jose;
    private static Usuario joao;
    Lance lance;

    @BeforeClass
    public static void setUp(){

        leilao = new Leilao("IPhone X");

        maria = new Usuario("Maria");
        jose = new Usuario("José");
        joao = new Usuario("João");
    }

    @Test
    public void leilaoComUmLance(){
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(maria, 2000.0));

        assertEquals(1, leilao.getLances().size());
    }

    @Test
    public void receberVariosLances(){
        leilao.propoe(new Lance(maria, 2000.0));
        leilao.propoe(new Lance(joao, 3000.0));
        leilao.propoe(new Lance(jose, 4000.0));
        leilao.propoe(new Lance(maria, 10000.0));

        assertEquals(4, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.0001);
        assertEquals(10000.0, leilao.getLances().get(3).getValor(), 0.0001);
    }

    @Test
    public void naoDeveAceitarDoisLAncesDeUmMesmoUsuario(){
        leilao.propoe(new Lance(maria, 2000.0));
        leilao.propoe(new Lance(maria, 2000.0));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.0001);
    }

    @Test
    public void naoDeveAceitarMaisQue5LancesDeUmMesmoUsuario(){
        leilao.propoe(new Lance(maria, 2000.0));
        leilao.propoe(new Lance(joao, 1000.0));

        leilao.propoe(new Lance(jose, 2000.0));
        leilao.propoe(new Lance(maria, 4000.0));

        leilao.propoe(new Lance(maria, 4000.0));
        leilao.propoe(new Lance(joao, 5000.0));

        leilao.propoe(new Lance(jose, 6000.0));
        leilao.propoe(new Lance(joao, 2000.0));

        leilao.propoe(new Lance(maria, 7000.0));
        leilao.propoe(new Lance(jose, 12000.0));

        leilao.propoe(new Lance(joao, 2000.0));

        assertEquals(10, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(9).getValor(), 0.0001);
    }

    @Test
    public void deveDobrarOUltimoLanceDado() {
        leilao.propoe(new Lance(maria, 2000));
        leilao.propoe(new Lance(joao, 3000));
        leilao.dobraLance(maria);

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        leilao.dobraLance(maria);

        assertEquals(0, leilao.getLances().size());
    }

}
