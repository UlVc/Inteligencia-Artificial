package recocido;

import static java.lang.System.out;
import java.io.IOException;

/**
Clase para ejecutar un proceso de optimización usando recocido simulado.
@author Benjamin Torres Saavedra
@author Verónica E. Arriola
@version 0.1
*/
public class Main {
  public static void main(String []args) throws IOException{
    int interacciones = 10000;                 // Valor que determina cúantas veces se ejecutará el recocido simulado.
    float decaimento = 0.8f;                   // Valor que determina cómo ira decayendo la temperatura.
    CodificacionTSP tsp = new CodificacionTSP("ejemplares/ejemplo1.tsp"); // Problema del TSP que queremos resolver.
    Grafica ciudades = tsp.obtenerCiudades(); // Clase que modela el problema del TSP en el objeto Ciudades.
    Solucion s = new SolucionTSP("a", ciudades);  // Solución aleatoria del problema del TSP.
    float temperatura = 0.4f * s.evaluar();    // Temperatura inicial. Se multiplica la evaluación de la solución obtenida por 0.4, haciendo que ya no dependa del problema per se.

    /* Creamos el objeto de RecocidoSimulado. */
    RecocidoSimulado recocido = new RecocidoSimulado(s, temperatura, decaimento, interacciones);

    /* Ejecutamos el recocido simulado. */
    recocido.ejecutar();

    /* Imprimos la solución obtenida. */
    out.println("Solución obtenida:\n" + recocido); 
  }
}
