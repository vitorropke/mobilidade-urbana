import random

#random.seed(1)

# https://www.geeksforgeeks.org/python-program-for-binary-search/
# Iterative Binary Search Function
# It returns index of x in given array arr if present,
# else returns -1
def binary_search(arr, x, rangeCoordenadas):
	low = 0
	high = len(arr) - 1
	mid = 0

	# Enquanto o limite inferior e superior não alcaçarem o tamanho do range de coordenadas
	while (high - low) > rangeCoordenadas:

		mid = (high + low) // 2

		# If x is greater, ignore left half
		if arr[mid][0] < x:
			low = mid + 1

		# If x is smaller, ignore right half
		elif arr[mid][0] > x:
			high = mid - 1

		# means x is present at mid
		else:
			return mid

	# Retorna a última posição encontrada
	return mid

#Criando arquivo de entrada
arquivoEntrada = open("coordenadas.txt", "r")
#Salvando o texto do arquivo de entrada em uma variável
coordenadas = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

arquivoParametros = open("parametros.txt", "r")
#Salvando o texto do arquivo de entrada em uma variável
parametros = arquivoParametros.read()
#Fechando arquivo de entrada
arquivoParametros.close()

#Caso exista aspas no código a ser copiado aqui, utilize este símbolo \ antes das aspas, para que estas não sejam consideradas como parte da sintaxe do python
#Ou seja, é uma forma de esconder as aspas do texto

#Criação do cabeçalho do arquivo population
cabecalhoDoArquivoPopulation = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE plans SYSTEM \"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n\n<plans>\n"

#Criação do cabeçalho do arquivo facilities
cabecalhoDoArquivoFacilities = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE facilities SYSTEM \"http://www.matsim.org/files/dtd/facilities_v1.dtd\">\n\n<facilities>\n"

# obtém as coordenadas da network para salvar em um vetor
# Coleta o número de coordenadas
numeroCoordenadas = 0
indice = 0
while True:
	if coordenadas[indice] == '\n':
		numeroCoordenadas += 1
	#Incrementa o índice. Se for o último dígito o loop pára
	indice += 1
	if indice == len(coordenadas):
		break

# Inicializa vetor de coordenadas
floatCoordenadas = [[0 for y in range(2)] for x in range(numeroCoordenadas)]

# Insere as coordenadas no vetor
indice = 0
for x in range(numeroCoordenadas):
	strNumeroAtual = ""
	while coordenadas[indice] != '\t':
		strNumeroAtual += coordenadas[indice]
		indice += 1

	floatCoordenadas[x][0] = float(strNumeroAtual)
	strNumeroAtual = ""
	indice += 1

	while coordenadas[indice] != '\n':
		strNumeroAtual += coordenadas[indice]
		indice += 1

	floatCoordenadas[x][1] = float(strNumeroAtual)
	indice += 1

#Criando as corpoDoArquivoPopulation
corpoDoArquivoPopulation = ""
corpoDoArquivoFacilities = ""

numeroPessoasString = ""
latitudeMinimaCasaString = ""
latitudeMaximaCasaString = ""
longitudeMinimaCasaString = ""
longitudeMaximaCasaString = ""
latitudeMinimaLojaString = ""
latitudeMaximaLojaString = ""
longitudeMinimaLojaString = ""
longitudeMaximaLojaString = ""
latitudeMinimaTrabalhoString = ""
latitudeMaximaTrabalhoString = ""
longitudeMinimaTrabalhoString = ""
longitudeMaximaTrabalhoString = ""
indice = 0
linha = 1
# Coleta informações necessárias do arquivo 'parametros.txt'
while True:
	if linha == 1 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			numeroPessoasString += parametros[indice]
			indice += 1
	elif linha == 3 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			latitudeMinimaCasaString += parametros[indice]
			indice += 1
	elif linha == 4 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			latitudeMaximaCasaString += parametros[indice]
			indice += 1
	elif linha == 5 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			longitudeMinimaCasaString += parametros[indice]
			indice += 1
	elif linha == 6 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			longitudeMaximaCasaString += parametros[indice]
			indice += 1
	elif linha == 8 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			latitudeMinimaLojaString += parametros[indice]
			indice += 1
	elif linha == 9 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			latitudeMaximaLojaString += parametros[indice]
			indice += 1
	elif linha == 10 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			longitudeMinimaLojaString += parametros[indice]
			indice += 1
	elif linha == 11 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			longitudeMaximaLojaString += parametros[indice]
			indice += 1
	elif linha == 13 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			latitudeMinimaTrabalhoString += parametros[indice]
			indice += 1
	elif linha == 14 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			latitudeMaximaTrabalhoString += parametros[indice]
			indice += 1
	elif linha == 15 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			longitudeMinimaTrabalhoString += parametros[indice]
			indice += 1
	elif linha == 16 and parametros[indice] == ':':
		indice += 1
		# Pula espaços e tabulações
		while parametros[indice].isspace() or parametros[indice] == '\t':
			indice += 1

		# Número de pessoas
		while parametros[indice] != '\n':
			longitudeMaximaTrabalhoString += parametros[indice]
			indice += 1
	
	# Incrementa a linha
	if parametros[indice] == '\n':
		linha += 1
	
	#Incrementa o índice. Se for o último dígito o loop pára
	indice += 1
	if indice == len(parametros):
		break

# Cria o plano de todas as pessoas
x = 0
numeroDePessoas = int(numeroPessoasString)
while x < numeroDePessoas:
	#Ações(act)
	#Para tornar o script mais aleatório, coloquei algumas condições com números aleatórios
	
	#saiDeCasa = 0: A pessoa sai de casa pela manhã
	#saiDeCasa = 1: A pessoa sai de casa pela tarde
	#saiDeCasa = 2: A pessoa sai de casa pela noite
	saiDeCasa = random.randrange(3)
	
	#saiDeTrabalho = 0: A pessoa não trabalha
	#saiDeTrabalho = 1: A pessoa sai do trabalho pela tarde
	#saiDeTrabalho = 2: A pessoa sai do trabalho pela noite
	#saiDeTrabalho = 3: A pessoa sai do trabalho pela manha, do outro dia
	saiDeTrabalho = random.randrange(4)
	
	#saiDeLoja = 0: A pessoa não vai à loja
	#saiDeLoja = 1: A pessoa sai da loja pela tarde
	#saiDeLoja = 2: A pessoa sai da loja pela noite
	saiDeLoja = random.randrange(3)
	
	# Evita pessoas ociosas e voltas no tempo
	if ((saiDeTrabalho != 0) and (saiDeTrabalho > saiDeCasa)) or ((saiDeLoja != 0) and (saiDeLoja > saiDeCasa)):
		print(x)

		#Abrindo tag person
		corpoDoArquivoPopulation += "\n\t<person id=\"pessoa{}\">\n".format(x)

		#Abrindo tag plan
		corpoDoArquivoPopulation += "\t\t<plan>\n"
		#Hora aleatória indo das 4 às 8 da manhã
		horaAleatoriaManha = random.randrange(4, 9)
		#Hora aleatória indo das 11 da manhã às 14 da tarde
		horaAleatoriaTarde = random.randrange(11, 15)
		#Hora aleatória indo das 17 da tarde às 22 da noite
		horaAleatoriaNoite = random.randrange(17, 23)

		minutoAleatorio = random.randrange(60)
		segundoAleatorio = random.randrange(60)

		"""
		#Coordenadas aleatórias float
		coordenadaXAleatoriaCasa = random.uniform(float(longitudeMinimaCasaString), float(longitudeMaximaCasaString))
		coordenadaYAleatoriaCasa = random.uniform(float(latitudeMinimaCasaString), float(latitudeMaximaCasaString))

		coordenadaXAleatoriaLoja = random.uniform(float(longitudeMinimaLojaString), float(longitudeMaximaLojaString))
		coordenadaYAleatoriaLoja = random.uniform(float(latitudeMinimaLojaString), float(latitudeMaximaLojaString))

		coordenadaXAleatoriaTrabalho = random.uniform(float(longitudeMinimaTrabalhoString), float(longitudeMaximaTrabalhoString))
		coordenadaYAleatoriaTrabalho = random.uniform(float(latitudeMinimaTrabalhoString), float(latitudeMaximaTrabalhoString))
		"""

		#Coordenadas aleatórias int
		coordenadaXAleatoriaCasa = random.randrange(int(longitudeMinimaCasaString), int(longitudeMaximaCasaString))
		coordenadaYAleatoriaCasa = random.randrange(int(latitudeMinimaCasaString), int(latitudeMaximaCasaString))

		coordenadaXAleatoriaTrabalho = random.randrange(int(longitudeMinimaTrabalhoString), int(longitudeMaximaTrabalhoString))
		coordenadaYAleatoriaTrabalho = random.randrange(int(latitudeMinimaTrabalhoString), int(latitudeMaximaTrabalhoString))

		coordenadaXAleatoriaLoja = random.randrange(int(longitudeMinimaLojaString), int(longitudeMaximaLojaString))
		coordenadaYAleatoriaLoja = random.randrange(int(latitudeMinimaLojaString), int(latitudeMaximaLojaString))

		# Cria um círculo virtual para verificar se a coordenada gerada está próximo de algum nó
		# casa
		longeDoNo = True
		raioDoCirculo = 50
		rangeCoordenadas = 20
		while longeDoNo:
			#Define um range de coordenadas para procurar pelas coordenadas próximas
			posicao = binary_search(floatCoordenadas, coordenadaXAleatoriaCasa, rangeCoordenadas)

			# Limite inferior do vetor de coordenadas
			if (posicao - rangeCoordenadas) > 0:
				posicaoInferior = posicao - rangeCoordenadas
			else:
				posicaoInferior = 0
			# Limite superior do vetor de coordenadas
			if (posicao + rangeCoordenadas) < numeroCoordenadas:
				posicaoSuperior = posicao + rangeCoordenadas
			else:
				posicaoSuperior = numeroCoordenadas

			# Procura por uma faixa de coordenadas definida em 'rangeCoordenadas'
			for i in range(posicaoInferior, posicaoSuperior):
				# (i - center_x)^2 + (y - center_y)^2 <= radius^2
				if ((floatCoordenadas[i][0] - coordenadaXAleatoriaCasa) ** 2) + ((floatCoordenadas[i][1] - coordenadaYAleatoriaCasa) ** 2) <= (raioDoCirculo ** 2):
					longeDoNo = False
					break

			# Procura por novas coordenadas caso sejam longe de nós
			if longeDoNo:
				#Coordenadas aleatórias
				coordenadaXAleatoriaCasa = random.randrange(int(longitudeMinimaCasaString), int(longitudeMaximaCasaString))
				#Parte de baixo do mapa
				coordenadaYAleatoriaCasa = random.randrange(int(latitudeMinimaCasaString), int(latitudeMaximaCasaString))
				"""
				coordenadaXAleatoriaCasa = random.uniform(float(longitudeMinimaCasaString), float(longitudeMaximaCasaString))
				coordenadaYAleatoriaCasa = random.uniform(float(latitudeMinimaCasaString), float(latitudeMaximaCasaString))
				"""

		# trabalho
		longeDoNo = True
		raioDoCirculo = 50
		while longeDoNo:
			posicao = binary_search(floatCoordenadas, coordenadaXAleatoriaTrabalho, rangeCoordenadas)

			if (posicao - rangeCoordenadas) > 0:
				posicaoInferior = posicao - rangeCoordenadas
			else:
				posicaoInferior = 0
			
			if (posicao + rangeCoordenadas) < numeroCoordenadas:
				posicaoSuperior = posicao + rangeCoordenadas
			else:
				posicaoSuperior = numeroCoordenadas

			# Procura por uma faixa de 1000 coordenadas
			for i in range(posicaoInferior, posicaoSuperior):
				# (i - center_x)^2 + (y - center_y)^2 <= radius^2
				if ((floatCoordenadas[i][0] - coordenadaXAleatoriaTrabalho) ** 2) + ((floatCoordenadas[i][1] - coordenadaYAleatoriaTrabalho) ** 2) <= (raioDoCirculo ** 2):
					longeDoNo = False
					break

			# Procura por novas coordenadas caso sejam longe de nós
			if longeDoNo:
				#Coordenadas aleatórias
				coordenadaXAleatoriaTrabalho = random.randrange(int(longitudeMinimaTrabalhoString), int(longitudeMaximaTrabalhoString))
				#Parte de baixo do mapa
				coordenadaYAleatoriaTrabalho = random.randrange(int(latitudeMinimaTrabalhoString), int(latitudeMaximaTrabalhoString))
				"""
				coordenadaXAleatoriaTrabalho = random.uniform(float(longitudeMinimaTrabalhoString), float(longitudeMaximaTrabalhoString))
				coordenadaYAleatoriaTrabalho = random.uniform(float(latitudeMinimaTrabalhoString), float(latitudeMaximaTrabalhoString))
				"""
		
		# loja
		longeDoNo = True
		raioDoCirculo = 50
		while longeDoNo:
			posicao = binary_search(floatCoordenadas, coordenadaXAleatoriaLoja, rangeCoordenadas)

			if (posicao - rangeCoordenadas) > 0:
				posicaoInferior = posicao - rangeCoordenadas
			else:
				posicaoInferior = 0
			
			if (posicao + rangeCoordenadas) < numeroCoordenadas:
				posicaoSuperior = posicao + rangeCoordenadas
			else:
				posicaoSuperior = numeroCoordenadas

			# Procura por uma faixa de 1000 coordenadas
			for i in range(posicaoInferior, posicaoSuperior):
				# (i - center_x)^2 + (y - center_y)^2 <= radius^2
				if ((floatCoordenadas[i][0] - coordenadaXAleatoriaLoja) ** 2) + ((floatCoordenadas[i][1] - coordenadaYAleatoriaLoja) ** 2) <= (raioDoCirculo ** 2):
					longeDoNo = False
					break

			# Procura por novas coordenadas caso sejam longe de nós
			if longeDoNo:
				#Coordenadas aleatórias
				coordenadaXAleatoriaLoja = random.randrange(int(longitudeMinimaLojaString), int(longitudeMaximaLojaString))
				#Parte de baixo do mapa
				coordenadaYAleatoriaLoja = random.randrange(int(latitudeMinimaLojaString), int(latitudeMaximaLojaString))
				"""
				coordenadaXAleatoriaLoja = random.uniform(float(longitudeMinimaLojaString), float(longitudeMaximaLojaString))
				coordenadaYAleatoriaLoja = random.uniform(float(latitudeMinimaLojaString), float(latitudeMaximaLojaString))
				"""

		#Tag de construção, pessoa trabalho e loja
		corpoDoArquivoFacilities += "\n\t<facility id=\"casaPessoa{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		if saiDeTrabalho != 0:
			corpoDoArquivoFacilities += "\t<facility id=\"trabalho{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
		if saiDeLoja != 0:
			corpoDoArquivoFacilities += "\t<facility id=\"loja{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)

		#:02d significa que o número terá obrigatoriamente dois dígitos
		#Se sair de casa de manhã
		if saiDeCasa == 0 and saiDeTrabalho != 3:
			corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(horaAleatoriaManha, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"

			#CASA-TRABALHO
			#Se sair de trabalho de tarde
			if saiDeTrabalho == 1:
				corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
				corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"

				#CASA-TRABALHO-LOJA-CASA
				#Se sair de loja de noite
				if saiDeLoja == 2:
					corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
					corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
					corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
				
				#Se não for para loja
				#CASA-TRABALHO-CASA
				else:
					corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			
			#Se sair de trabalho de noite
			elif saiDeTrabalho == 2:
				#CASA-LOJA-TRABALHO-CASA
				#Se sair de loja de tarde
				if saiDeLoja == 1:
					corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
					corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
					corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
					corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
					corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
				
				#CASA-TRABALHO-CASA
				else:
					corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
					corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
					corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			
			#Se não trabalhar
			else:
				#CASA-LOJA-CASA
				#Se sair da loja de tarde
				if saiDeLoja == 1:
					corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
					corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
					corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
				
				#Se sair da loja de noite
				elif saiDeLoja == 2:
					corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
					corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
					corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
				
		#Se sair de casa de tarde
		elif saiDeCasa == 1 and saiDeTrabalho != 3:
			corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"

			#CASA-TRABALHO-CASA
			#Se sair de trabalho de noite
			if saiDeTrabalho == 2:
				corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
				corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
				corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			
			#CASA-LOJA-CASA
			#Se sair de loja de noite
			elif saiDeLoja == 2:
				corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
				corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
				corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		
		#Se sair de casa de noite, chega no outro dia de manhã
		else:
			# TRABALHO-CASA
			corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaManha, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
			corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"

			# CASA-TRABALHO
			corpoDoArquivoPopulation += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			corpoDoArquivoPopulation += "\t\t\t<leg mode=\"pt\"/>\n"
			corpoDoArquivoPopulation += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)

		#Fechando tag plan
		corpoDoArquivoPopulation += "\t\t</plan>\n"
		
		#Fechando tag person
		corpoDoArquivoPopulation += "\t</person>\n"

		x += 1

#Criação do rodapé do arquivo xml
rodapeDoArquivoPopulation = "\n</plans>"
rodapeDoArquivoFacilities = "\n</facilities>"

#Criando arquivo population
arquivoPopulacao = open("population.xml", "w")
#Escrevendo no arquivo population
arquivoPopulacao.write(cabecalhoDoArquivoPopulation + corpoDoArquivoPopulation + rodapeDoArquivoPopulation)
#Fechando arquivo population
arquivoPopulacao.close()

#Criando arquivo facilities
arquivoFacilities = open("facilities.xml", "w")
#Escrevendo no arquivo facilities
arquivoFacilities.write(cabecalhoDoArquivoFacilities + corpoDoArquivoFacilities + rodapeDoArquivoFacilities)
#Fechando arquivo facilities
arquivoFacilities.close()
