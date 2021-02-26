import random

#Caso exista aspas no código a ser copiado aqui, utilize este símbolo \ antes das aspas, para que estas não sejam consideradas como parte da sintaxe do python
#Ou seja, é uma forma de esconder as aspas do texto
#Criação do cabeçalho do arquivo facilities
cabecalhoDoArquivoFacilities = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE facilities SYSTEM \"http://www.matsim.org/files/dtd/facilities_v1.dtd\">\n\n<facilities>"

construcoes = ""

#Defina o número de pontos que desejas
numeroPontos = 100

for x in range(numeroPontos):
	#Longitude
	coordenadaXAleatoria = random.randrange(-4164462, -4148877)
	#Latitude
	coordenadaYAleatoria = random.randrange(-587482, -569598)

	#Tag da construção
	construcoes += "\n\t<facility id=\"casaPessoa{}\" x=\"{}\" y=\"{}\"/>".format(x, coordenadaXAleatoria, coordenadaYAleatoria)

#Criação do rodapé do arquivo xml
rodapeDoArquivoFacilities = "\n</facilities>"

#Criando arquivo facilities
arquivoFacilities = open("facilities.xml", "w")
#Escrevendo no arquivo facilities
arquivoFacilities.write(cabecalhoDoArquivoFacilities + construcoes + rodapeDoArquivoFacilities)
#Fechando arquivo facilities
arquivoFacilities.close()
