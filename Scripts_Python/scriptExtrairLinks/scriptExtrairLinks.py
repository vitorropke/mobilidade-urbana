#Criando arquivo de entrada
arquivoEntrada = open("links.txt", "r")
#Salvando o texto do arquivo de entrada em uma variável 'texto'
linksEntrada = arquivoEntrada.read()
#Fechando arquivo de entrada
arquivoEntrada.close()

#Inicialização das variáveis
arestas = ""
indice = 0

#Percorre texto de entrada até o final
while linksEntrada[indice] != None:
	#Início da tag
	arestas += "\t\t\t\t<link refId=\""
	
	#Coloca as arestas no arquivo de texto até achar o tab
	while linksEntrada[indice] != '\t':
		arestas += linksEntrada[indice]
		indice += 1

	#Incrementa o linksEntrada até o final da linha se já não for o final
	if indice < len(linksEntrada):
		while linksEntrada[indice] != '\n':
			#Se o próximo caractere for o fim, pare esse loop
			if indice + 1 == len(linksEntrada):
				break
			indice += 1

	#Fim da tag
	arestas += "\"/>"

	#Incrementa o índice. Se chegar no final do texto, encerre o loop
	indice += 1
	if indice == len(linksEntrada):
		break
	arestas += '\n'

#Criando arquivo de rota
arquivoRota = open("rota.xml", "w")
#Escrevendo no arquivo de rota
arquivoRota.write(arestas)
#Fechando arquivo de rota
arquivoRota.close()
