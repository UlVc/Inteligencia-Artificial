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

        if set(self.alcance).isdisjoint(phi.alcance): # Vemos si tienen variables en común. 
            # Como no hay variables en común, hacemos un "todos contra todos":
            for e in product(self.valores, phi.valores):
                nuevos_valores.append(e[0] * e[1])

            nuevo_factor.set_valores(nuevos_valores)

            return nuevo_factor

        for r in nuevo_factor.obtener_renglones(): # Iteramos sobre los renglones del nuevo factor.
            d  = dict(zip(self.alcance, r)) # Diccionario con las variables del renglón.
            p1 = self.obtener_indice(d) # Índice del renglón dado en este factor.
            p2 = phi.obtener_indice(d) # Índice del renglón dado en factor.
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
        variables_diccionario = dicc.keys()
        variables_en_comun = [x for x in variables_diccionario if x in self.alcance]

        return self.polinomio_redireccionamiento(variables_en_comun, dicc)

    def polinomio_redireccionamiento(self, variables, dicc):
        """
        Obtiene el índice en la tabla de valores.
        :param variables: Variables 
        :param dicc: Diccionario con las variables y sus valores respectivamente. Este representa un renglón en la tabla.
        """
        etiqueta = dicc[variables[0]]

        if len(variables) == 1:
            return self.obtener_indice_var(variables[0], etiqueta)

        mult = 1
        for v in variables[1:]:
            mult *= self.obtener_longitud(v)

        return (self.obtener_indice_var(variables[0], etiqueta)*mult) + self.polinomio_redireccionamiento(variables[1:], dicc)

    def obtener_indice_var(self, variable, etiqueta):
        """
        Obtiene el índice del valor de la variable dada.
        :param variables: Variable dada. 
        :param etiqueta: Valor de la variable.
        """
        for var in self.alcance:
            if var.nombre == variable.nombre:
                return var.valores_posibles.index(etiqueta)

    def obtener_longitud(self, variable):
        """Obtiene todos los renglones del factor. Se obtienen por orden."""
        for var in self.alcance:
            if var.nombre == variable.nombre:
                return len(var.valores_posibles)

    def obtener_renglones(self):
        """Obtiene todos los renglones del factor. Se obtienen por orden."""
        return list(product(*[x.valores_posibles for x in self.alcance]))
