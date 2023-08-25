"""
Gera um arquivo "population.xml" para ser usado no MATSim
Cria várias pessoas que se deslocam da origem ao destino a partir e até um horário.
"""


def gerar_pessoa(origem, destino, hora, minuto, segundo):
    pessoa = f"\t<person id=\"{origem[0]}_{destino[0]}_{hora:0>2}_{minuto:0>2}_"
    pessoa += f"{segundo:0>2}\">\n"

    pessoa += "\t\t<plan>\n"

    pessoa += f"\t\t\t<act end_time=\"{hora:0>2}:{minuto:0>2}:{segundo:0>2}\" "
    pessoa += f"x=\"{origem[1]}\" y=\"{origem[2]}\" "

    pessoa += f"type=\"home\"/>\n"

    pessoa += "\t\t\t<leg mode=\"pt\"/>\n"

    pessoa += f"\t\t\t<act x=\"{destino[1]}\" y=\"{destino[2]}\" type=\"home\"/>\n"

    pessoa += "\t\t</plan>\n"

    pessoa += "\t</person>\n\n"

    return pessoa


def gerar_populacao_um_por_hora(paradas, horario_inicial, horario_final):
    pessoas = ""

    for parada_origem in paradas:
        for parada_destino in paradas:
            if parada_origem != parada_destino:
                for hora in range(horario_inicial, horario_final):
                    pessoas += gerar_pessoa(parada_origem, parada_destino, hora, 0, 0)

    return pessoas


def gerar_populacao_horarios_pico(paradas, horarios_de_pico):
    pessoas = ""

    for parada_origem in paradas:
        for parada_destino in paradas:
            if parada_origem != parada_destino:
                for hora in horarios_de_pico:
                    pessoas += gerar_pessoa(parada_origem, parada_destino, hora, 0, 0)

    return pessoas


def gerar_populacao_um_por_meia_hora(paradas, horario_inicial, horario_final):
    pessoas = ""

    for parada_origem in paradas:
        for parada_destino in paradas:
            if parada_origem != parada_destino:
                for hora in range(horario_inicial, horario_final):
                    pessoas += gerar_pessoa(parada_origem, parada_destino, hora, 0, 0)
                    if hora != horario_final - 1:
                        pessoas += gerar_pessoa(parada_origem, parada_destino, hora, 30, 0)

    return pessoas


def gerar_populacao_um_por_minuto(paradas, horario_inicial, horario_final):
    pessoas = ""

    for parada_origem in paradas:
        for parada_destino in paradas:
            if parada_origem != parada_destino:
                for hora in range(horario_inicial, horario_final):
                    for minuto in range(60):
                        pessoas += gerar_pessoa(parada_origem, parada_destino, hora, minuto, 0)
                        if hora == horario_final - 1:
                            break

    return pessoas


saida = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE plans SYSTEM "
saida += "\"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n\n<plans>\n\n"

coordenadas = [["nova-mossoro", -4155775.828369, -570475.332125],
               ["alto-das-brisas", -4151086.894415, -578548.284105],
               ["odete-rosado", -4150529.142974, -582338.037939],
               ["nova-vida", -4153254.003600, -584200.145466],
               ["sumare", -4155353.334638, -584170.884416],
               ["cidade-oeste", -4162199.972440, -584408.481155],
               ["bom-pastor", -4161553.691394, -578962.716118],
               ["partage-shopping", -4160643.2241252107, -576411.0633567738],
               ["abolicao-5", -4159454.882874, -572701.299314],
               ["teatro-municipal-dix-huit-rosado", -4157267.5718863956, -578558.8743964208]]

hora_inicio = 5
hora_fim = 23

with open("population-extremes-one-per-hour.xml", 'w') as arquivo_population:
    saida = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE plans SYSTEM "
    saida += "\"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n\n<plans>\n\n"
    saida += gerar_populacao_um_por_hora(coordenadas, hora_inicio, hora_fim)
    saida += "</plans>\n"
    arquivo_population.write(saida)

with open("population-extremes-peak-hours.xml", 'w') as arquivo_population:
    saida = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE plans SYSTEM "
    saida += "\"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n\n<plans>\n\n"
    horarios_pico = [6, 7, 8, 11, 12, 13, 17, 18, 19]
    saida += gerar_populacao_horarios_pico(coordenadas, horarios_pico)
    saida += "</plans>\n"
    arquivo_population.write(saida)

with open("population-extremes-one-per-half-hour.xml", 'w') as arquivo_population:
    saida = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE plans SYSTEM "
    saida += "\"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n\n<plans>\n\n"
    saida += gerar_populacao_um_por_meia_hora(coordenadas, hora_inicio, hora_fim)
    saida += "</plans>\n"
    arquivo_population.write(saida)

with open("population-extremes-one-per-minute.xml", 'w') as arquivo_population:
    saida = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE plans SYSTEM "
    saida += "\"http://www.matsim.org/files/dtd/plans_v4.dtd\">\n\n<plans>\n\n"
    saida += gerar_populacao_um_por_minuto(coordenadas, hora_inicio, hora_fim)
    saida += "</plans>\n"
    arquivo_population.write(saida)
