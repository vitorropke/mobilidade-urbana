#Caso exista aspas no código a ser copiado aqui, utilize este símbolo \ antes das aspas, para que estas não sejam consideradas como parte da sintaxe do python
#Ou seja, é uma forma de esconder as aspas do texto

veiculos = ""

numeroDeTiposDeVeiculos = 30

for x in range(numeroDeTiposDeVeiculos):
	veiculos += "<vehicle id=\"veiculo{}\" type=\"tipoDeVeiculo1\"/>\n".format(x)

#Criando arquivo horariosOnibus
arquivoVeiculos = open("veiculos.xml", "w")
#Escrevendo no arquivo horariosOnibus
arquivoVeiculos.write(veiculos)
#Fechando arquivo horariosOnibus
arquivoVeiculos.close()