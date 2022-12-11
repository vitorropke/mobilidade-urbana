"""
Converte um arquivo .json para uma lista
"""
import json
import pandas as pd

with open("entradas/distacia_tempo_viagem_paradas_google_maps.json") as entrada:
    lista_json = json.load(entrada)

distancias_paradas = []
tempos_paradas = []

cabecalho_saida = []

coordenadas = []

for parada_origem in lista_json:
    lista_distancias_parada_origem_atual = []
    lista_tempos_parada_origem_atual = []

    for parada_destino in parada_origem["calculo"]:
        lista_distancias_parada_origem_atual.append(parada_destino["distance"]["value"])
        lista_tempos_parada_origem_atual.append(parada_destino["duration"]["value"])

    distancias_paradas.append(lista_distancias_parada_origem_atual)
    tempos_paradas.append(lista_tempos_parada_origem_atual)

    cabecalho_saida.append(parada_origem["id"])

    coordenadas.append([parada_origem["lat"], parada_origem["lon"]])

# salva as distâncias em um csv com cabeçalho
tabela_distancias = pd.DataFrame(distancias_paradas, columns=cabecalho_saida, index=cabecalho_saida)
tabela_tempos = pd.DataFrame(tempos_paradas, columns=cabecalho_saida, index=cabecalho_saida)
tabela_coordenadas = pd.DataFrame(coordenadas, columns=["Latitude", "Longitude"], index=cabecalho_saida)

tabela_distancias.to_csv("saidas/Distâncias entre as paradas.csv")
tabela_tempos.to_csv("saidas/Tempo entre as paradas.csv")
tabela_coordenadas.to_csv("saidas/Paradas com coordenadas.csv")

"""
# sem cabeçalho
tabela_distancias = pd.DataFrame(distancias_paradas)
tabela_tempos = pd.DataFrame(tempos_paradas)

tabela_distancias.to_csv("saidas/Distâncias entre as paradas.csv", header=False, index=False)
tabela_tempos.to_csv("saidas/Tempos entre as paradas.csv", header=False, index=False)
"""
