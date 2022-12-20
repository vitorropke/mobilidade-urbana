"""
Gera um arquivo "population.xml" para ser usado no MATSim
Cria várias pessoas que se deslocam da origem ao destino a partir e até um horário.
"""

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
horarios_pico = [6, 7, 8, 11, 12, 13, 17, 18, 19]

for parada_origem in coordenadas:
    for parada_destino in coordenadas:
        if parada_origem != parada_destino:
            for hora in range(hora_inicio, hora_fim):
                for minuto in range(60):
                    for segundo in range(1):
                        saida += f"\t<person id=\"{parada_origem[0]}_{parada_destino[0]}_{hora:0>2}_{minuto:0>2}_"
                        saida += f"{segundo:0>2}\">\n"

                        saida += "\t\t<plan>\n"

                        saida += f"\t\t\t<act end_time=\"{hora:0>2}:{minuto:0>2}:{segundo:0>2}\" "
                        saida += f"x=\"{parada_origem[1]}\" y=\"{parada_origem[2]}\" "

                        saida += f"type=\"home\"/>\n"

                        saida += "\t\t\t<leg mode=\"pt\"/>\n"

                        saida += f"\t\t\t<act x=\"{parada_destino[1]}\" y=\"{parada_destino[2]}\" type=\"home\"/>\n"

                        saida += "\t\t</plan>\n"

                        saida += "\t</person>\n\n"

saida += "</plans>\n"

with open("population-extremes-one-per-minute.xml", 'w') as arquivo_population:
    arquivo_population.write(saida)
