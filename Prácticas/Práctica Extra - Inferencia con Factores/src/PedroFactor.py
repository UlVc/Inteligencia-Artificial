from Factor import Factor
from Variable import Variable

MA = Variable('MA', [0, 1])
MP = Variable('MP', [0, 1])
AA = Variable('AA', [0, 1])
AP = Variable('AP', [0, 1])
JA = Variable('JA', [0, 1])
JP = Variable('JP', [0, 1])
BA = Variable('BA', [0, 1])
BP = Variable('BP', [0, 1])
VP = Variable('VP', [0, 1])
LI = Variable('LI', [0, 1])
LF = Variable('LF', [0, 1])
FIN = Variable('FIN', [0, 1])
E = Variable('E', [0, 1])
IT = Variable('IT', [0, 1])

FLF  = Factor((LF,),  [0.78, 0.22])
FLI  = Factor((LI,),  [0.78, 0.22])
FFIN = Factor((FIN,), [0.72, 0.28])
FIT  = Factor((IT,),  [0.7, 0.3])
FE   = Factor((E,),   [0.35, 0.65])

FJALI  = Factor((JA, LI),  [0.1, 0.6, 0.9, 0.4])
FAAFIN = Factor((AA, FIN), [0.6, 0.2, 0.4, 0.8])
FBAIT  = Factor((BA, IT),  [0.05, 0.7, 0.95, 0.3])
FMPMA  = Factor((MP, MA),  [0.97, 0.03, 0.03, 0.97])
FAPAA  = Factor((AP, AA),  [0.5, 0.2, 0.5, 0.8])

FMAJAAA = Factor((MA, JA, AA), [0.5, 0.15, 0.05, 0.95, 
                                0.5, 0.85, 0.95, 0.05])
FVPAABA = Factor((VP, AA, BA), [0.3, 0.6, 0.1, 0, 
                                0.7, 0.4, 0.9, 1])
FJPJALF = Factor((JP, JA, LF), [0.6, 1, 0.1, 0.3, 
                                0.4, 0, 0.9, 0.7])
FBPBAE  = Factor((BP, BA, E),  [0.8, 1, 0.05, 1, 
                                0.2, 0, 0.95, 0])

def calcular_probabilidad_conjunta_completa():
    return FLF.multiply(FLI).multiply(FFIN).multiply(FIT).multiply(FE).multiply(FJALI).multiply(FAAFIN).multiply(FBAIT).multiply(FMAJAAA).multiply(FJPJALF).multiply(FMPMA).multiply(FAPAA).multiply(FVPAABA).multiply(FBPBAE)

def ecuacion_7_1():
    print()
    print('╭───────⟆ Ecuación 7.1: P(VP,BP) = sigma_{AA,BA,FIN,IT,E} P(VP|AA, BA) P(BP|BA,E) P(AA|FIN) P(BA|IT) P(FIN) P(IT) P(E)\n│')

    print('│ ╭──⊰ Multiplicaciones:' + '\n│ │')
    print('│ │ Multiplicando P(VP|AA, BA) * P(BP|BA, E)')
    x = FVPAABA.multiply(FBPBAE)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Multiplicando P(VP|AA, BA) * P(BP|BA, E) * P(AA|FIN)')
    x = x.multiply(FAAFIN)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Multiplicando P(VP|AA, BA) * P(BP|BA, E) * P(AA|FIN) * P(BA|IT)')
    x = x.multiply(FBAIT)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Multiplicando P(VP|AA, BA) * P(BP|BA, E) * P(AA|FIN) * P(BA|IT) * P(FIN)')
    x = x.multiply(FFIN)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Multiplicando P(VP|AA, BA) * P(BP|BA, E) * P(AA|FIN) * P(BA|IT) * P(FIN) * P(IT)')
    x = x.multiply(FIT)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Multiplicando P(VP|AA, BA) * P(BP|BA, E) * P(AA|FIN) * P(BA|IT) * P(FIN) * P(IT) * P(E)')
    x = x.multiply(FE)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Marginalizaciones:' + '\n│ │')

    print('│ │ Marginalización de la columna AA')
    x = x.marginalize(AA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Marginalización de la columna BA')
    x = x.marginalize(BA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Marginalización de la columna FIN')
    x = x.marginalize(FIN)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Marginalización de la columna IT')
    x = x.marginalize(IT)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Marginalización de la columna E')
    x = x.marginalize(E)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Resultado:' + '\n│ │')
    x = x.normalize()
    print(f'│ │ Valores de los renglones obtenidos: {x.valores}\n│ │')
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─\n╰────')

def ecuacion_7_2():
    print('╭───────⟆ Ecuación 2: P(VP,BP) = sigma_{AA,BA} P(VP|AA,BA) sigma_{E} P(BP| BA, E) sigma_{FIN} P(AA|FIN) sigma_{IT} P(BA|IT) P(FIN) P(IT) P(E) \n│')

    print('│────˃ De izquierda a derecha hacemos las multiplicaciones y marginalizaciones correspondientes:\n│')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos P(IT) * P(E)')
    x = FIT.multiply(FE)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Multiplicamos P(FIN) * P(IT) * P(E)')
    x = FFIN.multiply(x)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Multiplicamos P(BA|IT) * P(FIN) * P(IT) * P(E)')
    x = FBAIT.multiply(x)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Marginalización de la columna IT')
    x = x.marginalize(IT)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos P(AA|FIN) * sigma_{IT} P(BA|IT) P(FIN) P(IT) P(E)')
    x = FAAFIN.multiply(x)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalización de la columna FIN')
    x = x.marginalize(FIN)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos P(BP| BA, E) * sigma_{FIN} P(AA|FIN) sigma_{IT} P(BA|IT) P(FIN) P(IT) P(E)')
    x = FBPBAE.multiply(x)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalización de la columna E')
    x = x.marginalize(E)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos P(VP|AA,BA) sigma_{E} P(BP| BA, E) sigma_{FIN} P(AA|FIN) sigma_{IT} P(BA|IT) P(FIN) P(IT) P(E)')
    x = FVPAABA.multiply(x)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalización de la columna AA')
    x = x.marginalize(AA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Marginalización de la columna BA')
    x = x.marginalize(BA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Resultado:' + '\n│ │')
    x = x.normalize()
    print(f'│ │ Valores de los renglones obtenidos: {x.valores}\n│ │')
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─\n╰────')
    print(str(x.valores))

def ecuacion_7_3():
    print('╭───────⟆ Ecuación 3: P(VP,BP) = sigma_{AA,BA}{P(VP|AA,BA) [sigma_{FIN}P(AA|FIN)P(FIN)]} [sigma_{E}P(BP| BA, E)P(E)]  [sigma_{IT}P(BA|IT)P(IT)] \n│')

    print('│────˃ De izquierda a derecha hacemos las multiplicaciones y marginalizaciones correspondientes:\n│')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos P(BA|IT) * P(IT)')
    x = FBAIT.multiply(FIT)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ │')

    print('│ │ Marginalización de la columna IT')
    x = x.marginalize(IT)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos P(BP| BA, E) * P(E)')
    x2 = FBPBAE.multiply(FE)
    print( f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x2.valores)}\n│ │')

    print('│ │ Multiplicamos sigma_{E} P(BP| BA, E) P(E)  sigma_{IT} P(BA|IT) P(IT) ')
    x2 = x.multiply(x2)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x2.valores)}\n│ │')

    print('│ │ Marginalización de la columna E')
    x2 = x2.marginalize(E)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x2.valores)}\n│ ╰─')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos P(AA|FIN) * P(FIN)')
    x3 = FAAFIN.multiply(FFIN)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x3.valores)}\n│ │')

    print('│ │ Marginalización de la columna FIN')
    x3 = x3.marginalize(FIN)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x3.valores)}\n│ ╰─')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos P(VP|AA,BA) sigma_{FIN} P(AA|FIN) P(FIN) ')
    x4 = FVPAABA.multiply(x3)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x4.valores)}\n│ ╰─')

    print('│ ╭──⊰')

    print('│ │ Multiplicamos sigma_{AA,BA} {P(VP|AA,BA) [sigma_{FIN}P(AA|FIN)P(FIN)]} [sigma_{E}P(BP| BA, E)P(E)]  [sigma_{IT}P(BA|IT)P(IT)] ')
    x5 = x4.multiply(x2)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x5.valores)}\n│ ╰─')

    print('│ ╭──⊰')

    print('│ │ Marginalización de la columna AA')
    x6 = x5.marginalize(AA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x6.valores)}\n│ │')

    print('│ │ Marginalización de la columna BA')
    x6 = x6.marginalize(BA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(x6.valores)}\n│ ╰─')

    print('│ ╭──⊰ Resultado:' + '\n│ │')
    f_VPBP_resultante = x6.normalize()
    print(f'│ │ Valores de los renglones obtenidos: {x.valores}\n│ │')
    print(f'│ │ ╚╍╍➣ Número de renglones obtenido: {len(f_VPBP_resultante.valores)}\n│ ╰─\n╰────')
    print(str(f_VPBP_resultante.valores))

def ejercicio4():
    # P(MP, AP, VP)
    dpcc = calcular_probabilidad_conjunta_completa()
    print('╭───────⟆ Obtenemos P(MP, AP, VP) usando marginalizaciones y la distrubución de probabilidad conjunta completa:\n│')
    print('│ ╭──⊰ Marginalizaciones: ' + '\n│ │')

    print('│ │ Marginalizando la columna MA.')
    x = dpcc.marginalize(MA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna AA.')
    x = x.marginalize(AA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna JA.')
    x = x.marginalize(JA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna JP.')
    x = x.marginalize(JP)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna BA.')
    x = x.marginalize(BA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna BP.')
    x = x.marginalize(BP)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna LI.')
    x = x.marginalize(LI)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna LF.')
    x = x.marginalize(LF)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna FIN.')
    x = x.marginalize(FIN)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna E.')
    x = x.marginalize(E)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna IT.')
    x = x.marginalize(IT)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Normalizamos el factor obtenido:' + '\n│ │')
    x = x.normalize()
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Reducciones:' + '\n│ │')

    print('│ │ MP = 1')
    x = x.reduce(MP, 1)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')

    print('│ │ AP = 1')
    x = x.reduce(AP, 1)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')

    print('│ │ VP = 1')
    x = x.reduce(VP, 1)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Resultado:' + '\n│ │')
    print(f'│ │ P(mp, ap, vp) = {x.valores[0]}\n│ ╰─\n╰────')

def ejercicio5():
    # P(LI = 1, mp, ap) = 0.08351981439999999 (Se obtuvo usando la regla de la cadena para redes de Bayes y factorizando).
    dpcc = calcular_probabilidad_conjunta_completa()
    print('╭───────⟆ Obtenemos P(mp, ap) usando marginalizaciones y la distrubución de probabilidad conjunta completa:\n│')
    print('│ ╭──⊰ Marginalizaciones: ' + '\n│ │')

    print('│ │ Marginalizando la columna MA.')
    x = dpcc.marginalize(MA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna AA.')
    x = x.marginalize(AA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna JA.')
    x = x.marginalize(JA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna JP.')
    x = x.marginalize(JP)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna BA.')
    x = x.marginalize(BA)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna BP.')
    x = x.marginalize(BP)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna VP.')
    x = x.marginalize(VP)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna LF.')
    x = x.marginalize(LF)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna FIN.')
    x = x.marginalize(FIN)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna E.')
    x = x.marginalize(E)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')
    
    print('│ │ Marginalizando la columna IT.')
    x = x.marginalize(IT)
    x = x.marginalize(LI)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Normalizamos el factor obtenido:' + '\n│ │')
    x = x.normalize()
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Reducciones:' + '\n│ │')

    print('│ │ MP = 1')
    x = x.reduce(MP, 1)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ │')

    print('│ │ AP = 1')
    x = x.reduce(AP, 1)
    print(f'│ │ ╚╍╍➣ Número de renglones obtenidos: {len(x.valores)}\n│ ╰─')

    print('│ ╭──⊰ Resultado:' + '\n│ │')
    print(f'│ │ P(mp, ap) = {x.valores[0]}\n│ │')
    print(f'│ │ ∴ P(LI = 1 | mp, ap) = P(LI = 1, mp, ap) / P(mp, ap) = {0.08351981439999999}/{x.valores[0]} = {0.08351981439999999/x.valores[0]}\n│ ╰─\n╰────')


if __name__ == '__main__':
    print('─'*50 + '｢ Ejercicio 3 ｣' + '─'*50)
    ecuacion_7_1()
    print()
    ecuacion_7_2()
    print()
    ecuacion_7_3()
    print()
    print('─'*50 + '｢ Ejercicio 4 ｣' + '─'*50)
    print()
    ejercicio4()
    print()
    print('─'*50 + '｢ Ejercicio 5 ｣' + '─'*50)
    print()
    ejercicio5()