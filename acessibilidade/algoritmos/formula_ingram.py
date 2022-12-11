"""
Indicadores de separação espacial. Acessibilidade das paradas de ônibus.

Ingram (1971)

     n
Ai = ∑aij
     j

Ai: Acessibilidade do ponto i;
aij: Acessibilidade relativa entre os pontos i e j;
n: Número total de pontos da rede

Quanto maior o número, menos acessível
"""
import matplotlib.pyplot as plt
import pandas as pd

tabela_distancias = pd.read_csv("entradas/Distâncias entre as paradas.csv", index_col=0)
tabela_paradas_coordenadas = pd.read_csv("entradas/Paradas com coordenadas.csv", index_col=0)

# soma todos os valores de cada linha
tabela_acessibilidades_tempos = tabela_distancias.sum(axis=1, numeric_only=True)

tabela_saida = pd.DataFrame(index=tabela_distancias.index)
tabela_saida = pd.concat([tabela_saida, tabela_acessibilidades_tempos, tabela_paradas_coordenadas], axis=1)
tabela_saida.columns = ["Acessibilidade da parada", "Latitude", "Longitude"]

print(f"\nAcessibilidade média: {tabela_saida['Acessibilidade da parada'].mean()}")
print(f"Mediana da acessibilidade: {tabela_saida['Acessibilidade da parada'].median()}\n\n")

print(f"Maior valor de acessibilidade\n{tabela_saida.loc[tabela_saida.idxmax()]}\n")
print(f"Menor valor de acessibilidade\n{tabela_saida.loc[tabela_saida.idxmin()]}")

# gera gráfico de dispersão
grafico_dispersao = plt.figure(1)

plt.scatter(tabela_saida.index, tabela_saida["Acessibilidade da parada"])

plt.title("Dispersão da acessibilidade das paradas")
plt.xlabel("Paradas")
plt.ylabel("Distância para outras paradas")

plt.savefig("saidas/Dispersão da acessibilidade das paradas - Ingram.png")

#grafico_dispersao.show()

# gera o gráfico de caixa
grafico_caixa = plt.figure(2)

tabela_saida.boxplot(column="Acessibilidade da parada")

plt.title("Dados estatísticos da acessibilidade de cada parada")
plt.ylabel("Valor da acessibilidade")
plt.savefig("saidas/Gráfico de caixa da acessibilidade das paradas - Ingram.png")

#grafico_caixa.show()

# ordena e salva os valores em um arquivo csv
tabela_saida = tabela_saida.sort_values("Acessibilidade da parada")
tabela_saida.to_csv("saidas/Acessibilidade integral das paradas por distância - Ingram.csv")

#plt.show()
