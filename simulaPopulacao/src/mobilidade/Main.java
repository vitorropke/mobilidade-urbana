package mobilidade;

import java.util.Random;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int intervaloDeslocamento = 60;

		// coordenadaX, coordenadaY, terminal
		Parada[] paradas = new Parada[5];
		paradas[0] = new Parada(-37.34368, -5.19395, false);
		paradas[1] = new Parada(-37.345013, -5.195714, false);
		paradas[2] = new Parada(-37.345806, -5.197364, false);
		paradas[3] = new Parada(-37.34525, -5.197718, false);
		paradas[4] = new Parada(-37.34481, -5.194238, true);

		// nome, 'origem', 'destino'
		Pedestre[] pessoas = new Pedestre[10];
		pessoas[0] = new Pedestre("joao");
		pessoas[1] = new Pedestre("maria");
		pessoas[2] = new Pedestre("pedro");
		pessoas[3] = new Pedestre("jose");
		pessoas[4] = new Pedestre("geremias");
		pessoas[5] = new Pedestre("sicrano");
		pessoas[6] = new Pedestre("beltrano");
		pessoas[7] = new Pedestre("fulano");
		pessoas[8] = new Pedestre("bob");
		pessoas[9] = new Pedestre("james");

		// capacidade, 'velocidade'
		Onibus[] onibus = new Onibus[1];
		onibus[0] = new Onibus(10, 40);
		onibus[0].setParadaAtual(paradas[0]);

		// -------------------------------------------------------------
		int numeroParadas = paradas.length;
		int numeroPedestres = pessoas.length;
		int numeroDaParadaAleatoria;
		int numeroPedestresAleatorio;
		Random paradasAleatorias = new Random();

		numeroPedestresAleatorio = paradasAleatorias.nextInt(numeroPedestres);
		System.out.println("Número de pessoas nessa simulação é " + numeroPedestresAleatorio + '\n');

		// Adiciona paradas no pedestre e os pedestres na parada de forma aleatória
		for (int i = 0; i < numeroPedestresAleatorio; i++) {
			numeroDaParadaAleatoria = paradasAleatorias.nextInt(numeroParadas - 1);

			pessoas[i].setOrigem(paradas[numeroDaParadaAleatoria]);
			paradas[numeroDaParadaAleatoria].pedestres.add(pessoas[i]);
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
			while (!onibus[x].getParadaAtual().isTerminal()) {
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
					if ((numeroPessoasNoOnibus <= onibus[x].getCapacidadeMaximaPassageiros()) && Main.caraCoroa()) {
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
				System.out.println("Quem subiu: " + pessoasNoOnibus);
				System.out.println("Quem desceu: " + pessoasQueDesceramDoOnibus);
				System.out.println("Quem está no ônibus: " + onibus[x].pedestres);
				System.out.println();

				// Simula deslocamento
				Main.simularDeslocamento(paradas[indice], paradas[indice + 1], onibus[0].getVelocidade(),
						intervaloDeslocamento, pessoasNoOnibus, pessoasQueDesceramDoOnibus);

				// Avança a parada do ônibus
				onibus[x].setParadaAtual(paradas[indice += 1]);
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

	public static void escanearBluetooth(Pedestre pedestre, boolean saiu, double distanciaUltimaParada) {
		Calendar momentoAtual = Calendar.getInstance();
		Random random = new Random();
		float[] sinais = { 0, 15, 45, 90 };
		int sinalSorteado = random.nextInt(4);

		pedestre.horario.add(momentoAtual);
		pedestre.sinal.add(sinais[sinalSorteado]);
	}

	public static ArrayList<Pedestre> averiguaSeSaiu(ArrayList<Pedestre> entradaPessoas) {
		int numeroPessoas = entradaPessoas.size();
		int numeroSinais = 0;
		ArrayList<Pedestre> saidaPessoas = new ArrayList<Pedestre>();
		float mediaSinal = 0;

		for (int i = 0; i < numeroPessoas; i++) {
			numeroSinais = entradaPessoas.get(i).sinal.size();

			if (numeroSinais >= 5) {
				// Reduz o número de sinais para obter os últimos 5 valores de sinal de
				// bluetooth
				numeroSinais -= 5;

				// Percorre os últimos 5 valores de sinais
				for (int j = numeroSinais; j < numeroSinais + 5; j++) {
					mediaSinal += entradaPessoas.get(i).sinal.get(j);
				}
			} else {
				// Percorre os últimos n valores de sinais
				for (int j = 0; j < numeroSinais; j++) {
					mediaSinal += entradaPessoas.get(i).sinal.get(j);
				}
			}

			// Se a média de sinais for maior que 0, significa que a pessoa está no ônibus
			mediaSinal /= numeroSinais;

			if (mediaSinal > 0) {
				saidaPessoas.add(entradaPessoas.get(i));
			}
		}

		return saidaPessoas;
	}

	public static void simularDeslocamento(Parada parada1, Parada parada2, float velocidade, int tempoSimulacao,
			ArrayList<Pedestre> pessoasNoOnibus, ArrayList<Pedestre> pessoasQueDesceramDoOnibus) {
		// em Km (quilômetros)
		double distanciaEntreParadas = Haversine.distance(parada1.getCoordenadaY(), parada1.getCoordenadaX(),
				parada2.getCoordenadaY(), parada2.getCoordenadaX());
		DecimalFormat formatter = new DecimalFormat("#0");

		System.out.println("Distancia: " + distanciaEntreParadas);

		// Cria o vetor de pessoas no onibus e na parada, juntas
		int numeroPessoasNoOnibus = pessoasNoOnibus.size();
		int numeroPessoasQueDesceramDoOnibus = pessoasQueDesceramDoOnibus.size();
		int numeroPessoasNoOnibusENaParada = numeroPessoasNoOnibus + numeroPessoasQueDesceramDoOnibus;
		boolean[] saiu = new boolean[numeroPessoasNoOnibusENaParada];

		ArrayList<Pedestre> pessoasNoOnibusENaParada = pessoasNoOnibus;
		pessoasNoOnibusENaParada.addAll(pessoasQueDesceramDoOnibus);

		for (int x = 0; x < numeroPessoasNoOnibus; x++) {
			saiu[x] = false;
		}
		for (int x = 0; x < numeroPessoasQueDesceramDoOnibus; x++) {
			saiu[x + numeroPessoasNoOnibus] = true;
		}

		// Deslocamento
		for (double i = distanciaEntreParadas; i > 0; i -= velocidade / (tempoSimulacao * 60)) {
			System.out.print("Faltam ");
			System.out.print(formatter.format(i * 1000));
			System.out.println(" metro(s) para a próxima parada.");
			System.out.print("ETA: ");
			System.out.print(formatter.format((i * 1000) / velocidade));
			System.out.println(" segundo(s)\n");

			// Calcula sinal do bluetooth
			for (int x = 0; x < numeroPessoasNoOnibusENaParada; x++) {
				escanearBluetooth(pessoasNoOnibusENaParada.get(x), saiu[x], x - i);
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Verifica quem sobrou no ônibus
		pessoasNoOnibus = averiguaSeSaiu(pessoasNoOnibusENaParada);

		System.out.println("Embarcando e desembarcando!");

		// Embarque desembarque
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.print("Quem acho que está no ônibus: ");
		System.out.println(pessoasNoOnibus);
		System.out.println();
	}
}
