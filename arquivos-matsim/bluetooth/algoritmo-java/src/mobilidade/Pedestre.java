package mobilidade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pedestre {
	private String nome;
	private Parada origem;
	private Parada destino;
	private Parada paradaAtual;
	private Calendar horarioPartida;
	private List<Double> sinal = new ArrayList<Double>();
	private List<Calendar> horario = new ArrayList<Calendar>();
	private boolean noDestino;
	private boolean emViagem;

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

	public Parada getParadaAtual() {
		return paradaAtual;
	}

	public void setParadaAtual(Parada paradaAtual) {
		this.paradaAtual = paradaAtual;
	}

	public List<Double> getSinal() {
		return sinal;
	}

	public void setSinal(List<Double> sinal) {
		this.sinal = sinal;
	}

	public void addSinal(final Double sinal) {
		this.sinal.add(sinal);
	}

	public List<Calendar> getHorario() {
		return horario;
	}

	public void setHorario(List<Calendar> horario) {
		this.horario = horario;
	}

	public void addHorario(final Calendar horario) {
		this.horario.add(horario);
	}

	public boolean isNoDestino() {
		return noDestino;
	}

	public void setNoDestino(boolean noDestino) {
		this.noDestino = noDestino;
	}

	public boolean isEmViagem() {
		return emViagem;
	}

	public void setEmViagem(boolean emViagem) {
		this.emViagem = emViagem;
	}

	public Calendar getHorarioPartida() {
		return horarioPartida;
	}

	public void setHorarioPartida(Calendar horarioPartida) {
		this.horarioPartida = horarioPartida;
	}
}
