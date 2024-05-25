"""
Gera um gráfico de caixa de um arquivo csv que contém valor de acessibilidade.
"""
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib import rcParams

rcParams['font.family'] = ['Times New Roman']
rcParams['font.size'] = 11

indice_usado = "allen-et-al"
tabela_padrao = pd.read_csv(
    "entradas/" + indice_usado + "/output-one-per-hour/acessibilidade-por-rota.csv")
tabela_hora_pico = pd.read_csv(
    "entradas/" + indice_usado + "/output-peak-hours/acessibilidade-por-rota.csv")
tabela_meia_hora = pd.read_csv(
    "entradas/" + indice_usado + "/output-one-per-half-hour/acessibilidade-por-rota.csv")
tabela_minuto = pd.read_csv(
    "entradas/" + indice_usado + "/output-one-per-minute/acessibilidade-por-rota.csv")

coluna_usada = "Acessibilidade da rota"
tabelas = pd.concat([tabela_minuto[coluna_usada], tabela_meia_hora[coluna_usada],
                     tabela_hora_pico[coluna_usada], tabela_padrao[coluna_usada]], axis=1)

tabelas.columns = ["D", "C", "B", "A"]
tabelas.boxplot(vert=False)

plt.title("Acessibilidade em diferentes cenários")
plt.xlabel("Valor de acessibilidade")
plt.ylabel("Cenários")

plt.savefig("saidas/boxplot-acessibilidade-" + indice_usado + ".pdf")

