package laberintos;

import processing.core.PApplet;
import java.util.Random;
import java.util.Stack;
import java.util.LinkedList;

/**
 * Clase que crea un laberinto con Processing.
 * @author Sara
 * @author Baruch
 */
public class Laberinto extends PApplet {
    int alto = 30;          // Altura (en celdas) de la cuadricula.
    int ancho = 50;         // Anchura (en celdas) de la cuadricula.
    int celda = 20;         // Tamanio de cada celda cuadrada (en pixeles).
    ModeloLaberinto modelo; // El objeto que representa el modelo del laberinto.
    
    @Override
    public void setup() {
        frameRate(60);
        background(50);
        modelo = new ModeloLaberinto(ancho, alto, celda);
    }

    @Override
    public void settings() {
        size(ancho * celda, (alto * celda));
    }
    
    /**
     * Pintar el mundo del modelo.
     */
    @Override
    public void draw() {
      for (int y = 0; y < alto; y++)
        for (int x = 0; x < ancho; x++) {
                  fill(204, 204, 204);
                  stroke(25,25,25);
                  rect(x * modelo.tamanio, y * modelo.tamanio, 
                        modelo.tamanio, modelo.tamanio);

                  // En caso de que las paredes de las celdas ya no se encuentren activas, 
                  // éstas se pintarán del color del fondo.
                  if(!modelo.mundo[x][y].pared_1) {
                      stroke(204, 204, 204);
                      line(x * modelo.tamanio, y * modelo.tamanio, 
                            ((x + 1) * modelo.tamanio), y * modelo.tamanio);                    
                  }
                  if(!modelo.mundo[x][y].pared_2) {
                      stroke(204, 204, 204);
                      line((x * modelo.tamanio) + modelo.tamanio, 
                            y * modelo.tamanio, (x + 1) * modelo.tamanio, 
                              (((y + 1) * modelo.tamanio)));
                  }
                  if(!modelo.mundo[x][y].pared_3) {
                      stroke(204, 204, 204);
                      line(x * modelo.tamanio, (y * modelo.tamanio) + modelo.tamanio, 
                            ((x + 1) * modelo.tamanio), ((y + 1) * modelo.tamanio));                    
                  }
                  if(!modelo.mundo[x][y].pared_4) {
                      stroke(204, 204, 204);
                      line(x * modelo.tamanio, y * modelo.tamanio, 
                            x * modelo.tamanio, ((y + 1) * modelo.tamanio));               
                  }
        }
    }

    /**
     * Clase que representa cada celda de la cuadricula.
     */
    class Celda{
        int celdaX; 
        int celdaY;
        boolean pared_1;
        boolean pared_2;
        boolean pared_3;
        boolean pared_4;
        boolean estado;
        
        /** Constructor de una celda.
          *@param celdaX Coordenada en x
          *@param celdaY Coordenada en y
          *@param estado Estado de la celda. true si no ha sido visitada, false en otro caso.
        */
        Celda(int celdaX, int celdaY, boolean estado) {
          this.celdaX = celdaX;
          this.celdaY = celdaY;
          this.estado = estado;
          this.pared_1 = true; // Booleano que representa la pared de arriba
          this.pared_2 = true; // Booleano que representa la pared de la derecha
          this.pared_3 = true; // Booleano que representa la pared de abajo
          this.pared_4 = true; // Booleano que representa la pared de la izquierda
        }
    }  

    /**
     * Clase que modela el laberinto, es decir, crea el mundo del laberinto.
     */
    class ModeloLaberinto {
        int ancho, alto;  // Tamaño de celdas a lo largo y ancho de la cuadrícula.
        int tamanio;      // Tamaño en pixeles de cada celda.
        Celda[][] mundo;  // Mundo de celdas
        int direccion;
        Random random;
        Stack<Celda> stack;
        Celda actual;
        
      /** Constructor del modelo
        @param ancho Cantidad de celdas a lo ancho en la cuadricula.
        @param ancho Cantidad de celdas a lo largo en la cuadricula.
        @param tamanio Tamaño (en pixeles) de cada celda cuadrada que compone la cuadricula.
      */
      ModeloLaberinto(int ancho, int alto, int tamanio) {
        this.ancho = ancho;
        this.alto = alto;
        this.tamanio = tamanio;
        mundo = new Celda[ancho][alto];
        random = new Random();
        stack = new Stack<Celda>();

        for(int y = 0; y < alto; y++)
          for(int x = 0; x < ancho; x++)
            mundo[x][y] = new Celda(x, y, true);

        // Obtenemos una posición aleatoria para la celda inicial.
        int a = random.nextInt(ancho);
        int b = random.nextInt(alto);

        // La celda actual al inicio de la ejecución es aleatoria.
        actual = mundo[a][b];

        // Agregamos la celda al stack (paso 2).
        stack.push(actual);

        do {
          // Marcamos como visitada la celda obtenida (paso 2).
          actual.estado = false;

          // Obtenemos las casillas adyacentes de la casilla actual.
          LinkedList<Celda> celdasAdyacentesDisponibles = 
              obtenerCeldasAdyacentesDisponibles(actual);

          // Vemos si hay alguna casilla adyacente (se elige de manera aleatoria) (paso 3).
          if (celdasAdyacentesDisponibles.size() > 0) {
            Celda ady = celdasAdyacentesDisponibles
                .get(random.nextInt(celdasAdyacentesDisponibles.size()));

            int x = actual.celdaX;
            int y = actual.celdaY;

            // Vemos cómo están colocadas (paso 3).
            // ^T indica transpuesta, tal cual como en matrices.
            if (y+1 == ady.celdaY) { // ((x, y) (ady.x, ady.y))^T
              ady.pared_1 = false;
              actual.pared_3 = false;
            }
            if (y-1 == ady.celdaY) { // ((ady.x, ady.y) (x, y))^T
              ady.pared_3 = false;
              actual.pared_1 = false;
            }
            if (x-1 == ady.celdaX) { // (ady.x, ady.y)(x, y)
              ady.pared_2 = false;
              actual.pared_4 = false;
            }
            if (x+1 == ady.celdaX) { // (x, y)(ady.x, ady.y)
              ady.pared_4 = false;
              actual.pared_2 = false;
            }

            // Se empuja al stack la celda adyacente, pues ya fue visitada.
            stack.push(ady);
            // Se actualiza la celda actual con la que se está trabajando.
            actual = ady;
          } else { // No hay celdas por visitar.
            // Se expulsa una celda de la pila para repetir el algoritmo hasta tener la pila vacía.
            actual = stack.pop();
          }
        } while (!stack.empty()); // Se repite todo el algoritmo hasta haber visitado todas las casillas.
        
      }

      /**
       * Función que obtiene las celdas adyacentes que se pueden visitar de una celda.
       * @param celda celda que se desea obtener sus celdas adyacentes.
       */
      private LinkedList<Celda> obtenerCeldasAdyacentesDisponibles(Celda celda) {
        LinkedList<Celda> adyacentes = new LinkedList<Celda>();
        int x = celda.celdaX;
        int y = celda.celdaY;

        // Buscamos celdas adyacentes tales que sean válidas dado el tamaño del laberinto.
        if (x+1 <= ancho-1)
          if (mundo[x+1][y].estado)
            adyacentes.add(mundo[x+1][y]);

        if (x-1 >= 0)
          if (mundo[x-1][y].estado)
            adyacentes.add(mundo[x-1][y]);

        if (y+1 <= alto-1)
          if (mundo[x][y+1].estado)
            adyacentes.add(mundo[x][y+1]);

        if (y-1 >= 0)
          if (mundo[x][y-1].estado)
            adyacentes.add(mundo[x][y-1]);

        return adyacentes;
      }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         PApplet.main(new String[] { "laberintos.Laberinto" });
    } 
}