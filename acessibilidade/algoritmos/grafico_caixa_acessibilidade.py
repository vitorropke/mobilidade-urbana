"""
Gera um gráfico de caixa de um arquivo csv que contém valor de acessibilidade.
"""
import matplotlib.pyplot as plt
import pandas as pd

tabela = pd.read_csv("entradas/Acessibilidade integral das rotas por tempo - Allen et al.csv")

grafico_caixa = plt.figure(1)

tabela.boxplot(column="Acessibilidade da rota")

plt.title("Metade")
plt.ylabel("Valor de acessibilidade de Allen")
plt.savefig("saidas/Gráfico de caixa da acessibilidade", dpi=200)

grafico_caixa.show()
plt.show()
