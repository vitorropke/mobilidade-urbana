#Criando arquivo de entrada
arquivoEntrada = open("network.xml", "r")
#Salvando o texto do arquivo de entrada em uma variável
mapa = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

"""
indice = 0
numeroCoordenadas = 0
detectouNos = False
# Coleta o número de coordenadas
while True:
	# Entrada no bloco de nós
	if mapa[indice] == 'n' and mapa[indice + 1] == 'o' and mapa[indice + 2] == 'd' and mapa[indice + 3] == 'e' and mapa[indice + 4] == 's':
		detectouNos = True

	# Detecta os nós e vai somando
	if detectouNos:
		if mapa[indice] == '/' and mapa[indice + 1] == 'n' and mapa[indice + 2] == 'o' and mapa[indice + 3] == 'd' and mapa[indice + 4] == 'e':
			numeroCoordenadas += 1

	indice += 1

	# Fim do bloco de nós
	if mapa[indice] == '/' and mapa[indice + 1] == 'n' and mapa[indice + 2] == 'o' and mapa[indice + 3] == 'd' and mapa[indice + 4] == 'e' and mapa[indice + 5] == 's':
		break
"""
strCoordenadas = ""

# Procura pelas coordenadas e salva na string
indice = 125
while True:
	if mapa[indice] == 'x':
		indice += 3
		while mapa[indice] != '\"':
			strCoordenadas += mapa[indice]
			indice += 1

		strCoordenadas += '\t'
		indice += 5

		while mapa[indice] != '\"':
			strCoordenadas += mapa[indice]
			indice += 1

		strCoordenadas += '\n'

	indice += 1

	if mapa[indice] == '/' and mapa[indice + 1] == 'n' and mapa[indice + 2] == 'o' and mapa[indice + 3] == 'd' and mapa[indice + 4] == 'e' and mapa[indice + 5] == 's':
		break

# obtém as coordenadas da network para salvar em um vetor
# Coleta o número de coordenadas
numeroCoordenadas = 0
indice = 0
while True:
	if strCoordenadas[indice] == '\n':
		numeroCoordenadas += 1
	#Incrementa o índice. Se for o último dígito o loop pára
	indice += 1
	if indice == len(strCoordenadas):
		break

# Inicializa vetor de coordenadas
floatCoordenadas = [[0 for y in range(2)] for x in range(numeroCoordenadas)]

# Insere as coordenadas no vetor
indice = 0
for x in range(numeroCoordenadas):
	strNumeroAtual = ""
	while strCoordenadas[indice] != '\t':
		strNumeroAtual += strCoordenadas[indice]
		indice += 1

	floatCoordenadas[x][0] = float(strNumeroAtual)
	strNumeroAtual = ""
	indice += 1

	while strCoordenadas[indice] != '\n':
		strNumeroAtual += strCoordenadas[indice]
		indice += 1

	floatCoordenadas[x][1] = float(strNumeroAtual)
	indice += 1

# Ordena o vetor de coordenadas
floatCoordenadas.sort()
"""
for x in range(numeroCoordenadas):
	print(floatCoordenadas[x][0])
strCoordenadas = ""
"""
# Vetor para string
for x in range(numeroCoordenadas):
	strCoordenadas += str(floatCoordenadas[x][0])
	strCoordenadas += '\t'
	strCoordenadas += str(floatCoordenadas[x][1])
	strCoordenadas += '\n'

#Criando arquivo de horários
arquivoCoordenadas = open("coordenadas.txt", "w")
#Escrevendo no arquivo de horários
arquivoCoordenadas.write(strCoordenadas)
#Fechando arquivo de horários
arquivoCoordenadas.close()
