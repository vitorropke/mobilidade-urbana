import random

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

#Criando as pessoas
pessoas = ""
construcoes = ""

numeroDePessoas = 1000
for x in range(numeroDePessoas):
	print(x)
	#Abrindo tag person
	pessoas += "\n\t<person id=\"pessoa{}\">\n".format(x)

	#Abrindo tag plan
	pessoas += "\t\t<plan>\n"
	
	#Ações(act)
	#Para tornar o script mais aleatório, coloquei algumas condições com números aleatórios
	
	#saiDeCasa = 0: A pessoa sai de casa pela manhã
	#saiDeCasa = 1: A pessoa sai de casa pela tarde
	#saiDeCasa = 2: A pessoa sai de casa pela noite
	saiDeCasa = random.randrange(3)
	
	#saiDeTrabalho = 0: A pessoa não trabalha
	#saiDeTrabalho = 1: A pessoa sai do trabalho pela tarde
	#saiDeTrabalho = 2: A pessoa sai do trabalho pela noite
	saiDeTrabalho = random.randrange(3)
	
	#saiDeLoja = 0: A pessoa não vai à loja
	#saiDeLoja = 1: A pessoa sai da loja pela tarde
	#saiDeLoja = 2: A pessoa sai da loja pela noite
	saiDeLoja = random.randrange(3)
	
	#Hora aleatória indo das 4 às 8 da manhã
	horaAleatoriaManha = random.randrange(4, 9)
	#Hora aleatória indo das 11 da manhã às 14 da tarde
	horaAleatoriaTarde = random.randrange(11, 15)
	#Hora aleatória indo das 17 da tarde às 22 da noite
	horaAleatoriaNoite = random.randrange(17, 23)

	minutoAleatorio = random.randrange(60)
	segundoAleatorio = random.randrange(60)

	#Coordenadas aleatórias
	coordenadaXAleatoriaCasa = random.randrange(-4164462, -4148877)
	#Parte de baixo do mapa
	coordenadaYAleatoriaCasa = random.randrange(-587482, -569598)

	coordenadaXAleatoriaTrabalho = random.randrange(-4164462, -4148877)
	#Parte de cima do mapa
	coordenadaYAleatoriaTrabalho = random.randrange(-587482, -569598)

	coordenadaXAleatoriaLoja = random.randrange(-4164462, -4148877)
	#Parte de cima do mapa
	coordenadaYAleatoriaLoja = random.randrange(-587482, -569598)

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
			coordenadaXAleatoriaCasa = random.randrange(-4164462, -4148877)
			#Parte de baixo do mapa
			coordenadaYAleatoriaCasa = random.randrange(-587482, -569598)

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
			coordenadaXAleatoriaTrabalho = random.randrange(-4164462, -4148877)
			#Parte de baixo do mapa
			coordenadaYAleatoriaTrabalho = random.randrange(-587482, -569598)

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
			coordenadaXAleatoriaLoja = random.randrange(-4164462, -4148877)
			#Parte de baixo do mapa
			coordenadaYAleatoriaLoja = random.randrange(-587482, -569598)

	#Tag de construção, pessoa trabalho e loja
	construcoes += "\n\t<facility id=\"casaPessoa{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
	if saiDeTrabalho != 0:
		construcoes += "\t<facility id=\"trabalho{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
	if saiDeLoja != 0:
		construcoes += "\t<facility id=\"loja{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)

	#:02d significa que o número terá obrigatoriamente dois dígitos
	#Se sair de casa de manhã
	if saiDeCasa == 0:
		pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(horaAleatoriaManha, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		pessoas += "\t\t\t<leg mode=\"pt\"/>\n"

		#CASA-TRABALHO
		#Se sair de trabalho de tarde
		if saiDeTrabalho == 1:
			pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
			pessoas += "\t\t\t<leg mode=\"pt\"/>\n"

			#CASA-TRABALHO-LOJA-CASA
			#Se sair de loja de noite
			if saiDeLoja == 2:
				pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
				pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
				pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			
			#Se não for para loja
			#CASA-TRABALHO-CASA
			else:
				pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		
		#Se sair de trabalho de noite
		elif saiDeTrabalho == 2:
			#CASA-LOJA-TRABALHO-CASA
			#Se sair de loja de tarde
			if saiDeLoja == 1:
				pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
				pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
				pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
				pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
				pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			
			#CASA-TRABALHO-CASA
			else:
				pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
				pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
				pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		
		#Se não trabalhar
		else:
			#CASA-LOJA-CASA
			#Se sair da loja de tarde
			if saiDeLoja == 1:
				pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
				pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
				pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			
			#Se sair da loja de noite
			elif saiDeLoja == 2:
				pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
				pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
				pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			
			#Se não for para loja nem para trabalho então vá para outra casa
			else:
				pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
				construcoes += "\t<facility id=\"casaDeNinguem{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
			
	#Se sair de casa de tarde
	elif saiDeCasa == 1:
		pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		pessoas += "\t\t\t<leg mode=\"pt\"/>\n"

		#CASA-TRABALHO-CASA
		#Se sair de trabalho de noite
		if saiDeTrabalho == 2:
			pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"work\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaTrabalho, coordenadaYAleatoriaTrabalho)
			pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
			pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		
		#CASA-LOJA-CASA
		#Se sair de loja de noite
		elif saiDeLoja == 2:
			pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"shop\"/>\n".format(horaAleatoriaNoite, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
			pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
			pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		
		#Se não for nem para loja nem para trabalho então vá para outra casa
		else:
			pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
			construcoes += "\t<facility id=\"casaDeNinguem{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)
	
	#Se sair de casa de noite
	else:
		pessoas += "\t\t\t<act end_time=\"{:02d}:{:02d}:{:02d}\" x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(horaAleatoriaTarde, minutoAleatorio, segundoAleatorio, coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		pessoas += "\t\t\t<leg mode=\"pt\"/>\n"
		pessoas += "\t\t\t<act x=\"{}\" y=\"{}\" type=\"home\"/>\n".format(coordenadaXAleatoriaCasa, coordenadaYAleatoriaCasa)
		construcoes += "\t<facility id=\"casaDeNinguem{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaXAleatoriaLoja, coordenadaYAleatoriaLoja)

	#Fechando tag plan
	pessoas += "\t\t</plan>\n"
	
	#Fechando tag person
	pessoas += "\t</person>\n"

#Criação do rodapé do arquivo xml
rodapeDoArquivoPopulation = "\n</plans>"
rodapeDoArquivoFacilities = "\n</facilities>"

#Criando arquivo population
arquivoPopulacao = open("population.xml", "w")
#Escrevendo no arquivo population
arquivoPopulacao.write(cabecalhoDoArquivoPopulation + pessoas + rodapeDoArquivoPopulation)
#Fechando arquivo population
arquivoPopulacao.close()

#Criando arquivo facilities
arquivoFacilities = open("facilities.xml", "w")
#Escrevendo no arquivo facilities
arquivoFacilities.write(cabecalhoDoArquivoFacilities + construcoes + rodapeDoArquivoFacilities)
#Fechando arquivo facilities
arquivoFacilities.close()
