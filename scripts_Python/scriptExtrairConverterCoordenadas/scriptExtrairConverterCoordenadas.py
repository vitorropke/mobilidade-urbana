#Biblioteca de conversão de coordenadas
import pyproj

nomeDaRotaAtual = "Linha9_Volta"

#Lendo arquivo de entrada
arquivoEntrada = open("BOM JESUS - BELO HORIZONTE - VOLTA", "r")
#Salvando o texto do arquivo de entrada em uma variável 'texto'
textoParadas = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

#Extração das coordenadas do arquivo de entrada

#Inicialização das variáveis
coordenadas = ""
indice = 0

#Percorre texto de entrada e salva as coordenadas
while textoParadas[indice] != None:
	#Se achar a string 'lat' comece a salvar na variável 'coordenadas' até achar o fechamento de chaves '}'
	if 'l' == textoParadas[indice]:
		indice += 1
		if 'a' == textoParadas[indice]:
			indice += 1
			if 't' == textoParadas[indice]:
				#índice + 3 para pular a letra 't' e os símbolos '"' e ':'
				indice += 3
				while textoParadas[indice] != '}':
					coordenadas += textoParadas[indice]
					indice += 1

					#Se encontrar a parte 'lng', pule 6 dígitos até o início da coordenada na longitude
					if textoParadas[indice] == '"':
						indice += 6
				
				#Adiciona quebra de linha para separar cada parada
				coordenadas += '\n'
	
	#Incrementa o índice. Se for o último dígito o loop pára
	indice += 1
	if indice == len(textoParadas):
		break


#Conversão das coordenadas 

#Entrada e saída dos modelos de projeção
#Decimal GPS
entradaProjecao = pyproj.Proj(init="epsg:4326")
#Pseudo-mercator X,Y
saidaProjecao = pyproj.Proj(init="epsg:3857")

#Inicialização das variáveis
indice = 0
latitudeEntrada = ""
longitudeEntrada = ""
coordenadaConvertidaX = ""
coordenadaConvertidaY = ""
coordenadasConvertidas = ""

#Percorre as coordenadas a serem convertidas
while coordenadas[indice] != None:
	#Percorre a latitude
	while coordenadas[indice] != ',':
		latitudeEntrada += coordenadas[indice]
		indice += 1
	
	#Pula a vírgula
	indice += 1

	#Percorre a longitude
	while coordenadas[indice] != '\n':
		longitudeEntrada += coordenadas[indice]
		indice += 1

	#Converte as coordenadas
	coordenadaConvertidaX, coordenadaConvertidaY = pyproj.transform(entradaProjecao, saidaProjecao, longitudeEntrada, latitudeEntrada)

	#Salva as coordenadas na string 'coordenadasConvertidas'
	coordenadasConvertidas += str(coordenadaConvertidaX)
	coordenadasConvertidas += ','
	coordenadasConvertidas += str(coordenadaConvertidaY)
	#Se não for a última linha
	if indice + 1 != len(coordenadas):
		coordenadasConvertidas += '\n'

	#Zera latitude e longitude
	latitudeEntrada = ""
	longitudeEntrada = ""
	coordenadaConvertidaX = ""
	coordenadaConvertidaY = ""

	#Pula o \n se não for o último
	if indice + 1 < len(coordenadas):
		indice += 1
	else:
		break

#Inserção das coordenadas convertidas no arquivo xml de paradas e construções (facilities)

#Inicialização das variáveis
indice = 0
paradas = ""
construcoes = ""
coordenadaX = ""
coordenadaY = ""
paradasNaRota = ""
numeroDaParada = 0

#Criação das paradas e das facilities
while coordenadasConvertidas[indice] != None:
	#Percorre a coordenada X
	while coordenadasConvertidas[indice] != ',':
		coordenadaX += coordenadasConvertidas[indice]
		indice += 1
	
	#Pula a vírgula
	indice += 1

	#Percorre a coordenada Y
	while coordenadasConvertidas[indice] != '\n':
		coordenadaY += coordenadasConvertidas[indice]
		
		#A longitude está depois da latitude. Caso seja a última longitude, o loop pára
		indice += 1
		if indice == len(coordenadasConvertidas):
			break

	#Cria a construção com as coordenadas atuais
	paradas += "\t\t<stopFacility id=\"parada_{}{}\" x=\"{}\" y=\"{}\" linkRefId=\"\"/>\n".format(nomeDaRotaAtual, numeroDaParada, coordenadaX, coordenadaY)
	paradasNaRota += "\t\t\t\t<stop refId=\"parada_{}{}\" arrivalOffset=\"00:02:00\" departureOffset=\"00:02:00\"/>\n".format(nomeDaRotaAtual, numeroDaParada)
	construcoes += "\t<facility id=\"parada_{}{}\" x=\"{}\" y=\"{}\"/>\n".format(nomeDaRotaAtual, numeroDaParada, coordenadaX, coordenadaY)

	#Zera as coordenadas 
	coordenadaX = ""
	coordenadaY = ""
	numeroDaParada += 1

	#A longitude está depois da latitude. Caso seja a última longitude, o loop pára
	if indice == len(coordenadasConvertidas):
		break
	indice += 1

#Criação do cabeçalho do arquivo facilities
cabecalhoDoArquivoFacilities = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE facilities SYSTEM \"http://www.matsim.org/files/dtd/facilities_v1.dtd\">\n\n<facilities>\n"

#Criação do rodapé do arquivo xml
rodapeDoArquivoFacilities = "</facilities>"

#Criando arquivo paradas
arquivoParadas = open("paradas.xml", "w")
#Escrevendo no arquivo paradas
arquivoParadas.write(paradas)
#Fechando arquivo paradas
arquivoParadas.close()

#Criando arquivo paradas
arquivoParadasRota = open("routeProfile.xml", "w")
#Escrevendo no arquivo paradas
arquivoParadasRota.write(paradasNaRota)
#Fechando arquivo paradas
arquivoParadasRota.close()

#Criando arquivo facilities
arquivoFacilities = open("facilities.xml", "w")
#Escrevendo no arquivo facilities
arquivoFacilities.write(cabecalhoDoArquivoFacilities + construcoes + rodapeDoArquivoFacilities)
#Fechando arquivo facilities
arquivoFacilities.close()
