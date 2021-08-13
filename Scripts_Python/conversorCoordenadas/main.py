#Biblioteca de conversão de coordenadas
from pyproj import Proj, transform

#Lendo arquivo de entrada
arquivoEntrada = open("facilities.xml", "r")
#Salvando o texto do arquivo de entrada em uma variável 'texto'
textoParadas = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

strSaida = ""

#Inicializa latitude e longitude
latitudeConvertida = ""
longitudeConvertida = ""
entradaX = ""
entradaY = ""

#Conversão das coordenadas 
#Entrada e saída dos modelos de projeção
#Pseudo-mercator X,Y
entradaProjecao = Proj(init="epsg:3857")
#Decimal GPS
saidaProjecao = Proj(init="epsg:4326")

indice = 0
# Percorre texto de entrada até o final
while textoParadas[indice] != None:
	# Se encontrar o padrão 'refId:' substituir pelo horário do vetor horarioParada
	if textoParadas[indice] == 'x' and textoParadas[indice + 1] == '=' and textoParadas[indice + 2] == '\"':
		strSaida += "lat=\""
		indice += 3
		
		while textoParadas[indice] != "\"":
			entradaX += textoParadas[indice]
			indice += 1

		indice += 5

		while textoParadas[indice] != "\"":
			entradaY += textoParadas[indice]
			indice += 1

		#Converte as coordenadas
		longitudeConvertida, latitudeConvertida = transform(entradaProjecao, saidaProjecao, entradaX, entradaY)

		print(latitudeConvertida, " ", longitudeConvertida)

		#Salva as coordenadas na string saída
		strSaida += str(latitudeConvertida)
		strSaida += "\" lon=\""
		strSaida += str(longitudeConvertida)

		#Zera latitude e longitude
		latitudeConvertida = ""
		longitudeConvertida = ""
		entradaX = ""
		entradaY = ""

	if indice != len(textoParadas):
		strSaida += textoParadas[indice]

	#Incrementa o índice. Se for o última linha o loop, pára
	indice += 1
	if indice == len(textoParadas):
		break

#Criando arquivo facilities
arquivoFacilities = open("facilities2.xml", "w")
#Escrevendo no arquivo facilities
arquivoFacilities.write(strSaida)
#Fechando arquivo facilities
arquivoFacilities.close()
