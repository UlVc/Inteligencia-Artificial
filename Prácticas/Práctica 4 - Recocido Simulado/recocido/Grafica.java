package recocido;

import java.util.Hashtable;

/**
* Clase para moderlar una gráfica de ciudades.
* @author Ulrich Villavicencio Cárdenas
*/
public class Grafica {
  private Hashtable<String, Ciudad> ciudades; /** Conexiones de esta ciudad con otras y su peso respectivamente. */
  private int numeroCiudades;                 // Número de ciudades

  /**
  * Constructor de la clase Ciudad.
  * @param ciudad Id de la ciudad.
  */
  public Grafica() {
    this.ciudades = new Hashtable<String, Ciudad>();
  }
  
  /**
  * Agrega una ciudad a nuestro diccionario de ciudades.
  * @param ciudad Ciudad a agregar.
  */
  public void agregarCiudad(String ciudad) {
    if (!this.ciudades.containsKey(ciudad)) {
      this.ciudades.put(ciudad, new Ciudad(ciudad));
      this.numeroCiudades++;
    }
  }
  
  /**
  * Conecta dos ciudades con su respectivo peso.
  */
  public void conectarCiudades(String desde, String hasta, float peso) {
    this.ciudades.get(desde).conectarCiudad(this.ciudades.get(hasta), peso);
    this.ciudades.get(hasta).conectarCiudad(this.ciudades.get(desde), peso);
  }

  /**
   * @return Número de ciudades de la gráfica.
   */
  public int obtenerNumeroCiudades() {
    return this.numeroCiudades;
  }

  /**
   * Obtiene una ciudad dada su representación en cadena.
   * @param ciudad Representación en cadena de la ciudad deseada.
   * @return Objeto ciudad.
   */
  public Ciudad obtenerCiudad(String ciudad) {
    for (String key : this.ciudades.keySet()) {
      if (key.equals(ciudad)) 
        return this.ciudades.get(key);
    }

    return null;
  }

}
