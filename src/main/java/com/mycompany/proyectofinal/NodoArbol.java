package com.mycompany.proyectofinal;

/**
 * La clase NodoArbol representa un nodo individual en un árbol binario.
 * Cada nodo contiene una cantidad (por ejemplo, la cantidad de stock de un producto),
 * un nombre (por ejemplo, el nombre del producto) y referencias a sus nodos hijos izquierdo y derecho.
 */
public class NodoArbol {
    int cantidad;           // Representa la cantidad asociada al nodo (por ejemplo, stock de un producto)
    String nombre;          // Nombre o identificador del nodo (por ejemplo, nombre del producto)
    NodoArbol izquierda;    // Referencia al nodo hijo izquierdo
    NodoArbol derecha;      // Referencia al nodo hijo derecho

    /**
     * Constructor para crear un nuevo nodo con una cantidad y un nombre específicos.
     * Inicializa las referencias a los hijos izquierdo y derecho como null.
     *
     * @param cantidad La cantidad asociada al nodo.
     * @param nombre   El nombre o identificador del nodo.
     */
    public NodoArbol(int cantidad, String nombre) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        izquierda = derecha = null; // Al crear un nuevo nodo, sus hijos son null por defecto
    }
}
