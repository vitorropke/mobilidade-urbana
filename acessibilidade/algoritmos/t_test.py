"""
Obtém o gráfico e as informações dos dados dos cenários padrão, dobro e metade
"""
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import numpy as np
from pylab import rcParams


def timestamp_to_seconds(timestamp):
    """
    Converte o tempo de HH:MM:SS para segundos.
    :param timestamp: str
    :return: int
    """
    return int(timestamp[0] + timestamp[1]) * 3600 + int(timestamp[3] + timestamp[4]) * 60 + int(
        timestamp[6] + timestamp[7])


def plot_distribution(coluna_tabela):
    """
    Mostra a distribuição dos valores da coluna da tabela
    :param coluna_tabela: coluna de uma tabela contendo vários valores inteiros ou reais
    :type coluna_tabela: pandas.DataFrame
    :return: Figura no modelo plot
    :rtype: matplotlib.pyplot.figure
    """
    plt.figure()
    ax = sns.displot(coluna_tabela)
    plt.axvline(np.mean(coluna_tabela), color='k', linestyle="dashed", linewidth=5)
    _, max_ = plt.ylim()
    plt.text(coluna_tabela.mean() + coluna_tabela.mean() / 10, max_ - max_ / 10,
             "Mean: {:.2f}".format(coluna_tabela.mean()))

    return plt.figure()


# define algumas configurações para mostrar figuras do seaborn
rcParams["figure.figsize"] = 20, 10
rcParams["font.size"] = 30
sns.set()

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

plot_distribution(tabela_padrao[coluna_usada])
plot_distribution(tabela_dobro[coluna_usada])
plot_distribution(tabela_metade[coluna_usada])

plt.figure()

ax1 = sns.displot(tabela_padrao[coluna_usada])
ax2 = sns.displot(tabela_dobro[coluna_usada])
ax3 = sns.displot(tabela_metade[coluna_usada])

plt.axvline(np.mean(tabela_padrao[coluna_usada]), color='r', linestyle="dashed", linewidth=5, label="Padrão")
plt.axvline(np.mean(tabela_dobro[coluna_usada]), color='g', linestyle="dashed", linewidth=5, label="Dobro")
plt.axvline(np.mean(tabela_metade[coluna_usada]), color='b', linestyle="dashed", linewidth=5, label="Metade")

plt.legend(loc="upper left")
plt.savefig(f"saidas/{coluna_usada}", dpi=200)
