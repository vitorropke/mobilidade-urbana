package mobilidade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

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

	public static void main(String[] args) {
		Main.relogioSimulacao.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, horaInicialSimulacao,
				minutoInicialSimulacao, segundoInicialSimulacao);

		// Define as paradas
		List<Parada> paradas = Parada.gerarParadas("facilitiesEntradaResumido.xml");
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

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - IDA
		subParadas.addAll(41, paradas.subList(143, 151));

		Linha linha1SantaJuliaIda = new Linha(subParadas);

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

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - VOLTA
		subParadas.addAll(46, paradas.subList(143, 151));
		Linha linha1SantaJuliaVolta = new Linha(subParadas);

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(40, 70));
		subParadas.addAll(paradas.subList(77, 94));
		subParadas.addAll(paradas.subList(97, 105));
		subParadas.addAll(paradas.subList(12, 20));
		subParadas.add(paradas.get(0));
		Linha linha2Ida = new Linha(subParadas);

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 11));
		subParadas.addAll(paradas.subList(193, 196));
		subParadas.addAll(paradas.subList(109, 132));
		subParadas.addAll(paradas.subList(161, 190));
		subParadas.add(paradas.get(40));
		Linha linha2Volta = new Linha(subParadas);

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA AEC
		subParadas.clear();
		subParadas.addAll(paradas.subList(362, 366));
		subParadas.addAll(paradas.subList(281, 284));
		subParadas.addAll(paradas.subList(32, 39));
		subParadas.addAll(paradas.subList(15, 20));
		subParadas.add(paradas.get(0));
		Linha linha2UniversidadesAeC = new Linha(subParadas);

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA IFRN
		subParadas.removeAll(subParadas.subList(5, 7));
		subParadas.addAll(5, paradas.subList(306, 310));
		subParadas.addAll(9, paradas.subList(317, 320));
		subParadas.addAll(12, paradas.subList(29, 32));
		subParadas.removeAll(subParadas.subList(23, 27));
		Linha linha2UniversidadesIFRN = new Linha(subParadas);

		// LINHA 03 - SANTO ANTÔNIO, BARROCAS
		subParadas.clear();
		subParadas.addAll(paradas.subList(203, 208));
		subParadas.addAll(paradas.subList(248, 252));
		subParadas.addAll(paradas.subList(208, 210));
		subParadas.addAll(paradas.subList(222, 231));
		subParadas.addAll(paradas.subList(7, 19));
		subParadas.add(paradas.get(20));
		Linha linha3 = new Linha(subParadas);

		// LINHA 04 - ABOLIÇÃO V - IDA
		subParadas.clear();
		subParadas.add(paradas.get(142));
		subParadas.addAll(paradas.subList(151, 161));
		subParadas.addAll(paradas.subList(70, 97));
		subParadas.addAll(paradas.subList(190, 193));
		subParadas.addAll(paradas.subList(10, 20));
		subParadas.add(paradas.get(0));
		Linha linha4Ida = new Linha(subParadas);

		// LINHA 04 - ABOLIÇÃO V, SANTA JÚLIA
		subParadas.addAll(1, paradas.subList(143, 151));
		Linha linha4SantaJulia = new Linha(subParadas);

		// LINHA 04 - ABOLIÇÃO V - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 11));
		subParadas.addAll(paradas.subList(193, 196));
		subParadas.addAll(paradas.subList(105, 109));
		subParadas.addAll(paradas.subList(115, 143));
		Linha linha4Volta = new Linha(subParadas);

		// LINHA 05 - VINGT ROSADO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(284, 306));
		subParadas.addAll(paradas.subList(276, 284));
		subParadas.addAll(paradas.subList(32, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha5Ida = new Linha(subParadas);

		// LINHA 05 - VINGT ROSADO VIA UNIVERSIDADES - IDA
		subParadas.addAll(27, paradas.subList(420, 423));
		subParadas.addAll(30, paradas.subList(362, 366));
		subParadas.addAll(35, paradas.subList(306, 310));
		subParadas.addAll(39, paradas.subList(317, 320));
		subParadas.addAll(42, paradas.subList(29, 32));
		subParadas.removeAll(subParadas.subList(45, 47));
		Linha linha5IdaUniversidades = new Linha(subParadas);

		// LINHA 05 - VINGT ROSADO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 371));
		subParadas.addAll(paradas.subList(376, 397));
		subParadas.add(paradas.get(284));
		Linha linha5Volta = new Linha(subParadas);

		// LINHA 05 - VINGT ROSADO, UERN - VOLTA
		subParadas.addAll(9, paradas.subList(420, 423));
		subParadas.addAll(12, paradas.subList(362, 366));
		Linha linha5VoltaUERN = new Linha(subParadas);

		// LINHA 05 - VINGT ROSADO, IFRN - VOLTA
		subParadas.removeAll(subParadas.subList(9, 16));
		subParadas.removeAll(subParadas.subList(6, 8));
		subParadas.add(6, paradas.get(26));
		subParadas.addAll(7, paradas.subList(397, 409));
		subParadas.addAll(19, paradas.subList(416, 420));
		Linha linha5VoltaIFRN = new Linha(subParadas);

		// LINHA 05 - VINGT ROSADO VIA UNIVERSIDADES - VOLTA
		subParadas.addAll(24, paradas.subList(420, 423));
		subParadas.addAll(27, paradas.subList(362, 366));
		Linha linha5VoltaUniversidades = new Linha(subParadas);

		// LINHA 05 - VINGT ROSADO VIA ODETE ROSADO - VOLTA
		subParadas.removeAll(subParadas.subList(27, 31));
		subParadas.removeAll(subParadas.subList(0, 23));
		subParadas.addAll(0, paradas.subList(366, 368));
		subParadas.addAll(6, paradas.subList(423, 425));
		subParadas.addAll(8, paradas.subList(355, 366));
		Linha linha5VoltaOdeteRosado = new Linha(subParadas);

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

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF - IDA
		subParadas.removeAll(subParadas.subList(9, 16));
		Linha linha6IdaSemUlrickGraff = new Linha(subParadas);

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, NEM AVENIDA RIO BRANCO - IDA
		subParadas.removeAll(subParadas.subList(22, 31));
		subParadas.addAll(22, paradas.subList(15, 18));
		subParadas.remove(subParadas.get(26));
		subParadas.add(paradas.get(19));
		subParadas.add(paradas.get(0));
		Linha linha6IdaSemUlrickGraffNemRioBranco = new Linha(subParadas);

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, COM UNIRB E COELHO NETO - IDA
		subParadas.addAll(0, paradas.subList(274, 281));
		subParadas.addAll(7, paradas.subList(420, 423));
		subParadas.removeAll(subParadas.subList(32, 35));
		subParadas.add(32, paradas.get(39));
		subParadas.addAll(33, paradas.subList(320, 324));
		subParadas.addAll(37, paradas.subList(328, 338));
		Linha linha6IdaSemUlrickGraffComUNIRBComCoelhoNeto = new Linha(subParadas);

		// LINHA 06 - UNIVERSIDADES - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 27));
		subParadas.addAll(paradas.subList(397, 420));
		subParadas.add(paradas.get(368));
		subParadas.addAll(paradas.subList(420, 423));
		subParadas.add(paradas.get(362));
		Linha linha6Volta = new Linha(subParadas);

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF- VOLTA
		subParadas.removeAll(subParadas.subList(19, 26));
		Linha linha6VoltaSemUlrickGraff = new Linha(subParadas);

		// LINHA 06 - UNIVERSIDADES COM UNIRB - VOLTA
		subParadas.addAll(19, paradas.subList(409, 416));
		subParadas.addAll(paradas.subList(363, 366));
		subParadas.addAll(paradas.subList(369, 373));
		Linha linha6VoltaUNIRB = new Linha(subParadas);

		// LINHA 07 - NOVA VIDA - IDA
		subParadas.clear();
		subParadas.add(paradas.get(455));
		subParadas.addAll(paradas.subList(435, 443));
		subParadas.addAll(paradas.subList(27, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha7Ida = new Linha(subParadas);

		// LINHA 07 - NOVA VIDA SEM PAREDÕES, TERMINANDO NA PRAÇA FELIPE GUERRA - IDA
		subParadas.removeAll(subParadas.subList(21, 40));
		subParadas.add(paradas.get(15));
		subParadas.add(paradas.get(0));
		Linha linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra = new Linha(subParadas);

		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - IDA
		subParadas.removeAll(subParadas.subList(21, 23));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		subParadas.remove(subParadas.get(0));
		subParadas.addAll(0, paradas.subList(425, 435));
		Linha linha7IdaJardimDasPalmeiras = new Linha(subParadas);

		// LINHA 07 - NOVA VIDA - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 27));
		subParadas.addAll(paradas.subList(443, 456));
		Linha linha7Volta = new Linha(subParadas);

		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - VOLTA
		subParadas.addAll(paradas.subList(456, 462));
		subParadas.add(paradas.get(425));
		Linha linha7VoltaJardimDasPalmeiras = new Linha(subParadas);

		// LINHA 07 - NOVA VIDA VIA SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		subParadas.add(0, paradas.get(0));
		subParadas.add(1, paradas.get(327));
		subParadas.add(2, paradas.get(18));
		subParadas.addAll(8, paradas.subList(478, 499));
		subParadas.addAll(29, paradas.subList(462, 478));
		subParadas.removeAll(subParadas.subList(45, 51));
		subParadas.removeAll(subParadas.subList(54, subParadas.size()));
		Linha linha7VoltaSumareLiberdadePlanalto = new Linha(subParadas);

		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - IDA
		subParadas.removeAll(subParadas.subList(0, 29));
		subParadas.removeAll(subParadas.subList(16, subParadas.size()));
		subParadas.addAll(paradas.subList(27, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha8Ida = new Linha(subParadas);

		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 25));
		subParadas.addAll(paradas.subList(478, 499));
		subParadas.add(paradas.get(462));
		Linha linha8Volta = new Linha(subParadas);

		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(511, 522));
		subParadas.addAll(paradas.subList(499, 511));
		subParadas.add(paradas.get(1));
		subParadas.addAll(paradas.subList(14, 20));
		subParadas.add(paradas.get(0));
		Linha linha9Ida = new Linha(subParadas);

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

		// LINHA 10 - SHOPPING, UNP - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(622, 632));
		subParadas.addAll(paradas.subList(13, 20));
		subParadas.add(paradas.get(0));
		Linha linha10Ida = new Linha(subParadas);

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

		// LINHA 10 - SHOPPING, UNP - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 6));
		subParadas.addAll(paradas.subList(603, 606));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(632, 642));
		subParadas.add(paradas.get(622));
		Linha linha10Volta = new Linha(subParadas);

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

		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 376));
		subParadas.add(paradas.get(338));
		Linha linha11Volta = new Linha(subParadas);

		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(196, 206));
		subParadas.addAll(paradas.subList(210, 231));
		subParadas.addAll(paradas.subList(7, 20));
		subParadas.add(paradas.get(0));
		Linha linha12Ida = new Linha(subParadas);

		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 7));
		subParadas.addAll(paradas.subList(231, 271));
		subParadas.add(paradas.get(196));
		Linha linha12Volta = new Linha(subParadas);

		// LINHA 14 - AEROPORTO, RODOVIÁRIA - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(642, 668));
		subParadas.addAll(paradas.subList(606, 610));
		subParadas.addAll(paradas.subList(668, 671));
		subParadas.addAll(paradas.subList(13, 20));
		subParadas.add(paradas.get(0));
		Linha linha14Ida = new Linha(subParadas);

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

		// LINHA 17 - ODETE ROSADO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(355, 366));
		subParadas.addAll(paradas.subList(281, 284));
		subParadas.addAll(paradas.subList(32, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha17Ida = new Linha(subParadas);

		// LINHA 17 - ODETE ROSADO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 369));
		subParadas.addAll(paradas.subList(420, 425));
		subParadas.add(paradas.get(355));
		Linha linha17Volta = new Linha(subParadas);

		// LINHA 19 - CIDADE OESTE - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(536, 548));
		subParadas.addAll(paradas.subList(499, 511));
		subParadas.add(paradas.get(1));
		subParadas.addAll(paradas.subList(14, 20));
		subParadas.add(paradas.get(0));
		Linha linha19Ida = new Linha(subParadas);

		// LINHA 19 - CIDADE OESTE - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 14));
		subParadas.addAll(paradas.subList(548, 558));
		subParadas.addAll(paradas.subList(582, 593));
		subParadas.add(paradas.get(536));
		Linha linha19Volta = new Linha(subParadas);

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
		List<Calendar> horarios = new ArrayList<Calendar>();

		horarios.add(new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 18, 0, 0));
		horarios.add(new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 19, 0, 0));
		horarios.add(new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 20, 0, 0));
		horarios.add(new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 21, 0, 0));
		horarios.add(new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 22, 0, 0));
		horarios.add(new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 23, 0, 0));

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - IDA
		onibus.add(new Onibus(50, 50, linha1Ida, horarios));
		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - IDA
		onibus.add(new Onibus(50, 50, linha1SantaJuliaIda, horarios));
		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - VOLTA
		onibus.add(new Onibus(50, 50, linha1Volta, horarios));
		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - VOLTA
		onibus.add(new Onibus(50, 50, linha1SantaJuliaVolta, horarios));
		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - IDA
		onibus.add(new Onibus(50, 50, linha2Ida, horarios));
		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - VOLTA
		onibus.add(new Onibus(50, 50, linha2Volta, horarios));
		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA AEC
		onibus.add(new Onibus(50, 50, linha2UniversidadesAeC, horarios));
		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA IFRN
		onibus.add(new Onibus(50, 50, linha2UniversidadesIFRN, horarios));
		// LINHA 03 - SANTO ANTÔNIO, BARROCAS
		onibus.add(new Onibus(50, 50, linha3, horarios));
		// LINHA 04 - ABOLIÇÃO V - IDA
		onibus.add(new Onibus(50, 50, linha4Ida, horarios));
		// LINHA 04 - ABOLIÇÃO V, SANTA JÚLIA
		onibus.add(new Onibus(50, 50, linha4SantaJulia, horarios));
		// LINHA 04 - ABOLIÇÃO V - VOLTA
		onibus.add(new Onibus(50, 50, linha4Volta, horarios));
		// LINHA 05 - VINGT ROSADO - IDA
		onibus.add(new Onibus(50, 50, linha5Ida, horarios));
		// LINHA 05 - VINGT ROSADO VIA UNIVERSIADES - IDA
		onibus.add(new Onibus(50, 50, linha5IdaUniversidades, horarios));
		// LINHA 05 - VINGT ROSADO - VOLTA
		onibus.add(new Onibus(50, 50, linha5Volta, horarios));
		// LINHA 05 - VINGT ROSADO, UERN - VOLTA
		onibus.add(new Onibus(50, 50, linha5VoltaUERN, horarios));
		// LINHA 05 - VINGT ROSADO, IFRN - VOLTA
		onibus.add(new Onibus(50, 50, linha5VoltaIFRN, horarios));
		// LINHA 05 - VINGT ROSADO VIA UNIVERSIDADES - VOLTA
		onibus.add(new Onibus(50, 50, linha5VoltaUniversidades, horarios));
		// LINHA 05 - VINGT ROSADO VIA ODETE ROSADO - VOLTA
		onibus.add(new Onibus(50, 50, linha5VoltaOdeteRosado, horarios));
		// LINHA 06 - UNIVERSIDADES - IDA
		onibus.add(new Onibus(50, 50, linha6Ida, horarios));
		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF - IDA
		onibus.add(new Onibus(50, 50, linha6IdaSemUlrickGraff, horarios));
		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, NEM AVENIDA RIO BRANCO - IDA
		onibus.add(new Onibus(50, 50, linha6IdaSemUlrickGraffNemRioBranco, horarios));
		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, COM UNIRB E COELHO NETO - IDA
		onibus.add(new Onibus(50, 50, linha6IdaSemUlrickGraffComUNIRBComCoelhoNeto, horarios));
		// LINHA 06 - UNIVERSIDADES - VOLTA
		onibus.add(new Onibus(50, 50, linha6Volta, horarios));
		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF- VOLTA
		onibus.add(new Onibus(50, 50, linha6VoltaSemUlrickGraff, horarios));
		// LINHA 06 - UNIVERSIDADES COM UNIRB - VOLTA
		onibus.add(new Onibus(50, 50, linha6VoltaUNIRB, horarios));
		// LINHA 07 - NOVA VIDA - IDA
		onibus.add(new Onibus(50, 50, linha7Ida, horarios));
		// LINHA 07 - NOVA VIDA SEM PAREDÕES, TERMINANDO NA PRAÇA FELIPE GUERRA - IDA
		onibus.add(new Onibus(50, 50, linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra, horarios));
		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - IDA
		onibus.add(new Onibus(50, 50, linha7IdaJardimDasPalmeiras, horarios));
		// LINHA 07 - NOVA VIDA - VOLTA
		onibus.add(new Onibus(50, 50, linha7Volta, horarios));
		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - VOLTA
		onibus.add(new Onibus(50, 50, linha7VoltaJardimDasPalmeiras, horarios));
		// LINHA 07 - NOVA VIDA VIA SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		onibus.add(new Onibus(50, 50, linha7VoltaSumareLiberdadePlanalto, horarios));
		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - IDA
		onibus.add(new Onibus(50, 50, linha8Ida, horarios));
		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		onibus.add(new Onibus(50, 50, linha8Volta, horarios));
		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO - IDA
		onibus.add(new Onibus(50, 50, linha9Ida, horarios));
		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO COM LAGOA DO MATO, BOA
		// VISTA, DOZE ANOS E SANTO ANTÔNIO - IDA
		onibus.add(new Onibus(50, 50, linha9IdaLagoaDoMatoBoaVistaDozeAnosSantoAntonio, horarios));
		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO - VOLTA
		onibus.add(new Onibus(50, 50, linha9Volta, horarios));
		// LINHA 10 - SHOPPING, UNP - IDA
		onibus.add(new Onibus(50, 50, linha10Ida, horarios));
		// LINHA 10 - SHOPPING, UNP, AEROPORTO, MACARRÃO, BOA VISTA - IDA
		onibus.add(new Onibus(50, 50, linha10IdaAeroportoMacarraoBoaVista, horarios));
		// LINHA 10 - SHOPPING, UNP - VOLTA
		onibus.add(new Onibus(50, 50, linha10Volta, horarios));
		// LINHA 10 - SHOPPING, UNP, AEROPORTO, MACARRÃO, BOA VISTA - VOLTA
		onibus.add(new Onibus(50, 50, linha10VoltaAeroportoMacarraoBoaVista, horarios));
		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - IDA
		onibus.add(new Onibus(50, 50, linha11Ida, horarios));
		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - VOLTA
		onibus.add(new Onibus(50, 50, linha11Volta, horarios));
		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - IDA
		onibus.add(new Onibus(50, 50, linha12Ida, horarios));
		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - VOLTA
		onibus.add(new Onibus(50, 50, linha12Volta, horarios));
		// LINHA 14 - AEROPORTO, RODOVIÁRIA - IDA
		onibus.add(new Onibus(50, 50, linha14Ida, horarios));
		// LINHA 14 - AEROPORTO, RODOVIÁRIA - VOLTA
		onibus.add(new Onibus(50, 50, linha14Volta, horarios));
		// LINHA 15 - MACARRÃO, BOA VISTA - IDA
		onibus.add(new Onibus(50, 50, linha15Ida, horarios));
		// LINHA 15 - MACARRÃO, BOA VISTA - VOLTA
		onibus.add(new Onibus(50, 50, linha15Volta, horarios));
		// LINHA 17 - ODETE ROSADO - IDA
		onibus.add(new Onibus(50, 50, linha17Ida, horarios));
		// LINHA 17 - ODETE ROSADO - VOLTA
		onibus.add(new Onibus(50, 50, linha17Volta, horarios));
		// LINHA 19 - CIDADE OESTE - IDA
		onibus.add(new Onibus(50, 50, linha19Ida, horarios));
		// LINHA 19 - CIDADE OESTE - VOLTA
		onibus.add(new Onibus(50, 50, linha19Volta, horarios));

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
		// Random pessoasAleatorias;
		String stringSaidaPopulation;
		String stringSaidaFacilities;

		// Instâncias da simulação
		for (int instancia = 0; instancia < 5; instancia++) {
			Main.seed = Main.sementeAleatoria.nextInt();

			// pessoasAleatorias = new Random(seed);
			// Define número de pessoas na simulação
			numeroPessoasAleatorio = numeroPessoas;
			// numeroPessoasAleatorio = pessoasAleatorias.nextInt(numeroPessoas);

			paradasAleatorias = new Random(seed);

			// numeroPedestresAleatorio = paradasAleatorias.nextInt(numeroPessoas - 2) + 2;
			System.out.println("Número de pessoas nessa simulação é " + numeroPessoasAleatorio + '\n');

			// Limpa as pessoas das paradas
			for (Parada paradaAtual : paradas) {
				paradaAtual.removeAllPedestres();
			}

			// Adiciona parada nos pedestres e os pedestres na parada de forma aleatória
			// e também o destino do pedestre
			for (int i = 0; i < numeroPessoasAleatorio; i++) {
				// define a origem
				numeroParadaAleatoria = paradasAleatorias.nextInt(numeroParadas);

				pessoas.get(i).setOrigem(paradas.get(numeroParadaAleatoria));
				pessoas.get(i).setParadaAtual(paradas.get(numeroParadaAleatoria));

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
					int numeroHorarios = onibusAtual.getHorarios().size();
					int indice = 0;

					// Enquanto não chegar no fim do vetor de horários e o horário do ônibus em
					// função do índice for diferente do horário da simulação
					while ((indice < numeroHorarios)
							&& (onibusAtual.getHorarios().get(indice).getTime().before(relogioSimulacao.getTime()))) {
						indice++;
					}

					// Se algum horário do ônibus for igual ao horário da simulação
					if (indice != numeroHorarios) {
						// Se o horário escolhido já não foi usado
						if (!onibusAtual.getHorarioCumprido().get(indice)) {
							onibusAtual.setHorarioCumprido(indice, true);

							momentoAtual.set(onibusAtual.getHorarios().get(indice).get(Calendar.YEAR),
									onibusAtual.getHorarios().get(indice).get(Calendar.MONTH),
									onibusAtual.getHorarios().get(indice).get(Calendar.DAY_OF_MONTH),
									onibusAtual.getHorarios().get(indice).get(Calendar.HOUR_OF_DAY),
									onibusAtual.getHorarios().get(indice).get(Calendar.MINUTE),
									onibusAtual.getHorarios().get(indice).get(Calendar.SECOND));

							horariosSaida += momentoAtual.get(Calendar.HOUR_OF_DAY) + ":"
									+ momentoAtual.get(Calendar.MINUTE) + ":" + momentoAtual.get(Calendar.SECOND)
									+ "\n";

							for (Parada paradaAtual : onibusAtual.getLinha().getParadas()) {
								indiceParada = onibusAtual.getLinha().getParadas().indexOf(paradaAtual);

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
								}

								// Subida de pessoas
								for (int i = 0; i < numeroPessoasParada; i++) {
									// Se o ônibus não está cheio E
									// Se o pedestre não está no destino
									if ((numeroPessoasOnibus <= onibusAtual.getCapacidadeMaximaPassageiros())
											&& (!pessoasParada.get(i).isNoDestino())) {
										// se ele estiver em viagem e o ônibus passar na parada de destino, ele sobe
										// se ele não estiver em viagem ele sobe
										if (pessoasParada.get(i).isEmViagem()) {
											// procura se a linha do ônibus passa pelo destino
											Linha linhaOnibusAtual = onibusAtual.getLinha();
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
															+ "\t\t\t<act end_time=\"" + horaAtual + ":" + minutoAtual
															+ ":" + segundoAtual + "\" x=\"" + coordenadaX + "\" y=\""
															+ coordenadaY + "\" type=\"home\"/>\n"
															+ "\t\t\t<leg mode=\"pt\"/>\n";
												} else {
													indiceString = stringPessoas[indicePessoa].length() - 2;

													while (stringPessoas[indicePessoa].charAt(indiceString) != '\n') {
														indiceString--;
													}

													stringPessoas[indicePessoa] = stringPessoas[indicePessoa]
															.substring(0, indiceString + 1);

													stringPessoas[indicePessoa] += "\t\t\t<act end_time=\"" + horaAtual
															+ ":" + minutoAtual + ":" + segundoAtual + "\" x=\""
															+ coordenadaX + "\" y=\"" + coordenadaY
															+ "\" type=\"home\"/>\n" + "\t\t\t<leg mode=\"pt\"/>\n";
												}

												stringSaidaFacilities += "\n\t<facility id=\""
														+ pessoasParada.get(i).getNome() + "Subiu" + horaAtual + ":"
														+ minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX
														+ "\" y=\"" + coordenadaY + "\"/>\n";
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

								System.out.println(
										"=====================================================================");
								System.out.println("Parada " + indiceParada + ": " + paradaAtual.getNomeParada() + " : "
										+ "Ônibus " + onibusAtual.getId());
								System.out.println("Quem subiu: " + pessoasSubiramOnibus);
								System.out.println("Quem desceu: " + pessoasDesceramOnibus);
								System.out.println("Quem está no ônibus: " + pessoasOnibus);
								System.out.println();

								// Simula deslocamento
								if ((indiceParada + 1) != onibusAtual.getLinha().getParadas().size()) {
									pessoasOnibusSimulado = Main.simularDeslocamento(momentoAtual, paradaAtual,
											onibusAtual.getLinha().getParadas().get(indiceParada + 1),
											onibusAtual.getVelocidade(), pessoasSubiramOnibus, pessoasDesceramOnibus,
											pessoasOnibus, pessoasOnibusSimulado);
								} else {
									pessoasOnibusSimulado = Main.simularDeslocamento(momentoAtual,
											onibusAtual.getLinha().getParadas().get(indiceParada - 1), paradaAtual,
											onibusAtual.getVelocidade(), pessoasSubiramOnibus, pessoasDesceramOnibus,
											pessoasOnibus, pessoasOnibusSimulado);
								}

								// Esvazia vetores de descida e subida
								pessoasDesceramOnibus.clear();
								pessoasSubiramOnibus.clear();
								pessoasTrocaramParada.clear();
							}
							pessoasOnibusSimulado.clear();

							Parada paradaAtual = onibusAtual.getLinha().getParadas().get(indiceParada);

							horaAtual = momentoAtual.get(Calendar.HOUR_OF_DAY);
							minutoAtual = momentoAtual.get(Calendar.MINUTE);
							segundoAtual = momentoAtual.get(Calendar.SECOND);

							coordenadasOrigem.setValue(paradaAtual.getCoordenadaX(), paradaAtual.getCoordenadaY());
							transform.transform(coordenadasOrigem, coordenadasDestino);

							coordenadaX = coordenadasDestino.x;
							coordenadaY = coordenadasDestino.y;

							System.out.println("Terminal: " + paradaAtual.getNomeParada());
							System.out.println("Quem desceu: " + onibusAtual.getPedestres());
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
				onibusAtual.resetHorarioCumprido();
			}
			// reseta o relógio da simulação
			Main.relogioSimulacao.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, horaInicialSimulacao,
					minutoInicialSimulacao, segundoInicialSimulacao);

			// Adiciona as pessoas que fizeram parte da simulação na string de saída
			for (int a = 0; a < numeroPessoas; a++) {
				if (stringPessoas[a] != null) {
					if (stringPessoas[a].charAt(stringPessoas[a].length() - 16) == 'l') {
						coordenadasOrigem.setValue(pessoas.get(a).getParadaAtual().getCoordenadaX(),
								pessoas.get(a).getParadaAtual().getCoordenadaY());
						transform.transform(coordenadasOrigem, coordenadasDestino);

						coordenadaX = coordenadasDestino.x;
						coordenadaY = coordenadasDestino.y;

						stringPessoas[a] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\"" + coordenadaY
								+ "\" type=\"home\"/>\n";
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
			System.out.print("Faltam ");
			System.out.print(formatter.format(i * 1000));
			System.out.println(" metro(s) para a próxima parada.");
			System.out.print("ETA: ");
			System.out.print(formatter.format(i / (velocidade / 3600)));
			System.out.println(" segundo(s)\n");

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
		System.out.println("-----------------------------------------");
		System.out.println("Embarcando e desembarcando!");

		// Avança no tempo
		horarioEmMilissegundos += 10000;
		horario.setTimeInMillis(horarioEmMilissegundos);

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