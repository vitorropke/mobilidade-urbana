package mobilidade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;

public class Main {
	static int seed = 1;
	// static Calendar momentoAtual = Calendar.getInstance();

	public static void main(String[] args) {
		Calendar horaPartida = Calendar.getInstance();
		// -----------------------------------------------------------------------------------------------------------------------
		// Define as paradas
		List<Parada> paradas = Parada.gerarParadas("facilitiesEntradaResumido.xml");
		List<Parada> subParadas = new ArrayList<Parada>();
		List<Calendar> horarios = new ArrayList<Calendar>();

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

		horaPartida.set(Calendar.SECOND, 0);
		horaPartida.set(Calendar.MINUTE, 0);
		horaPartida.set(Calendar.HOUR_OF_DAY, 18);
		horarios.add(horaPartida);
		Linha linha1Ida = new Linha(subParadas, horarios);

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - IDA
		subParadas.addAll(41, paradas.subList(143, 151));

		horarios.get(0).set(seed, Calendar.HOUR_OF_DAY, 18);
		Linha linha1SantaJuliaIda = new Linha(subParadas, horarios);

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
		Linha linha1Volta = new Linha(subParadas, horarios);

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - VOLTA
		subParadas.addAll(46, paradas.subList(143, 151));
		Linha linha1SantaJuliaVolta = new Linha(subParadas, horarios);

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(40, 70));
		subParadas.addAll(paradas.subList(77, 94));
		subParadas.addAll(paradas.subList(97, 105));
		subParadas.addAll(paradas.subList(12, 20));
		subParadas.add(paradas.get(0));
		Linha linha2Ida = new Linha(subParadas, horarios);

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 11));
		subParadas.addAll(paradas.subList(193, 196));
		subParadas.addAll(paradas.subList(109, 132));
		subParadas.addAll(paradas.subList(161, 190));
		subParadas.add(paradas.get(40));
		Linha linha2Volta = new Linha(subParadas, horarios);

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA AEC
		subParadas.clear();
		subParadas.addAll(paradas.subList(362, 366));
		subParadas.addAll(paradas.subList(281, 284));
		subParadas.addAll(paradas.subList(32, 39));
		subParadas.addAll(paradas.subList(15, 20));
		subParadas.add(paradas.get(0));
		Linha linha2UniversidadesAeC = new Linha(subParadas, horarios);

		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA IFRN
		subParadas.removeAll(subParadas.subList(5, 7));
		subParadas.addAll(5, paradas.subList(306, 310));
		subParadas.addAll(9, paradas.subList(317, 320));
		subParadas.addAll(12, paradas.subList(29, 32));
		subParadas.removeAll(subParadas.subList(23, 27));
		Linha linha2UniversidadesIFRN = new Linha(subParadas, horarios);

		// LINHA 03 - SANTO ANTÔNIO, BARROCAS
		subParadas.clear();
		subParadas.addAll(paradas.subList(203, 208));
		subParadas.addAll(paradas.subList(248, 252));
		subParadas.addAll(paradas.subList(208, 210));
		subParadas.addAll(paradas.subList(222, 231));
		subParadas.addAll(paradas.subList(7, 19));
		subParadas.add(paradas.get(20));
		Linha linha3 = new Linha(subParadas, horarios);

		// LINHA 04 - ABOLIÇÃO V - IDA
		subParadas.clear();
		subParadas.add(paradas.get(142));
		subParadas.addAll(paradas.subList(151, 161));
		subParadas.addAll(paradas.subList(70, 97));
		subParadas.addAll(paradas.subList(190, 193));
		subParadas.addAll(paradas.subList(10, 20));
		subParadas.add(paradas.get(0));
		Linha linha4Ida = new Linha(subParadas, horarios);

		// LINHA 04 - ABOLIÇÃO V, SANTA JÚLIA
		subParadas.addAll(1, paradas.subList(143, 151));
		Linha linha4SantaJulia = new Linha(subParadas, horarios);

		// LINHA 04 - ABOLIÇÃO V - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 11));
		subParadas.addAll(paradas.subList(193, 196));
		subParadas.addAll(paradas.subList(105, 109));
		subParadas.addAll(paradas.subList(115, 143));
		Linha linha4Volta = new Linha(subParadas, horarios);

		// LINHA 05 - VINGT ROSADO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(284, 306));
		subParadas.addAll(paradas.subList(276, 284));
		subParadas.addAll(paradas.subList(32, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha5Ida = new Linha(subParadas, horarios);

		// LINHA 05 - VINGT ROSADO VIA UNIVERSIDADES - IDA
		subParadas.addAll(27, paradas.subList(420, 423));
		subParadas.addAll(30, paradas.subList(362, 366));
		subParadas.addAll(35, paradas.subList(306, 310));
		subParadas.addAll(39, paradas.subList(317, 320));
		subParadas.addAll(42, paradas.subList(29, 32));
		subParadas.removeAll(subParadas.subList(45, 47));
		Linha linha5IdaUniversidades = new Linha(subParadas, horarios);

		// LINHA 05 - VINGT ROSADO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 371));
		subParadas.addAll(paradas.subList(376, 397));
		subParadas.add(paradas.get(284));
		Linha linha5Volta = new Linha(subParadas, horarios);

		// LINHA 05 - VINGT ROSADO, UERN - VOLTA
		subParadas.addAll(9, paradas.subList(420, 423));
		subParadas.addAll(12, paradas.subList(362, 366));
		Linha linha5VoltaUERN = new Linha(subParadas, horarios);

		// LINHA 05 - VINGT ROSADO, IFRN - VOLTA
		subParadas.removeAll(subParadas.subList(9, 16));
		subParadas.removeAll(subParadas.subList(6, 8));
		subParadas.add(6, paradas.get(26));
		subParadas.addAll(7, paradas.subList(397, 409));
		subParadas.addAll(19, paradas.subList(416, 420));
		Linha linha5VoltaIFRN = new Linha(subParadas, horarios);

		// LINHA 05 - VINGT ROSADO VIA UNIVERSIDADES - VOLTA
		subParadas.addAll(24, paradas.subList(420, 423));
		subParadas.addAll(27, paradas.subList(362, 366));
		Linha linha5VoltaUniversidades = new Linha(subParadas, horarios);

		// LINHA 05 - VINGT ROSADO VIA ODETE ROSADO - VOLTA
		subParadas.removeAll(subParadas.subList(27, 31));
		subParadas.removeAll(subParadas.subList(0, 23));
		subParadas.addAll(0, paradas.subList(366, 368));
		subParadas.addAll(6, paradas.subList(423, 425));
		subParadas.addAll(8, paradas.subList(355, 366));
		Linha linha5VoltaOdeteRosado = new Linha(subParadas, horarios);

		// LINHA 06 - UNIVERSIDADES - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(362, 366));
		subParadas.add(paradas.get(281));
		subParadas.addAll(paradas.subList(306, 320));
		subParadas.addAll(paradas.subList(29, 40));
		subParadas.addAll(paradas.subList(320, 328));
		subParadas.add(paradas.get(18));
		subParadas.add(paradas.get(20));
		Linha linha6Ida = new Linha(subParadas, horarios);

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF - IDA
		subParadas.removeAll(subParadas.subList(9, 16));
		Linha linha6IdaSemUlrickGraff = new Linha(subParadas, horarios);

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, NEM AVENIDA RIO BRANCO - IDA
		subParadas.removeAll(subParadas.subList(22, 31));
		subParadas.addAll(22, paradas.subList(15, 18));
		subParadas.remove(subParadas.get(26));
		subParadas.add(paradas.get(19));
		subParadas.add(paradas.get(0));
		Linha linha6IdaSemUlrickGraffNemRioBranco = new Linha(subParadas, horarios);

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, COM UNIRB E COELHO NETO - IDA
		subParadas.addAll(0, paradas.subList(274, 281));
		subParadas.addAll(7, paradas.subList(420, 423));
		subParadas.removeAll(subParadas.subList(32, 35));
		subParadas.add(32, paradas.get(39));
		subParadas.addAll(33, paradas.subList(320, 324));
		subParadas.addAll(37, paradas.subList(328, 338));
		Linha linha6IdaSemUlrickGraffComUNIRBComCoelhoNeto = new Linha(subParadas, horarios);

		// LINHA 06 - UNIVERSIDADES - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 27));
		subParadas.addAll(paradas.subList(397, 420));
		subParadas.add(paradas.get(368));
		subParadas.addAll(paradas.subList(420, 423));
		subParadas.add(paradas.get(362));
		Linha linha6Volta = new Linha(subParadas, horarios);

		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF- VOLTA
		subParadas.removeAll(subParadas.subList(19, 26));
		Linha linha6VoltaSemUlrickGraff = new Linha(subParadas, horarios);

		// LINHA 06 - UNIVERSIDADES COM UNIRB - VOLTA
		subParadas.addAll(19, paradas.subList(409, 416));
		subParadas.addAll(paradas.subList(363, 366));
		subParadas.addAll(paradas.subList(369, 373));
		Linha linha6VoltaUNIRB = new Linha(subParadas, horarios);

		// LINHA 07 - NOVA VIDA - IDA
		subParadas.clear();
		subParadas.add(paradas.get(455));
		subParadas.addAll(paradas.subList(435, 443));
		subParadas.addAll(paradas.subList(27, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha7Ida = new Linha(subParadas, horarios);

		// LINHA 07 - NOVA VIDA SEM PAREDÕES, TERMINANDO NA PRAÇA FELIPE GUERRA - IDA
		subParadas.removeAll(subParadas.subList(21, 40));
		subParadas.add(paradas.get(15));
		subParadas.add(paradas.get(0));
		Linha linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra = new Linha(subParadas, horarios);

		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - IDA
		subParadas.removeAll(subParadas.subList(21, 23));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		subParadas.remove(subParadas.get(0));
		subParadas.addAll(0, paradas.subList(425, 435));
		Linha linha7IdaJardimDasPalmeiras = new Linha(subParadas, horarios);

		// LINHA 07 - NOVA VIDA - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 27));
		subParadas.addAll(paradas.subList(443, 456));
		Linha linha7Volta = new Linha(subParadas, horarios);

		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - VOLTA
		subParadas.addAll(paradas.subList(456, 462));
		subParadas.add(paradas.get(425));
		Linha linha7VoltaJardimDasPalmeiras = new Linha(subParadas, horarios);

		// LINHA 07 - NOVA VIDA VIA SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		subParadas.add(0, paradas.get(0));
		subParadas.add(1, paradas.get(327));
		subParadas.add(2, paradas.get(18));
		subParadas.addAll(8, paradas.subList(478, 499));
		subParadas.addAll(29, paradas.subList(462, 478));
		subParadas.removeAll(subParadas.subList(45, 51));
		subParadas.removeAll(subParadas.subList(54, subParadas.size()));
		Linha linha7VoltaSumareLiberdadePlanalto = new Linha(subParadas, horarios);

		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - IDA
		subParadas.removeAll(subParadas.subList(0, 29));
		subParadas.removeAll(subParadas.subList(16, subParadas.size()));
		subParadas.addAll(paradas.subList(27, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha8Ida = new Linha(subParadas, horarios);

		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 25));
		subParadas.addAll(paradas.subList(478, 499));
		subParadas.add(paradas.get(462));
		Linha linha8Volta = new Linha(subParadas, horarios);

		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(511, 522));
		subParadas.addAll(paradas.subList(499, 511));
		subParadas.add(paradas.get(1));
		subParadas.addAll(paradas.subList(14, 20));
		subParadas.add(paradas.get(0));
		Linha linha9Ida = new Linha(subParadas, horarios);

		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO COM LAGOA DO MATO, BOA
		// VISTA, DOZE ANOS E SANTO ANTÔNIO - IDA
		subParadas.removeAll(subParadas.subList(15, 24));
		subParadas.add(15, paradas.get(555));
		subParadas.addAll(16, paradas.subList(522, 533));
		subParadas.addAll(27, paradas.subList(593, 598));
		subParadas.addAll(32, paradas.subList(533, 536));
		subParadas.addAll(35, paradas.subList(190, 193));
		subParadas.addAll(38, paradas.subList(10, 14));
		Linha linha9IdaLagoaDoMatoBoaVistaDozeAnosSantoAntonio = new Linha(subParadas, horarios);

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
		Linha linha9Volta = new Linha(subParadas, horarios);

		// LINHA 10 - SHOPPING, UNP - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(622, 632));
		subParadas.addAll(paradas.subList(13, 20));
		subParadas.add(paradas.get(0));
		Linha linha10Ida = new Linha(subParadas, horarios);

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
		Linha linha10IdaAeroportoMacarraoBoaVista = new Linha(subParadas, horarios);

		// LINHA 10 - SHOPPING, UNP - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 6));
		subParadas.addAll(paradas.subList(603, 606));
		subParadas.add(paradas.get(39));
		subParadas.addAll(paradas.subList(632, 642));
		subParadas.add(paradas.get(622));
		Linha linha10Volta = new Linha(subParadas, horarios);

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
		Linha linha10VoltaAeroportoMacarraoBoaVista = new Linha(subParadas, horarios);

		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(338, 345));
		subParadas.addAll(paradas.subList(271, 273));
		subParadas.addAll(paradas.subList(345, 355));
		subParadas.addAll(paradas.subList(273, 284));
		subParadas.addAll(paradas.subList(32, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha11Ida = new Linha(subParadas, horarios);

		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 376));
		subParadas.add(paradas.get(338));
		Linha linha11Volta = new Linha(subParadas, horarios);

		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(196, 206));
		subParadas.addAll(paradas.subList(210, 231));
		subParadas.addAll(paradas.subList(7, 20));
		subParadas.add(paradas.get(0));
		Linha linha12Ida = new Linha(subParadas, horarios);

		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 7));
		subParadas.addAll(paradas.subList(231, 271));
		subParadas.add(paradas.get(196));
		Linha linha12Volta = new Linha(subParadas, horarios);

		// LINHA 14 - AEROPORTO, RODOVIÁRIA - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(642, 668));
		subParadas.addAll(paradas.subList(606, 610));
		subParadas.addAll(paradas.subList(668, 671));
		subParadas.addAll(paradas.subList(13, 20));
		subParadas.add(paradas.get(0));
		Linha linha14Ida = new Linha(subParadas, horarios);

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
		Linha linha14Volta = new Linha(subParadas, horarios);

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
		Linha linha15Ida = new Linha(subParadas, horarios);

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
		Linha linha15Volta = new Linha(subParadas, horarios);

		// LINHA 17 - ODETE ROSADO - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(355, 366));
		subParadas.addAll(paradas.subList(281, 284));
		subParadas.addAll(paradas.subList(32, 40));
		subParadas.addAll(paradas.subList(2, 19));
		subParadas.add(paradas.get(20));
		Linha linha17Ida = new Linha(subParadas, horarios);

		// LINHA 17 - ODETE ROSADO - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(20, 26));
		subParadas.addAll(paradas.subList(366, 369));
		subParadas.addAll(paradas.subList(420, 425));
		subParadas.add(paradas.get(355));
		Linha linha17Volta = new Linha(subParadas, horarios);

		// LINHA 19 - CIDADE OESTE - IDA
		subParadas.clear();
		subParadas.addAll(paradas.subList(536, 548));
		subParadas.addAll(paradas.subList(499, 511));
		subParadas.add(paradas.get(1));
		subParadas.addAll(paradas.subList(14, 20));
		subParadas.add(paradas.get(0));
		Linha linha19Ida = new Linha(subParadas, horarios);

		// LINHA 19 - CIDADE OESTE - VOLTA
		subParadas.clear();
		subParadas.addAll(paradas.subList(0, 14));
		subParadas.addAll(paradas.subList(548, 558));
		subParadas.addAll(paradas.subList(582, 593));
		subParadas.add(paradas.get(536));
		Linha linha19Volta = new Linha(subParadas, horarios);

		// -----------------------------------------------------------------------------------------------------------------------
		// Define os ônibus
		// numeroVeiculo, capacidadePessoas, velocidade, linhaOnibus
		List<Onibus> onibus = new ArrayList<Onibus>();

		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - IDA
		onibus.add(new Onibus(50, 50, linha1Ida));
		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - IDA
		onibus.add(new Onibus(50, 50, linha1SantaJuliaIda));
		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - VOLTA
		onibus.add(new Onibus(50, 50, linha1Volta));
		// LINHA 01 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, SANTA JÚLIA - VOLTA
		onibus.add(new Onibus(50, 50, linha1SantaJuliaVolta));
		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - IDA
		onibus.add(new Onibus(50, 50, linha2Ida));
		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO - VOLTA
		onibus.add(new Onibus(50, 50, linha2Volta));
		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA AEC
		onibus.add(new Onibus(50, 50, linha2UniversidadesAeC));
		// LINHA 02 - ABOLIÇÕES, SANTA DELMIRA, REDENÇÃO, UNIVERSIDADES VIA IFRN
		onibus.add(new Onibus(50, 50, linha2UniversidadesIFRN));
		// LINHA 03 - SANTO ANTÔNIO, BARROCAS
		onibus.add(new Onibus(50, 50, linha3));
		// LINHA 04 - ABOLIÇÃO V - IDA
		onibus.add(new Onibus(50, 50, linha4Ida));
		// LINHA 04 - ABOLIÇÃO V, SANTA JÚLIA
		onibus.add(new Onibus(50, 50, linha4SantaJulia));
		// LINHA 04 - ABOLIÇÃO V - VOLTA
		onibus.add(new Onibus(50, 50, linha4Volta));
		// LINHA 05 - VINGT ROSADO - IDA
		onibus.add(new Onibus(50, 50, linha5Ida));
		// LINHA 05 - VINGT ROSADO VIA UNIVERSIADES - IDA
		onibus.add(new Onibus(50, 50, linha5IdaUniversidades));
		// LINHA 05 - VINGT ROSADO - VOLTA
		onibus.add(new Onibus(50, 50, linha5Volta));
		// LINHA 05 - VINGT ROSADO, UERN - VOLTA
		onibus.add(new Onibus(50, 50, linha5VoltaUERN));
		// LINHA 05 - VINGT ROSADO, IFRN - VOLTA
		onibus.add(new Onibus(50, 50, linha5VoltaIFRN));
		// LINHA 05 - VINGT ROSADO VIA UNIVERSIDADES - VOLTA
		onibus.add(new Onibus(50, 50, linha5VoltaUniversidades));
		// LINHA 05 - VINGT ROSADO VIA ODETE ROSADO - VOLTA
		onibus.add(new Onibus(50, 50, linha5VoltaOdeteRosado));
		// LINHA 06 - UNIVERSIDADES - IDA
		onibus.add(new Onibus(50, 50, linha6Ida));
		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF - IDA
		onibus.add(new Onibus(50, 50, linha6IdaSemUlrickGraff));
		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, NEM AVENIDA RIO BRANCO - IDA
		onibus.add(new Onibus(50, 50, linha6IdaSemUlrickGraffNemRioBranco));
		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF, COM UNIRB E COELHO NETO - IDA
		onibus.add(new Onibus(50, 50, linha6IdaSemUlrickGraffComUNIRBComCoelhoNeto));
		// LINHA 06 - UNIVERSIDADES - VOLTA
		onibus.add(new Onibus(50, 50, linha6Volta));
		// LINHA 06 - UNIVERSIDADES SEM ULRICK GRAFF- VOLTA
		onibus.add(new Onibus(50, 50, linha6VoltaSemUlrickGraff));
		// LINHA 06 - UNIVERSIDADES COM UNIRB - VOLTA
		onibus.add(new Onibus(50, 50, linha6VoltaUNIRB));
		// LINHA 07 - NOVA VIDA - IDA
		onibus.add(new Onibus(50, 50, linha7Ida));
		// LINHA 07 - NOVA VIDA SEM PAREDÕES, TERMINANDO NA PRAÇA FELIPE GUERRA - IDA
		onibus.add(new Onibus(50, 50, linha7IdaSemParedoesTerminandoNaPracaFelipeGuerra));
		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - IDA
		onibus.add(new Onibus(50, 50, linha7IdaJardimDasPalmeiras));
		// LINHA 07 - NOVA VIDA - VOLTA
		onibus.add(new Onibus(50, 50, linha7Volta));
		// LINHA 07 - NOVA VIDA VIA JARDIM DAS PALMEIRAS - VOLTA
		onibus.add(new Onibus(50, 50, linha7VoltaJardimDasPalmeiras));
		// LINHA 07 - NOVA VIDA VIA SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		onibus.add(new Onibus(50, 50, linha7VoltaSumareLiberdadePlanalto));
		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - IDA
		onibus.add(new Onibus(50, 50, linha8Ida));
		// LINHA 08 - SUMARÉ, LIBERDADE, PLANALTO - VOLTA
		onibus.add(new Onibus(50, 50, linha8Volta));
		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO - IDA
		onibus.add(new Onibus(50, 50, linha9Ida));
		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO COM LAGOA DO MATO, BOA
		// VISTA, DOZE ANOS E SANTO ANTÔNIO - IDA
		onibus.add(new Onibus(50, 50, linha9IdaLagoaDoMatoBoaVistaDozeAnosSantoAntonio));
		// LINHA 09 - BELO HORIZONTE, BOM JESUS, MONTE OLIMPO - VOLTA
		onibus.add(new Onibus(50, 50, linha9Volta));
		// LINHA 10 - SHOPPING, UNP - IDA
		onibus.add(new Onibus(50, 50, linha10Ida));
		// LINHA 10 - SHOPPING, UNP, AEROPORTO, MACARRÃO, BOA VISTA - IDA
		onibus.add(new Onibus(50, 50, linha10IdaAeroportoMacarraoBoaVista));
		// LINHA 10 - SHOPPING, UNP - VOLTA
		onibus.add(new Onibus(50, 50, linha10Volta));
		// LINHA 10 - SHOPPING, UNP, AEROPORTO, MACARRÃO, BOA VISTA - VOLTA
		onibus.add(new Onibus(50, 50, linha10VoltaAeroportoMacarraoBoaVista));
		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - IDA
		onibus.add(new Onibus(50, 50, linha11Ida));
		// LINHA 11 - PARQUE UNIVERSITÁRIO, UNIRB, ALTO DAS BRISAS - VOLTA
		onibus.add(new Onibus(50, 50, linha11Volta));
		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - IDA
		onibus.add(new Onibus(50, 50, linha12Ida));
		// LINHA 12 - NOVA MOSSORÓ, SANTO ANTÔNIO, BARROCAS - VOLTA
		onibus.add(new Onibus(50, 50, linha12Volta));
		// LINHA 14 - AEROPORTO, RODOVIÁRIA - IDA
		onibus.add(new Onibus(50, 50, linha14Ida));
		// LINHA 14 - AEROPORTO, RODOVIÁRIA - VOLTA
		onibus.add(new Onibus(50, 50, linha14Volta));
		// LINHA 15 - MACARRÃO, BOA VISTA - IDA
		onibus.add(new Onibus(50, 50, linha15Ida));
		// LINHA 15 - MACARRÃO, BOA VISTA - VOLTA
		onibus.add(new Onibus(50, 50, linha15Volta));
		// LINHA 17 - ODETE ROSADO - IDA
		onibus.add(new Onibus(50, 50, linha17Ida));
		// LINHA 17 - ODETE ROSADO - VOLTA
		onibus.add(new Onibus(50, 50, linha17Volta));
		// LINHA 19 - CIDADE OESTE - IDA
		onibus.add(new Onibus(50, 50, linha19Ida));
		// LINHA 19 - CIDADE OESTE - VOLTA
		onibus.add(new Onibus(50, 50, linha19Volta));

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
		int numeroParadas = paradas.size();
		int numeroParadaAleatoria;
		int numeroPessoasAleatorio = numeroPessoas;
		Random paradasAleatorias = new Random(seed);
		// Random paradasAleatorias = new Random();

		// numeroPedestresAleatorio = paradasAleatorias.nextInt(numeroPessoas - 2) + 2;
		System.out.println("Número de pessoas nessa simulação é " + numeroPessoasAleatorio + '\n');

		// Adiciona paradas no pedestre e os pedestres na parada de forma aleatória
		for (int i = 0; i < numeroPessoasAleatorio; i++) {
			numeroParadaAleatoria = paradasAleatorias.nextInt(numeroParadas);

			pessoas.get(i).setOrigem(paradas.get(numeroParadaAleatoria));
			pessoas.get(i).setParadaAtual(paradas.get(numeroParadaAleatoria));

			paradas.get(numeroParadaAleatoria).addPedestre(pessoas.get(i));
		}

		/*
		 * // Imprime as paradas com os pedestres String paradasString = ""; for (int i
		 * = 0; i < paradas.size(); i++) { paradasString += paradas.get(i) + "\n"; }
		 * Main.armazenarDados("paradasID.txt", paradasString);
		 */

		String stringSaidaPopulation = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<!DOCTYPE plans SYSTEM \"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n" + "\n" + "<plans>\n";
		String stringSaidaFacilities = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<!DOCTYPE facilities SYSTEM \"http://www.matsim.org/files/dtd/facilities_v1.dtd\">\n" + "\n"
				+ "<facilities>\n";

		List<Pedestre> pessoasParada = new ArrayList<Pedestre>();
		List<Pedestre> pessoasOnibus = new ArrayList<Pedestre>();
		List<Pedestre> pessoasOnibusSimulado = new ArrayList<Pedestre>();
		List<Pedestre> pessoasDesceramOnibus = new ArrayList<Pedestre>();
		List<Pedestre> pessoasSubiramOnibus = new ArrayList<Pedestre>();

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

		// Faz as viagens dos ônibus
		int indiceParada = 0;
		for (Onibus onibusAtual : onibus) {
			momentoAtual.set(onibusAtual.getLinha().getHorarios().get(0).get(Calendar.YEAR),
					onibusAtual.getLinha().getHorarios().get(0).get(Calendar.MONTH),
					onibusAtual.getLinha().getHorarios().get(0).get(Calendar.DAY_OF_MONTH),
					onibusAtual.getLinha().getHorarios().get(0).get(Calendar.HOUR_OF_DAY),
					onibusAtual.getLinha().getHorarios().get(0).get(Calendar.MINUTE),
					onibusAtual.getLinha().getHorarios().get(0).get(Calendar.SECOND));

			horariosSaida += momentoAtual.get(Calendar.HOUR_OF_DAY) + ":" + momentoAtual.get(Calendar.MINUTE) + ":"
					+ momentoAtual.get(Calendar.SECOND) + "\n";

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

				// Primeiro, decide quem vai descer do ônibus através do cara e coroa
				for (int i = 0; i < numeroPessoasOnibus; i++) {
					// Se for cara, o pedestre sai do ônibus
					if (Main.caraCoroa()) {
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
							stringPessoas[indicePessoa] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\"" + coordenadaY
									+ "\" type=\"home\"/>\n";
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
					if ((numeroPessoasOnibus <= onibusAtual.getCapacidadeMaximaPassageiros()) && Main.caraCoroa()) {
						indicePessoa = -1;
						pessoasSubiramOnibus.add(pessoasParada.get(i));
						pessoasOnibus.add(pessoasParada.get(i));
						numeroPessoasOnibus++;

						// Procura pelo índice da pessoa no vetor de pessoas
						for (int a = 0; a < numeroPessoas; a++) {
							if (pessoasParada.get(i).getNome().equals(pessoas.get(a).getNome())) {
								indicePessoa = a;
								break;
							}
						}

						if (indicePessoa != -1) {
							if (stringPessoas[indicePessoa] == null) {
								stringPessoas[indicePessoa] = "\n\t<person id=\"" + pessoas.get(indicePessoa).getNome()
										+ "\" >\n\t\t<plan>\n" + "\t\t\t<act end_time=\"" + horaAtual + ":"
										+ minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX + "\" y=\""
										+ coordenadaY + "\" type=\"home\"/>\n" + "\t\t\t<leg mode=\"pt\"/>\n";
							} else {
								indiceString = stringPessoas[indicePessoa].length() - 2;

								while (stringPessoas[indicePessoa].charAt(indiceString) != '\n') {
									indiceString--;
								}

								stringPessoas[indicePessoa] = stringPessoas[indicePessoa].substring(0,
										indiceString + 1);

								stringPessoas[indicePessoa] += "\t\t\t<act end_time=\"" + horaAtual + ":" + minutoAtual
										+ ":" + segundoAtual + "\" x=\"" + coordenadaX + "\" y=\"" + coordenadaY
										+ "\" type=\"home\"/>\n" + "\t\t\t<leg mode=\"pt\"/>\n";
							}

							stringSaidaFacilities += "\n\t<facility id=\"" + pessoasParada.get(i).getNome() + "Subiu"
									+ horaAtual + ":" + minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX
									+ "\" y=\"" + coordenadaY + "\"/>\n";
						}
					}
				}

				paradaAtual.addAllPedestres(pessoasDesceramOnibus);
				paradaAtual.removeAllPedestres(pessoasSubiramOnibus);

				/*
				 * for (int i = 0; i < numeroPessoasSubiramOnibus; i++) { if
				 * (onibus.get(x).pedestres.get(i).destino == paradas[indiceParada]) {
				 * onibus.get(x).pedestres.remove(i); numeroPessoasSubiramOnibus--; } }
				 */

				// Atualiza os pedestres que estão no ônibus
				onibusAtual.setPedestres(pessoasOnibus);

				System.out.println("=====================================================================");
				System.out.println("Parada " + indiceParada + ": " + paradaAtual.getNomeParada() + " : " + "Ônibus "
						+ onibusAtual.getId());
				System.out.println("Quem subiu: " + pessoasSubiramOnibus);
				System.out.println("Quem desceu: " + pessoasDesceramOnibus);
				System.out.println("Quem está no ônibus: " + pessoasOnibus);
				System.out.println();

				// Simula deslocamento
				if ((indiceParada + 1) != onibusAtual.getLinha().getParadas().size()) {
					pessoasOnibusSimulado = Main.simularDeslocamento(momentoAtual, paradaAtual,
							onibusAtual.getLinha().getParadas().get(indiceParada + 1), onibusAtual.getVelocidade(),
							pessoasSubiramOnibus, pessoasDesceramOnibus, pessoasOnibus, pessoasOnibusSimulado);
				} else {
					pessoasOnibusSimulado = Main.simularDeslocamento(momentoAtual,
							onibusAtual.getLinha().getParadas().get(indiceParada - 1), paradaAtual,
							onibusAtual.getVelocidade(), pessoasSubiramOnibus, pessoasDesceramOnibus, pessoasOnibus,
							pessoasOnibusSimulado);
				}

				// Esvazia vetores de descida e subida
				pessoasDesceramOnibus.clear();
				pessoasSubiramOnibus.clear();
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
				indicePessoa = -1;
				onibusAtual.getPedestres().get(i).setParadaAtual(paradaAtual);

				// Procura pelo índice da pessoa no vetor de pessoas
				for (int a = 0; a < numeroPessoas; a++) {
					if (onibusAtual.getPedestres().get(i).getNome().equals(pessoas.get(a).getNome())) {
						indicePessoa = a;
						break;
					}
				}

				if (indicePessoa != -1) {
					stringPessoas[indicePessoa] += "\t\t\t<act x=\"" + coordenadaX + "\" y=\"" + coordenadaY
							+ "\" type=\"home\"/>\n";
					stringSaidaFacilities += "\n\t<facility id=\"" + pessoasOnibus.get(i).getNome() + "Desceu"
							+ horaAtual + ":" + minutoAtual + ":" + segundoAtual + "\" x=\"" + coordenadaX + "\" y=\""
							+ coordenadaY + "\"/>\n";
				}
			}

			onibusAtual.clearPedestres();
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

		Main.armazenarDados("population.xml", stringSaidaPopulation);
		Main.armazenarDados("facilitiesSaida.xml", stringSaidaFacilities);
		Main.armazenarDados("paradasPercorridas.xml", horariosSaida);
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