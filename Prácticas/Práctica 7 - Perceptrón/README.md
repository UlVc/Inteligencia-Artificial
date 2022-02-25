[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6473616&assignment_repo_type=AssignmentRepo)
# Práctica 7 - Perceptrón

Se crea un objeto Perceptrón; se le puede pasar el número de iteraciones y la taza de aprendizaje (por defecto valen 100 y 1.0 respectivamente). Una vez creado, se ejecuta el método entrenar() pasandole como argumentos las características y objetivo de cada una de ellas, cuando se ejecuta este método, se crean de manera aleatoria pesos entre el rango [-0.5, 0.5] (1 peso por cada columna de las características). Una vez entrenado el perceptrón, se puede hacer uso del método predecir(); toma como argumento un conjunto de entradas y regresa las predicciones para cada uno de ellas.

## Reporte

Usando ya sea el conjunto de entrenamiento dado del punto 1 o los tres conjuntos del punto 3, no se llega a los resultados deseados ya sea con cualquiera de las dos operaciones. En cambio, si se usa todas las combinaciones posibles (punto 2.), sí se llega a los resultados deseados.

Por lo tanto, es importante elegir bien los datos para entrenar; con ellos se van modificando los pesos de la red.

## Selección de conjuntos de entrenamiento

Se definen todos los conjuntos de entrenamiento deseados. Cada uno se encuentra ya sea en el arreglo and_datasets_X o or_datasets_X. Además, su salida de cada uno se encuentra tanto en and_datasets_Y o or_datasets_Y. Así, si si quiere usar el primer conjunto de entrenamiento ([[0,0,0,0],[1,1,1,1]]) con la operacón AND, se entrena al perceptrón con and_datasets_X[0] y and_datasets_Y[0] (de manera análoga para la operación OR y para cualquier otro conjunto de entrenamiento).

## Ejecución

Para ejecutar el programa, basta con estar en la carpeta src y ejecutar el comando
> python3 __init__.py
