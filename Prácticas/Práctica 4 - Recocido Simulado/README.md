
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6044346&assignment_repo_type=AssignmentRepo)

# Recocido Simulado

## Implementación

  

Se creó 3 nuevas clases: Ciudad, CodificiacionTSP y Grafica. La primera y la última modelan una ciudad y la gráfica de todas las ciudades respectivamente.

La clase CodificiacionTSP, como su nombre lo dice, codifica un ejemplar del problema TSP. El constructor recibe el path del archivo a leer. La codificación se realiza en un archivo de texto y tiene que contener lo siguiente:

  

- La primera línea contendrá las ciudades de la gráfica separadas por espacios.

- El resto de las líneas estarán formadas cada una por 2 ciudades separadas por un espacio y un número igual separado por un espacio. Esto representa que se quiere conectar ambas ciudades con cierto peso. En este caso no estamos trabajando con gráficas dirigidas, por lo que no es necesario poner simetrías, i.e., si ya se colocó la línea, por ejemplo, n m p, no es necesario poner la línea m n p; el programa automáticamente las conecta tal cual como una gráfica no dirigida.

## Extra

En el repositorio, además de los archivos necesarios de la práctica y el ejemplar, se agregarón dos archivos: datos.txt y grafica.png. El archivo de texto fue generado con el recocido simulado y la imágen representa la gráfica obtenida dado los datos del archivo de texto. Esta gráfica muestra cómo se va comportando el coste de la solución del TSP para el ejemplo1.tsp contenido en la carpeta ejemplares (no se imprime el eje de temperatura porque no se ve bien, pero la temperatura va decrementando). Por el comportamiento del algoritmo, mientras más baja sea la temperatura, menos saltos grandes de exploración va a haber hacia vecindades y se irá ajustando cada vez hacía un mínimo local obtenido.

## Compilación
> javac -d ./classes -cp lib/core.jar:. recocido/*.java

## Ejecución
> java -cp ./classes:lib/core.jar recocido.Main