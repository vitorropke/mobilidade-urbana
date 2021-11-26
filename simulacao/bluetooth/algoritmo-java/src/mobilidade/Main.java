package mobilidade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;

public class Main {
	static Random sementeAleatoria = new Random();
	static int seed = sementeAleatoria.nextInt();
	static Calendar relogioSimulacao = Calendar.getInstance();
	static int horaInicialSimulacao = 18;
	static int minutoInicialSimulacao = 0;
	static int segundoInicialSimulacao = 0;
	static String log = "";

	public static void main(String[] args) {
		Main.relogioSimulacao.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, horaInicialSimulacao,
				minutoInicialSimulacao, segundoInicialSimulacao);

		// Define as paradas
		List<Parada> paradas = Parada.gerarParadas("facilities-entrada-resumido.xml");
		List<Parada> subParadas = new ArrayList<Parada>();

		// -----------------------------------------------------------------------------------------------------------------------
		// Define as linhas
		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - IDA
		subParadas.addAll(paradas.subList(40, 70));
		subParadas.addAll(paradas.subList(132, 143));
		subParadas.addAll(paradas.subList(151, 161));
		subParadas.addAll(paradas.subList(70, 97));
		subParadas.addAll(paradas.subList(190, 193));
		subParadas.addAll(paradas.subList(10, 20));
		subParadas.add(paradas.get(0));

		Linha linha1Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha1Ida);
		}

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - IDA
		subParadas.addAll(41, paradas.subList(143, 151));

		Linha linha1SantaJuliaIda = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha1SantaJuliaIda);
		}

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 11));
		subParadas.addAll(paradas.subList(193, 196));
		subParadas.addAll(paradas.subList(105, 109));
		subParadas.addAll(paradas.subList(115, 143));
		subParadas.addAll(paradas.subList(151, 161));
		subParadas.addAll(paradas.subList(70, 77));
		subParadas.addAll(paradas.subList(161, 190));
		subParadas.add(paradas.get(40));
		Linha linha1Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha1Volta);
		}

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - VOLTA
		subParadas.addAll(46, paradas.subList(143, 151));
		Linha linha1SantaJuliaVolta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha1SantaJuliaVolta);
		}

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(40, 70));
		subParadas.addAll(paradas.subList(77, 94));
		subParadas.addAll(paradas.subList(97, 105));
		subParadas.addAll(paradas.subList(12, 20));
		subParadas.add(paradas.get(0));
		Linha linha2Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha2Ida);
		}

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 11));
		subParadas.addAll(paradas.subList(193, 196));
		subParadas.addAll(paradas.subList(109, 132));
		subParadas.addAll(paradas.subList(161, 190));
		subParadas.add(paradas.get(40));
		Linha linha2Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha2Volta);
		}

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA AEC
		subParadas.clear();
		subParadas.addAll(paradas.subList(362, 366));
		subParadas.addAll(paradas.subList(281, 284));
		subParadas.addAll(paradas.subList(32, 39));
		subParadas.addAll(paradas.subList(15, 20));
		subParadas.add(paradas.get(0));
		Linha linha2UniversidadesAeC = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha2UniversidadesAeC);
		}

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA IFRN
		subParadas.removeAll(subParadas.subList(5, 7));
		subParadas.addAll(5, paradas.subList(306, 310));
		subParadas.addAll(9, paradas.subList(317, 320));
		subParadas.addAll(12, paradas.subList(29, 32));
		subParadas.removeAll(subParadas.subList(23, 27));
		Linha linha2UniversidadesIFRN = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha2UniversidadesIFRN);
		}

		// LINHA 03 - SANTO ANTÔNIO, BARROCAS
		subParadas.clear();
		subParadas.addAll(paradas.subList(203, 208));
		subParadas.addAll(paradas.subList(248, 252));
		subParadas.addAll(paradas.subList(208, 210));
		subParadas.addAll(paradas.subList(222, 231));
		subParadas.addAll(paradas.subList(7, 19));
		subParadas.add(paradas.get(20));
		Linha linha3 = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha3);
		}

		// LINHA 04 - ABOLIÇÃO V - IDA
		subParadas.clear();
		subParadas.add(paradas.get(142));
		subParadas.addAll(paradas.subList(151, 161));
		subParadas.addAll(paradas.subList(70, 97));
		subParadas.addAll(paradas.subList(190, 193));
		subParadas.addAll(paradas.subList(10, 20));
		subParadas.add(paradas.get(0));
		Linha linha4Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha4Ida);
		}

		// LINHA 04 - ABOLIÇÃO V, SANTA JÚLIA
		subParadas.addAll(1, paradas.subList(143, 151));
		Linha linha4SantaJulia = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha4SantaJulia);
		}

		// LINHA 04 - ABOLIÇÃO V - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 11));
		subParadas.addAll(paradas.subList(193, 196));
		subParadas.addAll(paradas.subList(105, 109));
		subParadas.addAll(paradas.subList(115, 143));
		Linha linha4Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha4Volta);
		}

		// LINHA 05 - VINGT ROSADO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(284, 306));
		subParadas.addAll(paradas.subList(276, 284));
		subParadas.addAll(paradas.subList(32, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha5Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha5Ida);
		}

		// LINHA 05 - VINGT ROSADO VIA UNIVERSIDADES - IDA
		subParadas.addAll(27, paradas.subList(420, 423));
		subParadas.addAll(30, paradas.subList(362, 366));
		subParadas.addAll(35, paradas.subList(306, 310));
		subParadas.addAll(39, paradas.subList(317, 320));
		subParadas.addAll(42, paradas.subList(29, 32));
		subParadas.removeAll(subParadas.subList(45, 47));
		Linha linha5IdaUniversidades = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha5IdaUniversidades);
		}

		// LINHA 05 - VINGT ROSADO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 371));
		subParadas.addAll(paradas.subList(376, 397));
		subParadas.add(paradas.get(284));
		Linha linha5Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha5Volta);
		}

		// LINHA 05 - VINGT ROSADO, UERN - VOLTA
		subParadas.addAll(9, paradas.subList(420, 423));
		subParadas.addAll(12, paradas.subList(362, 366));
		Linha linha5VoltaUERN = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha5VoltaUERN);
		}

		// LINHA 05 - VINGT ROSADO, IFRN - VOLTA
		subParadas.removeAll(subParadas.subList(9, 16));
		subParadas.removeAll(subParadas.subList(6, 8));
		subParadas.add(6, paradas.get(26));
		subParadas.addAll(7, paradas.subList(397, 409));
		subParadas.addAll(19, paradas.subList(416, 420));
		Linha linha5VoltaIFRN = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha5VoltaIFRN);
		}

		// LINHA 05 - VINGT ROSADO VIA UNIVERSIDADES - VOLTA
		subParadas.addAll(24, paradas.subList(420, 423));
		subParadas.addAll(27, paradas.subList(362, 366));
		Linha linha5VoltaUniversidades = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha5VoltaUniversidades);
		}

		// LINHA 05 - VINGT ROSADO VIA ODETE ROSADO - VOLTA
		subParadas.removeAll(subParadas.subList(27, 31));
		subParadas.removeAll(subParadas.subList(0, 23));
		subParadas.addAll(0, paradas.subList(366, 368));
		subParadas.addAll(6, paradas.subList(423, 425));
		subParadas.addAll(8, paradas.subList(355, 366));
		Linha linha5VoltaOdeteRosado = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha5VoltaOdeteRosado);
		}

		// LINHA 06 - UNIVERSIDADES - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(362, 366));
		subParadas.add(paradas.get(281));
		subParadas.addAll(paradas.subList(306, 320));
		subParadas.addAll(paradas.subList(29, 40));
		subParadas.addAll(paradas.subList(320, 328));
		subParadas.add(paradas.get(18));
		subParadas.add(paradas.get(20));
		Linha linha6Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha6Ida);
		}

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF - IDA
		subParadas.removeAll(subParadas.subList(9, 16));
		Linha linha6IdaSemUlrickGraff = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha6IdaSemUlrickGraff);
		}

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, NEM AVENIDA RIO BRANCO - IDA
		subParadas.removeAll(subParadas.subList(22, 31));
		subParadas.addAll(22, paradas.subList(15, 18));
		subParadas.remove(subParadas.get(26));
		subParadas.add(paradas.get(19));
		subParadas.add(paradas.get(0));
		Linha linha6IdaSemUlrickGraffNemRioBranco = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha6IdaSemUlrickGraffNemRioBranco);
		}

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, COM UNIRB E COELHO NETO - IDA
		subParadas.addAll(0, paradas.subList(274, 281));
		subParadas.addAll(7, paradas.subList(420, 423));
		subParadas.removeAll(subParadas.subList(32, 35));
		subParadas.add(32, paradas.get(39));
		subParadas.addAll(33, paradas.subList(320, 324));
		subParadas.addAll(37, paradas.subList(328, 338));
		Linha linha6IdaSemUlrickGraffComUNIRBComCoelhoNeto = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha6IdaSemUlrickGraffComUNIRBComCoelhoNeto);
		}

		// LINHA 06 - UNIVERSIDADES - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 27));
		subParadas.addAll(paradas.subList(397, 420));
		subParadas.add(paradas.get(368));
		subParadas.addAll(paradas.subList(420, 423));
		subParadas.add(paradas.get(362));
		Linha linha6Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha6Volta);
		}

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF- VOLTA
		subParadas.removeAll(subParadas.subList(19, 26));
		Linha linha6VoltaSemUlrickGraff = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha6VoltaSemUlrickGraff);
		}

		// LINHA 06 - UNIVERSIDADES COM UNIRB - VOLTA
		subParadas.addAll(19, paradas.subList(409, 416));
		subParadas.addAll(paradas.subList(363, 366));
		subParadas.addAll(paradas.subList(369, 373));
		Linha linha6VoltaUNIRB = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha6VoltaUNIRB);
		}

		// LINHA 07 - NOVA VIDA - IDA
		subParadas.clear();
		subParadas.add(paradas.get(455));
		subParadas.addAll(paradas.subList(435, 443));
		subParadas.addAll(paradas.subList(27, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha7Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha7Ida);
		}

		// LINHA 07 - NOVA VIDA SEM PAREDÕES, TERMINANDO NA PRAÇA FELIPE GUERRA - IDA
		subParadas.removeAll(subParadas.subList(21, 40));
		subParadas.add(paradas.get(15));
		subParadas.add(paradas.get(0));
		Linha linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra);
		}

		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - IDA
		subParadas.removeAll(subParadas.subList(21, 23));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		subParadas.remove(subParadas.get(0));
		subParadas.addAll(0, paradas.subList(425, 435));
		Linha linha7IdaJardimDasPalmeiras = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha7IdaJardimDasPalmeiras);
		}

		// LINHA 07 - NOVA VIDA - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 27));
		subParadas.addAll(paradas.subList(443, 456));
		Linha linha7Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha7Volta);
		}

		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - VOLTA
		subParadas.addAll(paradas.subList(456, 462));
		subParadas.add(paradas.get(425));
		Linha linha7VoltaJardimDasPalmeiras = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha7VoltaJardimDasPalmeiras);
		}

		// LINHA 07 - NOVA VIDA VIA SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		subParadas.add(0, paradas.get(0));
		subParadas.add(1, paradas.get(327));
		subParadas.add(2, paradas.get(18));
		subParadas.addAll(8, paradas.subList(478, 499));
		subParadas.addAll(29, paradas.subList(462, 478));
		subParadas.removeAll(subParadas.subList(45, 51));
		subParadas.removeAll(subParadas.subList(54, subParadas.size()));
		Linha linha7VoltaSumareLiberdadePlanalto = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha7VoltaSumareLiberdadePlanalto);
		}

		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - IDA
		subParadas.removeAll(subParadas.subList(0, 29));
		subParadas.removeAll(subParadas.subList(16, subParadas.size()));
		subParadas.addAll(paradas.subList(27, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha8Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha8Ida);
		}

		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 25));
		subParadas.addAll(paradas.subList(478, 499));
		subParadas.add(paradas.get(462));
		Linha linha8Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha8Volta);
		}

		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(511, 522));
		subParadas.addAll(paradas.subList(499, 511));
		subParadas.add(paradas.get(1));
		subParadas.addAll(paradas.subList(14, 20));
		subParadas.add(paradas.get(0));
		Linha linha9Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha9Ida);
		}

		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO COM LAGOA DO MATO, BOA
		// VISTA, DOZE ANOS E SANTO ANTÔNIO - IDA
		subParadas.removeAll(subParadas.subList(15, 24));
		subParadas.add(15, paradas.get(555));
		subParadas.addAll(16, paradas.subList(522, 533));
		subParadas.addAll(27, paradas.subList(593, 598));
		subParadas.addAll(32, paradas.subList(533, 536));
		subParadas.addAll(35, paradas.subList(190, 193));
		subParadas.addAll(38, paradas.subList(10, 14));
		Linha linha9IdaLagoaDoMatoBoaVistaDozeAnosSantoAntonio = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha9IdaLagoaDoMatoBoaVistaDozeAnosSantoAntonio);
		}

		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 11));
		subParadas.addAll(paradas.subList(193, 196));
		subParadas.addAll(paradas.subList(558, 561));
		subParadas.addAll(paradas.subList(598, 603));
		subParadas.addAll(paradas.subList(561, 571));
		subParadas.addAll(paradas.subList(556, 558));
		subParadas.addAll(paradas.subList(571, 582));
		subParadas.add(paradas.get(511));
		Linha linha9Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha9Volta);
		}

		// LINHA 10 - SHOPPING, UNP - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(622, 632));
		subParadas.addAll(paradas.subList(13, 20));
		subParadas.add(paradas.get(0));
		Linha linha10Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha10Ida);
		}

		// LINHA 10 - SHOPPING, UNP, AEROPORTO, MACARRÃO, BOA VISTA - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(642, 653));
		subParadas.addAll(paradas.subList(615, 622));
		subParadas.addAll(paradas.subList(637, 642));
		subParadas.addAll(paradas.subList(622, 626));
		subParadas.addAll(paradas.subList(656, 668));
		subParadas.addAll(paradas.subList(709, 711));
		subParadas.add(paradas.get(676));
		subParadas.addAll(paradas.subList(686, 693));
		subParadas.addAll(paradas.subList(606, 610));
		subParadas.add(paradas.get(693));
		subParadas.addAll(paradas.subList(598, 603));
		subParadas.addAll(paradas.subList(694, 701));
		subParadas.addAll(paradas.subList(324, 326));
		subParadas.add(paradas.get(701));
		subParadas.addAll(paradas.subList(509, 511));
		subParadas.add(paradas.get(1));
		subParadas.addAll(paradas.subList(14, 20));
		subParadas.add(paradas.get(0));
		Linha linha10IdaAeroportoMacarraoBoaVista = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha10IdaAeroportoMacarraoBoaVista);
		}

		// LINHA 10 - SHOPPING, UNP - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 6));
		subParadas.addAll(paradas.subList(603, 606));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(632, 642));
		subParadas.add(paradas.get(622));
		Linha linha10Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha10Volta);
		}

		// LINHA 10 - SHOPPING, UNP, AEROPORTO, MACARRÃO, BOA VISTA - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 6));
		subParadas.addAll(paradas.subList(603, 606));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(610, 613));
		subParadas.addAll(paradas.subList(702, 709));
		subParadas.addAll(paradas.subList(593, 598));
		subParadas.addAll(paradas.subList(613, 615));
		subParadas.addAll(paradas.subList(673, 676));
		subParadas.add(paradas.get(642));
		Linha linha10VoltaAeroportoMacarraoBoaVista = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha10VoltaAeroportoMacarraoBoaVista);
		}

		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(338, 345));
		subParadas.addAll(paradas.subList(271, 273));
		subParadas.addAll(paradas.subList(345, 355));
		subParadas.addAll(paradas.subList(273, 284));
		subParadas.addAll(paradas.subList(32, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha11Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha11Ida);
		}

		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 376));
		subParadas.add(paradas.get(338));
		Linha linha11Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha11Volta);
		}

		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(196, 206));
		subParadas.addAll(paradas.subList(210, 231));
		subParadas.addAll(paradas.subList(7, 20));
		subParadas.add(paradas.get(0));
		Linha linha12Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha12Ida);
		}

		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 7));
		subParadas.addAll(paradas.subList(231, 271));
		subParadas.add(paradas.get(196));
		Linha linha12Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha12Volta);
		}

		// LINHA 14 - AEROPORTO, RODOVIÁRIA - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(642, 668));
		subParadas.addAll(paradas.subList(606, 610));
		subParadas.addAll(paradas.subList(668, 671));
		subParadas.addAll(paradas.subList(13, 20));
		subParadas.add(paradas.get(0));
		Linha linha14Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha14Ida);
		}

		// LINHA 14 - AEROPORTO, RODOVIÁRIA - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 6));
		subParadas.addAll(paradas.subList(603, 606));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(610, 613));
		subParadas.addAll(paradas.subList(671, 673));
		subParadas.addAll(paradas.subList(613, 615));
		subParadas.addAll(paradas.subList(673, 676));
		subParadas.add(paradas.get(642));
		Linha linha14Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha14Volta);
		}

		// LINHA 15 - MACARRÃO, BOA VISTA - IDA,
		subParadas.clear();
		subParadas.addAll(paradas.subList(676, 693));
		subParadas.addAll(paradas.subList(606, 610));
		subParadas.add(paradas.get(693));
		subParadas.addAll(paradas.subList(598, 603));
		subParadas.addAll(paradas.subList(694, 701));
		subParadas.addAll(paradas.subList(324, 326));
		subParadas.add(paradas.get(701));
		subParadas.addAll(paradas.subList(509, 511));
		subParadas.add(paradas.get(1));
		subParadas.addAll(paradas.subList(14, 20));
		subParadas.add(paradas.get(0));
		Linha linha15Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha15Ida);
		}

		// LINHA 15 - MACARRÃO, BOA VISTA - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 6));
		subParadas.addAll(paradas.subList(603, 606));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(610, 613));
		subParadas.addAll(paradas.subList(702, 709));
		subParadas.addAll(paradas.subList(593, 598));
		subParadas.addAll(paradas.subList(613, 615));
		subParadas.addAll(paradas.subList(709, 711));
		subParadas.add(paradas.get(676));
		Linha linha15Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha15Volta);
		}

		// LINHA 17 - ODETE ROSADO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(355, 366));
		subParadas.addAll(paradas.subList(281, 284));
		subParadas.addAll(paradas.subList(32, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha17Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha17Ida);
		}

		// LINHA 17 - ODETE ROSADO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 369));
		subParadas.addAll(paradas.subList(420, 425));
		subParadas.add(paradas.get(355));
		Linha linha17Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha17Volta);
		}

		// LINHA 19 - CIDADE OESTE - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(536, 548));
		subParadas.addAll(paradas.subList(499, 511));
		subParadas.add(paradas.get(1));
		subParadas.addAll(paradas.subList(14, 20));
		subParadas.add(paradas.get(0));
		Linha linha19Ida = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha19Ida);
		}

		// LINHA 19 - CIDADE OESTE - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 14));
		subParadas.addAll(paradas.subList(548, 558));
		subParadas.addAll(paradas.subList(582, 593));
		subParadas.add(paradas.get(536));
		Linha linha19Volta = new Linha(subParadas);
		for (Parada paradaAtual : subParadas) {
			paradaAtual.addLinha(linha19Volta);
		}

		// Monta os ciclos das linhas----------------------------------
		// Linha 1
		linha1Volta.addLinhaCiclo(linha1Ida);
		linha1Volta.addLinhaCiclo(linha1SantaJuliaIda);
		linha1Volta.addLinhaCiclo(linha2Ida);

		linha1SantaJuliaVolta.addLinhaCiclo(linha1Ida);
		linha1SantaJuliaVolta.addLinhaCiclo(linha1SantaJuliaIda);
		linha1SantaJuliaVolta.addLinhaCiclo(linha2Ida);

		// Linha 2
		linha2Volta.addLinhaCiclo(linha1Ida);
		linha2Volta.addLinhaCiclo(linha1SantaJuliaIda);
		linha2Volta.addLinhaCiclo(linha2Ida);

		// Linha 4
		linha4Volta.addLinhaCiclo(linha4Ida);
		linha4Volta.addLinhaCiclo(linha4SantaJulia);
		linha4Volta.addLinhaCiclo(linha1Ida);
		linha4Volta.addLinhaCiclo(linha1SantaJuliaIda);
		linha4Volta.addLinhaCiclo(linha1Volta);

		// Linha 5
		linha5Volta.addLinhaCiclo(linha5Ida);
		linha5Volta.addLinhaCiclo(linha5IdaUniversidades);

		linha5VoltaUERN.addLinhaCiclo(linha5Ida);
		linha5VoltaUERN.addLinhaCiclo(linha5IdaUniversidades);

		linha5VoltaIFRN.addLinhaCiclo(linha5Ida);
		linha5VoltaIFRN.addLinhaCiclo(linha5IdaUniversidades);

		linha5VoltaUniversidades.addLinhaCiclo(linha5Ida);
		linha5VoltaUniversidades.addLinhaCiclo(linha5IdaUniversidades);

		linha5VoltaOdeteRosado.addLinhaCiclo(linha5Ida);
		linha5VoltaOdeteRosado.addLinhaCiclo(linha5IdaUniversidades);

		// Linha 6
		linha6Volta.addLinhaCiclo(linha6Ida);
		linha6Volta.addLinhaCiclo(linha6IdaSemUlrickGraff);
		linha6Volta.addLinhaCiclo(linha6IdaSemUlrickGraffNemRioBranco);
		linha6Volta.addLinhaCiclo(linha6VoltaUNIRB);

		linha6VoltaSemUlrickGraff.addLinhaCiclo(linha6Ida);
		linha6VoltaSemUlrickGraff.addLinhaCiclo(linha6IdaSemUlrickGraff);
		linha6VoltaSemUlrickGraff.addLinhaCiclo(linha6IdaSemUlrickGraffNemRioBranco);
		linha6Volta.addLinhaCiclo(linha6VoltaUNIRB);

		linha6VoltaUNIRB.addLinhaCiclo(linha11Volta);

		// Linha 7
		linha7Volta.addLinhaCiclo(linha7Ida);
		linha7Volta.addLinhaCiclo(linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra);
		linha7Volta.addLinhaCiclo(linha7VoltaJardimDasPalmeiras);

		linha7VoltaJardimDasPalmeiras.addLinhaCiclo(linha7IdaJardimDasPalmeiras);

		linha7VoltaSumareLiberdadePlanalto.addLinhaCiclo(linha7Ida);
		linha7VoltaSumareLiberdadePlanalto.addLinhaCiclo(linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra);
		linha7VoltaSumareLiberdadePlanalto.addLinhaCiclo(linha7VoltaJardimDasPalmeiras);

		// Linha 8
		linha8Volta.addLinhaCiclo(linha8Ida);
		linha8Volta.addLinhaCiclo(linha7VoltaSumareLiberdadePlanalto);

		// Linha 9
		linha8Volta.addLinhaCiclo(linha9Ida);
		linha8Volta.addLinhaCiclo(linha9IdaLagoaDoMatoBoaVistaDozeAnosSantoAntonio);

		// Linha 10
		linha10Volta.addLinhaCiclo(linha10Ida);
		linha10Volta.addLinhaCiclo(linha10IdaAeroportoMacarraoBoaVista);

		linha10VoltaAeroportoMacarraoBoaVista.addLinhaCiclo(linha10IdaAeroportoMacarraoBoaVista);
		linha10VoltaAeroportoMacarraoBoaVista.addLinhaCiclo(linha14Ida);

		// Linha 11
		linha11Volta.addLinhaCiclo(linha11Ida);

		// Linha 12
		linha12Volta.addLinhaCiclo(linha12Ida);

		// Linha 14
		linha14Volta.addLinhaCiclo(linha14Ida);
		linha14Volta.addLinhaCiclo(linha10IdaAeroportoMacarraoBoaVista);

		// Linha 15
		linha15Volta.addLinhaCiclo(linha15Ida);
		linha15Volta.addLinhaCiclo(linha10IdaAeroportoMacarraoBoaVista);

		// Linha 17
		linha17Volta.addLinhaCiclo(linha17Ida);
		linha17Volta.addLinhaCiclo(linha5VoltaOdeteRosado);

		// Linha 19
		linha19Volta.addLinhaCiclo(linha19Ida);

		// -----------------------------------------------------------------------------------------------------------------------
		// Define os ônibus
		// numeroVeiculo, capacidadePessoas, velocidade, linhaOnibus
		List<Onibus> onibus = new ArrayList<Onibus>();
		List<Viagem> viagens = new ArrayList<Viagem>();

		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 37)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 30)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 27)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 25)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 15)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 10)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 5)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 9)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 1)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 59)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 49)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 42)));
		viagens.add(new Viagem(linha1Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 38)));
		viagens.add(new Viagem(linha1Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 36)));
		viagens.add(new Viagem(linha2UniversidadesAeC,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 0)));
		viagens.add(new Viagem(linha1Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 20)));

		// Veículo 1
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha1Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 5)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 8)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 5)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 5)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 56)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 51)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 43)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 41)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 34)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 32)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 22)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 20)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 12)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 20)));
		viagens.add(new Viagem(linha1Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 10)));
		viagens.add(new Viagem(linha1Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 5)));
		viagens.add(new Viagem(linha2UniversidadesIFRN,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 0)));
		viagens.add(new Viagem(linha1Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 25)));

		// Veículo 2
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 59)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 52)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 50)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 53)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 50)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 46)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 50)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 40)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 28)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 26)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 17)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 15)));
		viagens.add(new Viagem(linha1SantaJuliaIda,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 20)));
		viagens.add(new Viagem(linha1SantaJuliaVolta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 10)));
		viagens.add(new Viagem(linha1Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 30)));
		viagens.add(new Viagem(linha1Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 20, 5)));
		viagens.add(new Viagem(linha1Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 21, 15)));
		viagens.add(new Viagem(linha1Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 55)));

		// Veículo 3
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha4SantaJulia,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 45)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 40)));
		viagens.add(new Viagem(linha4SantaJulia,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 20)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 12)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 53)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 41)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 31)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 20)));
		viagens.add(new Viagem(linha4SantaJulia,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 6)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 53)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 33)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 14)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 3)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 47)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 27)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 6)));
		viagens.add(new Viagem(linha4SantaJulia,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 46)));

		// Veículo 4
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(
				new Viagem(linha3, new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 55)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 40)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 25)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 55)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 35)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 5)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 45)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 15)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 50)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 30)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 10)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 45)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 25)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 0)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 40)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 10)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 50)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 20)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 0)));
		viagens.add(new Viagem(linha6IdaSemUlrickGraff,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 55)));
		viagens.add(new Viagem(linha6VoltaSemUlrickGraff,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 40)));
		viagens.add(new Viagem(linha6IdaSemUlrickGraff,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 10)));
		viagens.add(new Viagem(linha6VoltaSemUlrickGraff,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 50)));
		viagens.add(new Viagem(linha6IdaSemUlrickGraffComUNIRBComCoelhoNeto,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 0)));
		viagens.add(new Viagem(linha10VoltaAeroportoMacarraoBoaVista,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 40)));

		// Veículo 5
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 53)));
		viagens.add(new Viagem(linha4SantaJulia,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 35)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 25)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 11)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 2)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 49)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 35)));
		viagens.add(new Viagem(linha4SantaJulia,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 22)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 17)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 57)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 42)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 20)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 5)));
		viagens.add(new Viagem(linha4Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 43)));
		viagens.add(new Viagem(linha4Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 25)));
		viagens.add(new Viagem(linha4SantaJulia,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 30)));

		// Veículo 6
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 40)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 24)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 55)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 47)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 13)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 2)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 29)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 23)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 50)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 44)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 13)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 5)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 34)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 26)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 56)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 48)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 24)));
		viagens.add(new Viagem(linha7Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 20)));

		// Veículo 7
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 55)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 42)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 11)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 3)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 30)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 22)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 51)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 43)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 10)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 5)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 33)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 25)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 55)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 47)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 17)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 9)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 46)));
		viagens.add(new Viagem(linha7Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 42)));

		// Veículo 8
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 21)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 15)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 13)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 22)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 16)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 13)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 7)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 4)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 55)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 55)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 48)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 43)));
		viagens.add(new Viagem(linha1Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 47)));
		viagens.add(new Viagem(linha1Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 45)));
		viagens.add(new Viagem(linha1Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 50)));
		viagens.add(new Viagem(linha1Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 21, 45)));

		// Veículo 9
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha6VoltaUNIRB,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 35)));
		viagens.add(new Viagem(linha5VoltaOdeteRosado,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 15)));

		// Veículo 10
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 30)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 13)));
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 50)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 46)));
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 20)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 16)));
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 52)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 49)));
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 21)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 24)));
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 4)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 2)));
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 42)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 40)));
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 19)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 20)));
		viagens.add(new Viagem(linha8Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 54)));
		viagens.add(new Viagem(linha8Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 45)));

		// Veículo 11
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha12Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 55)));
		viagens.add(new Viagem(linha12Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 50)));
		viagens.add(new Viagem(linha12Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 37)));
		viagens.add(new Viagem(linha12Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 18)));
		viagens.add(new Viagem(linha12Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 5)));
		viagens.add(new Viagem(linha12Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 0)));
		viagens.add(new Viagem(linha12Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 47)));
		viagens.add(new Viagem(linha12Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 47)));
		viagens.add(new Viagem(linha12Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 35)));
		viagens.add(new Viagem(linha12Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 35)));
		viagens.add(new Viagem(linha12Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 23)));
		viagens.add(new Viagem(linha12Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 20)));
		viagens.add(new Viagem(linha12Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 10)));
		viagens.add(new Viagem(linha12Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 1)));

		// Veículo 12
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 10)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 0)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 27)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 19)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 50)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 42)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 11)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 3)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 31)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 25)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 54)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 46)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 15)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 8)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 41)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 34)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 8)));
		viagens.add(new Viagem(linha7Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 4)));

		// Veículo 13
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 40)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 32)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 58)));

		// Veículo 14
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 25)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 12)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 42)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 41)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 8)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 2)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 31)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 23)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 53)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 45)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 13)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 7)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 36)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 28)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 3)));
		viagens.add(new Viagem(linha7VoltaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 59)));
		viagens.add(new Viagem(linha7IdaJardimDasPalmeiras,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 34)));
		viagens.add(new Viagem(linha7Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 30)));
		viagens.add(new Viagem(linha7Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 10)));
		viagens.add(new Viagem(linha7Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 53)));
		viagens.add(new Viagem(linha7Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 20, 13)));
		viagens.add(new Viagem(linha7Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 20, 53)));
		viagens.add(new Viagem(linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 21, 13)));
		viagens.add(new Viagem(linha7VoltaSumareLiberdadePlanalto,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 21, 50)));

		// Veículo 18
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 35)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 43)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 5)));
		viagens.add(new Viagem(linha2Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 40)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 55)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 30)));
		viagens.add(new Viagem(linha2Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 35)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 10)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 40)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 20)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 0)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 40)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 15)));
		viagens.add(new Viagem(linha6Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 55)));
		viagens.add(new Viagem(linha6Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 25)));
		viagens.add(new Viagem(linha6IdaSemUlrickGraff,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 30)));
		viagens.add(new Viagem(linha6VoltaSemUlrickGraff,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 18)));

		// Veículo 22
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha9IdaLagoaDoMatoBoaVistaDozeAnosSantoAntonio,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 45)));
		viagens.add(new Viagem(linha9Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 35)));
		viagens.add(new Viagem(linha9Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 20)));
		viagens.add(new Viagem(linha9Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 5)));
		viagens.add(new Viagem(linha9Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 55)));
		viagens.add(new Viagem(linha9Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 35)));
		viagens.add(new Viagem(linha9Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 24)));
		viagens.add(new Viagem(linha9Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 0)));
		viagens.add(new Viagem(linha9IdaLagoaDoMatoBoaVistaDozeAnosSantoAntonio,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 55)));
		viagens.add(new Viagem(linha9Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 48)));
		viagens.add(new Viagem(linha9Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 36)));
		viagens.add(new Viagem(linha9Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 15)));
		viagens.add(new Viagem(linha9Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 5)));
		viagens.add(new Viagem(linha9Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 40)));
		viagens.add(new Viagem(linha9Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 30)));
		viagens.add(new Viagem(linha9Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 20)));

		// Veículo 23
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 10)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 5)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 37)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 42)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 12)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 10)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 47)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 45)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 21)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 20)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 57)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 55)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 34)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 33)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 14)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 15)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 50)));
		viagens.add(new Viagem(linha5VoltaUniversidades,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 45)));

		// Veículo 24
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 40)));
		viagens.add(new Viagem(linha5VoltaUERN,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 35)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 7)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 5)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 42)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 40)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 19)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 20)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 52)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 50)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 24)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 25)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 0)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 0)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 41)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 40)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 20)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 12)));
		viagens.add(new Viagem(linha5IdaUniversidades,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 40)));
		viagens.add(new Viagem(linha5VoltaUniversidades,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 20, 35)));

		// Veículo 25
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 0)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 58)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 32)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 35)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 12)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 10)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 47)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 45)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 20)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 25)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 59)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 0)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 35)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 35)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 10)));
		viagens.add(new Viagem(linha5Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 15)));
		viagens.add(new Viagem(linha5Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 56)));
		viagens.add(new Viagem(linha5VoltaUniversidades,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 52)));
		viagens.add(new Viagem(linha5IdaUniversidades,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 20, 18)));
		viagens.add(new Viagem(linha5VoltaUniversidades,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 21, 35)));
		viagens.add(new Viagem(linha5VoltaIFRN,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 40)));

		// Veículo 26
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha19Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 45)));
		viagens.add(new Viagem(linha19Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 27)));
		viagens.add(new Viagem(linha19Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 22)));
		viagens.add(new Viagem(linha19Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 20)));
		viagens.add(new Viagem(linha6IdaSemUlrickGraffNemRioBranco,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 5)));
		viagens.add(new Viagem(linha10VoltaAeroportoMacarraoBoaVista,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 30)));

		// Veículo 27
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha11Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 38)));
		viagens.add(new Viagem(linha11Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 0)));
		viagens.add(new Viagem(linha11Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 19)));
		viagens.add(new Viagem(linha11Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 15)));

		// Veículo 28
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 40)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 4)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 31)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 55)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 22)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 46)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 13)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 37)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 4)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 28)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 55)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 19)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 46)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 10)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 37)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 1)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 28)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 52)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 10)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 34)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 1)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 25)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 57)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 21)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 48)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 12)));
		viagens.add(new Viagem(linha10Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 39)));
		viagens.add(new Viagem(linha10Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 3)));
		viagens.add(new Viagem(linha10VoltaAeroportoMacarraoBoaVista,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 21, 40)));
		viagens.add(new Viagem(linha10IdaAeroportoMacarraoBoaVista,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 20)));
		viagens.add(new Viagem(linha7VoltaSumareLiberdadePlanalto,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 55)));

		// Veículo 29
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 50)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 28)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 58)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 42)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 16)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 3)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 35)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 15)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 47)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 16)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 50)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 40)));

		// Veículo 30
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 45)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 22)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 44)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 26)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 48)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 30)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 52)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 34)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 56)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 42)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 4)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 46)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 8)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 50)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 12)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 54)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 16)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 58)));
		viagens.add(new Viagem(linha14Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 20)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 2)));
		viagens.add(new Viagem(linha14Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 56)));

		// Veículo 31
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha17Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 5, 45)));
		viagens.add(new Viagem(linha17Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 35)));
		viagens.add(new Viagem(linha17Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 0)));
		viagens.add(new Viagem(linha17Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 27)));
		viagens.add(new Viagem(linha17Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 50)));
		viagens.add(new Viagem(linha17Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 38)));
		viagens.add(new Viagem(linha17Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 0)));
		viagens.add(new Viagem(linha17Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 30)));

		// Veículo 32
		onibus.add(new Onibus(50, 50, viagens));

		viagens.clear();
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 6, 20)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 2)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 7, 32)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 19)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 8, 54)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 9, 41)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 10, 16)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 3)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 11, 38)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 25)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 59)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 13, 46)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 14, 20)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 7)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 15, 41)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 16, 33)));
		viagens.add(new Viagem(linha15Ida,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 7)));
		viagens.add(new Viagem(linha15Volta,
				new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 17, 57)));

		// Veículo 33
		onibus.add(new Onibus(50, 50, viagens));

		// -----------------------------------------------------------------------------------------------------------------------
		// Define as pessoas
		// nome, 'origem', 'destino'
		List<Pedestre> pessoas = new ArrayList<Pedestre>();

		// Adiciona 'i' pessoas no vetor de pessoas
		for (int i = 0; i < 600; i++) {
			pessoas.add(new Pedestre("pessoa" + i));
		}

		// Gera um vetor de strings para armazenar cada movimento da pessoa
		// onde ela subiu e onde ela desceu
		int numeroPessoas = pessoas.size();
		String stringPessoas[] = new String[numeroPessoas];

		// -----------------------------------------------------------------------------------------------------------------------

		/*
		 * // Imprime as paradas com os pedestres String paradasString = ""; for (int i
		 * = 0; i < paradas.size(); i++) { paradasString += paradas.get(i) + "\n"; }
		 * Main.armazenarDados("paradasID.txt", paradasString);
		 */

		List<Pedestre> pessoasParada = new ArrayList<Pedestre>();
		List<Pedestre> pessoasOnibus = new ArrayList<Pedestre>();
		List<Pedestre> pessoasOnibusSimulado = new ArrayList<Pedestre>();
		List<Pedestre> pessoasDesceramOnibus = new ArrayList<Pedestre>();
		List<Pedestre> pessoasSubiramOnibus = new ArrayList<Pedestre>();
		List<Pedestre> pessoasTrocaramParada = new ArrayList<Pedestre>();

		int numeroPessoasOnibus;
		int numeroPessoasParada;
		int indicePessoa;
		int indiceString;
		int horaAtual;
		int minutoAtual;
		int segundoAtual;
		double coordenadaX;
		double coordenadaY;

		Calendar momentoAtual = Calendar.getInstance();
		String horariosSaida = "";

		// Biblioteca proj4j
		// Lida com conversão de coordenadas
		CRSFactory factory = new CRSFactory();
		CoordinateReferenceSystem origemCRS = factory.createFromName("EPSG:4326");
		CoordinateReferenceSystem destinoCRS = factory.createFromName("EPSG:3857");
		BasicCoordinateTransform transform = new BasicCoordinateTransform(origemCRS, destinoCRS);
		ProjCoordinate coordenadasOrigem = new ProjCoordinate();
		ProjCoordinate coordenadasDestino = new ProjCoordinate();

		int indiceParada;
		int numeroParadas = paradas.size();
		int numeroParadaAleatoria;
		int numeroPessoasAleatorio;
		Random paradasAleatorias;
		String stringSaidaPopulation;
		String stringSaidaFacilities;

		// Instâncias da simulação
		for (int instancia = 0; instancia < 5; instancia++) {
			Main.seed = Main.sementeAleatoria.nextInt();

			// Define número de pessoas na simulação
			numeroPessoasAleatorio = numeroPessoas;

			paradasAleatorias = new Random(seed);

			Main.log += "Número de pessoas nessa simulação é " + numeroPessoasAleatorio + '\n' + '\n';

			// Limpa as pessoas das paradas
			for (Parada paradaAtual : paradas) {
				paradaAtual.removeAllPedestres();
			}

			// Adiciona parada nos pedestres e os pedestres na parada de forma aleatória
			// e também o destino do pedestre
			for (int i = 0; i < numeroPessoasAleatorio; i++) {
				Calendar horarioPartida = Calendar.getInstance();
				int horaAleatoria = ThreadLocalRandom.current().nextInt(18, 23);
				int minutoAleatorio = ThreadLocalRandom.current().nextInt(60);
				int segundoAleatorio = ThreadLocalRandom.current().nextInt(60);

				// define a origem
				numeroParadaAleatoria = paradasAleatorias.nextInt(numeroParadas);

				
				pessoas.get(i).setOrigem(paradas.get(numeroParadaAleatoria));
				pessoas.get(i).setParadaAtual(paradas.get(numeroParadaAleatoria));

				// define o horário de partida
				horarioPartida.set(Calendar.HOUR_OF_DAY, horaAleatoria);
				horarioPartida.set(Calendar.MINUTE, minutoAleatorio);
				horarioPartida.set(Calendar.SECOND, segundoAleatorio);

				pessoas.get(i).setHorarioPartida(horarioPartida);

				paradas.get(numeroParadaAleatoria).addPedestre(pessoas.get(i));

				// define o destino
				numeroParadaAleatoria = paradasAleatorias.nextInt(numeroParadas);

				pessoas.get(i).setDestino(paradas.get(numeroParadaAleatoria));
				pessoas.get(i).setNoDestino(false);
				pessoas.get(i).setEmViagem(false);
			}

			// Reinicia as strings
			stringSaidaPopulation = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
					+ "<!DOCTYPE plans SYSTEM \"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n" + "\n" + "<plans>\n";
			stringSaidaFacilities = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
					+ "<!DOCTYPE facilities SYSTEM \"http://www.matsim.org/files/dtd/facilities_v1.dtd\">\n" + "\n"
					+ "<facilities>\n";

			for (int i = 0; i < numeroPessoas; i++) {
				stringPessoas[i] = null;
			}

			// Faz as viagens dos ônibus
			indiceParada = 0;
			// Enquanto o dia não passou (simulaçao limitada a 23 horas e 59 minutos)
			while (Main.relogioSimulacao.get(Calendar.DAY_OF_MONTH) == Calendar.DAY_OF_MONTH) {
				for (Onibus onibusAtual : onibus) {
					int numeroViagens = onibusAtual.getViagens().size();
					int indice = 0;

					// Enquanto não chegar no fim do vetor de viagens e o horário do ônibus em
					// função do índice for diferente do horário da simulação
					while ((indice < numeroViagens)
							&& (onibusAtual.getViagens().get(indice).getHorario().equals(relogioSimulacao))) {
						indice++;
					}

					// Se algum horário do ônibus for igual ao horário da simulação
					if (indice != numeroViagens) {
						// Se a viagem escolhida já não foi usada
						if (!onibusAtual.getViagensCumpridas().get(indice)) {
							onibusAtual.setViagensCumpridas(indice, true);

							momentoAtual.set(onibusAtual.getViagens().get(indice).getHorario().get(Calendar.YEAR),
									onibusAtual.getViagens().get(indice).getHorario().get(Calendar.MONTH),
									onibusAtual.getViagens().get(indice).getHorario().get(Calendar.DAY_OF_MONTH),
									onibusAtual.getViagens().get(indice).getHorario().get(Calendar.HOUR_OF_DAY),
									onibusAtual.getViagens().get(indice).getHorario().get(Calendar.MINUTE),
									onibusAtual.getViagens().get(indice).getHorario().get(Calendar.SECOND));

							horariosSaida += momentoAtual.get(Calendar.HOUR_OF_DAY) + ":"
									+ momentoAtual.get(Calendar.MINUTE) + ":" + momentoAtual.get(Calendar.SECOND)
									+ "\n";

							for (Parada paradaAtual : onibusAtual.getViagens().get(indice).getLinha().getParadas()) {
								indiceParada = onibusAtual.getViagens().get(indice).getLinha().getParadas()
										.indexOf(paradaAtual);

								horaAtual = momentoAtual.get(Calendar.HOUR_OF_DAY);
								minutoAtual = momentoAtual.get(Calendar.MINUTE);
								segundoAtual = momentoAtual.get(Calendar.SECOND);

								coordenadasOrigem.setValue(paradaAtual.getCoordenadaX(), paradaAtual.getCoordenadaY());
								transform.transform(coordenadasOrigem, coordenadasDestino);

								coordenadaX = coordenadasDestino.x;
								coordenadaY = coordenadasDestino.y;

								pessoasOnibus = onibusAtual.getPedestres();
								pessoasParada = paradaAtual.getPedestres();

								numeroPessoasOnibus = pessoasOnibus.size();
								numeroPessoasParada = pessoasParada.size();

								// Descida de pessoas
								for (int i = 0; i < numeroPessoasOnibus; i++) {
									// Se a parada é o destino do pedestre, ele desce
									if (paradaAtual.getId() == pessoasOnibus.get(i).getDestino().getId()) {
										indicePessoa = -1;
										pessoasDesceramOnibus.add(pessoasOnibus.get(i));
										pessoasOnibus.get(i).setParadaAtual(paradaAtual);

										// Procura pelo índice da pessoa no vetor de pessoas
										for (int a = 0; a < numeroPessoas; a++) {
											if (pessoasOnibus.get(i).getNome().equals(pessoas.get(a).getNome())) {
												indicePessoa = a;
												break;
											}
										}

										if (indicePessoa != -1) {
											stringPessoas[indicePessoa] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\""
													+ coordenadaY + "\" type=\"home\"/>\n";
											stringSaidaFacilities += "\n\t<facility id=\""
													+ pessoasOnibus.get(i).getNome() + "Desceu" + horaAtual + ":"
													+ minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX
													+ "\" y=\"" + coordenadaY + "\"/>\n";
										}

										pessoasOnibus.get(i).setNoDestino(true);
										pessoasOnibus.get(i).setEmViagem(false);
										pessoasOnibus.remove(i);
										numeroPessoasOnibus--;
									}
									// Se a parada que o ônibus está possui uma linha (que não é a atual linha que
									// está sendo feita) que tem o destino do pedestre, ele desce
									else {
										boolean paradaEncontrada = false;
										// Se a linha que o ônibus está não possui o destino do pedestre
										if (!onibusAtual.getViagens().get(indice).getLinha().getParadas()
												.contains(pessoasOnibus.get(i).getDestino())) {
											for (Linha linhaAtual : paradaAtual.getLinhas()) {
												// se não for a linha atual
												if (!linhaAtual
														.equals(onibusAtual.getViagens().get(indice).getLinha())) {
													// se a linha do índice possuir a parada de destino do pedestre
													if (linhaAtual.getParadas()
															.contains(pessoasOnibus.get(i).getDestino())) {
														pessoasDesceramOnibus.add(pessoasOnibus.get(i));
														pessoasOnibus.get(i).setParadaAtual(paradaAtual);

														pessoasOnibus.remove(i);
														numeroPessoasOnibus--;
														paradaEncontrada = true;
													} else {
														// Se não, procura nos ciclos da linha
														int numeroLinhasCiclo = linhaAtual.getLinhaCiclo().size();

														for (int indiceLinha = 0; (indiceLinha < numeroLinhasCiclo)
																&& (!paradaEncontrada); indiceLinha++) {
															if (linhaAtual.getLinhaCiclo().get(indiceLinha).getParadas()
																	.contains(pessoasOnibus.get(i).getDestino())) {
																pessoasDesceramOnibus.add(pessoasOnibus.get(i));
																pessoasOnibus.get(i).setParadaAtual(paradaAtual);

																pessoasOnibus.remove(i);
																numeroPessoasOnibus--;
																paradaEncontrada = true;
															}
														}
													}
												}
												if (paradaEncontrada) {
													break;
												}
											}
										}
									}
								}

								// Subida de pessoas
								for (int i = 0; i < numeroPessoasParada; i++) {
									// Se é hora do pedestre sair E
									// Se o ônibus não está cheio E
									// Se o pedestre não está no destino
									if ((!pessoasParada.get(i).getHorarioPartida().before(relogioSimulacao))
											&& (numeroPessoasOnibus <= onibusAtual.getCapacidadeMaximaPassageiros())
											&& (!pessoasParada.get(i).isNoDestino())) {
										// se ele estiver em viagem e o ônibus passar na parada de destino, ele sobe
										// se ele não estiver em viagem ele sobe
										if (pessoasParada.get(i).isEmViagem()) {
											// procura se a linha do ônibus passa pelo destino
											Linha linhaOnibusAtual = onibusAtual.getViagens().get(indice).getLinha();
											int numeroParadasLinhaOnibusAtual = linhaOnibusAtual.getParadas().size();
											boolean destinoEncontrado = false;

											// procura pela parada de destino
											for (int x = 0; (x < numeroParadasLinhaOnibusAtual)
													&& (!destinoEncontrado); x++) {
												if (linhaOnibusAtual.getParadas().get(x).getId() == pessoasParada.get(i)
														.getDestino().getId()) {
													destinoEncontrado = true;
												}
											}

											// se o destino ainda não foi encontrado, procura pelo ciclo da parada
											if (!destinoEncontrado) {
												for (Linha linhaCiclo : linhaOnibusAtual.getLinhaCiclo()) {
													if (!destinoEncontrado) {
														int numeroParadasLinhaCiclo = linhaCiclo.getParadas().size();

														// procura pela parada de destino
														for (int x = 0; (x < numeroParadasLinhaCiclo)
																&& (!destinoEncontrado); x++) {
															if (linhaCiclo.getParadas().get(x).getId() == pessoasParada
																	.get(i).getDestino().getId()) {
																destinoEncontrado = true;
															}
														}
													}
												}
											}

											// se o destino foi encontrado então o pedestre sobe
											// senão, o pedestre muda de terminal
											if (destinoEncontrado) {
												pessoasSubiramOnibus.add(pessoasParada.get(i));
												pessoasOnibus.add(pessoasParada.get(i));
												numeroPessoasOnibus++;
												pessoasParada.get(i).setEmViagem(true);
											} else {
												if (paradaAtual.getId() == 0) {
													pessoasParada.get(i).setParadaAtual(paradas.get(20));
													paradas.get(20).addPedestre(pessoasParada.get(i));
												} else if (paradaAtual.getId() == 20) {
													pessoasParada.get(i).setParadaAtual(paradas.get(0));
													paradas.get(0).addPedestre(pessoasParada.get(i));
												}
												pessoasTrocaramParada.add(pessoasParada.get(i));
											}
										} else {
											pessoasSubiramOnibus.add(pessoasParada.get(i));
											pessoasOnibus.add(pessoasParada.get(i));
											numeroPessoasOnibus++;

											indicePessoa = -1;
											// Procura pelo índice da pessoa no vetor de pessoas
											for (int a = 0; a < numeroPessoas; a++) {
												if (pessoasParada.get(i).getNome().equals(pessoas.get(a).getNome())) {
													indicePessoa = a;
													break;
												}
											}

											if (indicePessoa != -1) {
												if (stringPessoas[indicePessoa] == null) {
													stringPessoas[indicePessoa] = "\n\t<person id=\""
															+ pessoas.get(indicePessoa).getNome() + "\" >\n\t\t<plan>\n"
															+ "\t\t\t<act end_time=\""
															+ pessoas.get(indicePessoa).getHorarioPartida()
																	.get(Calendar.HOUR_OF_DAY)
															+ ":"
															+ pessoas.get(indicePessoa).getHorarioPartida()
																	.get(Calendar.MINUTE)
															+ ":"
															+ pessoas.get(indicePessoa).getHorarioPartida()
																	.get(Calendar.SECOND)
															+ "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY
															+ "\" type=\"home\"/>\n" + "\t\t\t<leg mode=\"pt\"/>\n";
												} else {
													indiceString = stringPessoas[indicePessoa].length() - 2;

													while (stringPessoas[indicePessoa].charAt(indiceString) != '\n') {
														indiceString--;
													}

													stringPessoas[indicePessoa] = stringPessoas[indicePessoa]
															.substring(0, indiceString + 1);

													stringPessoas[indicePessoa] += "\t\t\t<act end_time=\""
															+ pessoas.get(indicePessoa).getHorarioPartida()
																	.get(Calendar.HOUR_OF_DAY)
															+ ":"
															+ pessoas.get(indicePessoa).getHorarioPartida()
																	.get(Calendar.MINUTE)
															+ ":"
															+ pessoas.get(indicePessoa).getHorarioPartida()
																	.get(Calendar.SECOND)
															+ "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY
															+ "\" type=\"home\"/>\n" + "\t\t\t<leg mode=\"pt\"/>\n";
												}

												stringSaidaFacilities += "\n\t<facility id=\""
														+ pessoasParada.get(i).getNome() + "Subiu"
														+ pessoas.get(indicePessoa).getHorarioPartida()
																.get(Calendar.HOUR_OF_DAY)
														+ ":"
														+ pessoas.get(indicePessoa).getHorarioPartida()
																.get(Calendar.MINUTE)
														+ ":"
														+ pessoas.get(indicePessoa).getHorarioPartida()
																.get(Calendar.SECOND)
														+ "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\"/>\n";
											}
											pessoasParada.get(i).setEmViagem(true);
										}
									}
								}

								paradaAtual.addAllPedestres(pessoasDesceramOnibus);
								paradaAtual.removeAllPedestres(pessoasSubiramOnibus);
								paradaAtual.removeAllPedestres(pessoasTrocaramParada);

								// Atualiza os pedestres que estão no ônibus
								onibusAtual.setPedestres(pessoasOnibus);

								Main.log += "=====================================================================\n";
								Main.log += "Instância " + instancia + " : " + "Parada " + indiceParada + " : "
										+ paradaAtual.getNomeParada() + " : " + "Ônibus " + onibusAtual.getId() + '\n';
								Main.log += "Quem subiu: " + pessoasSubiramOnibus + '\n';
								Main.log += "Quem desceu: " + pessoasDesceramOnibus + '\n';
								Main.log += "Quem está no ônibus: " + pessoasOnibus + '\n' + '\n';

								// Simula deslocamento
								if ((indiceParada + 1) != onibusAtual.getViagens().get(indice).getLinha().getParadas()
										.size()) {
									pessoasOnibusSimulado = Main.simularDeslocamento(momentoAtual, paradaAtual,
											onibusAtual.getViagens().get(indice).getLinha().getParadas()
													.get(indiceParada + 1),
											onibusAtual.getVelocidade(), pessoasSubiramOnibus, pessoasDesceramOnibus,
											pessoasOnibus, pessoasOnibusSimulado);
								} else {
									pessoasOnibusSimulado = Main.simularDeslocamento(momentoAtual,
											onibusAtual.getViagens().get(indice).getLinha().getParadas()
													.get(indiceParada - 1),
											paradaAtual, onibusAtual.getVelocidade(), pessoasSubiramOnibus,
											pessoasDesceramOnibus, pessoasOnibus, pessoasOnibusSimulado);
								}

								// Esvazia vetores de descida e subida
								pessoasDesceramOnibus.clear();
								pessoasSubiramOnibus.clear();
								pessoasTrocaramParada.clear();
							}
							pessoasOnibusSimulado.clear();

							Parada paradaAtual = onibusAtual.getViagens().get(indice).getLinha().getParadas()
									.get(indiceParada);

							horaAtual = momentoAtual.get(Calendar.HOUR_OF_DAY);
							minutoAtual = momentoAtual.get(Calendar.MINUTE);
							segundoAtual = momentoAtual.get(Calendar.SECOND);

							coordenadasOrigem.setValue(paradaAtual.getCoordenadaX(), paradaAtual.getCoordenadaY());
							transform.transform(coordenadasOrigem, coordenadasDestino);

							coordenadaX = coordenadasDestino.x;
							coordenadaY = coordenadasDestino.y;

							Main.log += "Terminal: " + paradaAtual.getNomeParada() + '\n';
							Main.log += "Quem desceu: " + onibusAtual.getPedestres() + '\n';
							paradaAtual.addAllPedestres(onibusAtual.getPedestres());

							numeroPessoasOnibus = onibusAtual.getPedestres().size();

							// Atualiza a parada atual de cada pessoa que estava no ônibus
							for (int i = 0; i < numeroPessoasOnibus; i++) {
								pessoasOnibus.get(i).setParadaAtual(paradaAtual);

								if (pessoasOnibus.get(i).isNoDestino()) {
									indicePessoa = -1;
									// Procura pelo índice da pessoa no vetor de pessoas
									for (int a = 0; a < numeroPessoas; a++) {
										if (pessoasOnibus.get(i).getNome().equals(pessoas.get(a).getNome())) {
											indicePessoa = a;
											break;
										}
									}

									if (indicePessoa != -1) {
										stringPessoas[indicePessoa] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\""
												+ coordenadaY + "\" type=\"home\"/>\n";
										stringSaidaFacilities += "\n\t<facility id=\"" + pessoasOnibus.get(i).getNome()
												+ "Desceu" + horaAtual + ":" + minutoAtual + ":" + segundoAtual
												+ "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\"/>\n";
									}
									pessoasOnibus.get(i).setEmViagem(false);
								}
							}

							onibusAtual.clearPedestres();
						}
					}
				}

				// avança o relógio da simulação em 1 minuto
				relogioSimulacao.setTimeInMillis(Main.relogioSimulacao.getTimeInMillis() + 60000);
			}

			// reseta os horários cumpridos de todos os ônibus
			for (Onibus onibusAtual : onibus) {
				onibusAtual.resetViagensCumpridas();
			}
			// reseta o relógio da simulação
			Main.relogioSimulacao.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, horaInicialSimulacao,
					minutoInicialSimulacao, segundoInicialSimulacao);

			// Adiciona as pessoas que fizeram parte da simulação na string de saída
			for (int a = 0; a < numeroPessoas; a++) {
				if (stringPessoas[a] != null) {
					// se o último passo do pedestre está incompleto
					if (stringPessoas[a].charAt(stringPessoas[a].length() - 16) == 'l') {
						coordenadasOrigem.setValue(pessoas.get(a).getParadaAtual().getCoordenadaX(),
								pessoas.get(a).getParadaAtual().getCoordenadaY());
						transform.transform(coordenadasOrigem, coordenadasDestino);

						coordenadaX = coordenadasDestino.x;
						coordenadaY = coordenadasDestino.y;

						stringPessoas[a] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\"" + coordenadaY
								+ "\" type=\"home\"/>\n";
						stringSaidaFacilities += "\n\t<facility id=\"" + pessoas.get(a).getNome() + "Desceu"
								+ relogioSimulacao.get(Calendar.HOUR_OF_DAY) + ":"
								+ relogioSimulacao.get(Calendar.MINUTE) + ":" + relogioSimulacao.get(Calendar.SECOND)
								+ "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY + "\"/>\n";
					}

					stringPessoas[a] += "\t\t</plan>\n" + "\t</person>\n";
					stringSaidaPopulation += stringPessoas[a];
				}
			}

			stringSaidaPopulation += "\n</plans>\n";
			stringSaidaFacilities += "\n</facilities>\n";

			Main.armazenarDados("saidas/population" + instancia + ".xml", stringSaidaPopulation);
			Main.armazenarDados("saidas/facilities" + instancia + ".xml", stringSaidaFacilities);
			Main.armazenarDados("saidas/paradasPercorridas" + instancia + ".xml", horariosSaida);
		}
		Main.armazenarDados("saidas/log.txt", Main.log);
	}

	public static void armazenarDados(String nomeArquivo, String dados) {
		// https://www.w3schools.com/java/java_files_create.asp
		try {
			File arquivoSaida = new File(nomeArquivo);

			FileWriter myWriter = new FileWriter(arquivoSaida);

			myWriter.write(dados);
			myWriter.close();

			if (arquivoSaida.createNewFile()) {
				System.out.println("Arquivo criado: " + arquivoSaida.getName());
			} else {
				System.out.println("Arquivo \"" + arquivoSaida.getName() + "\" ja existe.");
			}

			System.out.println("Arquivos escritos!");
		} catch (IOException e) {
			System.out.println("Falha catastrófica!");
			e.printStackTrace();
		}
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

	public static double[] mediaSinal(List<Pedestre> entradaPessoas) {
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

	public static ArrayList<Pedestre> averiguaSeSaiu(List<Pedestre> entradaPessoas) {
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

	public static ArrayList<Pedestre> averiguaSeEntrou(List<Pedestre> entradaPessoas) {
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

	public static List<Pedestre> simularDeslocamento(Calendar horario, Parada paradaAtual, Parada proximaParada,
			float velocidade, List<Pedestre> pessoasSubiramOnibus, List<Pedestre> pessoasDesceramOnibus,
			List<Pedestre> pessoasOnibus, List<Pedestre> pessoasOnibusSimulado) {
		// Calcula a distância em Km (quilômetros) entre as paradas
		double distanciaEntreParadas = Haversine.distance(paradaAtual.getCoordenadaY(), paradaAtual.getCoordenadaX(),
				proximaParada.getCoordenadaY(), proximaParada.getCoordenadaX());
		/*
		 * DecimalFormat formatter = new DecimalFormat("#0");
		 * 
		 * System.out.print("Distancia: ");
		 * System.out.print(formatter.format(distanciaEntreParadas * 1000));
		 * System.out.println(" metro(s)");
		 */

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
		List<Pedestre> pessoasSubiramDesceram = new ArrayList<Pedestre>();

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

		long horarioEmMilissegundos = horario.getTimeInMillis();
		double distanciaPedestreParada;

		// Deslocamento
		for (double i = distanciaEntreParadas; i > 0; i -= velocidade / 3600) {
			/*
			 * System.out.print("Faltam "); System.out.print(formatter.format(i * 1000));
			 * System.out.println(" metro(s) para a próxima parada.");
			 * System.out.print("ETA: "); System.out.print(formatter.format(i / (velocidade
			 * / 3600))); System.out.println(" segundo(s)\n");
			 */
			// Avança no tempo
			horarioEmMilissegundos += (i / (velocidade / 3600)) * 100;

			// Calcula sinal do bluetooth
			for (x = 0; x < numeroPessoasSubiramDesceram; x++) {
				distanciaPedestreParada = Haversine.distance(proximaParada.getCoordenadaY(),
						proximaParada.getCoordenadaX(), pessoasSubiramDesceram.get(x).getParadaAtual().getCoordenadaY(),
						pessoasSubiramDesceram.get(x).getParadaAtual().getCoordenadaX()) - i;

				// escanearBluetooth(pedestre, saiu, distanciaUltimaParada)
				escanearBluetooth(pessoasSubiramDesceram.get(x), saiu[x], distanciaPedestreParada);
			}
		}

		// Define os valores para saída
		List<Pedestre> saidaPessoasSubiramOnibus = averiguaSeEntrou(pessoasSubiramDesceram);
		List<Pedestre> saidaPessoasDesceramOnibus = averiguaSeSaiu(pessoasSubiramDesceram);
		List<Pedestre> saidaPessoasOnibus = new ArrayList<Pedestre>();

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
		Main.log += "-----------------------------------------\n";
		Main.log += "Embarcando e desembarcando!\n";

		// Avança no tempo
		horarioEmMilissegundos += 10000;
		horario.setTimeInMillis(horarioEmMilissegundos);

		Main.log += "\nPessoas que subiram: " + pessoasSubiramOnibus + '\n';
		Main.log += "Pessoas que desceram: " + pessoasDesceramOnibus + '\n';
		Main.log += "Pessoas no onibus: " + pessoasOnibus + '\n' + '\n';

		Main.log += "Acho que subiu no ônibus: " + saidaPessoasSubiramOnibus + '\n';
		Main.log += "Acho que desceu do ônibus: " + saidaPessoasDesceramOnibus + '\n';
		Main.log += "Acho que está no ônibus: " + saidaPessoasOnibus + '\n' + '\n';

		return saidaPessoasOnibus;
	}
}
