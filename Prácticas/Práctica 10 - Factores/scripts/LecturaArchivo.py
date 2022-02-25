from Factor import Factor
from Variable import Variable

from ast import literal_eval
from sys import argv

def main():
    """
    Recibe la descripción de los factores en un archivo de texto y los convierte a su representación.
    El diccionario vars contiene todas las variables creadas, y las llaves son los nombres de estas variables.
    La lista f contiene todos los factores creados.
    """
    with open(argv[1], 'r') as f:
        variables = literal_eval(f.readline().replace('Variables: ', ''))
        factores  = literal_eval(f.readline().replace('Factores: ',  ''))
        valores   = literal_eval(f.readline().replace('Valores: ',   ''))

        vars = {} # Diccionario con las variables. La llave es el nombre de la variable.
        f    = [] # Lista de los factores.

        # Creamos los objetos Variable y los almacenamos en el diccionario vars:
        for v in variables:
            vars[v] = Variable(v, variables[v])
        
        # Creamos los objetos Factor  y los almacenamos en la lista f:
        for i, a in enumerate(factores):
            temp = [vars[x] for x in a]
            f.append(Factor(tuple(temp), valores[i]))

if __name__ == '__main__':
    main()
