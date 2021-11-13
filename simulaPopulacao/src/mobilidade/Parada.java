package mobilidade;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;

public class Parada {
	private static int contadorId = 0;
	private int id;
	private String nomeParada;
	private double coordenadaX, coordenadaY;
	private List<Pedestre> pedestres = new ArrayList<Pedestre>();
	private List<Linha> linhas = new ArrayList<Linha>();

	public Parada() {
		setId(contadorId++);
	}

	public Parada(double coordenadaX, double coordenadaY) {
		setId(contadorId++);
		setCoordenadaX(coordenadaX);
		setCoordenadaY(coordenadaY);
	}

	public Parada(int id, double coordenadaX, double coordenadaY) {
		setId(id);
		setCoordenadaX(coordenadaX);
		setCoordenadaY(coordenadaY);
	}

	public Parada(String nomeParada, int id, double coordenadaX, double coordenadaY) {
		setNomeParada(nomeParada);
		setId(id);
		setCoordenadaX(coordenadaX);
		setCoordenadaY(coordenadaY);
	}

	@Override
	public String toString() {
		String saida = "";

		saida += id;
		saida += '\t';
		saida += nomeParada;
		saida += '\t';
		saida += '\t';
		saida += coordenadaX;
		saida += '\t';
		saida += coordenadaY;

		/*
		 * int numeroPedestres = pedestres.size(); saida += '\n';
		 * 
		 * for (int i = 0; i < numeroPedestres; i++) { saida += pedestres.get(i); }
		 */

		return saida;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(double coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public double getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(double coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public List<Pedestre> getPedestres() {
		return pedestres;
	}

	public void setPedestres(List<Pedestre> pedestres) {
		this.pedestres = pedestres;
	}

	public void addPedestre(final Pedestre pedestre) {
		this.pedestres.add(pedestre);
	}

	public void addAllPedestres(List<Pedestre> pedestres) {
		this.pedestres.addAll(pedestres);
	}

	public void removeAllPedestres(List<Pedestre> pedestres) {
		this.pedestres.removeAll(pedestres);
	}

	public void removeAllPedestres() {
		this.pedestres.clear();
	}

	public String getNomeParada() {
		return nomeParada;
	}

	public void setNomeParada(String nomeParada) {
		this.nomeParada = nomeParada;
	}

	public static ArrayList<Parada> gerarParadas(String nomeArquivoFacilitiesEntrada) {
		String entrada = "";

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

		// Biblioteca proj4j
		// Lida com conversão de coordenadas
		CRSFactory factory = new CRSFactory();
		CoordinateReferenceSystem origemCRS = factory.createFromName("EPSG:3857");
		CoordinateReferenceSystem destinoCRS = factory.createFromName("EPSG:4326");
		BasicCoordinateTransform transform = new BasicCoordinateTransform(origemCRS, destinoCRS);
		ProjCoordinate coordenadasOrigem = new ProjCoordinate();
		ProjCoordinate coordenadasDestino = new ProjCoordinate();

		ArrayList<Parada> paradas = new ArrayList<Parada>();
		int idParada = -1;
		String nomeParada;

		String stringCoordenadaX, stringCoordenadaY;
		double doubleCoordenadaX, doubleCoordenadaY;

		int tamanhoEntrada = entrada.length();
		for (int i = 0; i < tamanhoEntrada; i++) {
			if (entrada.charAt(i) == 'i') {
				i++;
				if (entrada.charAt(i) == 'd') {
					i++;
					if (entrada.charAt(i) == '=') {
						// Salta os símbolos ="
						i += 2;
						idParada++;

						// Salva o nome da parada
						nomeParada = "";
						do {
							nomeParada += entrada.charAt(i);
							i++;
						} while (entrada.charAt(i) != '\"');

						do {
							i++;
						} while (entrada.charAt(i) != '\"');

						i++;

						// Salva a coordenada X da parada
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

						// Salva a coordenada Y da parada
						stringCoordenadaY = "";
						while (entrada.charAt(i) != '\"') {
							stringCoordenadaY += entrada.charAt(i);
							i++;
						}

						doubleCoordenadaY = Double.valueOf(stringCoordenadaY);

						// Converte de X, Y para Longitude, Latitude
						coordenadasOrigem.setValue(doubleCoordenadaX, doubleCoordenadaY);
						transform.transform(coordenadasOrigem, coordenadasDestino);

						// Gera um novo objeto 'Parada'
						paradas.add(new Parada(nomeParada, idParada, coordenadasDestino.x, coordenadasDestino.y));
					}
				}
			}
		}

		return paradas;
	}

	public List<Linha> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Linha> linhas) {
		this.linhas = linhas;
	}

	public void addLinha(Linha linha) {
		this.linhas.add(linha);
	}
}
