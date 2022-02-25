[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6227122&assignment_repo_type=AssignmentRepo)
# ia-proyecto
Máquina de inferencias

Código auxiliar con Python 3.
No se permite transferir este código ni hacer públicas las soluciones.

## Tareas realizadas:

1. El dibujo del estado inicial se encuentra a la misma altura que este README y el nombre de la imágen es Dibujo del estado inicial del problema.
2. Los objetos del dominio y del problema son declarados en el archivo __init__.py dentro de la carpeta scripts/pclasica. Cada paso está documentado. Además, se generan archivos de texto donde se coloca el dominio y el problema dado respecivamente; estos archivos se encuentran en la misma altura que este README.
3. Se agregó la clase Planner en el archivo strips.py. Este contiene varias funciones auxiliares con cada una de ellas documentadas. Se ven todas las acciones posibles y por cada una de ellas se intenta ver si es aplicable o no; si sí lo es, se regresan todas las posibles sustituciones y en caso contrario se dice que no se puede aplicar. Para usar esta función usamos las siguientes líneas de código:
>planner = Planner(dominio, problema)
planes = planner.acciones_aplicables()

donde el dominio y el problema ya fueron definidos en el punto 2. Nuevamente, todo esto se encuentra en el archivo __init__.py dentro de la carpeta scripts/pclasica.

4. Se encuentra dentro de la clase Planner creada y el método se llama satisiface_meta().
5. Las pruebas se muestran a continuación:

Ejecutaremos el problema y dominio ya dado y buscaremos las posibilidades usando la acción toma. Notemos del dibujo, que solo se pueden tomar los contenedores cc y cf, pues son los que están hasta arriba y, además, como hay 2 grúas disponibles, entonces es una combinación entre las dos grúas y los dos contenedores. Por lo tanto, solo hay 4 posibilidades. Y, en efecto, ejecutando el código 
> planes = planner.accion_aplicable(planner.obtener_acciones()[0])
 for p in planes:
     print('----')
     for pred in p:
         print(pred)

Obtenemos que solo hay 4 posibilidades y en cada una de estas se muestran los predicados a usar en la acción toma. 

Por como está definido el dominio, el código
> planner.obtener_acciones()[0]

dará la acción toma y usando el índice 1 dará la acción pon. Por lo que, ahora veamos qué se puede hacer con la acción pon usando el problema y dominio ya dado: Como todas las grúas están libres, no hay nada con qué usar la acción, ya que como precondición de esta acción, algún contenedor tiene que estar sosteniendo algún contenedor, cosa que no es así. Y, en efecto, ejecutando el código 
> planes = planner.accion_aplicable(planner.obtener_acciones()[1])
 for p in planes:
     print('----')
     for pred in p:
        print(pred)

Obtenemos que no hay ninguna posibilidad.

Ambos códigos se tienen que ejecutar en el archivo init justo abajo de la definición del planificador (objeto de la clase Planner).

## Ejecución

Estando dentro de la carpeta proyecto-parte-i-UlVc ejecutamos el comando
> python3 scripts/pclasica/__init__.py

Se generarán 3 archivos de texto a la altura de la carpeta scripts: 2 son el dominio y el problema definido y el otro es un archivo de texto con las acciones a realizar para llegar a la meta del problema.

## Parte 2

En la parte dos se agrega una búsqueda en amplitud para encontrar las acciones necesarias para llegar a la solución del problema. Para esto se creo una clase adicional llamada BFS. En el archivo __init__.py se muestra cómo hacer para que se guarden las acciones en un archivo de texto dado. Como se dijo en la sección de ejecución, uno de los archivos creados es un archivo de texto con las acciones a realizar para llegar a la meta del problema; por defecto tiene como nombre acciones.txt. Este archivo tiene las acciones en el formato nombre_accion: lista_de_predicados_a_aplicar y se tienen que aplicar de arriba para abajo para llegar a la meta deseada.

### Observaciones

Se implementó BFS, por lo que se explora nivel por nivel, haciendo que cada vez se necesite más tiempo y memoria. Por lo mismo, no se pudo llegar a un plan para la solución del problema dado. Lo que tuve que hacer fue aligerar la carga y nada más pedir que 1 bloque esté en p2 y el otro en q2. Se requieren dos niveles para que un bloque se mueva: en un nivel una grúa lo toma y en el siguiente nivel esta grúa lo pone en la pila deseada (suponiendo en ambos casos que es posible). Así, para 3 bloques movidos se requiere una exploración de 4 niveles y, para el ejemplo dado, se requiere una exploración en el nivel 12. Cabe destacar que cada nivel se explora en tiempo exponencial al estar trabajando con combinaciones de acciones, por lo que no es nada eficiente.

Intenté mejorar este algoritmo usando A* pero no pude generar una herística buena. Me gustaría saber si efectivamente es buena idea usar A* o no y si sí qué heurística se debería de usar.

### Nota

Si este README se está viendo en un visualizador de markdown, el archivo __init__ se muestra sin sus guines bajos y en negritas. Pero realmente es el archivo init con dos guiones bajos tanto en el principio como al final.
