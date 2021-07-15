package mobilidade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;

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

		/*paradas[5] = new Parada(-37.337994, -5.202101, false);
		paradas[6] = new Parada(-37.338793, -5.200765, false);
		paradas[7] = new Parada(-37.341271, -5.195738, false);
		paradas[8] = new Parada(-37.341803, -5.193056, false);
		paradas[9] = new Parada(-37.342789, -5.191568, true);
		
		paradas[10] = new Parada(-37.378884, -5.242920, false);
		paradas[11] = new Parada(-37.383642, -5.245159, false);
		paradas[12] = new Parada(-37.386075, -5.244305, false);
		paradas[13] = new Parada(-37.387884, -5.243302, false);
		paradas[14] = new Parada(-37.389736, -5.242496, true);*/

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

		int numeroPessoas = pessoas.length;
		String stringPessoas[] = new String[numeroPessoas];

		// capacidade, 'velocidade (Km/h)', 'paradaAtual'
		Onibus[] onibus = new Onibus[1];
		onibus[0] = new Onibus(10, 40, paradas[0]);
		/*onibus[1] = new Onibus(10, 50, paradas[5]);
		onibus[2] = new Onibus(10, 50, paradas[10]);*/

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

		String stringSaidaPopulation = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<!DOCTYPE plans SYSTEM \"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n" + "\n" + "<plans>\n";
		String stringSaidaFacilities = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<!DOCTYPE facilities SYSTEM \"http://www.matsim.org/files/dtd/facilities_v1.dtd\">\n" + "\n"
				+ "<facilities>\n";

		ArrayList<Pedestre> pessoasParada = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasOnibus = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasOnibusSimulado = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasDesceramOnibus = new ArrayList<Pedestre>();
		ArrayList<Pedestre> pessoasSubiramOnibus = new ArrayList<Pedestre>();

		int numeroOnibus = onibus.length;
		int numeroPessoasOnibus;
		int numeroPessoasParada;
		int indiceParada;
		int indiceParadaPessoa;
		int horaAtual;
		int minutoAtual;
		int segundoAtual;
		double coordenadaX;
		double coordenadaY;

		Calendar momentoAtual;

		CRSFactory factory = new CRSFactory();
		CoordinateReferenceSystem origemCRS = factory.createFromName("EPSG:4326");
		CoordinateReferenceSystem destinoCRS = factory.createFromName("EPSG:3857");
		BasicCoordinateTransform transform = new BasicCoordinateTransform(origemCRS, destinoCRS);
		ProjCoordinate coordenadasOrigem = new ProjCoordinate();
		ProjCoordinate coordenadasDestino = new ProjCoordinate();

		// Faz as viagens dos ônibus
		for (int x = 0; x < numeroOnibus; x++) {
			// Enquanto o ônibus não estiver no terminal
			indiceParada = 0;
			while (!onibus[x].getParadaAtual().isTerminal()) {
				momentoAtual = Calendar.getInstance();

				coordenadasOrigem.setValue(paradas[indiceParada].getCoordenadaX(), paradas[indiceParada].getCoordenadaY());
				transform.transform(coordenadasOrigem, coordenadasDestino);

				coordenadaX = coordenadasDestino.x;
				coordenadaY = coordenadasDestino.y;

				pessoasOnibus = onibus[x].getPedestres();
				pessoasParada = paradas[indiceParada].getPedestres();

				numeroPessoasOnibus = pessoasOnibus.size();
				numeroPessoasParada = pessoasParada.size();

				// Primeiro, decide quem vai descer do ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasOnibus; i++) {
					// Se for cara, o pedestre sai do ônibus
					if (Main.caraCoroa()) {
						indiceParadaPessoa = -1;
						pessoasDesceramOnibus.add(pessoasOnibus.get(i));
						pessoasOnibus.get(i).setParadaAtual(paradas[indiceParada]);

						// Procura pelo índice da pessoa no vetor de pessoas
						for (int a = 0; a < numeroPessoas; a++) {
							if (pessoasOnibus.get(i).getNome().equals(pessoas[a].getNome())) {
								indiceParadaPessoa = a;
								break;
							}
						}

						if (indiceParadaPessoa != -1) {
							stringPessoas[indiceParadaPessoa] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\"" + coordenadaY
									+ "\" type=\"home\"/>\n";
							stringSaidaFacilities += "\n\t<facility id=\"" + pessoasOnibus.get(i).getNome()
									+ "Desceu\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\"/>\n";
						}

						pessoasOnibus.remove(i);
						numeroPessoasOnibus--;
					}
				}

				// Segundo, decide quem vai entrar no ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasParada; i++) {
					// Se for cara e a capacidade máxima do ônibus não for atingida, o pedestre
					// entra no ônibus
					if ((numeroPessoasOnibus <= onibus[x].getCapacidadeMaximaPassageiros()) && Main.caraCoroa()) {
						indiceParadaPessoa = -1;
						pessoasSubiramOnibus.add(pessoasParada.get(i));
						pessoasOnibus.add(pessoasParada.get(i));
						numeroPessoasOnibus++;

						// Procura pelo índice da pessoa no vetor de pessoas
						for (int a = 0; a < numeroPessoas; a++) {
							if (pessoasParada.get(i).getNome().equals(pessoas[a].getNome())) {
								indiceParadaPessoa = a;
								break;
							}
						}

						if (indiceParadaPessoa != -1) {
							horaAtual = momentoAtual.get(Calendar.HOUR_OF_DAY);
							minutoAtual = momentoAtual.get(Calendar.MINUTE);
							segundoAtual = momentoAtual.get(Calendar.SECOND);

							if (stringPessoas[indiceParadaPessoa] == null) {
								stringPessoas[indiceParadaPessoa] = "\n\t<person id=\"" + pessoas[indiceParadaPessoa].getNome()
										+ "\" >\n\t\t<plan>\n" + "\t\t\t<act end_time=\"" + horaAtual + ":"
										+ minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX + "\" y=\""
										+ coordenadaY + "\" type=\"home\"/>\n" + "\t\t\t<leg mode=\"pt\"/>\n";
							} else {
								stringPessoas[indiceParadaPessoa] += "\t\t\t<leg mode=\"pt\"/>\n" + "\n\t<person id=\""
										+ pessoas[indiceParadaPessoa].getNome() + "\" >\n\t\t<plan>\n"
										+ "\t\t\t<act end_time=\"" + horaAtual + ":" + minutoAtual + ":" + segundoAtual
										+ "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\" type=\"home\"/>\n";
							}

							stringSaidaFacilities += "\n\t<facility id=\"" + pessoasParada.get(i).getNome()
									+ "Subiu\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\"/>\n";
						}
					}
				}

				paradas[indiceParada].addAllPedestres(pessoasDesceramOnibus);
				paradas[indiceParada].removeAllPedestres(pessoasSubiramOnibus);

				/*
				 * for (int i = 0; i < numeroPessoasSubiramOnibus; i++) { if
				 * (onibus[x].pedestres.get(i).destino == paradas[indiceParada]) {
				 * onibus[x].pedestres.remove(i); numeroPessoasSubiramOnibus--; } }
				 */

				// Atualiza os pedestres que estão no ônibus
				onibus[x].setPedestres(pessoasOnibus);

				System.out.println("=====================================================================");
				System.out.println("Parada " + indiceParada + " : " + "Ônibus " + x);
				System.out.println("Quem subiu: " + pessoasSubiramOnibus);
				System.out.println("Quem desceu: " + pessoasDesceramOnibus);
				System.out.println("Quem está no ônibus: " + pessoasOnibus);
				System.out.println();

				// Simula deslocamento
				pessoasOnibusSimulado = Main.simularDeslocamento(paradas[indiceParada], paradas[indiceParada + 1],
						onibus[x].getVelocidade(), pessoasSubiramOnibus, pessoasDesceramOnibus, pessoasOnibus,
						pessoasOnibusSimulado);

				indiceParada += 1;

				// Avança a parada do ônibus
				onibus[x].setParadaAtual(paradas[indiceParada]);

				// Esvazia vetores de descida e subida
				pessoasDesceramOnibus.clear();
				pessoasSubiramOnibus.clear();
			}

			momentoAtual = Calendar.getInstance();

			coordenadasOrigem.setValue(paradas[indiceParada].getCoordenadaX(), paradas[indiceParada].getCoordenadaY());
			transform.transform(coordenadasOrigem, coordenadasDestino);

			coordenadaX = coordenadasDestino.x;
			coordenadaY = coordenadasDestino.y;

			System.out.println("Terminal");
			System.out.println("Quem desceu: " + onibus[x].getPedestres());
			paradas[indiceParada].addAllPedestres(onibus[x].getPedestres());

			numeroPessoasOnibus = onibus[x].getPedestres().size();

			// Atualiza a parada atual de cada pessoa que estava no ônibus
			for (int i = 0; i < numeroPessoasOnibus; i++) {
				indiceParadaPessoa = -1;
				onibus[x].getPedestres().get(i).setParadaAtual(paradas[indiceParada]);

				// Procura pelo índice da pessoa no vetor de pessoas
				for (int a = 0; a < numeroPessoas; a++) {
					if (onibus[x].getPedestres().get(i).getNome().equals(pessoas[a].getNome())) {
						indiceParadaPessoa = a;
						break;
					}
				}

				if (indiceParadaPessoa != -1) {
					stringPessoas[indiceParadaPessoa] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\"" + coordenadaY
							+ "\" type=\"home\"/>\n";
					stringSaidaFacilities += "\n\t<facility id=\"" + onibus[x].getPedestres().get(i).getNome()
							+ "Desceu\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\"/>\n";
				}
			}

			onibus[x].clearPedestres();
		}

		// Adiciona as pessoas que fizeram parte da simulação na string de saída
		for (int a = 0; a < numeroPessoas; a++) {
			if (stringPessoas[a] != null) {
				stringPessoas[a] += "\t\t</plan>\n" + "\t</person>\n";
				stringSaidaPopulation += stringPessoas[a];
			}
		}

		stringSaidaPopulation += "\n</plans>\n";
		stringSaidaFacilities += "\n</facilities>\n";

		String nomeArquivoPopulation = "population.xml";
		String nomeArquivoFacilities = "facilities.xml";
		// https://www.w3schools.com/java/java_files_create.asp
		try {
			File arquivoSaidaPopulation = new File(nomeArquivoPopulation);
			File arquivoSaidaFacilities = new File(nomeArquivoFacilities);

			FileWriter myWriterPopulation = new FileWriter(nomeArquivoPopulation);
			FileWriter myWriterFacilities = new FileWriter(nomeArquivoFacilities);

			myWriterPopulation.write(stringSaidaPopulation);
			myWriterPopulation.close();

			myWriterFacilities.write(stringSaidaFacilities);
			myWriterFacilities.close();

			if (arquivoSaidaPopulation.createNewFile()) {
				System.out.println("File created: " + arquivoSaidaPopulation.getName());
			} else {
				System.out.println("File \"" + arquivoSaidaPopulation.getName() + "\" already exists.");
			}
			if (arquivoSaidaFacilities.createNewFile()) {
				System.out.println("File created: " + arquivoSaidaFacilities.getName());
			} else {
				System.out.println("File \"" + arquivoSaidaPopulation.getName() + "\" already exists.");
			}

			System.out.println("Arquivo escrito!");
		} catch (IOException e) {
			System.out.println("Erro!");
			e.printStackTrace();
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
			System.out.print(formatter.format(i / (velocidade / 3600)));
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
