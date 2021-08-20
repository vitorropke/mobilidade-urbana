package mobilidade;

import java.util.ArrayList;
import java.util.List;

public class Parada {
	private static int contadorId = 0;
	private int id;
	private String nomeParada;
	private double coordenadaX, coordenadaY;
	private List<Pedestre> pedestres = new ArrayList<Pedestre>();

	public Parada() {
		setId(contadorId++);
	}

	public Parada(double coordenadaX, double coordenadaY) {
		setId(contadorId++);
		setCoordenadaX(coordenadaX);
		setCoordenadaY(coordenadaY);
	}

	public Parada(int id, double coordenadaX, double coordenadaY) {
		setId(id);
		setCoordenadaX(coordenadaX);
		setCoordenadaY(coordenadaY);
	}

	public Parada(String nomeParada, int id, double coordenadaX, double coordenadaY) {
		setNomeParada(nomeParada);
		setId(id);
		setCoordenadaX(coordenadaX);
		setCoordenadaY(coordenadaY);
	}

	@Override
	public String toString() {
		String saida = "";

		saida += id;
		saida += '\t';
		saida += nomeParada;
		saida += '\t';
		saida += '\t';
		saida += coordenadaX;
		saida += '\t';
		saida += coordenadaY;

		return saida;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(double coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public double getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(double coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public List<Pedestre> getPedestres() {
		return pedestres;
	}

	public void setPedestres(List<Pedestre> pedestres) {
		this.pedestres = pedestres;
	}

	public void addPedestre(final Pedestre pedestre) {
		this.pedestres.add(pedestre);
	}

	public void addAllPedestres(List<Pedestre> pedestres) {
		this.pedestres.addAll(pedestres);
	}

	public void removeAllPedestres(List<Pedestre> pedestres) {
		this.pedestres.removeAll(pedestres);
	}

	public String getNomeParada() {
		return nomeParada;
	}

	public void setNomeParada(String nomeParada) {
		this.nomeParada = nomeParada;
	}
}
