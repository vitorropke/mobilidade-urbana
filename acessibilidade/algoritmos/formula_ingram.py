"""
Indicadores de separação espacial. Acessibilidade das paradas de ônibus.

Ingram (1971)

     n
Ai = ∑aij
     j

Ai: Acessibilidade do ponto i;
aij: Acessibilidade relativa entre os pontos i e j;
n: Número total de pontos da rede.

Quanto maior o número, menos acessível
"""
import matplotlib.pyplot as plt
import pandas as pd

tabela_distancias = pd.read_csv("dados-processados/Distâncias entre paradas.csv", index_col=0)
tabela_tempos = pd.read_csv("dados-processados/Tempos entre paradas.csv", index_col=0)
tabela_coordenadas = pd.read_csv("dados-processados/Coordenadas das paradas.csv", index_col=0)

# soma todos os valores de cada linha
tabela_acessibilidades_distancias = tabela_distancias.sum(axis=1, numeric_only=True)
tabela_acessibilidades_tempos = tabela_tempos.sum(axis=1, numeric_only=True)

# junta com as coordenadas
tabela_saida_distancia = pd.DataFrame(index=tabela_distancias.index)
tabela_saida_distancia = pd.concat([tabela_saida_distancia, tabela_acessibilidades_distancias, tabela_coordenadas],
                                   axis=1)
tabela_saida_distancia.columns = ["Acessibilidade da parada", "Latitude", "Longitude"]

tabela_saida_tempo = pd.DataFrame(index=tabela_tempos.index)
tabela_saida_tempo = pd.concat([tabela_saida_tempo, tabela_acessibilidades_tempos, tabela_coordenadas], axis=1)
tabela_saida_tempo.columns = ["Acessibilidade da parada", "Latitude", "Longitude"]

# salva as informações gerais em um txt
with open("dados-processados/Estatísticas gerais por distância.txt", 'w') as arquivo_saida:
    saida = ""

    saida += f"Acessibilidade média: {tabela_saida_distancia['Acessibilidade da parada'].mean()}\n"
    saida += f"Mediana da acessibilidade: {tabela_saida_distancia['Acessibilidade da parada'].median()}\n\n"

    indice_maior_acessibilidade = tabela_saida_distancia['Acessibilidade da parada'].idxmax()
    indice_menor_acessibilidade = tabela_saida_distancia['Acessibilidade da parada'].idxmin()

    saida += f"Maior valor de acessibilidade\n{tabela_saida_distancia.loc[indice_maior_acessibilidade]}\n\n"
    saida += f"Menor valor de acessibilidade\n{tabela_saida_distancia.loc[indice_menor_acessibilidade]}\n"

    arquivo_saida.write(saida)

# salva as informações gerais em um txt
with open("dados-processados/Estatísticas gerais por tempo.txt", 'w') as arquivo_saida:
    saida = ""

    saida += f"Acessibilidade média: {tabela_saida_tempo['Acessibilidade da parada'].mean()}\n"
    saida += f"Mediana da acessibilidade: {tabela_saida_tempo['Acessibilidade da parada'].median()}\n\n"

    indice_maior_acessibilidade = tabela_saida_tempo['Acessibilidade da parada'].idxmax()
    indice_menor_acessibilidade = tabela_saida_tempo['Acessibilidade da parada'].idxmin()

    saida += f"Maior valor de acessibilidade\n{tabela_saida_tempo.loc[indice_maior_acessibilidade]}\n\n"
    saida += f"Menor valor de acessibilidade\n{tabela_saida_tempo.loc[indice_menor_acessibilidade]}\n"

    arquivo_saida.write(saida)

# gera gráfico de dispersão
grafico_dispersao_distancia = plt.figure(1)

plt.scatter(tabela_saida_distancia.index, tabela_saida_distancia["Acessibilidade da parada"], c='b')

plt.title("Dispersão da acessibilidade das paradas por distância")
plt.xlabel("Paradas")
plt.ylabel("Acessibilidade")

plt.savefig("dados-processados/Dispersão da acessibilidade das paradas por distância - Ingram.png")

# gera gráfico de dispersão
grafico_dispersao_tempo = plt.figure(2)

plt.scatter(tabela_saida_tempo.index, tabela_saida_tempo["Acessibilidade da parada"], c='r')

plt.title("Dispersão da acessibilidade das paradas por tempo")
plt.xlabel("Paradas")
plt.ylabel("Acessibilidade")

plt.savefig("dados-processados/Dispersão da acessibilidade das paradas por tempo - Ingram.png")

# gera o gráfico de caixa
grafico_caixa_distancia = plt.figure(3)

tabela_saida_distancia.boxplot(column="Acessibilidade da parada")

plt.title("Acessibilidade de cada parada por distância")
plt.ylabel("Acessibilidade")
plt.savefig("dados-processados/Gráfico de caixa da acessibilidade das paradas por distância - Ingram.png")

# gera o gráfico de caixa
grafico_caixa_tempo = plt.figure(4)

tabela_saida_tempo.boxplot(column="Acessibilidade da parada")

plt.title("Acessibilidade de cada parada por tempo")
plt.ylabel("Acessibilidade")
plt.savefig("dados-processados/Gráfico de caixa da acessibilidade das paradas por tempo - Ingram.png")

# ordena e salva os valores num arquivo csv
tabela_saida_distancia = tabela_saida_distancia.sort_values("Acessibilidade da parada")
tabela_saida_distancia.to_csv("dados-processados/Acessibilidade integral das paradas por distância - Ingram.csv")

tabela_saida_tempo = tabela_saida_tempo.sort_values("Acessibilidade da parada")
tabela_saida_tempo.to_csv("dados-processados/Acessibilidade integral das paradas por tempo - Ingram.csv")
