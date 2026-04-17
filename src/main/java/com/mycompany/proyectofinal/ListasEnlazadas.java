package com.mycompany.proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Esta clase implementa un sistema básico de inventario utilizando listas enlazadas.
 * Permite agregar, actualizar, eliminar y visualizar productos, además de guardar historial de acciones.
 */
public class ListasEnlazadas extends JFrame {

    // Nodo principal (cabeza) de la lista enlazada
    private Nodo cabeza = null;

    // Componentes gráficos
    private JTextArea areaLista;
    private JTextField campoNombre, campoCantidad;

    // Ruta del archivo donde se guarda el inventario
    private final String ARCHIVO = "inventario.txt";

    // Manejador del historial de acciones (agregar, actualizar, eliminar)
    private ArchivoTXTGestion historial = new ArchivoTXTGestion();

    /**
     * Clase interna Nodo que representa cada producto del inventario.
     */
    public class Nodo {
        String nombre;
        int cantidad;
        Nodo siguiente;

        Nodo(String nombre, int cantidad) {
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.siguiente = null;
        }
    }

    // Retorna la cabeza de la lista (opcionalmente útil desde otras clases)
    public Nodo getCabeza() {
        return cabeza;
    }

    /**
     * Constructor que inicializa la ventana y configura la interfaz gráfica.
     */
    public ListasEnlazadas() {
        setTitle("Control de Inventario");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicialización de campos y botones
        campoNombre = new JTextField(10);
        campoCantidad = new JTextField(5);
        JButton botonAgregar = new JButton("Agregar/Reponer");
        JButton botonActualizar = new JButton("Actualizar");
        JButton botonEliminar = new JButton("Eliminar");

        areaLista = new JTextArea(10, 50);
        areaLista.setEditable(false);

        // Panel superior con campos y botones
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Producto:"));
        panelSuperior.add(campoNombre);
        panelSuperior.add(new JLabel("Cantidad:"));
        panelSuperior.add(campoCantidad);
        panelSuperior.add(botonAgregar);
        panelSuperior.add(botonActualizar);
        panelSuperior.add(botonEliminar);

        // Agrega el panel y el área de texto a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaLista), BorderLayout.CENTER);

        // Enlaza eventos de botones a sus funciones
        botonAgregar.addActionListener(e -> agregarOReponer());
        botonActualizar.addActionListener(e -> actualizarProducto());
        botonEliminar.addActionListener(e -> eliminarProducto());

        // Carga los datos del archivo (si existe)
        cargarDesdeArchivo();
        mostrarLista();

        // Guarda en archivo al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                guardarEnArchivo();
            }
        });
    }

    /**
     * Carga productos desde el archivo al iniciar el programa.
     */
    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 2) {
                        String nombre = partes[0].trim();
                        int cantidad = Integer.parseInt(partes[1].trim());
                        Nodo nuevo = new Nodo(nombre, cantidad);
                        nuevo.siguiente = cabeza;
                        cabeza = nuevo;
                    }
                }
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar inventario: " + e.getMessage());
            }
        }
    }

    /**
     * Guarda la lista actual en el archivo de texto.
     */
    private void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            Nodo actual = cabeza;
            while (actual != null) {
                writer.write(actual.nombre + "," + actual.cantidad);
                writer.newLine();
                actual = actual.siguiente;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar inventario: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo producto o repone uno existente.
     */
    private void agregarOReponer() {
        String nombre = campoNombre.getText().trim();
        String cantidadTexto = campoCantidad.getText().trim();

        if (nombre.isEmpty() || cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
            return;
        }

        // Si ya existe el producto, simplemente se repone
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.nombre.equalsIgnoreCase(nombre)) {
                actual.cantidad += cantidad;
                historial.agregarRegistro("Producto repuesto: " + nombre + " - Nueva cantidad: " + actual.cantidad);
                mostrarLista();
                limpiarCampos();
                return;
            }
            actual = actual.siguiente;
        }

        // Si no existe, se agrega al inicio de la lista
        Nodo nuevo = new Nodo(nombre, cantidad);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        historial.agregarRegistro("Producto agregado: " + nombre + " - Cantidad: " + cantidad);
        mostrarLista();
        limpiarCampos();
    }

    /**
     * Actualiza la cantidad exacta de un producto existente.
     */
    private void actualizarProducto() {
        String nombre = campoNombre.getText().trim();
        String cantidadTexto = campoCantidad.getText().trim();

        if (nombre.isEmpty() || cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
            return;
        }

        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.nombre.equalsIgnoreCase(nombre)) {
                int cantidadAnterior = actual.cantidad;
                actual.cantidad = cantidad;
                historial.agregarRegistro("Producto actualizado: " + nombre +
                        " - Cantidad anterior: " + cantidadAnterior +
                        ", nueva cantidad: " + cantidad);
                mostrarLista();
                limpiarCampos();
                return;
            }
            actual = actual.siguiente;
        }

        JOptionPane.showMessageDialog(this, "Producto no encontrado.");
    }

    /**
     * Elimina un producto de la lista.
     */
    private void eliminarProducto() {
        String nombre = campoNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el nombre del producto.");
            return;
        }

        // Caso especial: el producto es la cabeza
        if (cabeza != null && cabeza.nombre.equalsIgnoreCase(nombre)) {
            historial.agregarRegistro("Producto eliminado: " + cabeza.nombre +
                    " - Cantidad: " + cabeza.cantidad);
            cabeza = cabeza.siguiente;
            mostrarLista();
            limpiarCampos();
            return;
        }

        // Busca y elimina el nodo que coincide con el nombre
        Nodo actual = cabeza;
        while (actual != null && actual.siguiente != null) {
            if (actual.siguiente.nombre.equalsIgnoreCase(nombre)) {
                historial.agregarRegistro("Producto eliminado: " + actual.siguiente.nombre +
                        " - Cantidad: " + actual.siguiente.cantidad);
                actual.siguiente = actual.siguiente.siguiente;
                mostrarLista();
                limpiarCampos();
                return;
            }
            actual = actual.siguiente;
        }

        JOptionPane.showMessageDialog(this, "Producto no encontrado.");
    }

    /**
     * Muestra la lista enlazada de productos en el área de texto.
     */
    private void mostrarLista() {
        StringBuilder builder = new StringBuilder("Inventario:\n");
        Nodo actual = cabeza;
        while (actual != null) {
            builder.append("- ").append(actual.nombre).append(": ").append(actual.cantidad).append(" unidades\n");
            actual = actual.siguiente;
        }
        areaLista.setText(builder.toString());
    }

    /**
     * Limpia los campos de texto después de una operación.
     */
    private void limpiarCampos() {
        campoNombre.setText("");
        campoCantidad.setText("");
    }
}
