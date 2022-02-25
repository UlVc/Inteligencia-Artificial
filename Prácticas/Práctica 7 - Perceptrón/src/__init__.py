from perceptron import Perceptron

import numpy as np

if __name__ == '__main__':
    and_datasets_X = [np.array([[0,0,0], [1,1,1]]),
                      np.array([[0,0,0], [0,0,1], [0,1,0], [0,1,1],
                                [1,0,0], [1,0,1], [1,1,0], [1,1,1]]),
                      np.array([[0,0,0], [0,0,1], [0,1,0]]),
                      np.array([[1,1,0], [1,0,0], [1,1,1]]),
                      np.array([[1,0,1], [0,1,1]])]
    and_datasets_Y = [np.array([0, 1]),
                      np.array([0,0,0,0,0,0,0,1]),
                      np.array([0,0,0]),
                      np.array([0,0,1]),
                      np.array([0,0])]
    or_datasets_X  = [np.array([[0,0,0], [1,1,1]]),
                      np.array([[0,0,0], [0,0,1], [0,1,0], [0,1,1],
                                [1,0,0], [1,0,1], [1,1,0], [1,1,1]]),
                      np.array([[0,0,0], [0,0,1], [0,1,0]]),
                      np.array([[1,1,0], [1,0,0], [1,1,1]]),
                      np.array([[1,0,1], [0,1,1]])]
    or_datasets_Y  = [np.array([0, 1]),
                      np.array([0,1,1,1,1,1,1,1]),
                      np.array([0,1,1]),
                      np.array([1,1,1]),
                      np.array([1,1])]
    pruebas_dataset = np.array([[0, 0, 0], [1, 0, 1], [0, 0, 1], [1, 1, 1]])

    perceptron = Perceptron()

    perceptron.entrenar(and_datasets_X[1], and_datasets_Y[1])
    print(perceptron.predecir(pruebas_dataset))
