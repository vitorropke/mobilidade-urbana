# cálculo do ponto médio da rede em uma área. Parada mais central da área

def distancia_entre_pontos(x1, y1, x2, y2):
	return ((x2 - x1) ** 2 + (y2 - y1) ** 2) ** (0.5)

with open("facilities.xml", 'r') as arquivo_entrada:
	entrada = arquivo_entrada.read()

# 4 colunas -> nome_da_parada	coordenada_x	coordenada_y	distâcia_média_para_outras_paradas
numero_paradas = entrada.count("facility")
paradas_onibus = [[0 for y in range(4)] for x in range(numero_paradas)]

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

		# cnumero_paradasoloca o nome da parada e as coordenadas na matriz
		paradas_onibus[indice_parada_atual][0] = nome_da_parada
		paradas_onibus[indice_parada_atual][1] = float(coordenada_x)
		paradas_onibus[indice_parada_atual][2] = float(coordenada_y)

		indice_parada_atual += 1

	indice_entrada += 1

# seleciona a área desejada
limite_minimo_coordenada_x = -4157618.751993
limite_minimo_coordenada_y = -579238.979244
limite_maximo_coordenada_x = -4156332.488163732
limite_maximo_coordenada_y = -580834.4833578164

# ordena máximo e mínimo se necessário
if limite_minimo_coordenada_x > limite_maximo_coordenada_x:
	limite_minimo_coordenada_x, limite_maximo_coordenada_x = limite_maximo_coordenada_x, limite_minimo_coordenada_x
if limite_minimo_coordenada_y > limite_maximo_coordenada_y:
	limite_minimo_coordenada_y, limite_maximo_coordenada_y = limite_maximo_coordenada_y, limite_minimo_coordenada_y

# percorre todas as paradas em busca daquelas que estão dentro do limite
paradas_onibus_dentro_area = []

for i in range(numero_paradas):
	if (paradas_onibus[i][1] >= limite_minimo_coordenada_x) and (paradas_onibus[i][1] <= limite_maximo_coordenada_x) and (paradas_onibus[i][2] >= limite_minimo_coordenada_y) and (paradas_onibus[i][2] <= limite_maximo_coordenada_y):
		paradas_onibus_dentro_area.append(paradas_onibus[i])

numero_paradas_dentro_area = len(paradas_onibus_dentro_area)

# calcula a distancia de cada ponto entre cada ponto
for parada_atual in paradas_onibus_dentro_area:
	somatorio = 0

	for outra_parada in paradas_onibus_dentro_area:
		somatorio += distancia_entre_pontos(parada_atual[1], parada_atual[2], outra_parada[1], outra_parada[2])

	parada_atual[3] = somatorio / (numero_paradas_dentro_area - 1)

for parada in paradas_onibus_dentro_area:
	print(parada)

# encontra a menor distâcia média entre as paradas
menor_distancia_media = paradas_onibus_dentro_area[0][3]
indice_menor_distancia_media = 0

for i in range(1, numero_paradas_dentro_area):
	if paradas_onibus_dentro_area[i][3] < menor_distancia_media:
		menor_distancia_media = paradas_onibus_dentro_area[i][3]
		indice_menor_distancia_media = i

print("\nPonto médio da área é:\t", indice_menor_distancia_media, paradas_onibus_dentro_area[indice_menor_distancia_media])
