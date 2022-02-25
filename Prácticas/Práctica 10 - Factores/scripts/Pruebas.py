from Factor   import Factor
from Variable import Variable

"""
Pruebas para marginalización, reducción, normalización y multiplicación de factores.
Se usaron los ejemplos de las presentaciones de la Dra. Verónica.
"""
est = Variable('Estacion', ['Primavera', 'Verano', 'Otoño', 'Invierno'])
ll  = Variable('Lluvia', [0, 1])

f  = Factor((est, ll), [0.1875, 0.0625, 0.075, 0.175, 0.175, 0.075, 0.2, 0.05])
fm = f.marginalize(est)
fr = f.reduce(est, 'Primavera')
fn = fr.normalize()

print('Valores del factor marginalizando Estación:')
print(fm.valores)
print('Valores del factor reduciendo Estación = Primavera:')
print(f.reduce(est, 'Primavera').valores)
print('Valores del factor normalizando el factor obtenido anteriormente:')
print(fn.valores)

fmult1 = Factor((est, ll), [0.75, 0.25, 0.3, 0.7, 0.7, 0.3, 0.8, 0.2])
fmult2 = Factor((est,), [0.25]*4)
fmultres = fmult1.multiply(fmult2)

print('Valores del factor resultante de multiplicar ambos factores:')
print(fmultres.valores)
