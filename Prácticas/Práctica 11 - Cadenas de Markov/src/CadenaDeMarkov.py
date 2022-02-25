import numpy as np
import sympy

class CadenaDeMarkov:
    def __init__(self, estados, f, m_probabilidades):
        self.estados          = estados # Estados posibles
        self.f                = np.array(f) # Vector de probabilidad de iniciar en cada uno de los estados posibles.
        self.m_probabilidades = np.array(m_probabilidades) # Matriz de probabilidades

    def generar_secuencia(self, n):
        """
        Genera una secuencia de estados a partir del modelo de Márkov iniciado.
        :param n: Número de elementos que tendrá la secuencia.
        """
        return np.dot(np.linalg.matrix_power(self.m_probabilidades, n).T, self.f)

    def obtener_probabilidad(self, secuencia_estados, tiempo):
        """
        Obtiene la probabilidad de una cadena en un tiempo dado.
        :param secuencia_estados: Secuencia de estados que se quiere saber la probabilidad.
        :param tiempo: Tiempo dado.
        """
        secuencia = self.generar_secuencia(tiempo)
        estados_dict = {v : i for i, v in enumerate(self.estados)}
        p = 1

        for estado in secuencia_estados:
            p *= secuencia[estados_dict[estado]]

        return p

    def calcular_distribucion_limite(self):
        """Estima las probabilidades a largo plazo de cada uno de los estados."""
        _, no_col = self.m_probabilidades.shape
        z         = np.identity(3, dtype=float) - self.m_probabilidades.T
        res       = np.array([0] * no_col)

        # Reducimos la matriz obtenida a forma escalonada y con eso veremos si hay algún renglón que es combinación lineal de otrol.
        _, inds = sympy.Matrix(z).rref(iszerofunc=lambda x: abs(x)<1e-16)

        if len(inds) == no_col:
            return np.linalg.solve(z, res)

        # Hay un renglón que es combinación lineal de otro. Por lo que agregamos una nueva ecuación:
        # π1 + π2 + ... + πn = 1   (1)
        ind = [x for x in list(range(z.shape[1])) if x not in inds][0] # Índice del renglón que es combinación lineal de los otros.
        # Reemplazamos los valores de ambas matrices para representar a (1).
        z[ind]  = [1]*no_col
        res[ind] = 1

        return np.linalg.solve(z, res)
