package mobilidade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Linha {
	private List<Parada> paradas = new ArrayList<Parada>();
	private List<Calendar> horarios = new ArrayList<Calendar>();

	public Linha() {
	}

	public Linha(List<Parada> paradas) {
		setParadas(paradas);
	}

	public Linha(List<Parada> paradas, List<Calendar> horarios) {
		setParadas(paradas);
		setHorarios(horarios);
	}

	public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas.addAll(paradas);
	}

	public List<Calendar> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Calendar> horarios) {
		this.horarios.addAll(horarios);
	}
}
