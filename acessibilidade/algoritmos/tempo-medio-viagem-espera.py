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
import datetime

colunas_usadas = ["person", "dep_time", "trav_time", "wait_time", "modes"]
tabela_viagens = pd.read_csv("output_trips.csv", sep=';', usecols=colunas_usadas)

viagens = tabela_viagens.values.tolist()

# variáveis dos tempos totais
somatorio_tempo_viagem_total = 0
somatorio_tempo_espera_total = 0
numero_viagens_com_transporte_publico_total = 0

inicializa_tempo_viagem = int(viagens[0][2][0] + viagens[0][2][1]) * 3600 + int(
    viagens[0][2][3] + viagens[0][2][4]) * 60 + int(viagens[0][2][6] + viagens[0][2][7])
inicializa_tempo_espera = int(viagens[0][3][0] + viagens[0][3][1]) * 3600 + int(
    viagens[0][3][3] + viagens[0][3][4]) * 60 + int(viagens[0][3][6] + viagens[0][3][7])

maior_tempo_viagem_total = [viagens[0][0], inicializa_tempo_viagem]
menor_tempo_viagem_total = [viagens[0][0], inicializa_tempo_viagem]

maior_tempo_espera_total = [viagens[0][0], inicializa_tempo_espera]
menor_tempo_espera_total = [viagens[0][0], inicializa_tempo_espera]

# variáveis dos tempos de cada rota
rotas = []

nome_da_rota = viagens[0][1]
somatorio_tempo_viagem_rota = 0
somatorio_tempo_espera_rota = 0
numero_viagens_com_transporte_publico_rota = 0

maior_tempo_viagem_rota = [nome_da_rota, inicializa_tempo_viagem]
menor_tempo_viagem_rota = [nome_da_rota, inicializa_tempo_viagem]

maior_tempo_espera_rota = [nome_da_rota, inicializa_tempo_espera]
menor_tempo_espera_rota = [nome_da_rota, inicializa_tempo_espera]

for viagem in viagens:
    # se o modo de deslocamento for apenas "a pé", desconsidera
    if len(viagem[4]) != 4:
        tempo_viagem_atual_segundos = int(viagem[2][0] + viagem[2][1]) * 3600 + int(
            viagem[2][3] + viagem[2][4]) * 60 + int(viagem[2][6] + viagem[2][7])
        tempo_espera_atual_segundos = int(viagem[3][0] + viagem[3][1]) * 3600 + int(
            viagem[3][3] + viagem[3][4]) * 60 + int(viagem[3][6] + viagem[3][7])

        # operações com tempo total
        somatorio_tempo_viagem_total += tempo_viagem_atual_segundos
        somatorio_tempo_espera_total += tempo_espera_atual_segundos

        if tempo_viagem_atual_segundos >= maior_tempo_viagem_total[1]:
            maior_tempo_viagem_total[0] = viagem[0]
            maior_tempo_viagem_total[1] = tempo_viagem_atual_segundos
        if tempo_viagem_atual_segundos < menor_tempo_viagem_total[1]:
            menor_tempo_viagem_total[0] = viagem[0]
            menor_tempo_viagem_total[1] = tempo_viagem_atual_segundos

        if tempo_espera_atual_segundos >= maior_tempo_espera_total[1]:
            maior_tempo_espera_total[0] = viagem[0]
            maior_tempo_espera_total[1] = tempo_espera_atual_segundos
        if tempo_espera_atual_segundos < menor_tempo_espera_total[1]:
            menor_tempo_espera_total[0] = viagem[0]
            menor_tempo_espera_total[1] = tempo_espera_atual_segundos

        # operações com tempo de cada rota
        somatorio_tempo_viagem_rota += tempo_viagem_atual_segundos
        somatorio_tempo_espera_rota += tempo_espera_atual_segundos

        if tempo_viagem_atual_segundos >= maior_tempo_viagem_rota[1]:
            maior_tempo_viagem_rota[0] = viagem[1]
            maior_tempo_viagem_rota[1] = tempo_viagem_atual_segundos
        if tempo_viagem_atual_segundos < menor_tempo_viagem_rota[1]:
            menor_tempo_viagem_rota[0] = viagem[1]
            menor_tempo_viagem_rota[1] = tempo_viagem_atual_segundos

        if tempo_espera_atual_segundos >= maior_tempo_espera_rota[1]:
            maior_tempo_espera_rota[0] = viagem[1]
            maior_tempo_espera_rota[1] = tempo_espera_atual_segundos
        if tempo_espera_atual_segundos < menor_tempo_espera_rota[1]:
            menor_tempo_espera_rota[0] = viagem[1]
            menor_tempo_espera_rota[1] = tempo_espera_atual_segundos

        if ((viagem[1][0] + viagem[1][1]) == "05") and (numero_viagens_com_transporte_publico_rota != 0):
            rotas.append([nome_da_rota[:-3],

                          str(datetime.timedelta(
                              seconds=round(somatorio_tempo_viagem_rota / numero_viagens_com_transporte_publico_rota))),
                          str(datetime.timedelta(
                              seconds=round(somatorio_tempo_espera_rota / numero_viagens_com_transporte_publico_rota))),

                          maior_tempo_viagem_rota[0],
                          str(datetime.timedelta(seconds=round(maior_tempo_viagem_rota[1]))),
                          menor_tempo_viagem_rota[0],
                          str(datetime.timedelta(seconds=round(menor_tempo_viagem_rota[1]))),

                          maior_tempo_espera_rota[0],
                          str(datetime.timedelta(seconds=round(maior_tempo_espera_rota[1]))),
                          menor_tempo_espera_rota[0],
                          str(datetime.timedelta(seconds=round(menor_tempo_espera_rota[1]))),

                          numero_viagens_com_transporte_publico_rota])

            somatorio_tempo_viagem_rota = 0
            somatorio_tempo_espera_rota = 0
            numero_viagens_com_transporte_publico_rota = 0

            reseta_tempo_viagem = int(viagem[2][0] + viagem[2][1]) * 3600 + int(viagem[2][3] + viagem[2][4]) * 60 + int(
                viagem[2][6] + viagem[2][7])
            maior_tempo_viagem_rota = [viagem[1], reseta_tempo_viagem]
            menor_tempo_viagem_rota = [viagem[1], reseta_tempo_viagem]

            reseta_tempo_espera = int(viagem[3][0] + viagem[3][1]) * 3600 + int(viagem[3][3] + viagem[3][4]) * 60 + int(
                viagem[3][6] + viagem[3][7])
            maior_tempo_espera_rota = [viagem[1], reseta_tempo_espera]
            menor_tempo_espera_rota = [viagem[1], reseta_tempo_espera]

        nome_da_rota = viagem[0]
        numero_viagens_com_transporte_publico_rota += 1
        numero_viagens_com_transporte_publico_total += 1

print(
    f"\nTempo médio total de viagem: "
    f"{datetime.timedelta(seconds=round(somatorio_tempo_viagem_total / numero_viagens_com_transporte_publico_total))}")
print(
    f"Tempo médio total de espera: "
    f"{datetime.timedelta(seconds=round(somatorio_tempo_espera_total / numero_viagens_com_transporte_publico_total))}")
print(f"Número de viagens onde o transporte público foi usado: "
      f"{numero_viagens_com_transporte_publico_total}\n")

print(
    f"Maior tempo de viagem: "
    f"{datetime.timedelta(seconds=round(maior_tempo_viagem_total[1]))} na viagem {maior_tempo_viagem_total[0]}")
print(
    f"Menor tempo de viagem: "
    f"{datetime.timedelta(seconds=round(menor_tempo_viagem_total[1]))} na viagem {menor_tempo_viagem_total[0]}\n")

print(
    f"Maior tempo de espera: "
    f"{datetime.timedelta(seconds=round(maior_tempo_espera_total[1]))} na viagem {maior_tempo_espera_total[0]}")
print(
    f"Menor tempo de espera: "
    f"{datetime.timedelta(seconds=round(menor_tempo_espera_total[1]))} na viagem {menor_tempo_espera_total[0]}\n")

print(rotas)

# salva no arquivo
tabela_saida = pd.DataFrame(rotas, columns=["Nome da rota", "Tempo médio de viagem", "Tempo médio de espera",
                                            "Viagem mais demorada", "Tempo da viagem mais demorada",
                                            "Viagem mais curta", "Tempo da viagem mais curta", "Viagem com mais espera",
                                            "Tempo da viagem com mais espera", "Viagem com menor espera",
                                            "Tempo da viagem com menor espera",
                                            "Número de viagens com transporte público"])
tabela_saida.to_csv("medias.csv", index=False)
