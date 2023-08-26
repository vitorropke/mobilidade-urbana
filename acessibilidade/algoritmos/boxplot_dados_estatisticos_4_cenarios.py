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


tabela_padrao = pd.read_csv("entradas/2-dados-estatisticos/output-one-per-hour/estatisticas-gerais-de-cada-rota.csv")
tabela_hora_pico = pd.read_csv("entradas/2-dados-estatisticos/output-peak-hours/estatisticas-gerais-de-cada-rota.csv")
tabela_meia_hora = pd.read_csv(
    "entradas/2-dados-estatisticos/output-one-per-half-hour/estatisticas-gerais-de-cada-rota.csv")
tabela_minuto = pd.read_csv("entradas/2-dados-estatisticos/output-one-per-minute/estatisticas-gerais-de-cada-rota.csv")

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
tabela_hora_pico["Tempo médio de viagem"] = tabela_hora_pico["Tempo médio de viagem"].apply(timestamp_to_seconds)
tabela_hora_pico["Tempo médio de espera"] = tabela_hora_pico["Tempo médio de espera"].apply(timestamp_to_seconds)

tabela_hora_pico["Mediana do tempo de viagem"] = tabela_hora_pico["Mediana do tempo de viagem"].apply(
    timestamp_to_seconds)
tabela_hora_pico["Mediana do tempo de espera"] = tabela_hora_pico["Mediana do tempo de espera"].apply(
    timestamp_to_seconds)

tabela_hora_pico["Viagem mais demorada"] = tabela_hora_pico["Viagem mais demorada"].apply(timestamp_to_seconds)
tabela_hora_pico["Viagem mais curta"] = tabela_hora_pico["Viagem mais curta"].apply(timestamp_to_seconds)

tabela_hora_pico["Viagem com mais espera"] = tabela_hora_pico["Viagem com mais espera"].apply(timestamp_to_seconds)
tabela_hora_pico["Viagem com menos espera"] = tabela_hora_pico["Viagem com menos espera"].apply(timestamp_to_seconds)
# ----------------------------------------------------------------------------------------------------------------------
tabela_meia_hora["Tempo médio de viagem"] = tabela_meia_hora["Tempo médio de viagem"].apply(timestamp_to_seconds)
tabela_meia_hora["Tempo médio de espera"] = tabela_meia_hora["Tempo médio de espera"].apply(timestamp_to_seconds)

tabela_meia_hora["Mediana do tempo de viagem"] = tabela_meia_hora["Mediana do tempo de viagem"].apply(
    timestamp_to_seconds)
tabela_meia_hora["Mediana do tempo de espera"] = tabela_meia_hora["Mediana do tempo de espera"].apply(
    timestamp_to_seconds)

tabela_meia_hora["Viagem mais demorada"] = tabela_meia_hora["Viagem mais demorada"].apply(timestamp_to_seconds)
tabela_meia_hora["Viagem mais curta"] = tabela_meia_hora["Viagem mais curta"].apply(timestamp_to_seconds)

tabela_meia_hora["Viagem com mais espera"] = tabela_meia_hora["Viagem com mais espera"].apply(timestamp_to_seconds)
tabela_meia_hora["Viagem com menos espera"] = tabela_meia_hora["Viagem com menos espera"].apply(timestamp_to_seconds)
# ----------------------------------------------------------------------------------------------------------------------
tabela_minuto["Tempo médio de viagem"] = tabela_minuto["Tempo médio de viagem"].apply(timestamp_to_seconds)
tabela_minuto["Tempo médio de espera"] = tabela_minuto["Tempo médio de espera"].apply(timestamp_to_seconds)

tabela_minuto["Mediana do tempo de viagem"] = tabela_minuto["Mediana do tempo de viagem"].apply(timestamp_to_seconds)
tabela_minuto["Mediana do tempo de espera"] = tabela_minuto["Mediana do tempo de espera"].apply(timestamp_to_seconds)

tabela_minuto["Viagem mais demorada"] = tabela_minuto["Viagem mais demorada"].apply(timestamp_to_seconds)
tabela_minuto["Viagem mais curta"] = tabela_minuto["Viagem mais curta"].apply(timestamp_to_seconds)

tabela_minuto["Viagem com mais espera"] = tabela_minuto["Viagem com mais espera"].apply(timestamp_to_seconds)
tabela_minuto["Viagem com menos espera"] = tabela_minuto["Viagem com menos espera"].apply(timestamp_to_seconds)
# ======================================================================================================================
# remove a coluna 'Quantidade de viagens' por ter valores muito pequenos em comparação aos outros
tabela_padrao = tabela_padrao.drop("Quantidade de viagens", axis=1)
tabela_hora_pico = tabela_hora_pico.drop("Quantidade de viagens", axis=1)
tabela_meia_hora = tabela_meia_hora.drop("Quantidade de viagens", axis=1)
tabela_minuto = tabela_minuto.drop("Quantidade de viagens", axis=1)

fig, [[ax1, ax2], [ax3, ax4]] = plt.subplots(nrows=2, ncols=2, figsize=(16, 9))

outra_fonte = {'fontfamily': 'Times New Roman', 'fontsize': 10}

tabela_padrao.boxplot(ax=ax1, vert=False)
ax1.set_title("Dados estatísticos do cenário A", **outra_fonte)
ax1.set_xlabel("Tempo em segundos", **outra_fonte)
ax1.set_ylabel("Dados estatísticos", **outra_fonte)
ax1.set_xlim(right=11000)
for x_tick in ax1.get_xticklabels():
    x_tick.set(**outra_fonte)
for y_tick in ax1.get_yticklabels():
    y_tick.set(**outra_fonte)

tabela_hora_pico.boxplot(ax=ax2, vert=False)
ax2.set_title('Dados estatísticos do cenário B', **outra_fonte)
ax2.set_xlabel("Tempo em segundos", **outra_fonte)
ax2.set_ylabel("Dados estatísticos", **outra_fonte)
ax2.set_xlim(right=11000)
for x_tick in ax2.get_xticklabels():
    x_tick.set(**outra_fonte)
for y_tick in ax2.get_yticklabels():
    y_tick.set(**outra_fonte)

tabela_meia_hora.boxplot(ax=ax3, vert=False)
ax3.set_title("Dados estatísticos do cenário C", **outra_fonte)
ax3.set_xlabel("Tempo em segundos", **outra_fonte)
ax3.set_ylabel("Dados estatísticos", **outra_fonte)
ax3.set_xlim(right=11000)
for x_tick in ax3.get_xticklabels():
    x_tick.set(**outra_fonte)
for y_tick in ax3.get_yticklabels():
    y_tick.set(**outra_fonte)

tabela_minuto.boxplot(ax=ax4, vert=False)
ax4.set_title('Dados estatísticos do cenário D', **outra_fonte)
ax4.set_xlabel("Tempo em segundos", **outra_fonte)
ax4.set_ylabel("Dados estatísticos", **outra_fonte)
ax4.set_xlim(right=11000)
for x_tick in ax4.get_xticklabels():
    x_tick.set(**outra_fonte)
for y_tick in ax4.get_yticklabels():
    y_tick.set(**outra_fonte)

plt.tight_layout()
plt.savefig(f"saidas/boxplot-dados-estatisticos-4-cenarios", dpi=600)
# plt.show()
