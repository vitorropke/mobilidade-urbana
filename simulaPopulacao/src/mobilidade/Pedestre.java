package mobilidade;

import java.util.ArrayList;
import java.util.Calendar;

public class Pedestre {
	private String nome;
	private Parada origem;
	private Parada destino;
	ArrayList<Double> sinal = new ArrayList<Double>();
	ArrayList<Calendar> horario = new ArrayList<Calendar>();

	public Pedestre() {
	}

	public Pedestre(String nome) {
		setNome(nome);
	}

	public Pedestre(String nome, Parada origem) {
		setNome(nome);
		setOrigem(origem);
	}

	public Pedestre(String nome, Parada origem, Parada destino) {
		setNome(nome);
		setOrigem(origem);
		setDestino(destino);
	}

	@Override
	public String toString() {
		return "[" + nome + "]";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Parada getOrigem() {
		return origem;
	}

	public void setOrigem(Parada origem) {
		this.origem = origem;
	}

	public Parada getDestino() {
		return destino;
	}

	public void setDestino(Parada destino) {
		this.destino = destino;
	}
}
