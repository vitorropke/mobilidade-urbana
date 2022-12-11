"""
Gera um gráfico de caixa de um arquivo csv que contém dados estatísticos
"""
import matplotlib.pyplot as plt
import pandas as pd


def timestamp_to_seconds(timestamp):
    """
    Converte o tempo de HH:MM:SS para segundos.
    :param timestamp: str
    :return: int
    """
    return int(timestamp[0] + timestamp[1]) * 3600 + int(timestamp[3] + timestamp[4]) * 60 + int(
        timestamp[6] + timestamp[7])


tabela_padrao = pd.read_csv("entradas/Estatísticas de cada rota - padrão.csv")
tabela_dobro = pd.read_csv("entradas/Estatísticas de cada rota - dobro.csv")
tabela_metade = pd.read_csv("entradas/Estatísticas de cada rota - metade.csv")

# muda os tempos de HH:MM:SS para segundos
tabela_padrao["Tempo médio de viagem"] = tabela_padrao["Tempo médio de viagem"].apply(timestamp_to_seconds)
tabela_padrao["Tempo médio de espera"] = tabela_padrao["Tempo médio de espera"].apply(timestamp_to_seconds)

tabela_padrao["Mediana do tempo de viagem"] = tabela_padrao["Mediana do tempo de viagem"].apply(timestamp_to_seconds)
tabela_padrao["Mediana do tempo de espera"] = tabela_padrao["Mediana do tempo de espera"].apply(timestamp_to_seconds)

tabela_padrao["Viagem mais demorada"] = tabela_padrao["Viagem mais demorada"].apply(timestamp_to_seconds)
tabela_padrao["Viagem mais curta"] = tabela_padrao["Viagem mais curta"].apply(timestamp_to_seconds)

tabela_padrao["Viagem com mais espera"] = tabela_padrao["Viagem com mais espera"].apply(timestamp_to_seconds)
tabela_padrao["Viagem com menos espera"] = tabela_padrao["Viagem com menos espera"].apply(timestamp_to_seconds)
# ----------------------------------------------------------------------------------------------------------------------
tabela_dobro["Tempo médio de viagem"] = tabela_dobro["Tempo médio de viagem"].apply(timestamp_to_seconds)
tabela_dobro["Tempo médio de espera"] = tabela_dobro["Tempo médio de espera"].apply(timestamp_to_seconds)

tabela_dobro["Mediana do tempo de viagem"] = tabela_dobro["Mediana do tempo de viagem"].apply(timestamp_to_seconds)
tabela_dobro["Mediana do tempo de espera"] = tabela_dobro["Mediana do tempo de espera"].apply(timestamp_to_seconds)

tabela_dobro["Viagem mais demorada"] = tabela_dobro["Viagem mais demorada"].apply(timestamp_to_seconds)
tabela_dobro["Viagem mais curta"] = tabela_dobro["Viagem mais curta"].apply(timestamp_to_seconds)

tabela_dobro["Viagem com mais espera"] = tabela_dobro["Viagem com mais espera"].apply(timestamp_to_seconds)
tabela_dobro["Viagem com menos espera"] = tabela_dobro["Viagem com menos espera"].apply(timestamp_to_seconds)
# ----------------------------------------------------------------------------------------------------------------------
tabela_metade["Tempo médio de viagem"] = tabela_metade["Tempo médio de viagem"].apply(timestamp_to_seconds)
tabela_metade["Tempo médio de espera"] = tabela_metade["Tempo médio de espera"].apply(timestamp_to_seconds)

tabela_metade["Mediana do tempo de viagem"] = tabela_metade["Mediana do tempo de viagem"].apply(timestamp_to_seconds)
tabela_metade["Mediana do tempo de espera"] = tabela_metade["Mediana do tempo de espera"].apply(timestamp_to_seconds)

tabela_metade["Viagem mais demorada"] = tabela_metade["Viagem mais demorada"].apply(timestamp_to_seconds)
tabela_metade["Viagem mais curta"] = tabela_metade["Viagem mais curta"].apply(timestamp_to_seconds)

tabela_metade["Viagem com mais espera"] = tabela_metade["Viagem com mais espera"].apply(timestamp_to_seconds)
tabela_metade["Viagem com menos espera"] = tabela_metade["Viagem com menos espera"].apply(timestamp_to_seconds)

coluna_usada = "Viagem com menos espera"

tabelas = pd.concat([tabela_padrao[coluna_usada], tabela_dobro[coluna_usada],
                     tabela_metade[coluna_usada]], axis=1)

tabelas.columns = ["Padrão", "Dobro", "Metade"]

grafico_caixa = plt.subplots()

tabelas.boxplot(vert=False)

plt.title(coluna_usada)
plt.xlabel("Tempo em segundos")
plt.ylabel("Cenários")
plt.savefig(f"saidas/{coluna_usada}", dpi=200)
#plt.show()
