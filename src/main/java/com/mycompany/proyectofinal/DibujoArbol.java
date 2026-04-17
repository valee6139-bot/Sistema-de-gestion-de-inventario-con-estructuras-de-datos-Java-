package com.mycompany.proyectofinal;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Esta clase es responsable de dibujar visualmente un árbol binario sobre un panel gráfico.
 * Extiende de JPanel y utiliza la clase Graphics para representar nodos y sus conexiones.
 */
public class DibujoArbol extends JPanel {

    // Referencia al nodo raíz del árbol binario que se va a dibujar
    private final NodoArbol raiz;

    /**
     * Constructor que recibe la raíz del árbol que se desea visualizar.
     * @param raiz Nodo principal del árbol binario.
     */
    public DibujoArbol(NodoArbol raiz) {
        this.raiz = raiz;
    }

    /**
     * Método que sobreescribe el comportamiento por defecto del componente de dibujo.
     * Se ejecuta automáticamente cuando se renderiza el panel.
     * @param g Objeto Graphics que permite dibujar en el panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja un título en la parte superior del panel
        g.drawString("Stock", getWidth() / 2 - 20, 20);

        // Comienza el dibujo del árbol desde la raíz, centrado horizontalmente
        dibujar(g, raiz, getWidth() / 2, 50, getWidth() / 4);
    }

    /**
     * Método recursivo que dibuja cada nodo del árbol y sus conexiones con los hijos.
     * @param g Objeto Graphics para dibujar.
     * @param nodo Nodo actual a representar.
     * @param x Coordenada X del nodo actual.
     * @param y Coordenada Y del nodo actual.
     * @param offset Distancia horizontal entre niveles para espaciar los nodos.
     */
    private void dibujar(Graphics g, NodoArbol nodo, int x, int y, int offset) {
        // Caso base: si el nodo es nulo, no se dibuja nada
        if (nodo == null) return;

        // Dibuja el nodo 
        g.setColor(Color.YELLOW);
        g.fillOval(x - 20, y - 20, 40, 40);
        g.setColor(Color.BLACK);
        g.drawOval(x - 20, y - 20, 40, 40);

        // Escribe el nombre del producto y su cantidad dentro del nodo
        g.drawString(nodo.nombre + " (" + nodo.cantidad + ")", x - 30, y + 5);

        // Si hay un hijo izquierdo, dibuja una línea hacia él y lo representa recursivamente
        if (nodo.izquierda != null) {
            g.drawLine(x, y, x - offset, y + 50);
            dibujar(g, nodo.izquierda, x - offset, y + 50, offset / 2);
        }

        // Si hay un hijo derecho, dibuja una línea hacia él y lo representa recursivamente
        if (nodo.derecha != null) {
            g.drawLine(x, y, x + offset, y + 50);
            dibujar(g, nodo.derecha, x + offset, y + 50, offset / 2);
        }
    }
}
