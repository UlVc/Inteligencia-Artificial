package recocido;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase base para representar soluciones del problema TSP
 * @author Ulrich Villavicencio Cárdenas
 */
public class SolucionTSP extends Solucion {
  private float valor;          // Calificación de la solución actual
  private Ciudad ciudadInicial; // Ciudad desde la que se quiere empezar el problema del TSP.
  private Grafica ciudades;
  private Stack<Ciudad> ruta;

  /**
  Metodo constructor de una solución a un problema.
  */
  public SolucionTSP(String ciudadInicial, Grafica ciudades) {
    this.valor = 6;
    this.ciudades = ciudades;
    this.ciudadInicial = this.ciudades.obtenerCiudad(ciudadInicial);
    this.ruta = new Stack<Ciudad>();
    Random random = new Random();

    this.ruta.push(this.ciudadInicial); // Agregamos a nuestra ruta la ciudad desde donde iniciaremos.

    // Generamos de manera aleatoria una ruta.
    for (int i = 0; i < ciudades.obtenerNumeroCiudades()-1; i++) {
      Ciudad[] vecinos = this.ruta.peek().obtenerConexiones().toArray(new Ciudad[0]);
      while (true) {
        int num = random.nextInt(vecinos.length);
        if (!this.ruta.contains(vecinos[num])) {
          this.ruta.push(vecinos[num]);
          break;
        }
      }
    }

    this.ruta.push(this.ciudadInicial); // Agregamos a nuestra ruta la ciudad desde donde iniciamos para finalizar esta misma.
  }

  /**
  Genera, a partir de una aproximacion de solución, una
  nueva dentro de la vecindad actual de forma aleatoria.
  @return una solución nueva generada al modificar la que llama
  al método.
  */
  public SolucionTSP siguienteSolucion() {
    /**
     * La idea para generar una siguiente solucón consiste en permutar dos aristas. Como la gráfica es completa, no ocurre ningún problema. 
     * Se modelo considerando a una solución como un ciclo Hamiltoniano para el problema del TSP.
     */
    SolucionTSP siguiente_solucion = new SolucionTSP(this.ciudadInicial.id, this.ciudades);;
    Ciudad[] ciudades = this.ruta.toArray(new Ciudad[0]);

    // Nos interesa un número aleatorio pero que este no sea los mismos números que representan la ciudad inicial.
    int num1 = ThreadLocalRandom.current().nextInt(1, ciudades.length-1);
    // Nos interesa otro número aleatorio que sea distinto a num1 y a los números que representan la ciudad inicial.
    int num2 = ThreadLocalRandom.current().nextInt(1, ciudades.length-1);
    while (true) {
      if (num1 != num2) { break; }
      num2 = ThreadLocalRandom.current().nextInt(1, ciudades.length-1);
    }

    // Hacemos el intercambio entre las dos ciudades obtenidas de manera aleatoria.
    Ciudad temp = ciudades[num1];
    ciudades[num1] = ciudades[num2];
    ciudades[num2] = temp;

    Stack<Ciudad> rutaPermutada = new Stack<Ciudad>(); // Nueva ruta formada a partir de la anterior.

    for (Ciudad ciudad : ciudades)
      rutaPermutada.push(ciudad);
    
    siguiente_solucion.setRuta(rutaPermutada);

    return siguiente_solucion;
  }

  /**
  Asigna una calificación (valor) a la solución que invoca el método
  @return evaluación de la solución
  */
  public float evaluar() {
    float evaluacion = 0;
    Stack<Ciudad> aux = (Stack<Ciudad>) this.ruta.clone();
    Ciudad a = aux.pop();
    Ciudad b;

    while (!aux.isEmpty()) {
      b = aux.pop();
      evaluacion += a.obtenerPeso(b);
      a = b;
    }

    this.valor = evaluacion;
    return evaluacion;
  }
  
  /**
  Método requerido para imprimir una solución.
  @return Representación de cadena para la solución
  */
  public String toString() {
    String representacion = "";
    Stack<Ciudad> aux = (Stack<Ciudad>) this.ruta.clone();
    Ciudad a = aux.pop();
    Ciudad b;
    while (!aux.isEmpty()) {
      b = aux.pop();
      representacion += a.obtenerId() + "------[" + a.obtenerPeso(b) + "]------" + b.obtenerId() + "\n";
      a = b;
    }

    return representacion + "Peso total:" + this.valor;
  }

  /**
   * Pone una nueva ruta.
   * @param rutaNueva Nueva ruta a colocar.
   */
  public void setRuta(Stack<Ciudad> rutaNueva) {
    this.ruta = rutaNueva;
  }
}
