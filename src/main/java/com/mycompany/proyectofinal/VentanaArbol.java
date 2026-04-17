package com.mycompany.proyectofinal;

import javax.swing.JFrame;

/**
 * Clase que representa una ventana para visualizar un árbol binario basado en el stock de productos.
 * Extiende de JFrame para crear una interfaz gráfica utilizando Swing.
 */
public class VentanaArbol extends JFrame {

    /**
     * Constructor de la clase VentanaArbol.
     * Configura la ventana y construye un árbol binario con algunos productos de ejemplo.
     */
    public VentanaArbol() {
        // Establece el título de la ventana
        setTitle("Árbol Binario por Stock");

        // Define el tamaño de la ventana: 800 píxeles de ancho y 600 de alto
        setSize(800, 600);

        // Especifica que la aplicación debe cerrarse cuando se cierre esta ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crea una instancia del árbol binario
        ArbolBinario arbol = new ArbolBinario();

        // Inserta productos en el árbol con sus respectivos valores de stock
        arbol.insertar(783, "Celular");
        arbol.insertar(1290, "Salchipapa");
        arbol.insertar(69, "Peras");
        arbol.insertar(12, "Manzana");
        arbol.insertar(500, "Mouse");

        // Agrega un componente personalizado que dibuja el árbol en la ventana
        add(new DibujoArbol(arbol.getRaiz()));
    }
}
