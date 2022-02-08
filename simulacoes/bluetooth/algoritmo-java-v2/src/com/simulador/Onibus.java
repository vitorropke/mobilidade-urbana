package com.simulador;

import java.util.ArrayList;
import java.util.List;

public class Onibus {
	private static int contadorId = 0;
	private int id;
	private int capacidadeMaximaPassageiros;
	private float velocidade;
	private List<Pedestre> pedestres = new ArrayList<Pedestre>();
	private List<Viagem> viagens = new ArrayList<Viagem>();
	private List<Boolean> viagensCumpridas = new ArrayList<Boolean>();

	public Onibus(int capacidade, int velocidade, List<Viagem> viagens) {
		setId(contadorId++);
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
		addViagens(viagens);

		int numeroViagens = viagens.size();

		for (int i = 0; i < numeroViagens; i++) {
			viagensCumpridas.add(false);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacidadeMaximaPassageiros() {
		return capacidadeMaximaPassageiros;
	}

	public void setCapacidadeMaximaPassageiros(int capacidadeMaximaPassageiros) {
		this.capacidadeMaximaPassageiros = capacidadeMaximaPassageiros;
	}

	public float getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(float velocidade) {
		this.velocidade = velocidade;
	}

	public List<Pedestre> getPedestres() {
		return pedestres;
	}

	public void setPedestres(List<Pedestre> pedestres) {
		this.pedestres = pedestres;
	}

	public void add(final Pedestre pedestre) {
		this.pedestres.add(pedestre);
	}

	public void clearPedestres() {
		this.pedestres.clear();
	}

	public List<Viagem> getViagens() {
		return viagens;
	}

	public void setViagens(List<Viagem> viagens) {
		this.viagens = viagens;
	}

	public void addViagens(List<Viagem> viagens) {
		this.viagens.addAll(viagens);
	}

	public List<Boolean> getViagensCumpridas() {
		return viagensCumpridas;
	}

	public void setViagensCumpridas(List<Boolean> viagensCumpridas) {
		this.viagensCumpridas = viagensCumpridas;
	}

	public void setViagensCumpridas(int indice, boolean valor) {
		this.viagensCumpridas.set(indice, valor);
	}

	public void resetViagensCumpridas() {
		int numeroViagens = viagensCumpridas.size();

		for (int i = 0; i < numeroViagens; i++) {
			this.viagensCumpridas.set(i, false);
		}
	}
}
