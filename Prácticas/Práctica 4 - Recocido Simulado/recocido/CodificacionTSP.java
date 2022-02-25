package recocido;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Clase que modela un archivo de texto hacía un objeto Ciudades.
 * @author Ulrich Villavicencio Cárdenas
 */
public class CodificacionTSP {

  private Grafica g;
  private File archivo;
    
  public CodificacionTSP(String path) {
    this.g = new Grafica();
    try {
      this.archivo = new File(path);
      Scanner myReader = new Scanner(this.archivo);
      String data = myReader.nextLine();

      // La primera línea del archivo de texto contiene todas las ciudades.
      String[] ciudades = data.split(" ");
      for (String ciudad : ciudades)
        g.agregarCiudad(ciudad);
      
      // Empezamos a hacer las conexiones.
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
        String[] conexiones = data.split(" ");
        g.conectarCiudades(conexiones[0], conexiones[1], Float.parseFloat(conexiones[2]));
      }
  
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("(!) No se pudo abrir el archivo.");
      e.printStackTrace();
    } 
    
  }

  /**
   * @return Regresa el objeto Ciudades formado.
   */
  public Grafica obtenerCiudades() {
    return this.g;
  }

}
