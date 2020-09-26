import pyproj

#Entrada e saída dos modelos de projeção
#Decimal GPS
entradaProjecao = pyproj.Proj(init="epsg:4326")
#Pseudo-mercator X,Y
saidaProjecao = pyproj.Proj(init="epsg:3857")

#Arquivo de leitura
#Criando arquivo de entrada
arquivoEntrada = open("paradas.txt", "r")
#Salvando o texto do arquivo de entrada em uma variável 'texto'
coordenadas = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()
print(len(coordenadas))
#Percorre as coordenadas a serem convertidas
indice = 0
latitudeEntrada = ""
longitudeEntrada = ""
coordenadaConvertidaX = ""
coordenadaConvertidaY = ""
coordenadasConvertidas = ""

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

#Arquivo de escrita
#Criando arquivo de saída
arquivoSaida = open("paradasConvertidas.txt", "w")
#Escrevendo no arquivo de saída
arquivoSaida.write(coordenadasConvertidas)
#Fechando arquivo de saída
arquivoSaida.close()
