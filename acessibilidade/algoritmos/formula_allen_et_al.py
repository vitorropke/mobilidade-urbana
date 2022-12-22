"""
Indicadores de separação espacial. Acessibilidade de cada rota.

Allen et al. (1993)

     N
Ai = ∑Cij / (N - 1)
     j

Ai: Acessibilidade da zona i;
N: Número de pontos utilizados no cálculo;
Cij: Custo despendido para se deslocar entre as zonas i e j

Quanto maior o número, menos acessível
"""
import pandas as pd


def timestamp_to_seconds(timestamp):
    """
    Converte o tempo de HH:MM:SS para segundos.
    :param timestamp:
    :return:
    """
    return int(timestamp[0] + timestamp[1]) * 3600 + int(timestamp[3] + timestamp[4]) * 60 + int(
        timestamp[6] + timestamp[7])


colunas_usadas = ["person", "Mediana do tempo de viagem"]
tabela_tempos = pd.read_csv("dados-processados/Estatísticas de cada rota.csv", index_col=0, usecols=colunas_usadas)

# muda os tempos de HH:MM:SS para segundos
tabela_tempos["Mediana do tempo de viagem"] = tabela_tempos["Mediana do tempo de viagem"].apply(timestamp_to_seconds)

# divide pelo número de paradas
tabela_saida = tabela_tempos.div(len(tabela_tempos) - 1)

# muda o nome da coluna
tabela_saida = tabela_saida.rename(columns={"Mediana do tempo de viagem": "Acessibilidade da rota"})

# salva as informações máximas e mínimas
with open("dados-processados/Estatísticas da acessibilidade integral - Allen et al.txt", 'w') as arquivo_saida:
    saida = ""

    saida += f"Acessibilidade média: {tabela_saida['Acessibilidade da rota'].mean()}\n"
    saida += f"Mediana da acessibilidade: {tabela_saida['Acessibilidade da rota'].median()}\n\n"

    saida += f"Maior valor de acessibilidade\n{tabela_saida.loc[tabela_saida['Acessibilidade da rota'].idxmax()]}\n\n"
    saida += f"Menor valor de acessibilidade\n{tabela_saida.loc[tabela_saida['Acessibilidade da rota'].idxmin()]}\n"

    arquivo_saida.write(saida)

# ordena a tabela pela acessibilidade da rota
tabela_saida = tabela_saida.sort_values("Acessibilidade da rota")

# salva em um arquivo csv
tabela_saida.to_csv("dados-processados/Acessibilidade integral - Allen et al.csv")
