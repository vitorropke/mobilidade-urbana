package mobilidade;

import java.util.Calendar;

public class Viagem {
	private Linha linha;
	private Calendar horario;

	public Viagem(Linha linha, Calendar horario) {
		setLinha(linha);
		setHorario(horario);
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Calendar getHorario() {
		return horario;
	}

	public void setHorario(Calendar horario) {
		this.horario = horario;
	}
}
