package com.mycompany.proyectofinal;

import javax.swing.*;
import java.awt.*;

/**
 * Esta clase representa la ventana principal del sistema de inventario.
 * Desde aquí el usuario puede acceder a las distintas funcionalidades:
 * control de inventario, categorías, más vendidos, historial, etc.
 */
public class MenuPrincipal extends JFrame {

    // Se mantiene una sola instancia de la clase ListasEnlazadas (el inventario principal)
    private ListasEnlazadas listasEnlazadas;

    public MenuPrincipal() {
        // Configuraciones básicas de la ventana
        setTitle("Menú Principal");
        setSize(400, 450);
        setLocationRelativeTo(null); // Centrar en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializamos el sistema de listas enlazadas (control de inventario)
        listasEnlazadas = new ListasEnlazadas();

        // Creamos todos los botones del menú principal
        JButton botonMasVendidos = new JButton("Ver productos más vendidos");
        JButton botonReposicion = new JButton("Gestión de reposición de stock");
        JButton botonMatriz = new JButton("Gestión por categoría y ubicación");
        JButton botonLista = new JButton("Control de Inventario");
        JButton botonArbol = new JButton("Gestión por stock");
        JButton botonVector = new JButton("Buscar en vector");
        JButton botonArchivo = new JButton("Historial");
        JButton botonSalir = new JButton("Salir");

        // Cada botón lanza una ventana diferente al ser presionado
        botonMasVendidos.addActionListener(e -> new PilasMasVendidos());
        botonReposicion.addActionListener(e -> new ColasReposicionStock());
        botonMatriz.addActionListener(e -> new MatrizAlmacen());
        
        // Este botón abre la ventana de inventario usando la misma instancia (muy eficiente)
        botonLista.addActionListener(e -> listasEnlazadas.setVisible(true));

        botonArbol.addActionListener(e -> new ArbolBinarioGestion());
        
        // Este botón busca en el vector utilizando los productos de la lista enlazada
        botonVector.addActionListener(e -> new VectorGestion(listasEnlazadas.getCabeza()));

        // Muestra el historial de acciones realizadas en el sistema
        botonArchivo.addActionListener(e -> new ArchivoTXTHistorial());

        // Sale completamente del sistema
        botonSalir.addActionListener(e -> System.exit(0));

        // Usamos un panel para organizar los botones en forma vertical con espacio entre ellos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1, 10, 10)); // 9 filas, 1 columna, espacio entre botones

        // Agregamos una etiqueta y todos los botones al panel
        panel.add(new JLabel("Seleccione una opción:", SwingConstants.CENTER));
        panel.add(botonMasVendidos);
        panel.add(botonReposicion);
        panel.add(botonMatriz);
        panel.add(botonLista);
        panel.add(botonArbol);
        panel.add(botonVector);
        panel.add(botonArchivo);
        panel.add(botonSalir);

        // Finalmente añadimos el panel al frame principal
        add(panel);
        setVisible(true); // Mostramos la ventana
    }
}
