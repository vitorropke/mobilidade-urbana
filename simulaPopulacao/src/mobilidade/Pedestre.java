package mobilidade;

public class Pedestre {
	String nome;
	Parada origem;
	Parada destino;

	public Pedestre() {
	}

	public Pedestre(String nome) {
		this.nome = nome;
	}

	public Pedestre(String nome, Parada origem) {
		this.nome = nome;
		this.origem = origem;
	}

	public Pedestre(String nome, Parada origem, Parada destino) {
		this.nome = nome;
		this.origem = origem;
		this.destino = destino;
	}

	@Override
	public String toString() {
		return "[" + nome + "]";
	}

}
