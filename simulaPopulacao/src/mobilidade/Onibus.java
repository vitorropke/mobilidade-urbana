package mobilidade;

import java.util.ArrayList;
import java.util.Calendar;
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
	private List<Calendar> horarios = new ArrayList<Calendar>();
	private List<Boolean> horarioCumprido = new ArrayList<Boolean>();

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

	public Onibus(int capacidade, int velocidade, Linha linha, List<Calendar> horarios) {
		setId(contadorId++);
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
		setLinha(linha);
		setHorarios(horarios);

		int numeroHorarios = horarios.size();

		for (int i = 0; i < numeroHorarios; i++) {
			horarioCumprido.add(false);
		}
	}

	public Onibus(int idVeiculo, int capacidade, int velocidade, Linha linha, List<Calendar> horarios) {
		setId(idVeiculo);
		setCapacidadeMaximaPassageiros(capacidade);
		setVelocidade(velocidade);
		setLinha(linha);
		setHorarios(horarios);

		int numeroHorarios = horarios.size();

		for (int i = 0; i < numeroHorarios; i++) {
			horarioCumprido.add(false);
		}
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

	public List<Calendar> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Calendar> horarios) {
		this.horarios.addAll(horarios);
	}

	public void addHorario(Calendar horario) {
		this.horarios.add(horario);
	}

	public List<Boolean> getHorarioCumprido() {
		return horarioCumprido;
	}

	public void setHorarioCumprido(List<Boolean> horarioCumprido) {
		this.horarioCumprido = horarioCumprido;
	}

	public void setHorarioCumprido(int indice, boolean valor) {
		this.horarioCumprido.set(indice, valor);
	}

	public void resetHorarioCumprido() {
		int numeroHorarios = horarioCumprido.size();

		for (int i = 0; i < numeroHorarios; i++) {
			this.horarioCumprido.set(i, false);
		}
	}
}
