import datetime

#Criando arquivo de entrada
arquivoEntrada = open("tempo-paradas.txt", "r")
#Salvando o texto do arquivo de entrada em uma variável
paradas = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

indice = 0
numeroParadas = 0
# detecta o número de paradas
while paradas[indice] != None:
	# Se encontrar o '\n' soma o numero de paradas
	if paradas[indice] == '\n':
		numeroParadas += 1

	#Incrementa o índice. Se for o último dígito o loop pára
	indice += 1
	if indice == len(paradas):
		break

numeroParadas += 1

# Entrada do tempo total das paradas
horarioParada = ["" for x in range(numeroParadas)]
horasTotal = 12
minutosTotal = 0
segundosTotal = 0
horarioParadaAtual = 0
strSaida = ""

# Conversão de minutos para segundos
minutosParaSegundos = (horasTotal * 3600) + (minutosTotal  * 60) + segundosTotal
# Divide segundos pelo número de paradas
tempoCadaParada = minutosParaSegundos / numeroParadas
# Converte segundos para o formato 'hh:mm:ss'
strTempo = str(datetime.timedelta(seconds=int(tempoCadaParada)))

horarioParadaAtual = 0
# Coloca os horários de cada parada no vetor de horarioParada
# Os horários vão sendo incrementados a cada loop
for x in range(numeroParadas):
	# Converte segundos para o formato 'hh:mm:ss'
	strTempo = str(datetime.timedelta(seconds=int(horarioParadaAtual)))
	
	horarioParada[x] += strTempo
	horarioParadaAtual += tempoCadaParada

for x in range(numeroParadas):
	print(horarioParada[x])

indice = 0
indiceHorario = 0

"""
# Percorre texto de entrada até o final
while paradas[indice] != None:
	# Se encontrar o padrão '00:' substituir pelo horário do vetor horarioParada
	if paradas[indice] == '0' and paradas[indice + 1] == '0' and paradas[indice + 2] == ':':
		strSaida += horarioParada[indiceHorario] + "\" awaitDeparture=\"true"
		indiceHorario += 1
		indice += 8

	strSaida += paradas[indice]

	#Incrementa o índice. Se for o último dígito o loop pára
	indice += 1
	if indice + 11 == len(paradas):
		break
"""

# Percorre texto de entrada até o final
while paradas[indice] != None:
	# Se encontrar o padrão 'refId:' substituir pelo horário do vetor horarioParada
	if paradas[indice] == 'r' and paradas[indice + 1] == 'e' and paradas[indice + 2] == 'f' and paradas[indice + 3] == 'I' and paradas[indice + 4] == 'd':
		strSaida += "refId=\""
		indice += 7
		
		while paradas[indice] != '\"':
			strSaida += paradas[indice]
			indice += 1

		strSaida += "\" departureOffset=\""
		#strSaida += horarioParada[indiceHorario] + "\" awaitDeparture=\"true\"/>"
		strSaida += horarioParada[indiceHorario] + "\"/>"
		indiceHorario += 1
		
		while indice != len(paradas):
			if paradas[indice] == '\n':
				break
			indice += 1

	if indice != len(paradas):
		strSaida += paradas[indice]

	#Incrementa o índice. Se for o última linha o loop, pára
	indice += 1
	if indiceHorario == numeroParadas - 1 or indice == len(paradas):
		break

# último loop
while True:
	# Se encontrar o padrão 'refId:' substituir pelo horário do vetor horarioParada
	if paradas[indice] == 'r' and paradas[indice + 1] == 'e' and paradas[indice + 2] == 'f' and paradas[indice + 3] == 'I' and paradas[indice + 4] == 'd':
		strSaida += "refId=\""
		indice += 7

		while paradas[indice] != '\"':
			strSaida += paradas[indice]
			indice += 1

		strSaida += "\" arrivalOffset=\""
		strSaida += str(datetime.timedelta(seconds=int(minutosParaSegundos))) + "\"" + "/>"

		break

	if indice != len(paradas):
		strSaida += paradas[indice]

	#Incrementa o índice. Se for o último dígito, pára
	indice += 1
	if indice == len(paradas):
		break

#Criando arquivo de horários
arquivoTempoParadas = open("tempo-paradas.xml", "w")
#Escrevendo no arquivo de horários
arquivoTempoParadas.write(strSaida)
#Fechando arquivo de horários
arquivoTempoParadas.close()
