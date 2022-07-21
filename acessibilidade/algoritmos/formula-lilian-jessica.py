"""
Equação do artigo 'Índice de acessibilidade para comparação dos modos de transporte privado e coletivo' das autoras
Lílian dos Santos Fontes Pereira Bracarense e Jéssica Oliveira Nunes Ferreira

Disponível nas seguintes URLs:
https://www.scielo.br/j/urbe/a/jypKpfGB6TwpXkYNMjSySLx/abstract/?lang=pt
https://www.redalyc.org/articulo.oa?id=193157811008
https://pucpr.emnuvens.com.br/Urbe/article/view/23183/23057

A equação está localizada na página 607 (página 8 do arquivo) na sessão de resultados
"""

import pandas as pd
import time

colunas_usadas = ["person", "dep_time", "trav_time", "modes"]
tabela_viagens = pd.read_csv("output_trips.csv", sep=';', usecols=colunas_usadas)

viagens = tabela_viagens.values.tolist()

menor_tempo_viagem_rota = maior_tempo_viagem_rota = int(viagens[0][2][0] + viagens[0][2][1]) * 60 + int(
    viagens[0][2][3] + viagens[0][2][4]) + int(viagens[0][2][6] + viagens[0][2][7]) / 60

tempo_viagem_minimo_maximo_rota = []

# identifica o tempo máximo e mínimo de cada rota
for n, viagem in enumerate(viagens):
    # se o modo de deslocamento não for 'walk'
    if len(viagem[3]) != 4:
        # tempo de viagem em minutos
        tempo_viagem = int(viagem[2][0] + viagem[2][1]) * 60 + int(viagem[2][3] + viagem[2][4]) + int(
            viagem[2][6] + viagem[2][7]) / 60

        if menor_tempo_viagem_rota > tempo_viagem:
            menor_tempo_viagem_rota = tempo_viagem
        if maior_tempo_viagem_rota < tempo_viagem:
            maior_tempo_viagem_rota = tempo_viagem

    # se for a última viagem ou se mudar a rota
    if (viagem == viagens[-1]) or (viagem[0][:-3] != viagens[n + 1][0][:-3]):
        tempo_viagem_minimo_maximo_rota.append([menor_tempo_viagem_rota, maior_tempo_viagem_rota])

        if viagem != viagens[-1]:
            # altera as variáveis com os valores da próxima rota
            menor_tempo_viagem_rota = maior_tempo_viagem_rota = int(
                viagens[n + 1][2][0] + viagens[n + 1][2][1]) * 60 + int(
                viagens[n + 1][2][3] + viagens[n + 1][2][4]) + int(viagens[n + 1][2][6] + viagens[n + 1][2][7]) / 60

soma_acessibilidade_rota = 0.0
rota_atual = 0
acessibilidade_rotas = []

# calcula a acessibilidade de cada rota
for n, viagem in enumerate(viagens):
    if len(viagem[3]) != 4:
        # 1 — (tempo de viagem atual — tempo de viagem mínimo) / (tempo de viagem máximo — tempo de viagem mínimo)
        soma_acessibilidade_rota += 1 - (int(viagem[2][0] + viagem[2][1]) * 60 + int(viagem[2][3] + viagem[2][4]) + int(
            viagem[2][6] + viagem[2][7]) / 60 - tempo_viagem_minimo_maximo_rota[rota_atual][0]) / (
                                            tempo_viagem_minimo_maximo_rota[rota_atual][1] -
                                            tempo_viagem_minimo_maximo_rota[rota_atual][0])

    if (viagem == viagens[-1]) or (viagem[0][:-3] != viagens[n + 1][0][:-3]):
        acessibilidade_rotas.append([viagem[0][:-3],
                                     soma_acessibilidade_rota,
                                     time.strftime('%H:%M:%S',
                                                   time.gmtime(tempo_viagem_minimo_maximo_rota[rota_atual][0] * 60)),
                                     time.strftime('%H:%M:%S',
                                                   time.gmtime(tempo_viagem_minimo_maximo_rota[rota_atual][1] * 60))])

        soma_acessibilidade_rota = 0.0
        rota_atual += 1

# salva no arquivo
tabela_saida = pd.DataFrame(acessibilidade_rotas, columns=["Nome da rota", "Acessibilidade da rota",
                                                           "Tempo mínimo de viagem", "Tempo máximo de viagem"])
tabela_saida.to_csv("Acessibilidade de cada rota.csv", index=False)
