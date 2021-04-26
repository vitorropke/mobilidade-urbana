package mobilidade;

import java.util.ArrayList;

public class Parada {
	double coordenadaX, coordenadaY;
	boolean terminal;
	ArrayList<Pedestre> pedestres = new ArrayList<Pedestre>();

	public Parada() {

	}

	public Parada(float coordenadaX, float coordenadaY, boolean terminal) {
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
		this.terminal = terminal;
	}

}
