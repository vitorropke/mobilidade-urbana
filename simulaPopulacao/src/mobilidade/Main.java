package mobilidade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;

public class Main {
	static int seed = 1;

	public static void main(String[] args) {
		// coordenadaX, coordenadaY, terminal
		ArrayList<Parada> paradas = Main.gerarParadas();
		paradas.get(0).setTerminal(true);
		paradas.get(1).setTerminal(true);

		// nome, 'origem', 'destino'
		ArrayList<Pedestre> pessoas = new ArrayList<Pedestre>();

		pessoas.add(new Pedestre("joao"));
		pessoas.add(new Pedestre("maria"));
		pessoas.add(new Pedestre("pedro"));
		pessoas.add(new Pedestre("jose"));
		pessoas.add(new Pedestre("geremias"));
		pessoas.add(new Pedestre("sicrano"));
		pessoas.add(new Pedestre("beltrano"));
		pessoas.add(new Pedestre("fulano"));
		pessoas.add(new Pedestre("bob"));
		pessoas.add(new Pedestre("james"));

		int numeroPessoas = pessoas.size();
		String stringPessoas[] = new String[numeroPessoas];

		// capacidade, 'velocidade (Km/h)', 'paradaAtual'
		ArrayList<Onibus> onibus = new ArrayList<Onibus>();

		onibus.add(new Onibus(10, 40, paradas.get(0)));

		onibus.add(new Onibus(50, 50, paradas.get(5)));
		onibus.add(new Onibus(25, 50, paradas.get(10)));

		// -------------------------------------------------------------
		int numeroParadas = paradas.size();
		int numeroPedestres = pessoas.size();
		int numeroParadaAleatoria;
		int numeroPedestresAleatorio;
		Random paradasAleatorias = new Random(seed);
		// Random paradasAleatorias = new Random();

		numeroPedestresAleatorio = paradasAleatorias.nextInt(numeroPedestres - 2) + 2;
		System.out.println("Número de pessoas nessa simulação é " + numeroPedestresAleatorio + '\n');

		// Adiciona paradas no pedestre e os pedestres na parada de forma aleatória
		for (int i = 0; i < numeroPedestresAleatorio; i++) {
			numeroParadaAleatoria = paradasAleatorias.nextInt(numeroParadas);

			pessoas.get(i).setOrigem(paradas.get(numeroParadaAleatoria));
			pessoas.get(i).setParadaAtual(paradas.get(numeroParadaAleatoria));

			paradas.get(numeroParadaAleatoria).addPedestre(pessoas.get(i));
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

		int numeroOnibus = onibus.size();
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
			indiceParada = (int) onibus.get(x).getParadaAtual().getId();
			while (!onibus.get(x).getParadaAtual().isTerminal()) {
				momentoAtual = Calendar.getInstance();
				horaAtual = momentoAtual.get(Calendar.HOUR_OF_DAY);
				minutoAtual = momentoAtual.get(Calendar.MINUTE);
				segundoAtual = momentoAtual.get(Calendar.SECOND);

				coordenadasOrigem.setValue(paradas.get(indiceParada).getCoordenadaX(),
						paradas.get(indiceParada).getCoordenadaY());
				transform.transform(coordenadasOrigem, coordenadasDestino);

				coordenadaX = coordenadasDestino.x;
				coordenadaY = coordenadasDestino.y;

				pessoasOnibus = onibus.get(x).getPedestres();
				pessoasParada = paradas.get(indiceParada).getPedestres();

				numeroPessoasOnibus = pessoasOnibus.size();
				numeroPessoasParada = pessoasParada.size();

				// Primeiro, decide quem vai descer do ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasOnibus; i++) {
					// Se for cara, o pedestre sai do ônibus
					if (Main.caraCoroa()) {
						indiceParadaPessoa = -1;
						pessoasDesceramOnibus.add(pessoasOnibus.get(i));
						pessoasOnibus.get(i).setParadaAtual(paradas.get(indiceParada));

						// Procura pelo índice da pessoa no vetor de pessoas
						for (int a = 0; a < numeroPessoas; a++) {
							if (pessoasOnibus.get(i).getNome().equals(pessoas.get(a).getNome())) {
								indiceParadaPessoa = a;
								break;
							}
						}

						if (indiceParadaPessoa != -1) {
							stringPessoas[indiceParadaPessoa] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\""
									+ coordenadaY + "\" type=\"home\"/>\n";
							stringSaidaFacilities += "\n\t<facility id=\"" + pessoasOnibus.get(i).getNome() + "Desceu"
									+ horaAtual + ":" + minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX
									+ "\" y=\"" + coordenadaY + "\"/>\n";
						}

						pessoasOnibus.remove(i);
						numeroPessoasOnibus--;
					}
				}

				// Segundo, decide quem vai entrar no ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasParada; i++) {
					// Se for cara e a capacidade máxima do ônibus não for atingida, o pedestre
					// entra no ônibus
					if ((numeroPessoasOnibus <= onibus.get(x).getCapacidadeMaximaPassageiros()) && Main.caraCoroa()) {
						indiceParadaPessoa = -1;
						pessoasSubiramOnibus.add(pessoasParada.get(i));
						pessoasOnibus.add(pessoasParada.get(i));
						numeroPessoasOnibus++;

						// Procura pelo índice da pessoa no vetor de pessoas
						for (int a = 0; a < numeroPessoas; a++) {
							if (pessoasParada.get(i).getNome().equals(pessoas.get(a).getNome())) {
								indiceParadaPessoa = a;
								break;
							}
						}

						if (indiceParadaPessoa != -1) {
							if (stringPessoas[indiceParadaPessoa] == null) {
								stringPessoas[indiceParadaPessoa] = "\n\t<person id=\""
										+ pessoas.get(indiceParadaPessoa).getNome() + "\" >\n\t\t<plan>\n"
										+ "\t\t\t<act end_time=\"" + horaAtual + ":" + minutoAtual + ":" + segundoAtual
										+ "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\" type=\"home\"/>\n"
										+ "\t\t\t<leg mode=\"pt\"/>\n";
							} else {
								stringPessoas[indiceParadaPessoa] += "\t\t\t<leg mode=\"pt\"/>\n"
										+ "\t\t\t<act end_time=\"" + horaAtual + ":" + minutoAtual + ":" + segundoAtual
										+ "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\" type=\"home\"/>\n"
										+ "\t\t\t<leg mode=\"pt\"/>\n";
							}

							stringSaidaFacilities += "\n\t<facility id=\"" + pessoasParada.get(i).getNome() + "Subiu"
									+ horaAtual + ":" + minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX
									+ "\" y=\"" + coordenadaY + "\"/>\n";
						}
					}
				}

				paradas.get(indiceParada).addAllPedestres(pessoasDesceramOnibus);
				paradas.get(indiceParada).removeAllPedestres(pessoasSubiramOnibus);

				/*
				 * for (int i = 0; i < numeroPessoasSubiramOnibus; i++) { if
				 * (onibus.get(x).pedestres.get(i).destino == paradas[indiceParada]) {
				 * onibus.get(x).pedestres.remove(i); numeroPessoasSubiramOnibus--; } }
				 */

				// Atualiza os pedestres que estão no ônibus
				onibus.get(x).setPedestres(pessoasOnibus);

				System.out.println("=====================================================================");
				System.out.println("Parada " + indiceParada + " : " + "Ônibus " + x);
				System.out.println("Quem subiu: " + pessoasSubiramOnibus);
				System.out.println("Quem desceu: " + pessoasDesceramOnibus);
				System.out.println("Quem está no ônibus: " + pessoasOnibus);
				System.out.println();

				// Simula deslocamento
				if ((indiceParada + 1) != numeroParadas) {

					pessoasOnibusSimulado = Main.simularDeslocamento(paradas.get(indiceParada),
							paradas.get(indiceParada + 1), onibus.get(x).getVelocidade(), pessoasSubiramOnibus,
							pessoasDesceramOnibus, pessoasOnibus, pessoasOnibusSimulado);

					indiceParada++;

					// Avança a parada do ônibus
					onibus.get(x).setParadaAtual(paradas.get(indiceParada));
				} else {
					break;
				}

				// Esvazia vetores de descida e subida
				pessoasDesceramOnibus.clear();
				pessoasSubiramOnibus.clear();
			}

			pessoasOnibusSimulado.clear();
			momentoAtual = Calendar.getInstance();
			horaAtual = momentoAtual.get(Calendar.HOUR_OF_DAY);
			minutoAtual = momentoAtual.get(Calendar.MINUTE);
			segundoAtual = momentoAtual.get(Calendar.SECOND);

			coordenadasOrigem.setValue(paradas.get(indiceParada).getCoordenadaX(),
					paradas.get(indiceParada).getCoordenadaY());
			transform.transform(coordenadasOrigem, coordenadasDestino);

			coordenadaX = coordenadasDestino.x;
			coordenadaY = coordenadasDestino.y;

			System.out.println("Terminal");
			System.out.println("Quem desceu: " + onibus.get(x).getPedestres());
			paradas.get(indiceParada).addAllPedestres(onibus.get(x).getPedestres());

			numeroPessoasOnibus = onibus.get(x).getPedestres().size();

			// Atualiza a parada atual de cada pessoa que estava no ônibus
			for (int i = 0; i < numeroPessoasOnibus; i++) {
				indiceParadaPessoa = -1;
				onibus.get(x).getPedestres().get(i).setParadaAtual(paradas.get(indiceParada));

				// Procura pelo índice da pessoa no vetor de pessoas
				for (int a = 0; a < numeroPessoas; a++) {
					if (onibus.get(x).getPedestres().get(i).getNome().equals(pessoas.get(a).getNome())) {
						indiceParadaPessoa = a;
						break;
					}
				}

				if (indiceParadaPessoa != -1) {
					stringPessoas[indiceParadaPessoa] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\"" + coordenadaY
							+ "\" type=\"home\"/>\n";
					stringSaidaFacilities += "\n\t<facility id=\"" + pessoasOnibus.get(i).getNome() + "Desceu"
							+ horaAtual + ":" + minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX + "\" y=\""
							+ coordenadaY + "\"/>\n";
				}
			}

			onibus.get(x).clearPedestres();
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

		Main.armazenarDados(stringSaidaPopulation, stringSaidaFacilities);
	}

	public static ArrayList<Parada> gerarParadas() {
		String entrada = "";
		String nomeArquivoFacilitiesEntrada = "facilitiesEntrada.xml";

		// https://www.w3schools.com/Java/java_files_read.asp
		try {
			File arquivoEntradaFacilities = new File(nomeArquivoFacilitiesEntrada);
			Scanner myReaderFacilities = new Scanner(arquivoEntradaFacilities);

			while (myReaderFacilities.hasNextLine()) {
				entrada += myReaderFacilities.nextLine();
			}

			myReaderFacilities.close();

			System.out.println("Leitura bem-sucedida!");
		} catch (FileNotFoundException e) {
			System.out.println("Erro de leitura!");
			e.printStackTrace();
		}

		CRSFactory factory = new CRSFactory();
		CoordinateReferenceSystem origemCRS = factory.createFromName("EPSG:3857");
		CoordinateReferenceSystem destinoCRS = factory.createFromName("EPSG:4326");
		BasicCoordinateTransform transform = new BasicCoordinateTransform(origemCRS, destinoCRS);
		ProjCoordinate coordenadasOrigem = new ProjCoordinate();
		ProjCoordinate coordenadasDestino = new ProjCoordinate();

		ArrayList<Parada> paradas = new ArrayList<Parada>();
		long idParada = -1;

		String stringCoordenadaX, stringCoordenadaY;
		double doubleCoordenadaX, doubleCoordenadaY;

		int tamanhoEntrada = entrada.length();
		for (int i = 0; i < tamanhoEntrada; i++) {
			if (entrada.charAt(i) == 'i') {
				i++;
				if (entrada.charAt(i) == 'd') {
					i++;
					if (entrada.charAt(i) == '=') {
						i++;
						idParada++;

						for (int x = 0; x < 2; x++) {
							do {
								i++;
							} while (entrada.charAt(i) != '\"');
						}

						i++;

						stringCoordenadaX = "";
						while (entrada.charAt(i) != '\"') {
							stringCoordenadaX += entrada.charAt(i);
							i++;
						}

						doubleCoordenadaX = Double.valueOf(stringCoordenadaX);

						do {
							i++;
						} while (entrada.charAt(i) != '\"');

						i++;

						stringCoordenadaY = "";
						while (entrada.charAt(i) != '\"') {
							stringCoordenadaY += entrada.charAt(i);
							i++;
						}

						doubleCoordenadaY = Double.valueOf(stringCoordenadaY);

						coordenadasOrigem.setValue(doubleCoordenadaX, doubleCoordenadaY);
						transform.transform(coordenadasOrigem, coordenadasDestino);

						paradas.add(new Parada(idParada, coordenadasDestino.x, coordenadasDestino.y, false));
					}
				}
			}
		}

		return paradas;
	}

	public static void armazenarDados(String stringSaidaPopulation, String stringSaidaFacilities) {
		String nomeArquivoPopulationSaida = "population.xml";
		String nomeArquivoFacilitiesSaida = "facilities.xml";
		// https://www.w3schools.com/java/java_files_create.asp
		try {
			File arquivoSaidaPopulation = new File(nomeArquivoPopulationSaida);
			File arquivoSaidaFacilities = new File(nomeArquivoFacilitiesSaida);

			FileWriter myWriterPopulation = new FileWriter(nomeArquivoPopulationSaida);
			FileWriter myWriterFacilities = new FileWriter(nomeArquivoFacilitiesSaida);

			myWriterPopulation.write(stringSaidaPopulation);
			myWriterPopulation.close();

			myWriterFacilities.write(stringSaidaFacilities);
			myWriterFacilities.close();

			if (arquivoSaidaPopulation.createNewFile()) {
				System.out.println("Arquivo criado: " + arquivoSaidaPopulation.getName());
			} else {
				System.out.println("Arquivo \"" + arquivoSaidaPopulation.getName() + "\" ja existe.");
			}
			if (arquivoSaidaFacilities.createNewFile()) {
				System.out.println("Arquivo criado: " + arquivoSaidaFacilities.getName());
			} else {
				System.out.println("Arquivo \"" + arquivoSaidaFacilities.getName() + "\" ja existe.");
			}

			System.out.println("Arquivos escritos!");
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

			/*
			 * try { Thread.sleep(1000); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
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

		/*
		 * try { Thread.sleep(10000); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */

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
