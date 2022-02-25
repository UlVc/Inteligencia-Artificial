/*
 * Código utilizado para el curso de Inteligencia Artificial.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package pacman.personajes.navegacion;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import javafx.scene.paint.Color;
import pacman.personajes.Movimiento;

/**
 * Clase donde se define en algoritmo de A* para que se use en el fantasma.
 * @author baruch
 * @author blackzafiro
 */
public class AEstrella extends Algoritmo {
	
	private final static Logger LOGGER = Logger.getLogger("pacman.personajes.navegacion.AEstrella");
	static { LOGGER.setLevel(Level.FINE); }
	
	private PriorityQueue<NodoBusqueda> listaAbierta;   // Cola de prioridad de donde obtendremos los nodos
	                                                    // sobre los que se realizará el algoritmo.
	private HashSet<Estado> listaCerrada;       // Tabla de dispersión donde se agregan todos los estados
                                                        // que se terminó de revisar.
    private Estado estadoFinal;                         // Casilla donde se encuentra pacman.
    private boolean terminado;                          // Define si nuestro algoritmo ha terminado.
    private NodoBusqueda nodoSolucion;                  // Nodo a partir del cual se define la solución,
	                                                    // porque ya se encontró la mejor rutal al estado meta.
	
    /**
     * Inicializador del algoritmo.
	 * Se debe mandar llamar cada vez que cambien el estado incial y el estado
	 * final.
     * @param estadoInicial Pasillo donde se encuentra el fantasma.
     * @param estadoFinal Pasillo donde se encuentra pacman.
     */
    private void inicializa(Estado estadoInicial, Estado estadoFinal){
		this.estadoFinal = estadoFinal;
        this.terminado = false;
		this.nodoSolucion = null;
        this.listaAbierta = new PriorityQueue<>();
        this.listaCerrada = new HashSet<>();
        estadoInicial.calculaHeuristica(estadoFinal);
        this.listaAbierta.offer(new NodoBusqueda(estadoInicial));
    }
    
    /**
     * Función que realiza un paso en la ejecución del algoritmo.
     */
    private void expandeNodoSiguiente(){
        // Sacamos el nodo actual de la lista abierta y se agrega a la lista cerrada.
        NodoBusqueda nodoActual = listaAbierta.poll();
        listaCerrada.add(nodoActual.estado());

        // Revisamos sus sucesores.
        for (NodoBusqueda nodo : nodoActual.getSucesores()) {
            if (listaCerrada.contains(nodo.estado())){ continue; }
            if (!listaAbierta.contains(nodo)) {
                nodo.estado().calculaHeuristica(estadoFinal);
                listaAbierta.add(nodo);
                continue;
            }
            for (NodoBusqueda n : listaAbierta)
                if (nodo.gn() < n.gn())
                    n.setNodo(nodo);
        }
        // Si se cumple el objetivo
        if (nodoActual.estado().equals(estadoFinal))
            nodoSolucion = nodoActual;
    }
	
	/**
	 * Se puede llamar cuando se haya encontrado la solución para obtener el
	 * plan desde el nodo inicial hasta la meta.
	 * @return secuencia de movimientos que llevan del estado inicial a la meta.
	 */
	private LinkedList<Movimiento> generaTrayectoria() {
            LinkedList<Movimiento> movimientos = new LinkedList<>(); // Lista que tendrá todos los movimientos.
            
            // Leemos las acciones desde el nodoSoluciom usando a sus padres de cada uno.
            NodoBusqueda temp = nodoSolucion;
            while (temp.padre() != null) {
                movimientos.addFirst(temp.accionPadre());
                temp = temp.padre();
            }

            return movimientos;
	}
	
	/**
	 * Pinta las celdas desde el nodo solución hasta el nodo inicial
	 */
	private void pintaTrayectoria(Color color) {
		if (nodoSolucion == null) return;
		NodoBusqueda temp = nodoSolucion.padre();
		while(temp.padre() != null) {
			temp.estado().pintaCelda(color);
			temp = temp.padre();
		}
	}
    
    /**
     * Función que ejecuta A* para determinar la mejor ruta desde el fantasma,
	 * cuya posición se encuetra dentro de <code>estadoInicial</code>, hasta
	 * Pacman, que se encuentra en <code>estadoFinal</code>.
	 * @return Una lista con la secuencia de movimientos que Sombra debe
	 *         ejecutar para llegar hasta PacMan.
     */
	@Override
    public LinkedList<Movimiento> resuelveAlgoritmo(Estado estadoInicial, Estado estadoFinal){
	inicializa(estadoInicial, estadoFinal);
        
        // Expandimos la búsqueda hasta llegar a una solución.
	while(this.nodoSolucion == null)
            expandeNodoSiguiente();
        
        // Regresamos la trayectoria generada.
	return generaTrayectoria(); 
		
    }

}
