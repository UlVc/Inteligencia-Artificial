/*
 * TODO: Completar el código faltante.
 */
package termitas;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

/**
 * @autor rodrigo
 * @author blackzafiro
 */

// Para ejecutar, desde la carpeta superior ejecuta los siguientes comandos:
// $ javac -cp .:lib/core.jar termitas/Termitas.java
// $ java -cp .:lib/core.jar termitas/Termitas

public class Termitas extends PApplet {

    PFont fuente;  // Fuente para mostrar texto en pantalla

    // Propiedades del modelo de termitas.
    int alto = 100;         // Altura (en celdas) de la cuadricula.
    int ancho = 150;        // Anchura (en celdas) de la cuadricula.
    int celda = 4;          // Tamanio de cada celda cuadrada (en pixeles).
    int termitas = 140;      // Cantidad de termitas dentro del modelo.
    float densidad = 0.2f;   // Proporcion de astilla en el modelo (con probabilidad de 0 a 1).
    ModeloTermitas modelo;  // El objeto que representa el modelo de termitas.

    /**
     * Configuración inicial de la applet.
     */
    @Override
    public void setup() {
        background(50);
        fuente = createFont("Arial", 12, true);
        modelo = new ModeloTermitas(ancho, alto, celda, termitas, densidad);

        // Preprocesamiento
        //for(int i = 0; i < 5000; i++)
        //modelo.evolucion2();
    }

    /**
     * Asigna el tamaño de la ventana.
     */
    @Override
    public void settings() {
        size(ancho * celda, (alto * celda) + 32);
    }

    /**
     * Pintar el mundo del modelo (la cuadricula y las astillas) en cada cuadro
     * de la animación.
     */
    @Override
    public void draw() {
        // Las astillas se representan por el valor true del estado de cada Celda.
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if (modelo.mundo[i][j].estado) {
                    fill(255, 210, 0);
                } else {
                    fill(50);
                }
                rect(j * modelo.tamanio, i * modelo.tamanio, modelo.tamanio, modelo.tamanio);
            }
        }

        // Dibujar las termitas.
        // Cada termita puede ser de color verde, si no carga astilla o roja en caso contrario.
        for (Termita t : modelo.termitas) {
            if (t.cargando) {
                fill(255, 0, 0);
                rect(t.posX * modelo.tamanio, t.posY * modelo.tamanio, modelo.tamanio, modelo.tamanio);
            } else {
                fill(0, 255, 0);
                rect(t.posX * modelo.tamanio, t.posY * modelo.tamanio, modelo.tamanio, modelo.tamanio);
            }
        }

        // Pintar informacion del modelo en la parte inferior de la ventana.
        fill(50);
        rect(0, alto * celda, (ancho * celda), 32);
        fill(255);
        textFont(fuente, 10);
        text("Cuadricula: " + modelo.ancho + " x " + modelo.alto, 5, (alto * celda) + 12);
        text("Generacion " + modelo.generacion, 128, (alto * celda) + 12);
        text("Termitas: " + modelo.termitas.size(), 5, (alto * celda) + 24);
        text("Proporcion de astillas: " + densidad, 128, (alto * celda) + 24);

        // Actualizar el modelo a la siguiente generacion.
        modelo.evolucion1();
        //modelo.evolucion2();
        //modelo.evolucion3();
    }

    // --- Clase Celda ---
    /**
     * Representación de cada celda de la cuadrícula.
     */
    class Celda {

        int celdaX, celdaY;
        boolean estado;

        /**
         * Constructor de una celda
         *
         * @param celdaX Coordenada en x
         * @param celdaY Coordenada en y
         * @param estado True para activada (espacio con astilla), false en otro
         * caso.
         */
        Celda(int celdaX, int celdaY, boolean estado) {
            this.celdaX = celdaX;
            this.celdaY = celdaY;
            this.estado = estado;
        }
    }

    // --- Clase Termita ---
    /**
     * Representa cada una de las termitas del modelo.
     */
    class Termita {

        int posX, posY;  // Coordenadas de la posicion de la termita
        int direccion;   // Valor entre 0 y 7 para indicar dirección de movimiento
        boolean cargando;  // True si está cargando una astilla, false en caso contrario.

        /**
         * Constructor de una termita
         *
         * @param posX Indica su posicion en el eje X
         * @param posX Indica su posicion en el eje Y
         * @param direccion Indica la dirección en la que mira.
         *    -----------
         *   | 0 | 1 | 2 |
         *   |-----------|
         *   | 7 |   | 3 |
         *   |-----------|
         *   | 6 | 5 | 4 |
         *    -----------
         */
        Termita(int posX, int posY, int dirección) {
            this.posX = posX;
            this.posY = posY;
            this.direccion = direccion;
            this.cargando = false;
        }
    }

    // --- Clase ModeloTermitas ---
    /**
     * Representa el automata celular que genera autorganizacion en una colonia
     * de termitas.
     */
    class ModeloTermitas {

        int ancho, alto;  // Tamaño de celdas a lo largo y ancho de la cuadrícula.
        int tamanio;      // Tamaño en pixeles de cada celda.
        int generacion;   // Conteo de generaciones (cantidad de iteraciones) del modelo.
        Celda[][] mundo;  // Mundo de celdas donde habitan las astillas.
        ArrayList<Termita> termitas;  // Todas las termitas del modelo.
        Random rnd = new Random();    // Auxiliar para decisiones aleatorias.

        /**
         * Constructor del modelo
         *
         * @param ancho Cantidad de celdas a lo ancho en la cuadricula.
         * @param ancho Cantidad de celdas a lo largo en la cuadricula.
         * @param tamanio Tamaño (en pixeles) de cada celda cuadrada que compone
         * la cuadricula.
         * @param cantidad Numero de termitas dentro de la cuadricula.
         * @param densidad Probabilidad para que una celda (en el estado
         * inicial) contenga una astilla. El valor esta dado entre 0 y 1.
         */
        ModeloTermitas(int ancho, int alto, int tamanio, int cantidad, float densidad) {
            this.ancho = ancho;
            this.alto = alto;
            this.tamanio = tamanio;
            this.generacion = 0;
            //Inicializar mundo (usar densidad)
            mundo = new Celda[alto][ancho];
            int astillas = 0;
            boolean crea;
            for (int i = 0; i < alto; i++) {
                for (int j = 0; j < ancho; j++) {
                    crea = rnd.nextFloat() < densidad ? true : false;
                    mundo[i][j] = new Celda(i, j, crea);
                    if (crea) {
                        astillas++;
                    }
                }
            }
            //Inicializar termitas (usar cantidad)
            termitas = new ArrayList<Termita>();
            int x, y;
            if (cantidad >= (ancho * alto) - astillas) {
                throw new IllegalArgumentException("No caben tantas termitas");
            }
            for (int i = 0; i < cantidad; i++) {
                do {
                    x = rnd.nextInt(ancho);
                    y = rnd.nextInt(alto);
                } while (mundo[y][x].estado == true);
                termitas.add(new Termita(x, y, rnd.nextInt(8)));
            }
        }

        /**
         * Mueve una termita según la dirección dada. Considerando que las
         * fronteras son periódicas.
         *
         * @param t La termita a mover en el modelo.
         * @param dirección La dirección en la que se desea mover la termita
         * (con valor entre 0 y 7).
         */
        void moverTermita(Termita t, int direccion) {
            switch (direccion) {
                case 0:
                    t.posX = (t.posX - 1) % ancho;
                    if (t.posX < 0) {
                        t.posX += ancho;
                    }
                    t.posY = (t.posY - 1) % alto;
                    if (t.posY < 0) {
                        t.posY += alto;
                    }
                    t.direccion = direccion;
                    break;
                case 1:
                    t.posY = (t.posY - 1) % alto;
                    if (t.posY < 0) {
                        t.posY += alto;
                    }
                    t.direccion = direccion;
                    break;
                case 2:
                    t.posX = (t.posX + 1) % ancho;
                    if (t.posX < 0) {
                        t.posX += ancho;
                    }
                    t.posY = (t.posY - 1) % alto;
                    if (t.posY < 0) {
                        t.posY += alto;
                    }
                    t.direccion = direccion;
                    break;
                case 3:
                    t.posX = (t.posX + 1) % ancho;
                    if (t.posX < 0) {
                        t.posX += ancho;
                    }
                    t.direccion = direccion;
                    break;
                case 4:
                    t.posX = (t.posX + 1) % ancho;
                    if (t.posX < 0) {
                        t.posX += ancho;
                    }
                    t.posY = (t.posY + 1) % alto;
                    if (t.posY < 0) {
                        t.posY += alto;
                    }
                    t.direccion = direccion;
                    break;
                case 5:
                    t.posY = (t.posY + 1) % alto;
                    if (t.posY < 0) {
                        t.posY += alto;
                    }
                    t.direccion = direccion;
                    break;
                case 6:
                    t.posX = (t.posX - 1) % ancho;
                    if (t.posX < 0) {
                        t.posX += ancho;
                    }
                    t.posY = (t.posY + 1) % alto;
                    if (t.posY < 0) {
                        t.posY += alto;
                    }
                    t.direccion = direccion;
                    break;
                case 7:
                    t.posX = (t.posX - 1) % ancho;
                    if (t.posX < 0) {
                        t.posX += ancho;
                    }
                    t.direccion = direccion;
                    break;
            }
        }

        /**
         * Auxiliar que genera un número que corresponde a la dirección "al
         * frente", "izquierda" y "derecha", relativa a la dirección dada como
         * parámetro.
         *
         * @param direccion La dirección que se toma como base para determinar
         * los valores "al frente", "izquierda" y "derecha".
         * @return Entero que representa la dirección aleatoria escogida.
         */
        int direccionAleatoriaFrente(int direccion) {
            // Ejemplo: Si recibes de parametro el valor '3' significa que la
            // termita está mirando hacia la derecha por lo que puede caminar
            // aleatoriamente hacia los valores 2, 3 ó 4 (representando
            // izquierda, adelante y derecha).
            int direcciones[];

            switch (direccion) {
                case 0:
                    direcciones = new int[] {7, 0, 1};
                    break;
                case 1:
                    direcciones = new int[] {0, 1, 2};
                    break;
                case 2:
                    direcciones = new int[] {1, 2, 3};
                    break;
                case 3:
                    direcciones = new int[] {2, 3, 4};
                    break;
                case 4:
                    direcciones = new int[] {3, 4, 5};
                    break;
                case 5:
                    direcciones = new int[] {4, 5, 6};
                    break;
                case 6:
                    direcciones = new int[] {5, 6, 7};
                    break;
                default:
                    direcciones = new int[] {6, 7, 0};
                    break;   
            }

            return direcciones[rnd.nextInt(3)];
        }

        /**
         * Determina si la casilla en la dirección en la que se mueve la termita
         * contiene una astilla.
         *
         * @param t La termita cuya posición se utiliza para ubicar la celda de
         * la cuadrícula.
         * @param dir La dirección en la que deseamos observar si hay una
         * astilla (con valor entre 0 y 7).
         * @return True si hay una astilla en la dirección de la termita, falso
         * en otro caso.
         */
        boolean hayAstilla(Termita t, int dir) {
            // Hint: El parametro direccion solo puede ser un valor entre 0-7.
            // Hint: mundo[t.posY][t.posX].estado Nos indica si hay una astilla en la misma posicion que la termita.
            int x = t.posX;
            int y = t.posY;

            switch (dir) {
                case 0:
                    y = (y - 1) % alto;
                    x = (x - 1) % ancho;
                    break;
                case 1:
                    y = (y - 1) % ancho;
                    break;
                case 2:
                    y = (y - 1) % alto;
                    x = (x + 1) % ancho;
                    break;
                case 3:
                    x = (x + 1) % ancho;
                    break;
                case 4:
                    y = (y + 1) % alto;
                    x = (x + 1) % ancho;
                    break;
                case 5:
                    y = (y + 1) % alto;
                    break;
                case 6:
                    y = (y + 1) % alto;
                    x = (x - 1) % ancho;
                    break;
                case 7:
                    x = (x - 1) % alto;
                    break;
            }

            if (x < 0) { x += ancho; }

            if (y < 0) { y += alto; }

            return mundo[y][x].estado;
        }

        /**
         * Simula el comportamiento de soltar una astilla y mover a la hormiga
         * en la dirección dada.
         *
         * @param t La termita que suelta la astilla y se mueve en la
         * cuadricula.
         * @param dir La dirección en la que se desea mover la termita. La
         * direccion real en la que se mueve tras dejar la astilla es la
         * direccion contraria a la dirección del parametro. Esto para intentar
         * mejorar el comportamiento de las termitas para que logren
         * autorganizacion mas rapido.
         */
        void dejarAstilla(Termita t, int dir) {
            // Hint: Indicar en el mundo que hay una astilla, indicar a la termita que 
            //       está cargando una astilla y mover a la termita
            //       en la dirección opuesta a la que está mirando (variable 'dir')
            mundo[t.posY][t.posX].estado = true;
            t.cargando = false;

            switch (dir) {
                case 0:
                    moverTermita(t, 4);
                    break;
                case 1:
                    moverTermita(t, 5);
                    break;
                case 2:
                    moverTermita(t, 6);
                    break;
                case 3:
                    moverTermita(t, 7);
                    break;
                case 4:
                    moverTermita(t, 0);
                    break;
                case 5:
                    moverTermita(t, 1);
                    break;
                case 6:
                    moverTermita(t, 2);
                    break;
                case 7:
                    moverTermita(t, 3);
                    break;
            }
        }

        /**
         * Simula el comportamiento de soltar una astilla y mover a la hormiga
         * aleatoriamente.
         *
         * @param t La termita que suelta la astilla y se mueve en la
         * cuadricula.
         */
        void dejarAstilla(Termita t) {
            // Hint: Marcar casilla para indicar la astilla, indicar que la termita carga una astilla y moverTermita aleatoriamente.
            mundo[t.posY][t.posX].estado = true;
            t.cargando = false;
            moverTermita(t, rnd.nextInt(8));
        }

        /**
         * Variacion del comportamiento cuando una termita suelta una astilla.
         * En este caso la termita suelta la astilla y es colocada en una celda
         * aleatoria de la cuadricula que no tiene astilla ("salta" a una celda
         * vacia).
         *
         * @param t La termita que va a soltar astilla en el modelo.
         */
        void dejarAstillaConSalto(Termita t) {
            // Hint: Marcar casilla con astilla, indicar que la termita ya no carga una astilla y asignar una nueva posicion a la termita.
            mundo[t.posY][t.posX].estado = true;
            t.cargando = false;

            while (mundo[t.posY][t.posX].estado) {
                t.posX = rnd.nextInt(ancho);
                t.posY = rnd.nextInt(alto);
            }

        }

        /**
         * Simula el proceso en el que una termita recoge una astilla.
         *
         * @param t La termita que recoge la astilla.
         * @param La dirección en la que se movera. Recoge la astilla despues de
                 * moverse en la dirección  .
         */
        void tomarAstilla(Termita t, int dir) {
            // Hint: Mover a la termita, quitar la astilla del mundo e indicar que la termita carga la astilla.
            moverTermita(t, dir);
            mundo[t.posY][t.posX].estado = false;
            t.cargando = true;
        }

        /**
         * Reglas de evolucion mas simples: Caminata aleatoria en las 8
         * direcciones de la vecindad de Moore. Al dejar una astilla continua
         * moviendose aleatoriamente.
         */
        void evolucion1() {
            for (Termita t : termitas) {
                int dir = rnd.nextInt(8);
                if (this.hayAstilla(t, dir)) {
                    if (t.cargando) {
                        this.dejarAstilla(t);
                    } else {
                        this.tomarAstilla(t, dir);
                    }
                } else {
                    this.moverTermita(t, dir);
                }
            }

            generacion += 1;
        }

        /**
         * Reglas de evolucion mejoradas: Con caminata hacia el frente, es
         * decir, aleatoriamente solo se considera moverse al frente, a la
         * izquierda o la derecha. Al soltar una astilla, la termita se da la
         * vuelta y continua moviendose al frente (como se acaba de describir).
         */
        void evolucion2() {
            for (Termita t : termitas) {
                int dir = direccionAleatoriaFrente(t.direccion);
                if (this.hayAstilla(t, dir)) {
                    if (t.cargando) {
                        this.dejarAstilla(t, dir);
                    } else {
                        this.tomarAstilla(t, dir);
                    }
                } else {
                    this.moverTermita(t, dir);
                }
            }

            generacion += 1;
        }

        /**
         * Reglas de evolución mejoradas y alteradas: Con caminata hacia el
         * frente, es decir, aleatoriamente sólo se considera moverse al frente,
         * a la izquierda o la derecha. Al soltar una astilla, la termita
         * "salta" a una celda desocupada, es decir, aleatoriamente se coloca a
         * la termita en una celda sin astilla.
         */
        void evolucion3() {
            for (Termita t : termitas) {
                int dir = direccionAleatoriaFrente(t.direccion);
                if (this.hayAstilla(t, dir)) {
                    if (t.cargando) {
                        this.dejarAstillaConSalto(t);
                    } else {
                        this.tomarAstilla(t, dir);
                    }
                } else {
                    this.moverTermita(t, dir);
                }
            }

            generacion += 1;
        }

    }

    /** Lanza el hilo donde se ejecutará el applet. */
    static public void main(String args[]) {
        PApplet.main(new String[]{"termitas.Termitas"});
    }
}
