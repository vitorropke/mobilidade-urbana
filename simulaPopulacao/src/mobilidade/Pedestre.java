package mobilidade;

import java.util.ArrayList;
import java.util.Calendar;

public class Pedestre {
	private String nome;
	private Parada origem;
	private Parada destino;
	private ArrayList<Double> sinal = new ArrayList<Double>();
	private ArrayList<Calendar> horario = new ArrayList<Calendar>();

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

	public ArrayList<Double> getSinal() {
		return sinal;
	}

	public void setSinal(ArrayList<Double> sinal) {
		this.sinal = sinal;
	}

	public void addSinal(final Double sinal) {
		this.sinal.add(sinal);
	}

	public ArrayList<Calendar> getHorario() {
		return horario;
	}

	public void setHorario(ArrayList<Calendar> horario) {
		this.horario = horario;
	}

	public void addHorario(final Calendar horario) {
		this.horario.add(horario);
	}
}
