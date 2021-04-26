package mobilidade;

import java.util.Random;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int intervaloDeslocamento = 60;

		// coordenadaX, coordenadaY, terminal
		Parada[] paradas = new Parada[5];
		paradas[0] = new Parada(40, 50, false);
		paradas[1] = new Parada(40, 100, false);
		paradas[2] = new Parada(50, 150, false);
		paradas[3] = new Parada(100, 100, false);
		paradas[4] = new Parada(120, 100, true);

		// nome, 'origem', 'destino'
		Pedestre[] pessoas = new Pedestre[10];
		pessoas[0] = new Pedestre("joao", paradas[0], paradas[3]);
		pessoas[1] = new Pedestre("maria", paradas[1]);
		pessoas[2] = new Pedestre("pedro", paradas[1]);
		pessoas[3] = new Pedestre("jose", paradas[2]);
		pessoas[4] = new Pedestre("geremias", paradas[0]);
		pessoas[5] = new Pedestre("sicrano", paradas[0]);
		pessoas[6] = new Pedestre("beltrano", paradas[2]);
		pessoas[7] = new Pedestre("fulano", paradas[1]);
		pessoas[8] = new Pedestre("bob", paradas[1]);
		pessoas[9] = new Pedestre("james", paradas[0]);

		// capacidade, 'velocidade'
		Onibus[] onibus = new Onibus[1];
		onibus[0] = new Onibus(10, 30);
		onibus[0].paradaAtual = paradas[0];

		// -------------------------------------------------------------
		int numeroParadas = paradas.length;
		int numeroPedestres = pessoas.length;

		// Adiciona os pedestres na parada
		for (int i = 0; i < numeroParadas; i++) {
			for (int j = 0; j < numeroPedestres; j++) {
				if (paradas[i] == pessoas[j].origem) {
					paradas[i].pedestres.add(pessoas[j]);
				}
			}
		}

		ArrayList<Pedestre> pessoasNoOnibus = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasNaParada = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasQueDesceramDoOnibus = new ArrayList<Pedestre>();
		int numeroOnibus = onibus.length;
		int numeroPessoasNoOnibus;
		int numeroPessoasNaParada;
		int indice;

		// Faz as viagens dos ônibus
		for (int x = 0; x < numeroOnibus; x++) {
			// enquanto o ônibus não estiver no terminal
			indice = 0;
			while (!onibus[x].paradaAtual.terminal) {
				pessoasNoOnibus.clear();
				pessoasNaParada.clear();
				pessoasQueDesceramDoOnibus.clear();

				pessoasNaParada = paradas[indice].pedestres;
				numeroPessoasNaParada = pessoasNaParada.size();
				numeroPessoasNoOnibus = onibus[x].pedestres.size();

				// Primeiro, decide quem vai descer do ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasNoOnibus; i++) {
					// Se for true, o pedestre entrará no vetor que entrará no ônibus
					if (Main.caraCoroa()) {
						pessoasQueDesceramDoOnibus.add(onibus[x].pedestres.get(i));
						onibus[x].pedestres.remove(i);
						numeroPessoasNoOnibus--;
					}
				}

				// Segundo, decide quem vai entrar no ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasNaParada; i++) {
					// Se for true e a capacidade máxima não for atingida, o pedestre entrará no
					// vetor que entrará no ônibus
					if ((numeroPessoasNoOnibus <= onibus[x].capacidadeMaximaPassageiros) && Main.caraCoroa()) {
						pessoasNoOnibus.add(pessoasNaParada.get(i));
						numeroPessoasNoOnibus++;
					}
				}

				/*
				 * for (int i = 0; i < numeroPessoasNoOnibus; i++) { if
				 * (onibus[x].pedestres.get(i).destino == paradas[indice]) {
				 * onibus[x].pedestres.remove(i); numeroPessoasNoOnibus--; } }
				 */

				// Coloca o vetor de pessoas no ônibus junto com as pessoas que estavam no
				// ônibus
				onibus[x].pedestres.addAll(pessoasNoOnibus);

				System.out.println("Parada " + indice);
				System.out.println("Quem está no ônibus: " + onibus[x].pedestres);
				System.out.println("Quem subiu: " + pessoasNoOnibus);
				System.out.println("Quem desceu: " + pessoasQueDesceramDoOnibus);
				System.out.println();
				
				// Simula deslocamento
				Main.simularDeslocamento(paradas[indice], paradas[indice + 1]);

				// Avança a parada do ônibus
				onibus[x].paradaAtual = paradas[indice += 1];
			}

			System.out.println("Terminal");
			System.out.println("Quem desceu: " + onibus[x].pedestres);
			onibus[x].pedestres.clear();
		}
	}

	public static boolean caraCoroa() {
		Random caraOuCoroa = new Random();
		return caraOuCoroa.nextBoolean();
	}
	
	public static void simularDeslocamento(Parada parada1, Parada parada2) {
		// em Km (quilômetros)
		double distanciaEntreParadas = Haversine.distance(parada1.coordenadaY, parada1.coordenadaX, parada2.coordenadaY, parada2.coordenadaX);
		
		System.out.println("Distancia: " + distanciaEntreParadas);
	}
}
