"""
Gera um gráfico de caixa de um arquivo csv que contém valor de acessibilidade.
"""
import matplotlib.pyplot as plt
import pandas as pd

indice_usado = "4-bracarense-e-ferreira"
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

outra_fonte = {'fontfamily': 'Times New Roman', 'fontsize': 10}
plt.title("Acessibilidade em diferentes cenários", **outra_fonte)
plt.xlabel("Valor de acessibilidade", **outra_fonte)
plt.xticks(**outra_fonte)
plt.ylabel("Cenários", **outra_fonte)
plt.yticks(**outra_fonte)

plt.savefig("saidas/boxplot-acessibilidade-" + indice_usado, dpi=600, transparent=True)
# plt.show()
