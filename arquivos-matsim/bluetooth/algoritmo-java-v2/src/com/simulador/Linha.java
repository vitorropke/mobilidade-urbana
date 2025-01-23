package com.simulador;

import java.util.ArrayList;
import java.util.List;

public class Linha {
	private List<Parada> paradas = new ArrayList<Parada>();
	private List<Linha> linhaCiclo = new ArrayList<Linha>();

	public Linha() {
	}

	public Linha(List<Parada> paradas) {
		setParadas(paradas);
	}

	public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas.addAll(paradas);
	}

	public List<Linha> getLinhaCiclo() {
		return linhaCiclo;
	}

	public void setLinhaCiclo(List<Linha> linhaCiclo) {
		this.linhaCiclo = linhaCiclo;
	}

	public void addLinhaCiclo(Linha linha) {
		this.linhaCiclo.add(linha);
	}
}
