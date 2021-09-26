package mobilidade;

import java.util.ArrayList;
import java.util.List;

public class Linha {
	private List<Parada> paradas = new ArrayList<Parada>();

	public Linha() {
	}

	public Linha(List<Parada> paradas) {
		setParadas(paradas);
	}

	public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas.addAll(paradas);
	}
}
