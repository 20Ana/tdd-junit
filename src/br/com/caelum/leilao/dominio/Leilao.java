package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}
	
	public void propoe(Lance lance) {
		int total = quantidadeDeLancesDo(lance.getUsuario());
		if(lances.isEmpty() || podeDarLance(lance.getUsuario())){
			lances.add(lance);
		}
	}

	private boolean podeDarLance(Usuario usuario) {
		return !ultimoLanceDado().getUsuario().equals(usuario) && quantidadeDeLancesDo(usuario) < 5;
	}

	private int quantidadeDeLancesDo(Usuario usuario) {
		int total = 0;

		for (Lance lance1 : lances){
			if (lance1.getUsuario().equals(usuario)) total++;
		}
		return total;
	}

	public void dobraLance(Usuario usuario) {
		Lance ultimoLance = ultimoLanceDo(usuario);
		if(ultimoLance!=null) {
			propoe(new Lance(usuario, ultimoLance.getValor()*2));
		}
	}

	private Lance ultimoLanceDo(Usuario usuario) {
		Lance ultimo = null;
		for(Lance lance : lances) {
			if(lance.getUsuario().equals(usuario)) ultimo = lance;
		}

		return ultimo;
	}

	private Lance ultimoLanceDado() {
		return lances.get(lances.size()-1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	
	
}
