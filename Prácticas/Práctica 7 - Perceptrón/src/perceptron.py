import numpy as np

class Perceptron:
    """Implementación de un perceptrón"""
    def __init__(self, epochs=100, alpha=1.0):
        """
        :param pesos: Pesos iniciales.
        :param umbral: Umbral.
        :param epochs: Número de iteraciones para el entrenamiento.
        :param alpha: Taza de aprendizaje.
        """
        self.pesos = []
        self.umbral = np.random.uniform(-0.5, 0.5, 1)[0]
        self.epochs = epochs
        self.alpha = alpha

    def activar(self, X):
        """Activa el perceptrón para un ejemplar.
        :param X: Ejemplar a usar en el perceptrón.
        """
        def step_function(x): return 1 if x >= 0 else 0

        z = self.pesos.T.dot(X)

        return step_function(z-self.umbral)

    def entrenar(self, X, y):
        """Entrena el perceptrón con datos dados.
        :param X: Características.
        :param y: Objetivo.
        """
        X = np.insert(X, 0, 1, axis=1) # Añadimos los sesgos.
        self.pesos  = np.random.uniform(-0.5, 0.5, X.shape[1])

        for _ in range(self.epochs):
            for xi, salida_deseada in zip(X, y):
                e = salida_deseada - self.activar(xi)
                delta = self.alpha * xi * e
                self.pesos += delta

    def predecir(self, X):
        """Devuelve los valores predecidos usando un conjunto de entradas.
        :param X: Conjunto de entradas.
        """
        y = []
        X = np.insert(X, 0, 1, axis=1) # Añadimos los sesgos.
        for xi in X:
            y.append(self.activar(xi))

        return y
