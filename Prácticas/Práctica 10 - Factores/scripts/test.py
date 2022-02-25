from Factor   import Factor
from Variable import Variable

"""
Pruebas para marginalización, reducción, normalización y multiplicación de factores.
Se usaron los ejemplos de las presentaciones de la Dra. Verónica.
"""

r = Variable('R', [0, 1])
t = Variable('T', [0, 1])
a = Variable('A', [0, 1])
j = Variable('J', [0, 1])
m = Variable('M', [0, 1])

R = Factor((r,), [.999, .001])
T = Factor((t,), [1, .998])
JA = Factor((a,j), [1, .05, 1, .9])
MA = Factor((m,a), [1, 1, .01, .7])
ART = Factor((r, t, a), [0.999, .001, 0.71, .29, 0.06, .94, 0.05, .95])
print(R.multiply(ART).valores)
#jama = JA.multiply(MA)
#artjama = ART.multiply(jama)