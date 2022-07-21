"""
Todos os seguintes cálculos consideram apenas viagens com, no mínimo, uma viagem com transporte público

Calcula o tempo médio de viagem de todas as viagens juntas
Calcula o tempo médio de espera de todas as viagens juntas
Obtém o maior e menor tempo de viagem de todas as viagens juntas
Obtém o maior e menor tempo de espera de todas as viagens juntas

Calcula o tempo médio de viagem de cada rota
Calcula o tempo médio de espera de cada rota
Obtém o maior e menor tempo de viagem da rota
Obtém o maior e menor tempo de espera da rota
"""

import pandas as pd
import time

colunas_usadas = ["person", "dep_time", "trav_time", "wait_time", "modes"]
tabela_viagens = pd.read_csv("output_trips.csv", sep=';', usecols=colunas_usadas)

viagens = tabela_viagens.values.tolist()

# variáveis dos tempos totais
numero_viagens_pt_total = 0

soma_tempo_viagem_total = 0
soma_tempo_espera_total = 0

maior_tempo_medio_viagem = []
menor_tempo_medio_viagem = []

maior_tempo_medio_espera = []
menor_tempo_medio_espera = []

tempo_viagem_inicial = int(viagens[0][2][0] + viagens[0][2][1]) * 3600 + int(
    viagens[0][2][3] + viagens[0][2][4]) * 60 + int(viagens[0][2][6] + viagens[0][2][7])
tempo_espera_inicial = int(viagens[0][3][0] + viagens[0][3][1]) * 3600 + int(
    viagens[0][3][3] + viagens[0][3][4]) * 60 + int(viagens[0][3][6] + viagens[0][3][7])

maior_tempo_viagem_total = [viagens[0][0], tempo_viagem_inicial]
menor_tempo_viagem_total = [viagens[0][0], tempo_viagem_inicial]

maior_tempo_espera_total = [viagens[0][0], tempo_espera_inicial]
menor_tempo_espera_total = [viagens[0][0], tempo_espera_inicial]

# variáveis dos tempos de cada rota
rotas = []

numero_viagens_pt_rota = 0

soma_tempo_viagem_rota = 0
soma_tempo_espera_rota = 0

maior_tempo_viagem_rota = [viagens[0][0], tempo_viagem_inicial]
menor_tempo_viagem_rota = [viagens[0][0], tempo_viagem_inicial]

maior_tempo_espera_rota = [viagens[0][0], tempo_espera_inicial]
menor_tempo_espera_rota = [viagens[0][0], tempo_espera_inicial]

primeira_viagem_rota_walk = False
primeira_rota = True

# identifica o tempo máximo e mínimo de cada rota
for n, viagem in enumerate(viagens):
    # se o modo de deslocamento não for 'walk'
    if len(viagem[4]) != 4:
        tempo_viagem_atual_segundos = int(viagem[2][0] + viagem[2][1]) * 3600 + int(
            viagem[2][3] + viagem[2][4]) * 60 + int(viagem[2][6] + viagem[2][7])
        tempo_espera_atual_segundos = int(viagem[3][0] + viagem[3][1]) * 3600 + int(
            viagem[3][3] + viagem[3][4]) * 60 + int(viagem[3][6] + viagem[3][7])

        # operações com a malha completa
        numero_viagens_pt_total += 1

        soma_tempo_viagem_total += tempo_viagem_atual_segundos
        soma_tempo_espera_total += tempo_espera_atual_segundos

        if tempo_viagem_atual_segundos > maior_tempo_viagem_total[1]:
            maior_tempo_viagem_total[0] = viagem[0]
            maior_tempo_viagem_total[1] = tempo_viagem_atual_segundos
        elif tempo_viagem_atual_segundos < menor_tempo_viagem_total[1]:
            menor_tempo_viagem_total[0] = viagem[0]
            menor_tempo_viagem_total[1] = tempo_viagem_atual_segundos

        if tempo_espera_atual_segundos > maior_tempo_espera_total[1]:
            maior_tempo_espera_total[0] = viagem[0]
            maior_tempo_espera_total[1] = tempo_espera_atual_segundos
        elif tempo_espera_atual_segundos < menor_tempo_espera_total[1]:
            menor_tempo_espera_total[0] = viagem[0]
            menor_tempo_espera_total[1] = tempo_espera_atual_segundos

        # operações com cada rota
        # quando a primeira viagem dessa rota for walk
        if primeira_viagem_rota_walk:
            maior_tempo_viagem_rota = [viagem[0], tempo_viagem_atual_segundos]
            menor_tempo_viagem_rota = [viagem[0], tempo_viagem_atual_segundos]

            maior_tempo_espera_rota = [viagem[0], tempo_espera_atual_segundos]
            menor_tempo_espera_rota = [viagem[0], tempo_espera_atual_segundos]

            primeira_viagem_rota_walk = False

        numero_viagens_pt_rota += 1

        soma_tempo_viagem_rota += tempo_viagem_atual_segundos
        soma_tempo_espera_rota += tempo_espera_atual_segundos

        if tempo_viagem_atual_segundos > maior_tempo_viagem_rota[1]:
            maior_tempo_viagem_rota[0] = viagem[0]
            maior_tempo_viagem_rota[1] = tempo_viagem_atual_segundos
        elif tempo_viagem_atual_segundos < menor_tempo_viagem_rota[1]:
            menor_tempo_viagem_rota[0] = viagem[0]
            menor_tempo_viagem_rota[1] = tempo_viagem_atual_segundos

        if tempo_espera_atual_segundos > maior_tempo_espera_rota[1]:
            maior_tempo_espera_rota[0] = viagem[0]
            maior_tempo_espera_rota[1] = tempo_espera_atual_segundos
        elif tempo_espera_atual_segundos < menor_tempo_espera_rota[1]:
            menor_tempo_espera_rota[0] = viagem[0]
            menor_tempo_espera_rota[1] = tempo_espera_atual_segundos

    # se for a última viagem ou se mudar a rota
    if (viagem == viagens[-1]) or (viagem[0][:-3] != viagens[n + 1][0][:-3]) and (numero_viagens_pt_rota != 0):
        tempo_medio_viagem_rota = soma_tempo_viagem_rota / numero_viagens_pt_rota
        tempo_medio_espera_rota = soma_tempo_espera_rota / numero_viagens_pt_rota

        # se for a primeira rota, inicializa as variáveis de maior e menor tempo médio de viagem e de espera
        # se não, atualiza os valores se for o caso
        if primeira_rota:
            primeira_rota = False

            maior_tempo_medio_viagem = [viagem[0][:-3], tempo_medio_viagem_rota]
            menor_tempo_medio_viagem = [viagem[0][:-3], tempo_medio_viagem_rota]

            maior_tempo_medio_espera = [viagem[0][:-3], tempo_medio_espera_rota]
            menor_tempo_medio_espera = [viagem[0][:-3], tempo_medio_espera_rota]
        else:
            if tempo_medio_viagem_rota > maior_tempo_medio_viagem[1]:
                maior_tempo_medio_viagem[0] = viagem[0][:-3]
                maior_tempo_medio_viagem[1] = tempo_medio_viagem_rota
            elif tempo_medio_viagem_rota < menor_tempo_medio_viagem[1]:
                menor_tempo_medio_viagem[0] = viagem[0][:-3]
                menor_tempo_medio_viagem[1] = tempo_medio_viagem_rota

            if tempo_medio_espera_rota > maior_tempo_medio_espera[1]:
                maior_tempo_medio_espera[0] = viagem[0][:-3]
                maior_tempo_medio_espera[1] = tempo_medio_espera_rota
            elif tempo_medio_espera_rota < menor_tempo_medio_espera[1]:
                menor_tempo_medio_espera[0] = viagem[0][:-3]
                menor_tempo_medio_espera[1] = tempo_medio_espera_rota

        rotas.append([viagem[0][:-3],

                      time.strftime('%H:%M:%S', time.gmtime(tempo_medio_viagem_rota)),
                      time.strftime('%H:%M:%S', time.gmtime(tempo_medio_espera_rota)),

                      maior_tempo_viagem_rota[0], time.strftime('%H:%M:%S', time.gmtime(maior_tempo_viagem_rota[1])),
                      menor_tempo_viagem_rota[0], time.strftime('%H:%M:%S', time.gmtime(menor_tempo_viagem_rota[1])),

                      maior_tempo_espera_rota[0], time.strftime('%H:%M:%S', time.gmtime(maior_tempo_espera_rota[1])),
                      menor_tempo_espera_rota[0], time.strftime('%H:%M:%S', time.gmtime(menor_tempo_espera_rota[1])),

                      numero_viagens_pt_rota])

        numero_viagens_pt_rota = 0

        soma_tempo_viagem_rota = 0
        soma_tempo_espera_rota = 0

        # se não for a última viagem e a próxima viagem não for walk
        if (viagem != viagens[-1]) and (len(viagens[n + 1][4]) != 4):
            reset_tempo_viagem = int(viagens[n + 1][2][0] + viagens[n + 1][2][1]) * 3600 + int(
                viagens[n + 1][2][3] + viagens[n + 1][2][4]) * 60 + int(viagens[n + 1][2][6] + viagens[n + 1][2][7])

            maior_tempo_viagem_rota = [viagens[n + 1][0], reset_tempo_viagem]
            menor_tempo_viagem_rota = [viagens[n + 1][0], reset_tempo_viagem]

            reset_tempo_espera = int(viagens[n + 1][2][0] + viagens[n + 1][2][1]) * 3600 + int(
                viagens[n + 1][2][3] + viagens[n + 1][2][4]) * 60 + int(viagens[n + 1][2][6] + viagens[n + 1][2][7])

            maior_tempo_espera_rota = [viagens[n + 1][0], reset_tempo_espera]
            menor_tempo_espera_rota = [viagens[n + 1][0], reset_tempo_espera]
        else:
            primeira_viagem_rota_walk = True

# muda o maior e menor tempo de viagem e de espera de inteiro para formato de horário hh:mm:ss
maior_tempo_viagem_total[1] = time.strftime('%H:%M:%S', time.gmtime(maior_tempo_viagem_total[1]))
menor_tempo_viagem_total[1] = time.strftime('%H:%M:%S', time.gmtime(menor_tempo_viagem_total[1]))

maior_tempo_espera_total[1] = time.strftime('%H:%M:%S', time.gmtime(maior_tempo_espera_total[1]))
menor_tempo_espera_total[1] = time.strftime('%H:%M:%S', time.gmtime(menor_tempo_espera_total[1]))

# muda o maior e menor tempo médio de viagem e de espera de inteiro para o formato de horário 'hh:mm:ss'
maior_tempo_medio_viagem[1] = time.strftime('%H:%M:%S', time.gmtime(maior_tempo_medio_viagem[1]))
menor_tempo_medio_viagem[1] = time.strftime('%H:%M:%S', time.gmtime(menor_tempo_medio_viagem[1]))

maior_tempo_medio_espera[1] = time.strftime('%H:%M:%S', time.gmtime(maior_tempo_medio_espera[1]))
menor_tempo_medio_espera[1] = time.strftime('%H:%M:%S', time.gmtime(menor_tempo_medio_espera[1]))

print(f"\nNúmero de viagens com ônibus: {numero_viagens_pt_total}\n")

print(
    f"Tempo médio de viagem da rede: "
    f"{time.strftime('%H:%M:%S', time.gmtime(soma_tempo_viagem_total / numero_viagens_pt_total))}")
print(
    f"Tempo médio de espera da rede: "
    f"{time.strftime('%H:%M:%S', time.gmtime(soma_tempo_espera_total / numero_viagens_pt_total))}\n")

print(f"Maior tempo médio de viagem da rede: {maior_tempo_medio_viagem}")
print(f"Menor tempo médio de viagem da rede: {menor_tempo_medio_viagem}")

print(f"Maior tempo médio de espera da rede: {maior_tempo_medio_espera}")
print(f"Menor tempo médio de espera da rede: {menor_tempo_medio_espera}\n")

print(f"Maior tempo de viagem da rede: {maior_tempo_viagem_total}")
print(f"Menor tempo de viagem da rede: {menor_tempo_viagem_total}")

print(f"Maior tempo de espera da rede: {maior_tempo_espera_total}")
print(f"Menor tempo de espera da rede: {menor_tempo_espera_total}")

# salva as informações de cada rota em um arquivo csv
tabela_saida = pd.DataFrame(rotas, columns=["Nome da rota", "Tempo médio de viagem", "Tempo médio de espera",
                                            "Viagem mais demorada", "Tempo da viagem mais demorada",
                                            "Viagem mais rápida", "Tempo da viagem mais rápida",
                                            "Viagem com mais espera", "Tempo da viagem com mais espera",
                                            "Viagem com menos espera", "Tempo da viagem com menos espera",
                                            "Número de viagens com ônibus"])

tabela_saida.to_csv("Estatísticas de cada rota.csv", index=False)
