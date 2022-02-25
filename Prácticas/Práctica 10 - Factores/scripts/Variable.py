class Variable:
    """Representación de una variable."""
    def __init__(self, nombre, valores_posibles):
        """
        :param nombre: Nombre de la variable.
        :param valores_posibles: Valores que puede tomar la variable.
        """
        self.nombre = nombre
        self.valores_posibles = valores_posibles

    def __str__(self):
        """Representación de la variable en cadena."""
        return self.nombre + ' : ' + str(self.valores_posibles)
