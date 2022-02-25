package recocido;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
Clase con los métodos necesarios para implementar el método
de recocido simulado junto con la solución a un problema particular.

@author Benjamin Torres
@author Verónica E. Arriola
@version 0.1
*/
public class RecocidoSimulado {
  
  /** Es la calificación que otorga la heurística a la solución actual. */
  private float valor;
  
  private float temperatura;
  private float temperatura_final = 0.000001f;
  private float decaimiento;
  private int interacciones;
  
  /** Solución actual. */
  private Solucion solucionActual;
  
  /**
  Inicializa los valores necesarios para realizar 
  recocido simulado durante un numero determinado de iteraciones
  @param inicial, instancia de la clase para el problema particular que
  se quiere resolver.  Contine la propuesta inicial.
  @param temperatura, float con el valor actual 
  @param decaimiento, float que sera usado para hacer decaer el valor de temperatura
  */
  public RecocidoSimulado(Solucion inicial,
  float temperaturaInicial,
  float decaimiento,
  int interacciones) { //escoge los parametros necesarios para inicializar el algoritmo
    this.solucionActual = inicial;
    this.temperatura = temperaturaInicial;
    this.decaimiento = decaimiento;
    this.interacciones = interacciones;
    this.valor = inicial.evaluar();
  }
  
  /**
  Función que calcula una nueva temperatura en base a
  la anterior y el decaemiento usado.
  @return nueva temperatura
  */
  public float nuevaTemperatura() {
    return this.temperatura * this.decaimiento;
  }
  
  /**
  Genera y devuelve la solución siguiente a partir de la solución
  actual. Dependiendo de su valor,
  si es mejor o peor que la que ya se tenía,
  y de la probabilidad de elegir una solución peor, puede devolver
  una solución nueva o quedarse con la que ya se tenía.
  @return Solución nueva
  */
  public Solucion seleccionarSiguienteSolucion(){
    Solucion s = this.solucionActual.siguienteSolucion();
    return s;
  }
  
  /**
  Ejecuta el algoritmo con los parametros con los que fue inicializado
  devuelve una solucion.
  @return Solucion al problema
  * @throws IOException
  */
  public void ejecutar() throws IOException {
    Random random = new Random();
    BufferedWriter writer = new BufferedWriter(new FileWriter("datos.txt"));
    
    // Como la temperatura va decreciendo de manera geométrica, esta se aproxima a cero pero nunca llega a él (asintotica con la función 0). Por ende, definimos a la temperatura final como un valor muy cercano a cero.
    while (this.temperatura >= temperatura_final) {

      for (int i=0; i < this.interacciones; i++) {
        Solucion solucionCandidata = seleccionarSiguienteSolucion(); // Generamos una nueva solución a partir de la que ya tenemos.

        // Si delta > 0, entonces la solución candidata cuesta más que la actual. En caso contrario, la solución candidata cuesta menos y por ende, nos quedamos con ella.
        float a = solucionCandidata.evaluar();
        float b = solucionActual.evaluar();
        float delta = a - b;
        
        if (delta < 0 || random.nextDouble() < Math.exp(-delta/this.temperatura)) {
          this.solucionActual = solucionCandidata;
          this.valor = a;
        }
      }
      writer.write(this.temperatura + " " + this.valor + "\n");
      this.temperatura = nuevaTemperatura(); // Decrementamos la temperatura.
    }
    writer.close();
  }
  
  /**
  * Regresa una cadena del camino obtenido por el recocido simulado.
  */
  public String toString() {
    return this.solucionActual.toString();
  }
}
