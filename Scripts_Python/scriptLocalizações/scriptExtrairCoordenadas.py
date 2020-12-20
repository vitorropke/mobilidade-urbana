#Criando arquivo de entrada
arquivoEntrada = open("AB UNIFICADO IDA - IDA", "r")
#Salvando o texto do arquivo de entrada em uma variável 'texto'
texto = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

coordenadas = ""
indice = 0
#percorre texto até o final
while texto[indice] != None:
	#Se achar a string 'lat' comece a salvar na variável coordenadas até achar o fechamento de chaves '}'
	if 'l' == texto[indice]:
		indice += 1
		if 'a' == texto[indice]:
			indice += 1
			if 't' == texto[indice]:
				#índice + 3 para pular a letra 't' e os símbolos '"' e ':'
				indice += 3
				while texto[indice] != '}':
					coordenadas += texto[indice]
					indice += 1

					#Se encontrar a parte 'lng', pule 6 dígitos até o início da coordenada na longitude
					if texto[indice] == '"':
						indice += 6
				
				coordenadas += '\n'
	
	#Incrementa o índice
	indice += 1
	if indice == len(texto):
		break

#Criando arquivo de saída
arquivoSaida = open("paradas.txt", "w")
#Escrevendo no arquivo de saída
arquivoSaida.write(coordenadas)
#Fechando arquivo de saída
arquivoSaida.close()
