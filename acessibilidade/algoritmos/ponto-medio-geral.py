# Cálculo do ponto médio da rede. Parada mais central da cidade
from pyexcel_ods import save_data
import xlsxwriter


def distancia_entre_pontos(x1, y1, x2, y2):
	return ((x2 - x1) ** 2 + (y2 - y1) ** 2) ** 0.5


with open("facilities.xml", 'r') as arquivo_entrada:
	entrada = arquivo_entrada.read()

# quatro colunas ≥ nome da parada	coordenada x	coordenada y	distâcia média para outras paradas
numero_de_paradas = entrada.count("facility")
paradas_de_onibus = [[0 for y in range(4)] for x in range(numero_de_paradas)]

# armazena as paradas do arquivo de entrada na matriz
indice_entrada = 0
indice_parada_atual = 0
substring = "facility id="
while indice_entrada < len(entrada):
	# detecta a substring
	indice_substring = 0
	while (indice_substring < len(substring)) and (entrada[indice_entrada] == substring[indice_substring]):
		indice_entrada += 1
		indice_substring += 1

	# armazena a parada na matriz
	if indice_substring == len(substring):
		indice_entrada += 1

		# armazena o nome da parada
		nome_da_parada = ""
		while entrada[indice_entrada] != '"':
			nome_da_parada += entrada[indice_entrada]
			indice_entrada += 1

		# avança para chegar na coordenada x
		indice_entrada += 5

		# armazena a coordenada x
		coordenada_x = ""
		while entrada[indice_entrada] != '"':
			coordenada_x += entrada[indice_entrada]
			indice_entrada += 1

		# avança para chegar na coordenada y
		indice_entrada += 5

		# armazena a coordenada y
		coordenada_y = ""
		while entrada[indice_entrada] != '"':
			coordenada_y += entrada[indice_entrada]
			indice_entrada += 1

		# coloca o nome da parada e as coordenadas na matriz
		paradas_de_onibus[indice_parada_atual][0] = nome_da_parada
		paradas_de_onibus[indice_parada_atual][1] = float(coordenada_x)
		paradas_de_onibus[indice_parada_atual][2] = float(coordenada_y)

		indice_parada_atual += 1

	indice_entrada += 1

# calcula a distancia de cada ponto entre cada ponto
for i in range(numero_de_paradas):
	somatorio = 0

	for j in range(numero_de_paradas):
		somatorio += distancia_entre_pontos(paradas_de_onibus[i][1], paradas_de_onibus[i][2], paradas_de_onibus[j][1],
											paradas_de_onibus[j][2])

	paradas_de_onibus[i][3] = somatorio / (numero_de_paradas - 1)

for i in range(numero_de_paradas):
	print(paradas_de_onibus[i])

# encontra a menor distâcia média entre as paradas
menor_distancia_media = paradas_de_onibus[0][3]
indice_menor_distancia_media = 0

for i in range(1, numero_de_paradas):
	if paradas_de_onibus[i][3] < menor_distancia_media:
		menor_distancia_media = paradas_de_onibus[i][3]
		indice_menor_distancia_media = i

print("\nPonto médio da rede é:\t", indice_menor_distancia_media, paradas_de_onibus[indice_menor_distancia_media])

# salva em arquivo ods
save_data("acessibilidade.ods", paradas_de_onibus)

# salva em arquivo xlsx
workbook = xlsxwriter.Workbook('acessibilidade.xlsx')
worksheet = workbook.add_worksheet()

# cabeçalho da tabela xlsx
worksheet.write(0, 0, "Nome da parada")
worksheet.write(0, 1, "Coordenada X")
worksheet.write(0, 2, "Coordenada Y")
worksheet.write(0, 3, "Distância média para outras paradas")

for i in range(numero_de_paradas):
	worksheet.write(i + 1, 0, paradas_de_onibus[i][0])
	worksheet.write(i + 1, 1, paradas_de_onibus[i][1])
	worksheet.write(i + 1, 2, paradas_de_onibus[i][2])
	worksheet.write(i + 1, 3, paradas_de_onibus[i][3])

workbook.close()
