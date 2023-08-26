"""
Equação do artigo 'Índice de acessibilidade para comparação dos modos de transporte privado e coletivo' das autoras.
Lílian dos Santos Fontes Pereira Bracarense e Jéssica Oliveira Nunes Ferreira.

Disponível nas seguintes URLs:
https://www.scielo.br/j/urbe/a/jypKpfGB6TwpXkYNMjSySLx/abstract/?lang=pt
https://www.redalyc.org/articulo.oa?id=193157811008
https://pucpr.emnuvens.com.br/Urbe/article/view/23183/23057

A equação está localizada na página 607 (página 8 do arquivo) na sessão de resultados

      n
Aij = ∑ (1 - (Tij - Tmin) / (Tmax - Tmin))
      j

Aij: é o índice de acessibilidade do nó i para os destinos j;
Tij: é o tempo de viagem do nó i para o destino j, em minutos;
Tmax: é o máximo tempo de viagem de i até j para o qual a viagem pode ser considerada viável para o usuário, em minutos;
Tmin: é o menor tempo de viagem de i até j encontrado para a rede de transportes analisada, em minutos.

Quanto maior o número, mais acessível.
"""
import pandas as pd


def timestamp_to_minutes(timestamp):
    """
    Converte o tempo de HH:MM:SS para minutos.
    :param timestamp: str
    :return: int
    """
    return int(timestamp[0] + timestamp[1]) * 60 + int(timestamp[3] + timestamp[4]) + int(
        timestamp[6] + timestamp[7]) / 60


def formula_acessibilidade(tempo_viagem, tempo_maximo, tempo_minimo):
    """
    Calcula a acessibilidade usando a fórmula.

    :param tempo_viagem: float
    :param tempo_maximo: float
    :param tempo_minimo: float
    :return: float
    """
    # evita divisão por 0
    if tempo_maximo == tempo_minimo:
        return 1 - (tempo_viagem - tempo_minimo)
    else:
        return 1 - (tempo_viagem - tempo_minimo) / (tempo_maximo - tempo_minimo)


parte_do_diretorio_entrada_saida = "output-peak-hours"
colunas_usadas = ["person", "trav_time", "wait_time", "modes"]
tabela_viagens = pd.read_csv("entradas/1-output-trips/" + parte_do_diretorio_entrada_saida + "/output_trips.csv",
                             sep=';', usecols=colunas_usadas)

# elimina todas as viagens com o modo 'walk'
tabela_viagens = tabela_viagens[tabela_viagens.modes != "walk"]
# remove a coluna 'walk'
tabela_viagens = tabela_viagens.drop("modes", axis=1)

# muda os tempos de HH:MM:SS para minutos
tabela_viagens["trav_time"] = tabela_viagens["trav_time"].apply(timestamp_to_minutes)
tabela_viagens["wait_time"] = tabela_viagens["wait_time"].apply(timestamp_to_minutes)

# remove os tempos muito grandes (outliers superiores)
# corta 25% das maiores viagens
tabela_viagens = tabela_viagens[tabela_viagens["trav_time"] < tabela_viagens["trav_time"].quantile(.75)]
# corta 25% das maiores esperas
tabela_viagens = tabela_viagens[tabela_viagens["wait_time"] < tabela_viagens["wait_time"].quantile(.75)]
# remove a coluna 'wait_time'
tabela_viagens = tabela_viagens.drop("wait_time", axis=1)

# remove os nove últimos caracteres de cada pessoa para poder agrupá-las
tabela_viagens["person"] = tabela_viagens["person"].str[:-9]

# ordena a tabela de viagens por ordem alfabética na coluna 'person'
tabela_viagens = tabela_viagens.sort_values("person").reset_index()

# encontra os tempos máximos e mínimos de viagem de cada rota
tabela_maior_tempo_viagem_rota = tabela_viagens.groupby("person", as_index=False).max().rename(
    columns={"trav_time": "Maior tempo de viagem"})
tabela_menor_tempo_viagem_rota = tabela_viagens.groupby("person", as_index=False).min().rename(
    columns={"trav_time": "Menor tempo de viagem"})

# calcula a acessibilidade de cada rota
tabela_acessibilidade_rota = []
acessibilidade = 0
rota_atual = 0

for indice_viagem, viagem_atual in tabela_viagens.iterrows():
    acessibilidade += formula_acessibilidade(viagem_atual["trav_time"],
                                             tabela_maior_tempo_viagem_rota.iat[rota_atual, 2],
                                             tabela_menor_tempo_viagem_rota.iat[rota_atual, 2])

    if ((indice_viagem + 1) == len(tabela_viagens)) or (
            tabela_viagens.at[indice_viagem + 1, "person"] != tabela_viagens.at[indice_viagem, "person"]):
        tabela_acessibilidade_rota.append([tabela_viagens.at[indice_viagem, "person"], acessibilidade])

        acessibilidade = 0
        rota_atual += 1

# cria uma tabela de saida usando a tabela de acessibilidade definindo o nome das colunas
tabela_saida = pd.DataFrame(tabela_acessibilidade_rota, columns=["Nome da rota", "Acessibilidade da rota"])

# remove as acessibilidades muito grandes (outliers superiores)
# corta 25% das maiores acessibilidades
tabela_saida = tabela_saida[
    tabela_saida["Acessibilidade da rota"] < tabela_saida["Acessibilidade da rota"].quantile(.75)]

# salva algumas informações estatísticas da tabela de acessibilidades
with open(
        "saidas/4-bracarense-e-ferreira/" + parte_do_diretorio_entrada_saida + "/estatisticas-gerais-de"
                                                                               "-acessibilidade.txt",
        'w') as arquivo_saida:
    saida = ""

    saida += f"Acessibilidade média: {tabela_saida['Acessibilidade da rota'].mean()}\n"
    saida += f"Mediana da acessibilidade: {tabela_saida['Acessibilidade da rota'].median()}\n\n"

    saida += f"Maior valor de acessibilidade: \n{tabela_saida.loc[tabela_saida['Acessibilidade da rota'].idxmax()]}\n\n"
    saida += f"Menor valor de acessibilidade: \n{tabela_saida.loc[tabela_saida['Acessibilidade da rota'].idxmin()]}\n"

    arquivo_saida.write(saida)

# ordena a tabela pela acessibilidade da rota
tabela_saida = tabela_saida.sort_values("Acessibilidade da rota")

# salva em um arquivo csv
tabela_saida.to_csv(
    "saidas/4-bracarense-e-ferreira/" + parte_do_diretorio_entrada_saida + "/acessibilidade-por-rota.csv",
    index=False)
