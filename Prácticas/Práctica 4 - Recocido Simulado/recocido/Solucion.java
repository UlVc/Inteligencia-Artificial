package recocido;

import java.util.Random;
import java.util.Stack;

/**
Clase base para representar soluciones que pueden ser usadas
en el método de recocido simulado.

@author Benjamin Torres
@author Verónica E. Arriola
@version 0.1
*/
abstract class Solucion {
  private float valor;          // Calificación de la solución actual
  private Ciudad ciudadInicial; // Ciudad desde la que se quiere empezar el problema del TSP.
  private Grafica ciudades;
  private Stack<Ciudad> ruta;

  /**
  Metodo constructor de una solución a un problema.
  */
  public Solucion() {}

  /**
  Genera, a partir de una aproximacion de solución, una
  nueva dentro de la vecindad actual de forma aleatoria.
  @return una solución nueva generada al modificar la que llama
  al método.
  */
  abstract Solucion siguienteSolucion();

  /**
  Asigna una calificación (valor) a la solución que invoca el método
  @return evaluación de la solución
  */
  abstract float evaluar();
  
  /**
  Método requerido para imprimir una solución.
  @return Representación de cadena para la solución
  */
  public abstract String toString();
}
