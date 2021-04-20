import datetime

# primeira viagem
horaInicial = 5
minutoInicial = 0
segundoInicial = 0

segundosHoraAtual = (horaInicial * 3600) + (minutoInicial  * 60) + segundoInicial

# tempo de cada viagem
tempoViagemHora = 1
tempoViagemMinuto = 0
tempoViagemSegundo = 0

numeroVeiculos = 1
numeroViagensPorVeiculo = 10

strSaida = ""

# converte o tempo da viagem para segundos
segundosTempoViagem = (tempoViagemHora * 3600) + (tempoViagemMinuto  * 60) + tempoViagemSegundo
# obtem o tempo entre viagens em segundos
segundosEntreViagens = segundosTempoViagem / numeroVeiculos

numeroViagem = 0
for x in range(numeroViagensPorVeiculo):
	for y in range(numeroVeiculos):
		strSaida += "<departure id=\"viagem" + str(numeroViagem) + "\" departureTime=\""
		# Converte segundos para o formato 'hh:mm:ss'
		strSaida += str(datetime.timedelta(seconds=int(segundosHoraAtual)))
		strSaida += "\" vehicleRefId=\"veiculo" + str(y + 1) + "\"/>\n"

		segundosHoraAtual += segundosEntreViagens
		numeroViagem += 1

#Criando arquivo de viagens
arquivoViagens = open("tempoViagens.xml", "w")
#Escrevendo no arquivo de viagens
arquivoViagens.write(strSaida)
#Fechando arquivo de viagens
arquivoViagens.close()
