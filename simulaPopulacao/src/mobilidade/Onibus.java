package mobilidade;

import java.util.ArrayList;

public class Onibus {
	int capacidadeMaximaPassageiros;
	float velocidadeOnibus;
	Parada paradaAtual;
	ArrayList<Pedestre> pedestres = new ArrayList<Pedestre>();

	public Onibus() {
	}

	public Onibus(int capacidade) {
		this.capacidadeMaximaPassageiros = capacidade;
	}

	public Onibus(int capacidade, int velocidadeOnibus) {
		this.capacidadeMaximaPassageiros = capacidade;
		this.velocidadeOnibus = velocidadeOnibus;
	}

}
