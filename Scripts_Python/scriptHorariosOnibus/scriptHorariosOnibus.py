#Caso exista aspas no código a ser copiado aqui, utilize este símbolo \ antes das aspas, para que estas não sejam consideradas como parte da sintaxe do python
#Ou seja, é uma forma de esconder as aspas do texto

horarios = ""

numeroDeViagens = 38

#Intervalo que o ônibus parte
intervaloDeTempo = 30
hora = 4
minuto = 0
for x in range(numeroDeViagens):
	#:02d significa que o número terá obrigatoriamente dois dígitos
	horarios += "\t\t\t\t<departure id=\"viagem{}\" departureTime=\"{:02d}:{:02d}:00\" vehicleRefId=\"veiculo1\"/>\n".format(x, hora, minuto)

	minuto += intervaloDeTempo
	if minuto >= 60:
		hora += 1
		minuto = 0

#Criando arquivo horariosOnibus
arquivoHorarios = open("horariosOnibus.xml", "w")
#Escrevendo no arquivo horariosOnibus
arquivoHorarios.write(horarios)
#Fechando arquivo horariosOnibus
arquivoHorarios.close()