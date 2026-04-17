package com.mycompany.proyectofinal;

import javax.swing.*;

/**
 * Clase principal que inicia la aplicación del Sistema de Gestión de Inventario.
 * Muestra una ventana inicial con un botón para ingresar al menú principal.
 */
public class ProyectoFinal {

    public static void main(String[] args) {
        // Crear una nueva ventana (JFrame) con el título especificado
        JFrame ventana = new JFrame("Sistema de Gestión de Inventario");

        // Establecer el tamaño de la ventana: 400 píxeles de ancho y 200 de alto
        ventana.setSize(400, 200);

        // Definir la operación por defecto al cerrar la ventana:
        // EXIT_ON_CLOSE cierra la aplicación completamente
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centrar la ventana en la pantalla
        ventana.setLocationRelativeTo(null);

        // Crear un botón con la etiqueta "Ingresar"
        JButton botonIngresar = new JButton("Ingresar");

        // Agregar un ActionListener al botón para definir la acción al hacer clic
        botonIngresar.addActionListener(e -> {
            // Cerrar (eliminar) la ventana actual
            ventana.dispose();

            // Crear y mostrar una nueva instancia del menú principal
            // (Se asume que la clase MenuPrincipal está definida en otro lugar del proyecto)
            new MenuPrincipal();
        });

        // Agregar el botón a la ventana
        ventana.add(botonIngresar);

        // Hacer visible la ventana
        ventana.setVisible(true);
    }
}
