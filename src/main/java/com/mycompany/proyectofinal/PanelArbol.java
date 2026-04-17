package com.mycompany.proyectofinal;

import javax.swing.*;
import java.awt.*;

/**
 * PanelArbol es una clase que extiende JPanel y se encarga de dibujar
 * visualmente un árbol binario en un panel gráfico.
 */
public class PanelArbol extends JPanel {
    // Referencia al nodo raíz del árbol que se va a dibujar
    private final NodoArbol raiz;

    /**
     * Constructor de la clase PanelArbol.
     * @param raiz NodoArbol que representa la raíz del árbol a visualizar.
     */
    public PanelArbol(NodoArbol raiz) {
        this.raiz = raiz;
        // Establece el tamaño preferido del panel
        setPreferredSize(new Dimension(800, 600));
        // Establece el color de fondo del panel
        setBackground(Color.WHITE);
    }

    /**
     * Método sobrescrito de JPanel que se encarga de pintar los componentes.
     * @param g Objeto Graphics utilizado para dibujar en el panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Si la raíz no es nula, comienza a dibujar el árbol
        if (raiz != null) {
            // Dibuja el nodo raíz en la posición inicial (400, 40) con una separación horizontal de 200
            dibujarNodo(g, "Stock", 400, 40, raiz, 200);
        }
    }

    /**
     * Método recursivo que dibuja un nodo y sus hijos en el panel.
     * @param g Objeto Graphics utilizado para dibujar.
     * @param etiqueta Texto que se mostrará dentro del nodo.
     * @param x Coordenada x del centro del nodo.
     * @param y Coordenada y del centro del nodo.
     * @param nodo NodoArbol actual que se está dibujando.
     * @param separacion Distancia horizontal entre el nodo actual y sus hijos.
     */
    private void dibujarNodo(Graphics g, String etiqueta, int x, int y, NodoArbol nodo, int separacion) {
        // Establece el color de relleno del nodo
        g.setColor(Color.YELLOW);
        // Dibuja un óvalo (círculo) que representa el nodo
        g.fillOval(x - 25, y - 25, 50, 50);
        // Establece el color del borde del nodo
        g.setColor(Color.BLACK);
        // Dibuja el borde del óvalo
        g.drawOval(x - 25, y - 25, 50, 50);
        // Dibuja la etiqueta (texto) dentro del nodo
        g.drawString(etiqueta, x - 15, y + 5);

        // Si el nodo tiene un hijo izquierdo, dibuja la línea y el subárbol izquierdo
        if (nodo.izquierda != null) {
            // Calcula la posición del hijo izquierdo
            int xHijo = x - separacion;
            int yHijo = y + 80;
            // Dibuja una línea desde el nodo actual al hijo izquierdo
            g.drawLine(x, y + 25, xHijo, yHijo - 25);
            // Llamada recursiva para dibujar el subárbol izquierdo
            dibujarNodo(g, String.valueOf(nodo.izquierda.cantidad), xHijo, yHijo, nodo.izquierda, separacion / 2);
        }

        // Si el nodo tiene un hijo derecho, dibuja la línea y el subárbol derecho
        if (nodo.derecha != null) {
            // Calcula la posición del hijo derecho
            int xHijo = x + separacion;
            int yHijo = y + 80;
            // Dibuja una línea desde el nodo actual al hijo derecho
            g.drawLine(x, y + 25, xHijo, yHijo - 25);
            // Llamada recursiva para dibujar el subárbol derecho
            dibujarNodo(g, String.valueOf(nodo.derecha.cantidad), xHijo, yHijo, nodo.derecha, separacion / 2);
        }
    }
}
