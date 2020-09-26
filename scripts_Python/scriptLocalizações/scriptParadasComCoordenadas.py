#Arquivo de leitura
#Criando arquivo de entrada
arquivoEntrada = open("paradasConvertidas.txt", "r")
#Salvando o texto do arquivo de entrada em uma variável 'texto'
coordenadas = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

#Criação do cabeçalho do arquivo facilities
cabecalhoDoArquivoFacilities = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE facilities SYSTEM \"http://www.matsim.org/files/dtd/facilities_v1.dtd\">\n\n<facilities>\n"

#Criação da parada e da facility
indice = 0
paradas = ""
construcoes = ""
coordenadaX = ""
coordenadaY = ""
numeroDaParada = 0

while coordenadas[indice] != None:
	#Percorre a coordenada X
	while coordenadas[indice] != ',':
		coordenadaX += coordenadas[indice]
		indice += 1
	
	#Pula a vírgula
	indice += 1

	#Percorre a coordenada Y
	while coordenadas[indice] != '\n':
		coordenadaY += coordenadas[indice]
		
		#A longitude está depois da latitude. Caso seja a última longitude, o loop pára
		indice += 1
		if indice == len(coordenadas):
			break

	#Cria a construção com as coordenadas atuais
	paradas += "\t\t<stopFacility id=\"paradaAB_UNIFICADO_IDA{}\" x=\"{}\" y=\"{}\" linkRefId=\"\"/>\n".format(numeroDaParada, coordenadaX, coordenadaY)
	construcoes += "\t<facility id=\"paradaAB_UNIFICADO_IDA{}\" x=\"{}\" y=\"{}\"/>\n".format(numeroDaParada, coordenadaX, coordenadaY)

	#Zera as coordenadas 
	coordenadaX = ""
	coordenadaY = ""
	numeroDaParada += 1

	#A longitude está depois da latitude. Caso seja a última longitude, o loop pára
	if indice == len(coordenadas):
		break
	indice += 1

#Criação do rodapé do arquivo xml
rodapeDoArquivoFacilities = "</facilities>"

#Criando arquivo paradas
arquivoParadas = open("paradas.xml", "w")
#Escrevendo no arquivo paradas
arquivoParadas.write(paradas)
#Fechando arquivo paradas
arquivoParadas.close()

#Criando arquivo facilities
arquivoFacilities = open("facilities.xml", "w")
#Escrevendo no arquivo facilities
arquivoFacilities.write(cabecalhoDoArquivoFacilities + construcoes + rodapeDoArquivoFacilities)
#Fechando arquivo facilities
arquivoFacilities.close()