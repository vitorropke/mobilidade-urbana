package mobilidade;

import java.util.Random;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {
	static int seed = 1;

	public static void main(String[] args) {
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
		int numeroParadaAleatoria;
		int numeroPedestresAleatorio;
		Random paradasAleatorias = new Random(seed);
		// Random paradasAleatorias = new Random();

		numeroPedestresAleatorio = paradasAleatorias.nextInt(numeroPedestres - 2) + 2;
		System.out.println("Número de pessoas nessa simulação é " + numeroPedestresAleatorio + '\n');

		// Adiciona paradas no pedestre e os pedestres na parada de forma aleatória
		for (int i = 0; i < numeroPedestresAleatorio; i++) {
			numeroParadaAleatoria = paradasAleatorias.nextInt(numeroParadas);

			pessoas[i].setOrigem(paradas[numeroParadaAleatoria]);
			pessoas[i].setParadaAtual(paradas[numeroParadaAleatoria]);

			paradas[numeroParadaAleatoria].addPedestre(pessoas[i]);
		}

		ArrayList<Pedestre> pessoasParada = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasOnibus = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasOnibusSimulado = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasDesceramOnibus = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasSubiramOnibus = new ArrayList<Pedestre>();

		int numeroOnibus = onibus.length;
		int numeroPessoasOnibus;
		int numeroPessoasParada;
		int indice;

		// Faz as viagens dos ônibus
		for (int x = 0; x < numeroOnibus; x++) {
			// Enquanto o ônibus não estiver no terminal
			indice = 0;
			while (!onibus[x].getParadaAtual().isTerminal()) {
				pessoasOnibus = onibus[x].getPedestres();
				pessoasParada = paradas[indice].getPedestres();

				numeroPessoasOnibus = pessoasOnibus.size();
				numeroPessoasParada = pessoasParada.size();

				// Primeiro, decide quem vai descer do ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasOnibus; i++) {
					// Se for cara, o pedestre sai do ônibus
					if (Main.caraCoroa()) {
						pessoasDesceramOnibus.add(pessoasOnibus.get(i));
						pessoasOnibus.get(i).setParadaAtual(paradas[indice]);
						pessoasOnibus.remove(i);
						numeroPessoasOnibus--;
					}
				}

				// Segundo, decide quem vai entrar no ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasParada; i++) {
					// Se for cara e a capacidade máxima do ônibus não for atingida, o pedestre
					// entra no ônibus
					if ((numeroPessoasOnibus <= onibus[x].getCapacidadeMaximaPassageiros()) && Main.caraCoroa()) {
						pessoasSubiramOnibus.add(pessoasParada.get(i));
						pessoasOnibus.add(pessoasParada.get(i));
						numeroPessoasOnibus++;
					}
				}

				paradas[indice].addAllPedestres(pessoasDesceramOnibus);
				paradas[indice].removeAllPedestres(pessoasSubiramOnibus);

				/*
				 * for (int i = 0; i < numeroPessoasSubiramOnibus; i++) { if
				 * (onibus[x].pedestres.get(i).destino == paradas[indice]) {
				 * onibus[x].pedestres.remove(i); numeroPessoasSubiramOnibus--; } }
				 */

				// Atualiza os pedestres que estão no ônibus
				onibus[x].setPedestres(pessoasOnibus);

				System.out.println("=====================================================================");
				System.out.println("Parada " + indice);
				System.out.println("Quem subiu: " + pessoasSubiramOnibus);
				System.out.println("Quem desceu: " + pessoasDesceramOnibus);
				System.out.println("Quem está no ônibus: " + pessoasOnibus);
				System.out.println();

				// Simula deslocamento
				pessoasOnibusSimulado = Main.simularDeslocamento(paradas[indice], paradas[indice + 1],
						onibus[x].getVelocidade(), pessoasSubiramOnibus, pessoasDesceramOnibus, pessoasOnibus,
						pessoasOnibusSimulado);

				indice += 1;

				// Avança a parada do ônibus
				onibus[x].setParadaAtual(paradas[indice]);

				// Esvazia vetores de descida e subida
				pessoasDesceramOnibus.clear();
				pessoasSubiramOnibus.clear();
			}

			System.out.println("Terminal");
			System.out.println("Quem desceu: " + onibus[x].getPedestres());
			paradas[indice].addAllPedestres(onibus[x].getPedestres());

			numeroPessoasOnibus = onibus[x].getPedestres().size();

			// Atualiza a parada atual de cada pessoa que estava no ônibus
			for (int i = 0; i < numeroPessoasOnibus; i++) {
				onibus[x].getPedestres().get(i).setParadaAtual(paradas[indice]);
			}

			onibus[x].clearPedestres();
		}
	}

	public static boolean caraCoroa() {
		Random caraOuCoroa = new Random(seed);
		// Random caraOuCoroa = new Random();
		return caraOuCoroa.nextBoolean();
	}

	public static void escanearBluetooth(Pedestre pedestre, boolean saiu, double distanciaUltimaParada) {
		Calendar momentoAtual = Calendar.getInstance();
		double potenciaSinal;
		double parteComumDaFormula = 20 * (Math.log(2.4) / Math.log(10)) + 92.45;

		// 20log10(d) + 20log10(f) + 92,45
		// d in km and f in GHz
		// log b(n) = log e(n) / log e(b)
		if (saiu) {
			potenciaSinal = 20 * (Math.log(distanciaUltimaParada) / Math.log(10)) + parteComumDaFormula;
		} else {
			// Valor padrão da distância do receptor até algum pedestre que está dentro do
			// ônibus
			double distanciaPadrao = 0.005;
			potenciaSinal = 20 * (Math.log(distanciaPadrao) / Math.log(10)) + parteComumDaFormula;
		}

		pedestre.addHorario(momentoAtual);
		pedestre.addSinal(potenciaSinal);
	}

	public static double[] mediaSinal(ArrayList<Pedestre> entradaPessoas) {
		int numeroPessoas = entradaPessoas.size();
		int numeroSinais;
		int ultimosSinais = 2;
		double[] mediaSinal = new double[numeroPessoas];

		for (int i = 0; i < numeroPessoas; i++) {
			numeroSinais = entradaPessoas.get(i).getSinal().size();

			// Média móvel dos últimos n sinais
			if (numeroSinais >= ultimosSinais) {
				// Percorre os últimos n sinais de bluetooth
				for (int j = numeroSinais - ultimosSinais; j < numeroSinais; j++) {
					mediaSinal[i] += entradaPessoas.get(i).getSinal().get(j);
				}
			} else {
				// Percorre todos os sinais
				for (int j = 0; j < numeroSinais; j++) {
					mediaSinal[i] += entradaPessoas.get(i).getSinal().get(j);
				}
			}

			mediaSinal[i] /= ultimosSinais;
		}

		return mediaSinal;
	}

	public static ArrayList<Pedestre> averiguaSeSaiu(ArrayList<Pedestre> entradaPessoas) {
		double[] mediaSinais = mediaSinal(entradaPessoas);
		int numeroMediaSinais = mediaSinais.length;
		ArrayList<Pedestre> saidaPessoas = new ArrayList<Pedestre>();

		for (int i = 0; i < numeroMediaSinais; i++) {
			if (mediaSinais[i] > 80) {
				saidaPessoas.add(entradaPessoas.get(i));
			}
		}

		return saidaPessoas;
	}

	public static ArrayList<Pedestre> averiguaSeEntrou(ArrayList<Pedestre> entradaPessoas) {
		double[] mediaSinais = mediaSinal(entradaPessoas);
		int numeroMediaSinais = mediaSinais.length;
		ArrayList<Pedestre> saidaPessoas = new ArrayList<Pedestre>();

		for (int i = 0; i < numeroMediaSinais; i++) {
			if (mediaSinais[i] <= 80) {
				saidaPessoas.add(entradaPessoas.get(i));
			}
		}

		return saidaPessoas;
	}

	public static ArrayList<Pedestre> simularDeslocamento(Parada paradaAtual, Parada proximaParada, float velocidade,
			ArrayList<Pedestre> pessoasSubiramOnibus, ArrayList<Pedestre> pessoasDesceramOnibus,
			ArrayList<Pedestre> pessoasOnibus, ArrayList<Pedestre> pessoasOnibusSimulado) {
		// Calcula a distância em Km (quilômetros) entre as paradas
		double distanciaEntreParadas = Haversine.distance(paradaAtual.getCoordenadaY(), paradaAtual.getCoordenadaX(),
				proximaParada.getCoordenadaY(), proximaParada.getCoordenadaX());
		DecimalFormat formatter = new DecimalFormat("#0");

		System.out.print("Distancia: ");
		System.out.print(formatter.format(distanciaEntreParadas * 1000));
		System.out.println(" metro(s)");

		// Remove as pessoas que sobraram na última parada porque sairam do alcançe do
		// bluetooth
		int numeroPessoasOnibusSimulado = pessoasOnibusSimulado.size();
		int x;
		for (x = 0; x < numeroPessoasOnibusSimulado; x++) {
			if (pessoasOnibus.contains(pessoasOnibusSimulado.get(x))
					|| pessoasSubiramOnibus.contains(pessoasOnibusSimulado.get(x))
					|| pessoasDesceramOnibus.contains(pessoasOnibusSimulado.get(x))) {
				pessoasOnibusSimulado.remove(x);
				numeroPessoasOnibusSimulado--;
				x--;
			}
		}

		// Cria o vetor que junta as pessoas que subiram e desceram do ônibus e as
		// pessoas que estavam no alcance do bluetooth e não estão mais
		int numeroPessoasSubiramOnibus = pessoasSubiramOnibus.size();
		int numeroPessoasDesceramOnibus = pessoasDesceramOnibus.size();
		int numeroPessoasSubiramDesceram = numeroPessoasSubiramOnibus + numeroPessoasDesceramOnibus
				+ numeroPessoasOnibusSimulado;
		boolean[] saiu = new boolean[numeroPessoasSubiramDesceram];
		ArrayList<Pedestre> pessoasSubiramDesceram = new ArrayList<Pedestre>();

		pessoasSubiramDesceram.addAll(pessoasSubiramOnibus);
		pessoasSubiramDesceram.addAll(pessoasDesceramOnibus);
		pessoasSubiramDesceram.addAll(pessoasOnibusSimulado);

		// Define o boolean com as pessoas que sairam ou não, do ônibus
		for (x = 0; x < numeroPessoasSubiramOnibus; x++) {
			saiu[x] = false;
		}
		for (x = 0; x < numeroPessoasDesceramOnibus; x++) {
			saiu[x + numeroPessoasSubiramOnibus] = true;
		}
		for (x = 0; x < numeroPessoasOnibusSimulado; x++) {
			saiu[x + numeroPessoasSubiramOnibus + numeroPessoasDesceramOnibus] = true;
		}

		double distanciaPedestreParada;
		// Deslocamento
		for (double i = distanciaEntreParadas; i > 0; i -= velocidade / 3600) {
			System.out.print("Faltam ");
			System.out.print(formatter.format(i * 1000));
			System.out.println(" metro(s) para a próxima parada.");
			System.out.print("ETA: ");
			System.out.print(formatter.format((i * 1000) / velocidade));
			System.out.println(" segundo(s)\n");

			// Calcula sinal do bluetooth
			for (x = 0; x < numeroPessoasSubiramDesceram; x++) {
				distanciaPedestreParada = Haversine.distance(proximaParada.getCoordenadaY(),
						proximaParada.getCoordenadaX(), pessoasSubiramDesceram.get(x).getParadaAtual().getCoordenadaY(),
						pessoasSubiramDesceram.get(x).getParadaAtual().getCoordenadaX()) - i;

				// escanearBluetooth(pedestre, saiu, distanciaUltimaParada)
				escanearBluetooth(pessoasSubiramDesceram.get(x), saiu[x], distanciaPedestreParada);
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Define os valores para saída
		ArrayList<Pedestre> saidaPessoasSubiramOnibus = averiguaSeEntrou(pessoasSubiramDesceram);
		ArrayList<Pedestre> saidaPessoasDesceramOnibus = averiguaSeSaiu(pessoasSubiramDesceram);
		ArrayList<Pedestre> saidaPessoasOnibus = new ArrayList<Pedestre>();

		// Adiciona pessoas que estão no ônibus
		saidaPessoasOnibus.addAll(pessoasOnibus);

		// Adiciona as pessoas que subiram se elas não estavam
		for (x = 0; x < numeroPessoasSubiramDesceram; x++) {
			if (!saidaPessoasOnibus.contains(pessoasSubiramDesceram.get(x))) {
				saidaPessoasOnibus.add(pessoasSubiramDesceram.get(x));
			}
		}

		// Remove as pessoas que desceram
		saidaPessoasOnibus.removeAll(saidaPessoasDesceramOnibus);

		// Embarque desembarque
		System.out.println("-----------------------------------------");
		System.out.println("Embarcando e desembarcando!");

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Pessoas que subiram: " + pessoasSubiramOnibus);
		System.out.println("Pessoas que desceram: " + pessoasDesceramOnibus);
		System.out.println("Pessoas no onibus: " + pessoasOnibus);

		System.out.println();

		System.out.println("Acho que subiu no ônibus: " + saidaPessoasSubiramOnibus);
		System.out.println("Acho que desceu do ônibus: " + saidaPessoasDesceramOnibus);
		System.out.println("Acho que está no ônibus: " + saidaPessoasOnibus);
		System.out.println();

		return saidaPessoasOnibus;
	}
}
