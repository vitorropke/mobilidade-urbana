"""
Todos os seguintes cálculos consideram apenas viagens com, no mínimo, uma viagem com transporte público

Calcula o tempo médio de viagem de todas as viagens juntas
Calcula o tempo médio de espera de todas as viagens juntas
Obtém o maior e menor tempo de viagem de todas as viagens juntas
Obtém o maior e menor tempo de espera de todas as viagens juntas

Calcula o tempo médio de viagem de cada rota
Calcula o tempo médio de espera de cada rota
Obtém o maior e menor tempo de viagem da rota
Obtém o maior e menor tempo de espera da rota
"""
import pandas as pd
import time


def timestamp_to_seconds(timestamp):
    """
    Converte o tempo de HH:MM:SS para segundos.
    :param timestamp: str
    :return: int
    """
    return int(timestamp[0] + timestamp[1]) * 3600 + int(timestamp[3] + timestamp[4]) * 60 + int(
        timestamp[6] + timestamp[7])


def seconds_to_timestamp(seconds):
    """
    Converte o tempo de segundos para HH:MM:SS.
    :param seconds: int
    :return: str
    """
    return time.strftime("%H:%M:%S", time.gmtime(seconds))


colunas_usadas = ["person", "dep_time", "trav_time", "wait_time", "modes"]
tabela_viagens = pd.read_csv("entradas/output_trips.csv", sep=';', usecols=colunas_usadas)

# elimina todas as viagens com o modo 'walk'
tabela_viagens = tabela_viagens[tabela_viagens.modes != "walk"]

# muda os tempos de HH:MM:SS para segundos
tabela_viagens["trav_time"] = tabela_viagens["trav_time"].apply(timestamp_to_seconds)
tabela_viagens["wait_time"] = tabela_viagens["wait_time"].apply(timestamp_to_seconds)

# remove os tempos muito grandes (outliers superiores)
tabela_viagens = tabela_viagens[tabela_viagens["trav_time"] < tabela_viagens["trav_time"].quantile(.75)]
tabela_viagens = tabela_viagens[tabela_viagens["wait_time"] < tabela_viagens["wait_time"].quantile(.75)]

# obtém informações gerais
tempo_medio_viagem = seconds_to_timestamp(tabela_viagens["trav_time"].mean())
tempo_medio_espera = seconds_to_timestamp(tabela_viagens["wait_time"].mean())

mediana_tempo_viagem = seconds_to_timestamp(tabela_viagens["trav_time"].median())
mediana_tempo_espera = seconds_to_timestamp(tabela_viagens["wait_time"].median())

moda_tempo_viagem = seconds_to_timestamp(tabela_viagens["trav_time"].mode()[0])
moda_tempo_espera = seconds_to_timestamp(tabela_viagens["wait_time"].mode()[0])

indice_maior_tempo_viagem = tabela_viagens["trav_time"].idxmax()
indice_maior_tempo_espera = tabela_viagens["wait_time"].idxmax()

indice_menor_tempo_viagem = tabela_viagens["trav_time"].idxmin()
indice_menor_tempo_espera = tabela_viagens["wait_time"].idxmin()

# remove os nove últimos caracteres de cada pessoa para poder agrupá-las
tabela_viagens["person"] = tabela_viagens["person"].str[:-9]

# agrupa as viagens para rotas com médias, medianas, máximos e mínimos
tabela_rotas_media = tabela_viagens.groupby("person").mean().rename(
    columns={"trav_time": "Tempo médio de viagem", "wait_time": "Tempo médio de espera"})
tabela_rotas_mediana = tabela_viagens.groupby("person").median().rename(
    columns={"trav_time": "Mediana do tempo de viagem", "wait_time": "Mediana do tempo de espera"})

maior_tempo_viagem_rota = tabela_viagens.groupby("person")["trav_time"].max().rename(
    "Viagem mais demorada")
menor_tempo_viagem_rota = tabela_viagens.groupby("person")["trav_time"].min().rename(
    "Viagem mais curta")

maior_tempo_espera_rota = tabela_viagens.groupby("person")["wait_time"].max().rename(
    "Viagem com mais espera")
menor_tempo_espera_rota = tabela_viagens.groupby("person")["wait_time"].min().rename(
    "Viagem com menos espera")

tabela_rotas_quantidade = tabela_viagens.groupby("person")["person"].count().rename("Quantidade de viagens")

tabela_saida = pd.concat(
    [tabela_rotas_media, tabela_rotas_mediana, maior_tempo_viagem_rota, menor_tempo_viagem_rota,
     maior_tempo_espera_rota, menor_tempo_espera_rota, tabela_rotas_quantidade], axis=1)

# obtém informações gerais das rotas
indice_maior_media_viagem = tabela_saida["Tempo médio de viagem"].idxmax()
indice_maior_media_espera = tabela_saida["Tempo médio de espera"].idxmax()

indice_menor_media_viagem = tabela_saida["Tempo médio de viagem"].idxmin()
indice_menor_media_espera = tabela_saida["Tempo médio de espera"].idxmin()

indice_maior_mediana_viagem = tabela_saida["Mediana do tempo de viagem"].idxmax()
indice_maior_mediana_espera = tabela_saida["Mediana do tempo de espera"].idxmax()

indice_menor_mediana_viagem = tabela_saida["Mediana do tempo de viagem"].idxmin()
indice_menor_mediana_espera = tabela_saida["Mediana do tempo de espera"].idxmin()

# muda os tempos de segundos para HH:MM:SS
tabela_viagens["trav_time"] = tabela_viagens["trav_time"].apply(seconds_to_timestamp)
tabela_viagens["wait_time"] = tabela_viagens["wait_time"].apply(seconds_to_timestamp)

tabela_saida["Tempo médio de viagem"] = tabela_saida["Tempo médio de viagem"].apply(seconds_to_timestamp)
tabela_saida["Tempo médio de espera"] = tabela_saida["Tempo médio de espera"].apply(seconds_to_timestamp)

tabela_saida["Mediana do tempo de viagem"] = tabela_saida["Mediana do tempo de viagem"].apply(seconds_to_timestamp)
tabela_saida["Mediana do tempo de espera"] = tabela_saida["Mediana do tempo de espera"].apply(seconds_to_timestamp)

tabela_saida["Viagem mais demorada"] = tabela_saida["Viagem mais demorada"].apply(seconds_to_timestamp)
tabela_saida["Viagem mais curta"] = tabela_saida["Viagem mais curta"].apply(seconds_to_timestamp)

tabela_saida["Viagem com mais espera"] = tabela_saida["Viagem com mais espera"].apply(seconds_to_timestamp)
tabela_saida["Viagem com menos espera"] = tabela_saida["Viagem com menos espera"].apply(seconds_to_timestamp)

# salva as estatísticas das rotas no csv
tabela_saida.to_csv("saidas/Estatísticas de cada rota.csv")

# salva as informações gerais em um txt
with open("saidas/Estatísticas gerais.txt", 'w') as arquivo_saida:
    saida = ""

    saida += f"Tempo médio de viagem: {tempo_medio_viagem}\n"
    saida += f"Tempo médio de espera: {tempo_medio_espera}\n"

    saida += f"Mediana do tempo de viagem: {mediana_tempo_viagem}\n"
    saida += f"Mediana do tempo de espera: {mediana_tempo_espera}\n"

    saida += f"Moda do tempo de viagem: {moda_tempo_viagem}\n"
    saida += f"Moda do tempo de espera: {moda_tempo_espera}\n\n"

    saida += f"\nViagem mais demorada\n{tabela_viagens.loc[indice_maior_tempo_viagem]}\n"
    saida += f"\nViagem com mais espera\n{tabela_viagens.loc[indice_maior_tempo_espera]}\n"

    saida += f"\nViagem mais curta\n{tabela_viagens.loc[indice_menor_tempo_viagem]}\n"
    saida += f"\nViagem com menos espera\n{tabela_viagens.loc[indice_menor_tempo_espera]}\n"

    arquivo_saida.write(saida)

# salva as informações gerais de cada rota em um txt
with open("saidas/Estatísticas gerais de cada rota.txt", 'w') as arquivo_saida:
    saida = ""

    saida += f"Maior média de viagem\n\n{tabela_saida.loc[indice_maior_media_viagem]}\n\n\n"
    saida += f"Maior média de espera\n\n{tabela_saida.loc[indice_maior_media_espera]}\n\n\n"

    saida += f"Menor média de viagem\n\n{tabela_saida.loc[indice_menor_media_viagem]}\n\n\n"
    saida += f"Menor média de espera\n\n{tabela_saida.loc[indice_menor_media_espera]}\n\n\n"

    saida += f"Maior mediana de viagem\n\n{tabela_saida.loc[indice_maior_mediana_viagem]}\n\n\n"
    saida += f"Maior mediana de espera\n\n{tabela_saida.loc[indice_maior_mediana_espera]}\n\n\n"

    saida += f"Menor mediana de viagem\n\n{tabela_saida.loc[indice_menor_mediana_viagem]}\n\n\n"
    saida += f"Menor mediana de espera\n\n{tabela_saida.loc[indice_menor_mediana_espera]}\n"

    arquivo_saida.write(saida)
