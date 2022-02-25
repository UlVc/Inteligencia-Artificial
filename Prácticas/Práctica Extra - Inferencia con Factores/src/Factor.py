from Variable import Variable

from itertools import product

class Factor:
    """Representación de un factor."""
    def __init__(self, alcance, valores):
        """
        :param alcance: Alcance del factor.
        :param valores: Valores del factor.
        """
        self.alcance = alcance # Lista de objetos de clase Variable.
        self.valores = valores # Lista de valores asociados a cada renglón. Debe asegurarse que se encuentre en el orden correspondiente a cada asignación indicada por la tabla de valores del objeto.

    def set_valores(self, valores):
        """
        Cambia los valores del factor.
        :param valor: nuevos valores
        """
        self.valores = valores

    def multiply(self, phi):
        """
        Multiplica el factor con otro dado.
        :param phi: Factor a multiplicar.
        """
        nuevo_alcance  = list(self.alcance) + [x for x in phi.alcance if x not in self.alcance] # Unimos ambos alcances.
        nuevo_factor   = Factor(tuple(nuevo_alcance), self.valores)
        nuevos_valores = []

        val1, val2 = {}, {}

        for r in nuevo_factor.obtener_renglones(): # Iteramos sobre los renglones del nuevo factor.
            for i in range(len(nuevo_alcance)):
                # Colocamos las variables en diccionarios dependiendo a qué factor correspondan.
                # val1 representa a un renglón del actual factor y val2 representa a un renglón del factor phi dado.
                if nuevo_alcance[i] in self.alcance:
                    val1[nuevo_alcance[i]] = r[i]

                if nuevo_alcance[i] in phi.alcance:
                    val2[nuevo_alcance[i]] = r[i]

            p1 = self.obtener_indice(val1) # Índice del renglón de este factor.
            p2 = phi.obtener_indice(val2)  # Índice del renglón del factor dado.
            nuevos_valores.append(self.valores[p1] * phi.valores[p2])

        nuevo_factor.set_valores(nuevos_valores)

        return nuevo_factor
    
    def reduce(self, variable, valor):
        """
        Reduce el factor.
        :param variable: Variable dada.
        :param valor: Valor de la variable dada.
        """
        nuevo_alcance = tuple(filter(lambda x: x.nombre != variable.nombre, self.alcance)) # Removemos variable del alcance de este factor.
        nuevo_factor  = Factor(nuevo_alcance, self.valores)

        renglones         = self.obtener_renglones()
        indice_variable   = self.alcance.index(variable) # Índice del parámetro variable en el alcance de este factor.
        val_comun         = list(filter(lambda x: x[indice_variable] == valor, renglones)) # Filtramos los renglones que tengan los mismos valores que el dado.
        renglones_validos = [dict(zip(self.alcance, x)) for x in val_comun] # Renglones que son válidos usando la restricción dada.
        indices           = [self.obtener_indice(x) for x in renglones_validos] # Índices de todos los renglones que cumplen con lo deseado.
        nuevos_renglones  = [self.valores[x] for x in indices] # Renglones deseados.

        nuevo_factor.set_valores(nuevos_renglones)
    
        return nuevo_factor#Factor(nuevo_alcance, nuevos_renglones)

    def normalize(self):
        """Normaliza el factor."""
        suma = sum(self.valores)
        nuevos_valores = [x/suma for x in self.valores]
        
        return Factor(self.alcance, nuevos_valores)
    
    def marginalize(self, variable):
        """
        Marginaliza el factor.
        :param variable: Variable dada.
        """
        nuevo_alcance   = tuple(filter(lambda x: x.nombre != variable.nombre, self.alcance)) # Removemos variable del alcance de este factor.
        nuevo_factor    = Factor(nuevo_alcance, self.valores)
        indice_variable = self.alcance.index(variable) # Índice del parámetro variable en el alcance de este factor.
        nuevos_valores  = [] # Lista de los nuevos valores para el nuevo factor obtenido tras marginalizar.

        for r in nuevo_factor.obtener_renglones():
            suma = 0

            # Calculamos la suma de todos los valores posibles de la variable dada para el renglon r dado:
            for x in self.alcance[indice_variable].valores_posibles:
                temp  = list(r)
                temp.insert(indice_variable, x)
                temp  = tuple(temp) # Insertamos el valor de la variable en la posición que debe de ir.
                suma += self.valores[self.obtener_indice(dict(zip(self.alcance, temp)))] # Sumamos el valor del renglón dado.

            nuevos_valores.append(suma)

        nuevo_factor.set_valores(nuevos_valores)

        return nuevo_factor

    def obtener_indice(self, dicc):
        """
        Obtiene el índice en la tabla de valores. En este método se preparan las variables para poder usar el polinomio de redireccionamiento.
        :param dicc: Diccionario con las variables y sus valores respectivamente. Este representa un renglón en la tabla.
        """
        return self.polinomio_redireccionamiento({ var: val for var, val in dicc.items() if var in self.alcance})

    def polinomio_redireccionamiento(self, dicc):
        """
        Obtiene el índice en la tabla de valores.
        :param dicc: Diccionario con las variables y sus valores respectivamente. Este representa un renglón en la tabla.
        """
        indice_resultado, tamanio_arreglo = 0, 1

        for v in reversed(self.alcance):
            indice_iteracion  = self.obtener_indice_var(v, dicc[v])
            indice_resultado += tamanio_arreglo * indice_iteracion
            tamanio_arreglo  *= self.obtener_longitud(v)

        return indice_resultado

    def obtener_indice_var(self, variable, etiqueta):
        """
        Obtiene el índice del valor de la variable dada.
        :param variables: Variable dada. 
        :param etiqueta: Valor de la variable.
        """
        return variable.valores_posibles.index(etiqueta)

    def obtener_longitud(self, variable):
        """Obtiene todos los renglones del factor. Se obtienen por orden."""
        return len(variable.valores_posibles)

    def obtener_renglones(self):
        """Obtiene todos los renglones del factor. Se obtienen por orden."""
        return list(product(*[x.valores_posibles for x in self.alcance]))
