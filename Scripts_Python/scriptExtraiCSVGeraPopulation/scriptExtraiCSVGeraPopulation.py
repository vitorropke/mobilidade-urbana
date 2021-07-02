# https://stackoverflow.com/questions/2740763/how-do-you-read-a-cells-value-from-an-openoffice-calc-ods-file
# https://stackoverflow.com/questions/17834995/how-to-convert-opendocument-spreadsheets-to-a-pandas-dataframe
from ezodf import opendoc, Sheet
import datetime
import random

# A função retorna o plano da pessoa como string. A última linha dessa string é o índice atual de pessoas
def processaVeiculos(colunaPlanilha, linhaPlanilha, paginaPlanilha, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, modoDeslocamento, origem, rota, destino):
	posicao = colunaPlanilha + str(linhaPlanilha)
	cell = paginaPlanilha[posicao]
	intNumeroVeiculos = int(cell.value)

	saidaFuncao = ""

	for veiculo in range(intNumeroVeiculos):
		intHorarioEscolhido = random.randint(segundosHorarioInicial, segundosHorarioFinal)

		# Converte segundos para o formato 'hh:mm:ss'
		strHorarioEscolhido = str(datetime.timedelta(seconds=int(intHorarioEscolhido)))

		#print(strHorarioEscolhido)

		#Abrindo tag person
		saidaFuncao += "\n\t<person id=\"pessoa{}\">\n".format(indicePessoa)

		#Abrindo tag plan
		saidaFuncao += "\t\t<plan>\n"

		saidaFuncao += "\t\t\t<act end_time=\"{}\" {} type=\"home\"/>\n".format(strHorarioEscolhido, origem)

		saidaFuncao += "\t\t\t<leg mode=\"{}\">\n".format(modoDeslocamento)
		saidaFuncao += "\t\t\t\t<route>{}</route>\n".format(rota)
		saidaFuncao += "\t\t\t</leg>\n"

		saidaFuncao += "\t\t\t<act {} type=\"home\"/>\n".format(destino)

		#Fechando tag plan
		saidaFuncao += "\t\t</plan>\n"

		#Fechando tag person
		saidaFuncao += "\t</person>\n"

		indicePessoa += 1

	saidaFuncao += str(indicePessoa)

	return saidaFuncao

doc = opendoc('matrizFrequenciaTrajeto.ods')

print("Spreadsheet contains %d sheet(s)." % len(doc.sheets))
for sheet in doc.sheets:
	print("-"*40)
	print("Sheet name : '%s'" % sheet.name)
	print("Size of Sheet : (rows=%d, cols=%d)" % (sheet.nrows(), sheet.ncols()) )

print()

ruaDelfimMoreiraOeste = "x=\"-4156765.926751\" y=\"-577413.522675\""
ruaDelfimMoreiraLeste = "x=\"-4156607.081052\" y=\"-577508.919669\""
ruaJuvenalLamartineNorte = "x=\"-4156625.443853\" y=\"-577333.950534\""
ruaJuvenalLamartineSul = "x=\"-4156779.960112\" y=\"-577595.060617\""

#Criação do cabeçalho do arquivo population
strSaida = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE plans SYSTEM \"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n\n<plans>\n"
linhaInicial = 3
linhaFinal = 47
indicePessoa = 0
# Percorre as páginas da planilha
for sheet in doc.sheets:
	# Percorre as linhas da página
	for linha in range(linhaInicial, linhaFinal):
		# Célula dos horários
		posicao = "A" + str(linha)
		cell = sheet[posicao]
		#print(cell.value)

		strHoraInicial = cell.value[0:2]
		strMinutoInicial = cell.value[3:5]

		strHoraFinal = cell.value[8:10]
		strMinutoFinal = cell.value[11:13]

		"""
		print(strHoraInicial)
		print(strMinutoInicial)
		print(strHoraFinal)
		print(strMinutoFinal)
		"""

		# Transforma o horário string para horário inteiro
		intHoraInicial = int(strHoraInicial)
		intMinutoInicial = int(strMinutoInicial)

		intHoraFinal = int(strHoraFinal)
		intMinutoFinal = int(strMinutoFinal)

		# Calcula o horário em segundos
		segundosHorarioInicial = (intHoraInicial * 3600) + (intMinutoInicial * 60)
		segundosHorarioFinal = (intHoraFinal * 3600) + (intMinutoFinal * 60)
		
		# Veículos em A
		origem = "{} link=\"781456337_0\"".format(ruaDelfimMoreiraOeste)
		rota = "4858126827 4852689014"
		destino = "{} link=\"781456337_2\"".format(ruaDelfimMoreiraLeste)

		# Carros
		strPlano = processaVeiculos("B", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("C", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("D", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em B
		origem = "{} link=\"781456337_0\"".format(ruaDelfimMoreiraOeste)
		rota = "4858126827 4852689014"
		destino = "{} link=\"493347377_3_r\"".format(ruaJuvenalLamartineSul)

		# Carros
		strPlano = processaVeiculos("F", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("G", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("H", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em C
		origem = "{} link=\"781456337_0\"".format(ruaDelfimMoreiraOeste)
		rota = "4858126827 4852689014 4858126831"
		destino = "{} link=\"493347377_5\"".format(ruaJuvenalLamartineNorte)

		# Carros
		strPlano = processaVeiculos("J", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("K", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("L", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em D
		origem = "{} link=\"781456326_0\"".format(ruaDelfimMoreiraLeste)
		rota = "4856339514 4858126831"
		destino = "{} link=\"781456326_2\"".format(ruaDelfimMoreiraOeste)

		# Carros
		strPlano = processaVeiculos("N", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("O", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("P", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em E
		origem = "{} link=\"781456326_0\"".format(ruaDelfimMoreiraLeste)
		rota = "4856339514 4858126831"
		destino = "{} link=\"493347377_5\"".format(ruaJuvenalLamartineNorte)

		# Carros
		strPlano = processaVeiculos("R", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("S", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("T", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em F
		origem = "{} link=\"781456326_0\"".format(ruaDelfimMoreiraLeste)
		rota = "4856339514 4858126831 4852689014"
		destino = "{} link=\"493347377_3_r\"".format(ruaJuvenalLamartineSul)

		# Carros
		strPlano = processaVeiculos("V", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("W", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("X", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em G
		origem = "{} link=\"493347377_2\"".format(ruaJuvenalLamartineSul)
		rota = "4852690265 4852689014 4858126831"
		destino = "{} link=\"493347377_5\"".format(ruaJuvenalLamartineNorte)

		# Carros
		strPlano = processaVeiculos("Z", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("AA", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("AB", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em H
		origem = "{} link=\"493347377_2\"".format(ruaJuvenalLamartineSul)
		rota = "4852690265 4852689014"
		destino = "{} link=\"781456337_2\"".format(ruaDelfimMoreiraLeste)

		# Carros
		strPlano = processaVeiculos("AD", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("AE", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("AF", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em I
		origem = "{} link=\"493347377_2\"".format(ruaJuvenalLamartineSul)
		rota = "4852690265 4852689014 4858126831"
		destino = "{} link=\"781456326_2\"".format(ruaDelfimMoreiraOeste)

		# Carros
		strPlano = processaVeiculos("AH", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("AI", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("AJ", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em J
		origem = "{} link=\"493347377_6_r\"".format(ruaJuvenalLamartineNorte)
		rota = "4858126824 4858126831 4852689014"
		destino = "{} link=\"493347377_3_r\"".format(ruaJuvenalLamartineSul)

		# Carros
		strPlano = processaVeiculos("AL", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("AM", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("AN", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Veículos em K
		origem = "{} link=\"493347377_6_r\"".format(ruaJuvenalLamartineNorte)
		rota = "4858126824 4858126831"
		destino = "{} link=\"781456326_2\"".format(ruaDelfimMoreiraOeste)

		# Carros
		strPlano = processaVeiculos("AP", linha, sheet, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		# Salva a string do plano menos a última linha. A última linha é o índice atual de pessoas
		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Motos
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "motorcycle", origem, rota, destino)
		strPlano = processaVeiculos("AQ", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

		# Caminhões
		# strPlano = processaVeiculos(intNumeroVeiculos, segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "truck", origem, rota, destino)
		strPlano = processaVeiculos("AR", linha, sheet,  segundosHorarioInicial, segundosHorarioFinal, indicePessoa, "car", origem, rota, destino)
		strIndicePessoa = strPlano.splitlines()[-1]

		strSaida += strPlano[0:len(strPlano) - len(strIndicePessoa)]
		indicePessoa = int(strIndicePessoa)

#Criação do rodapé do arquivo population
strSaida += "\n</plans>"

#Criando population
population = open("population-teste.xml", "w")
#Escrevendo no arquivo de viagens
population.write(strSaida)
#Fechando arquivo de viagens
population.close()