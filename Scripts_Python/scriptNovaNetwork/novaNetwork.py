#Criando arquivo de entrada
arquivoEntrada = open("network.xml", "r")
#Salvando o texto do arquivo de entrada em uma variável
mapa = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

tamanhoArquivo = len(mapa)
strSaida = ""

indice = 0
while True:
	strSaida += mapa[indice]

	if (indice + 6) < tamanhoArquivo:
		if mapa[indice] == 'm' and mapa[indice + 1] == 'o' and mapa[indice + 2] == 'd' and mapa[indice + 3] == 'e' and mapa[indice + 4] == 's':
			strSaida += "odes=\"car,pt\" "
			indice += 11

	#Incrementa o índice. Se for o último dígito, pára
	indice += 1
	if indice == tamanhoArquivo:
		break

#Criando arquivo de saída
arquivoMapa = open("novoNetwork.xml", "w")
#Escrevendo no arquivo de saída
arquivoMapa.write(strSaida)
#Fechando arquivo de sáida
arquivoMapa.close()
