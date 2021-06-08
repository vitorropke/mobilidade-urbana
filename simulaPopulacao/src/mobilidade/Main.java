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

		numeroPedestresAleatorio = paradasAleatorias.nextInt(numeroPedestres - 2) + 2;
		System.out.println("Número de pessoas nessa simulação é " + numeroPedestresAleatorio + '\n');

		// Adiciona paradas no pedestre e os pedestres na parada de forma aleatória
		for (int i = 0; i < numeroPedestresAleatorio; i++) {
			numeroDaParadaAleatoria = paradasAleatorias.nextInt(numeroParadas - 1);

			pessoas[i].setOrigem(paradas[numeroDaParadaAleatoria]);
			paradas[numeroDaParadaAleatoria].pedestres.add(pessoas[i]);
		}

		ArrayList<Pedestre> pessoasNaParada = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasQueSubiramNoOnibus = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasQueDesceramDoOnibus = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasQueEstaoNoOnibus = new ArrayList<Pedestre>();
		int numeroOnibus = onibus.length;
		int numeropessoasQueSubiramNoOnibus;
		int numeroPessoasNaParada;
		int indice;

		// Faz as viagens dos ônibus
		for (int x = 0; x < numeroOnibus; x++) {
			// enquanto o ônibus não estiver no terminal
			indice = 0;
			while (!onibus[x].getParadaAtual().isTerminal()) {
				pessoasQueSubiramNoOnibus.clear();
				pessoasNaParada.clear();
				pessoasQueDesceramDoOnibus.clear();
				pessoasQueEstaoNoOnibus.clear();

				pessoasNaParada = paradas[indice].pedestres;
				numeroPessoasNaParada = pessoasNaParada.size();
				numeropessoasQueSubiramNoOnibus = onibus[x].pedestres.size();

				// Primeiro, decide quem vai descer do ônibus através do cara e coroa
				for (int i = 0; i < numeropessoasQueSubiramNoOnibus; i++) {
					// Se for true, o pedestre entrará no vetor que entrará no ônibus
					if (Main.caraCoroa()) {
						pessoasQueDesceramDoOnibus.add(onibus[x].pedestres.get(i));
						onibus[x].pedestres.remove(i);
						numeropessoasQueSubiramNoOnibus--;
					}
				}

				// Segundo, decide quem vai entrar no ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasNaParada; i++) {
					// Se for true e a capacidade máxima não for atingida, o pedestre entrará no
					// vetor que entrará no ônibus
					if ((numeropessoasQueSubiramNoOnibus <= onibus[x].getCapacidadeMaximaPassageiros())
							&& Main.caraCoroa()) {
						pessoasQueSubiramNoOnibus.add(pessoasNaParada.get(i));
						numeropessoasQueSubiramNoOnibus++;
					}
				}

				/*
				 * for (int i = 0; i < numeropessoasQueSubiramNoOnibus; i++) { if
				 * (onibus[x].pedestres.get(i).destino == paradas[indice]) {
				 * onibus[x].pedestres.remove(i); numeropessoasQueSubiramNoOnibus--; } }
				 */

				// Coloca o vetor de pessoas no ônibus junto com as pessoas que estavam no
				// ônibus
				onibus[x].pedestres.addAll(pessoasQueSubiramNoOnibus);
				pessoasQueEstaoNoOnibus = onibus[x].pedestres;

				System.out.println("Parada " + indice);
				System.out.println("Quem subiu: " + pessoasQueSubiramNoOnibus);
				System.out.println("Quem desceu: " + pessoasQueDesceramDoOnibus);
				System.out.println("Quem está no ônibus: " + pessoasQueEstaoNoOnibus);
				System.out.println();

				// Simula deslocamento
				Main.simularDeslocamento(paradas[indice], paradas[indice + 1], onibus[0].getVelocidade(),
						intervaloDeslocamento, pessoasQueEstaoNoOnibus, pessoasQueDesceramDoOnibus);

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
		int ciclosDistancias = 0;
		int numeroSinais = 100;
		int sinalSorteado = random.nextInt(numeroSinais);
		float[] sinais = new float[numeroSinais];

		// Reduz a distância com ciclos de 100 metros (distância máxima de detecção de
		// sinal de bluetooth)
		for (double i = distanciaUltimaParada - 100; i > 0; i -= 100) {
			ciclosDistancias += 1;
		}

		if (saiu) {
			if (ciclosDistancias == 0) {
				// Inicializa sinais 90
				for (int i = 0; i < numeroSinais - 20; i++) {
					sinais[i] = 90;
				}
				// Inicializa sinais 45
				for (int i = numeroSinais - 20; i < numeroSinais - 5; i++) {
					sinais[i] = 45;
				}
				// Inicializa sinais 0
				for (int i = numeroSinais - 5; i < numeroSinais - 3; i++) {
					sinais[i] = 0;
				}
				// Inicializa sinais 15
				for (int i = numeroSinais - 3; i < numeroSinais; i++) {
					sinais[i] = 15;
				}
			} else if (ciclosDistancias == 1) {
				// Inicializa sinais 0
				for (int i = 0; i < numeroSinais - 5; i++) {
					sinais[i] = 0;
				}
				// Inicializa sinais 15
				for (int i = numeroSinais - 5; i < numeroSinais; i++) {
					sinais[i] = 15;
				}
			} else {
				// Inicializa sinais 0
				for (int i = 0; i < numeroSinais; i++) {
					sinais[i] = 0;
				}
			}
		} else {
			// Inicializa sinais 90
			for (int i = 0; i < numeroSinais - 20; i++) {
				sinais[i] = 90;
			}
			// Inicializa sinais 45
			for (int i = numeroSinais - 20; i < numeroSinais - 5; i++) {
				sinais[i] = 45;
			}
			// Inicializa sinais 0
			for (int i = numeroSinais - 5; i < numeroSinais - 3; i++) {
				sinais[i] = 0;
			}
			// Inicializa sinais 15
			for (int i = numeroSinais - 3; i < numeroSinais; i++) {
				sinais[i] = 15;
			}
		}

		pedestre.horario.add(momentoAtual);
		pedestre.sinal.add(sinais[sinalSorteado]);
	}

	public static float[] mediaSinal(ArrayList<Pedestre> entradaPessoas) {
		int numeroPessoas = entradaPessoas.size();
		int numeroSinais = 0;
		float[] mediaSinal = new float[numeroPessoas];

		for (int i = 0; i < numeroPessoas; i++) {
			numeroSinais = entradaPessoas.get(i).sinal.size();

			if (numeroSinais >= 5) {
				numeroSinais -= 5;
				// Percorre os últimos 5 valores de sinais
				for (int j = numeroSinais; j < numeroSinais + 5; j++) {
					mediaSinal[i] += entradaPessoas.get(i).sinal.get(j);

				}
			} else {
				// Percorre os últimos n valores de sinais
				for (int j = 0; j < numeroSinais; j++) {
					mediaSinal[i] += entradaPessoas.get(i).sinal.get(j);
				}
			}

			mediaSinal[i] /= numeroSinais;
		}

		return mediaSinal;
	}

	public static ArrayList<Pedestre> averiguaSeSaiu(ArrayList<Pedestre> entradaPessoas) {
		float[] mediaSinais = mediaSinal(entradaPessoas);
		int numeroMediaSinais = mediaSinais.length;
		ArrayList<Pedestre> saidaPessoas = new ArrayList<Pedestre>();

		for (int i = 0; i < numeroMediaSinais; i++) {
			if (mediaSinais[i] <= 10) {
				saidaPessoas.add(entradaPessoas.get(i));
			}
		}

		return saidaPessoas;
	}

	public static ArrayList<Pedestre> averiguaSeEntrou(ArrayList<Pedestre> entradaPessoas) {
		float[] mediaSinais = mediaSinal(entradaPessoas);
		int numeroMediaSinais = mediaSinais.length;
		ArrayList<Pedestre> saidaPessoas = new ArrayList<Pedestre>();

		for (int i = 0; i < numeroMediaSinais; i++) {
			if (mediaSinais[i] > 10) {
				saidaPessoas.add(entradaPessoas.get(i));
			}
		}

		return saidaPessoas;
	}

	public static void simularDeslocamento(Parada parada1, Parada parada2, float velocidade, int tempoSimulacao,
			ArrayList<Pedestre> pessoasQueSubiramNoOnibus, ArrayList<Pedestre> pessoasQueDesceramDoOnibus) {
		// em Km (quilômetros)
		double distanciaEntreParadas = Haversine.distance(parada1.getCoordenadaY(), parada1.getCoordenadaX(),
				parada2.getCoordenadaY(), parada2.getCoordenadaX());
		DecimalFormat formatter = new DecimalFormat("#0");

		System.out.print("Distancia: ");
		System.out.print(formatter.format(distanciaEntreParadas * 1000));
		System.out.println(" metro(s)");

		// Cria o vetor de pessoas no onibus e na parada, juntas
		int numeropessoasQueSubiramNoOnibus = pessoasQueSubiramNoOnibus.size();
		int numeroPessoasQueDesceramDoOnibus = pessoasQueDesceramDoOnibus.size();
		int numeropessoasQueSubiramNoOnibusENaParada = numeropessoasQueSubiramNoOnibus
				+ numeroPessoasQueDesceramDoOnibus;
		boolean[] saiu = new boolean[numeropessoasQueSubiramNoOnibusENaParada];

		ArrayList<Pedestre> pessoasQueSubiramNoOnibusENaParada = pessoasQueSubiramNoOnibus;
		pessoasQueSubiramNoOnibusENaParada.addAll(pessoasQueDesceramDoOnibus);

		for (int x = 0; x < numeropessoasQueSubiramNoOnibus; x++) {
			saiu[x] = false;
		}
		for (int x = 0; x < numeroPessoasQueDesceramDoOnibus; x++) {
			saiu[x + numeropessoasQueSubiramNoOnibus] = true;
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
			for (int x = 0; x < numeropessoasQueSubiramNoOnibusENaParada; x++) {
				escanearBluetooth(pessoasQueSubiramNoOnibusENaParada.get(x), saiu[x],
						(distanciaEntreParadas - i) * 1000);
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ArrayList<Pedestre> saidaPessoasQueSubiramNoOnibus = new ArrayList<Pedestre>();
		ArrayList<Pedestre> saidaPessoasQueDesceramDoOnibus = new ArrayList<Pedestre>();

		// Verifica quem sobrou no ônibus
		saidaPessoasQueSubiramNoOnibus = averiguaSeEntrou(pessoasQueSubiramNoOnibusENaParada);
		saidaPessoasQueDesceramDoOnibus = averiguaSeSaiu(pessoasQueSubiramNoOnibusENaParada);

		System.out.println("Embarcando e desembarcando!");

		// Embarque desembarque
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.print("Pessoas no onibus: ");
		System.out.println(pessoasQueSubiramNoOnibus);
		System.out.print("Pessoas que desceram: ");
		System.out.println(pessoasQueDesceramDoOnibus);
		System.out.println();

		System.out.print("Acho que está no ônibus: ");
		System.out.println(saidaPessoasQueSubiramNoOnibus);
		System.out.print("Acho que saiu do ônibus: ");
		System.out.println(saidaPessoasQueDesceramDoOnibus);
		System.out.println();
	}
}
