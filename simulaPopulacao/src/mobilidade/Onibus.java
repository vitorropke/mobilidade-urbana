package mobilidade;

import java.util.ArrayList;
import java.util.List;

public class Onibus {
	// ------------------------------------------------------------------------------------------------------------
	// Variáveis
	private static int contadorId = 0;
	private int id;
	private int capacidadeMaximaPassageiros;
	private float velocidade;
	private Linha linha;
	private List<Pedestre> pedestres = new ArrayList<Pedestre>();

	// ------------------------------------------------------------------------------------------------------------
	// Construtores
	public Onibus() {
		setId(contadorId++);
	}

	public Onibus(int capacidade) {
		setId(contadorId++);
		setCapacidadeMaximaPassageiros(capacidade);
	}

	public Onibus(int capacidade, int velocidade) {
		setId(contadorId++);
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
	}

	public Onibus(int capacidade, int velocidade, Linha linha) {
		setId(contadorId++);
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
		setLinha(linha);
	}

	public Onibus(int idVeiculo, int capacidade, int velocidade, Linha linha) {
		setId(idVeiculo);
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
		setLinha(linha);
	}

	// ------------------------------------------------------------------------------------------------------------
	// Getters e setters
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

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
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
}
