package recocido;

import java.util.Hashtable;
import java.util.Set;

/**
* Clase para moderlar una ciudad.
* @author Ulrich Villavicencio Cárdenas
*/
public class Ciudad {
  
  String id;                           // Id de la ciudad.
  Hashtable<Ciudad, Float> conexiones; // Conexiones de esta ciudad con otras y su peso respectivamente.
  
  /**
  * Constructor de la clase Ciudad.
  * @param ciudad Id de la ciudad.
  */
  public Ciudad(String ciudad) {
    this.id = ciudad;
    this.conexiones = new Hashtable<Ciudad, Float>();
  }
  
  /**
  * Conecta esta ciudad dada otra ciudad con su peso respectivo.
  * @param vecino Ciudad a conectar.
  * @param peso Peso respectivo.
  */
  public void conectarCiudad(Ciudad vecino, float peso) {
    this.conexiones.put(vecino, peso);
  }
  
  /**
  * Obtiene todas las ciudades a las que está conectada esta ciudad.
  */
  public Set<Ciudad> obtenerConexiones() {
    return this.conexiones.keySet();
  }
  
  /**
  * Obtiene el id de esta ciudad.
  */
  public String obtenerId() {
    return this.id;
  }
  
  /**
  * Obtiene el peso que hay entre esta ciudad y otra ciudad dada.
  * @param vecino Ciudad a la que se quiere saber el peso entre ella y esta ciudad.
  */
  public float obtenerPeso(Ciudad vecino) {
    return this.conexiones.get(vecino);
  }
  
}
