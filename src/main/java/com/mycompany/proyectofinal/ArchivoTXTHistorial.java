package com.mycompany.proyectofinal;

import javax.swing.*;
import java.awt.*;

/**
 * Esta clase representa una ventana que muestra el historial del inventario.
 * Extiende JFrame para crear una interfaz gráfica utilizando Swing.
 */
public class ArchivoTXTHistorial extends JFrame {

    /**
     * Constructor de la clase ArchivoTXTHistorial.
     * Configura la ventana, lee el historial desde un archivo y lo muestra en un área de texto.
     */
    public ArchivoTXTHistorial() {
        // Establece el título de la ventana
        setTitle("Historial del Inventario");

        // Define el tamaño de la ventana (ancho x alto)
        setSize(500, 400);

        // Centra la ventana en la pantalla del usuario
        setLocationRelativeTo(null);

        // Define la operación por defecto al cerrar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crea una instancia de ArchivoTXTGestion para manejar la lectura del historial
        ArchivoTXTGestion gestion = new ArchivoTXTGestion();

        // Lee el contenido del historial desde el archivo
        String historial = gestion.leerHistorial();

        // Crea un área de texto para mostrar el historial
        JTextArea areaHistorial = new JTextArea(historial);

        // Hace que el área de texto sea de solo lectura
        areaHistorial.setEditable(false);

        // Crea un panel de desplazamiento para el área de texto
        JScrollPane scroll = new JScrollPane(areaHistorial);

        // Añade el panel de desplazamiento al centro de la ventana
        add(scroll, BorderLayout.CENTER);

        // Hace visible la ventana
        setVisible(true);
    }
}
