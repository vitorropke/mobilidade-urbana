package mobilidade;

import java.util.ArrayList;
import java.util.List;

public class Onibus {
	private int capacidadeMaximaPassageiros;
	private float velocidade;
	private Linha linha;
	private List<Pedestre> pedestres = new ArrayList<Pedestre>();

	public Onibus() {
	}

	public Onibus(int capacidade) {
		setCapacidadeMaximaPassageiros(capacidade);
	}

	public Onibus(int capacidade, int velocidade) {
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
	}

	public Onibus(int capacidade, int velocidade, Linha linha) {
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
		setLinha(linha);
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
