#Criação do cabeçalho do arquivo facilities
cabecalhoDoArquivoFacilities = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE facilities SYSTEM \"http://www.matsim.org/files/dtd/facilities_v1.dtd\">\n\n<facilities>\n"

construcoes = ""
paradas = ""
coordenadaX = 0
coordenadaY = 0

numeroDeParadas = 31

for x in range(numeroDeParadas):
	paradas += "\t\t<stopFacility id=\"paradaAB_UNIFICADO_IDA{}\" x=\"{}\" y=\"{}\" linkRefId=\"\"/>\n".format(x, coordenadaX, coordenadaY)
	construcoes += "\t<facility id=\"paradaAB_UNIFICADO_IDA{}\" x=\"{}\" y=\"{}\"/>\n".format(x, coordenadaX, coordenadaY)

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