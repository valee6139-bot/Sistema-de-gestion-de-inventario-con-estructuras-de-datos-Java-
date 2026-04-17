package com.mycompany.proyectofinal;

/**
 * Clase que representa un árbol binario de búsqueda (ABB).
 * Permite insertar nodos con una cantidad (clave) y un nombre (valor).
 */
public class ArbolBinario {

    // Nodo raíz del árbol
    NodoArbol raiz;

    /**
     * Inserta un nuevo nodo en el árbol con la cantidad y nombre especificados.
     * Si el árbol está vacío, el nuevo nodo se convierte en la raíz.
     *
     * @param cantidad Valor numérico que determina la posición del nodo en el árbol.
     * @param nombre   Información asociada al nodo.
     */
    public void insertar(int cantidad, String nombre) {
        raiz = insertarRec(raiz, cantidad, nombre);
    }

    /**
     * Método recursivo que inserta un nuevo nodo en el árbol.
     * Compara la cantidad con la del nodo actual para decidir si debe ir a la izquierda o derecha.
     *
     * @param actual   Nodo actual del árbol durante la recursión.
     * @param cantidad Valor numérico que determina la posición del nuevo nodo.
     * @param nombre   Información asociada al nuevo nodo.
     * @return El nodo actual después de la inserción.
     */
    private NodoArbol insertarRec(NodoArbol actual, int cantidad, String nombre) {
        // Si el nodo actual es null, se ha encontrado la posición para insertar el nuevo nodo
        if (actual == null) {
            return new NodoArbol(cantidad, nombre);
        }

        // Si la cantidad es menor que la del nodo actual, insertar en el subárbol izquierdo
        if (cantidad < actual.cantidad) {
            actual.izquierda = insertarRec(actual.izquierda, cantidad, nombre);
        } else {
            // Si la cantidad es mayor o igual, insertar en el subárbol derecho
            actual.derecha = insertarRec(actual.derecha, cantidad, nombre);
        }

        // Retornar el nodo actual para mantener la estructura del árbol
        return actual;
    }

    /**
     * Obtiene la raíz del árbol.
     *
     * @return Nodo raíz del árbol.
     */
    public NodoArbol getRaiz() {
        return raiz;
    }
}
