package mobilidade;

import java.util.ArrayList;

public class Onibus {
	private int capacidadeMaximaPassageiros;
	private float velocidade;
	private Parada paradaAtual;
	private ArrayList<Pedestre> pedestres = new ArrayList<Pedestre>();

	public Onibus() {
	}

	public Onibus(int capacidade) {
		setCapacidadeMaximaPassageiros(capacidade);
	}

	public Onibus(int capacidade, int velocidade) {
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
	}
	
	public Onibus(int capacidade, int velocidade, Parada paradaInicio) {
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
		setParadaAtual(paradaInicio);
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

	public Parada getParadaAtual() {
		return paradaAtual;
	}

	public void setParadaAtual(Parada paradaAtual) {
		this.paradaAtual = paradaAtual;
	}

	public ArrayList<Pedestre> getPedestres() {
		return pedestres;
	}

	public void setPedestres(ArrayList<Pedestre> pedestres) {
		this.pedestres = pedestres;
	}

	public void add(final Pedestre pedestre) {
		this.pedestres.add(pedestre);
	}

	public void clearPedestres() {
		this.pedestres.clear();
	}
}
