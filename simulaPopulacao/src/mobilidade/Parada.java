package mobilidade;

import java.util.ArrayList;

public class Parada {
	private static long contadorId = 0;
	private long id;
	private double coordenadaX, coordenadaY;
	private boolean terminal;
	ArrayList<Pedestre> pedestres = new ArrayList<Pedestre>();

	public Parada() {
		setId(contadorId++);
	}

	public Parada(double coordenadaX, double coordenadaY, boolean terminal) {
		setId(contadorId++);
		setCoordenadaX(coordenadaX);
		setCoordenadaY(coordenadaY);
		setTerminal(terminal);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public boolean isTerminal() {
		return terminal;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

}
