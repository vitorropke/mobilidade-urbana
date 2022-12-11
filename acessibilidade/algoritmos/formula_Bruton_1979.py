frequencia_passagem_onibus_linha9 = 5
frequencia_passagem_onibus_linha19 = 0
area_quilometros_quadrados = 1

acessibilidade_regiao = ((frequencia_passagem_onibus_linha9 ** 0.5) + (frequencia_passagem_onibus_linha19 ** 0.5)) / (area_quilometros_quadrados ** 0.5)

print(acessibilidade_regiao)
