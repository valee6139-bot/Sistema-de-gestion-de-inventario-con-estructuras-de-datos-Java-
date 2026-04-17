package com.mycompany.proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Esta clase implementa un sistema de inventario basado en una matriz,
 * permitiendo organizar productos por categoría (filas) y ubicación (columnas).
 */
public class MatrizAlmacen extends JFrame {

    private final int FILAS = 3;  // 3 categorías: Hardware, Software, Periféricos
    private final int COLUMNAS = 4;  // 4 ubicaciones: A1, A2, B1, B2

    // Matriz que almacena los productos según categoría y ubicación
    private String[][] matrizProductos = new String[FILAS][COLUMNAS];

    // Componentes de la interfaz
    private JTextArea areaMatriz;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboUbicacion;
    private JTextField campoProducto;

    private final String ARCHIVO = "matriz_productos.txt";  // Archivo de almacenamiento

    /**
     * Constructor: configura la interfaz gráfica y carga datos desde archivo.
     */
    public MatrizAlmacen() {
        setTitle("Gestión por Categoría y Ubicación");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Opciones para los ComboBox
        String[] categorias = {"Hardware", "Software", "Perifericos"};
        String[] ubicaciones = {"A1", "A2", "B1", "B2"};

        comboCategoria = new JComboBox<>(categorias);
        comboUbicacion = new JComboBox<>(ubicaciones);
        campoProducto = new JTextField(15);
        JButton botonAgregar = new JButton("Agregar Producto");

        areaMatriz = new JTextArea(10, 50);
        areaMatriz.setEditable(false);

        // Acción del botón de agregar
        botonAgregar.addActionListener(e -> agregarProducto());

        // Construcción del panel superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Categoría:"));
        panelSuperior.add(comboCategoria);
        panelSuperior.add(new JLabel("Ubicación:"));
        panelSuperior.add(comboUbicacion);
        panelSuperior.add(new JLabel("Producto:"));
        panelSuperior.add(campoProducto);
        panelSuperior.add(botonAgregar);

        // Agrega el panel y el área de visualización a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaMatriz), BorderLayout.CENTER);

        // Cargar datos previos al iniciar
        cargarDesdeArchivo();
        mostrarMatriz();

        // Guardar datos al cerrar
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                guardarEnArchivo();
            }
        });

        setVisible(true);  // Mostrar la ventana
    }

    /**
     * Agrega un producto a la matriz según la categoría y ubicación seleccionadas.
     */
    private void agregarProducto() {
        int fila = comboCategoria.getSelectedIndex();  // Índice de la categoría seleccionada
        int columna = comboUbicacion.getSelectedIndex();  // Índice de la ubicación seleccionada
        String producto = campoProducto.getText().trim();  // El nombre del producto

        if (!producto.isEmpty()) {
            matrizProductos[fila][columna] = producto;  // Inserta el producto en la posición
            campoProducto.setText("");  // Limpia el campo de entrada
            mostrarMatriz();  // Actualiza la visualización
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un producto.");
        }
    }

    /**
     * Muestra visualmente el contenido de la matriz en el JTextArea.
     */
    private void mostrarMatriz() {
        StringBuilder builder = new StringBuilder();
        builder.append("Matriz de Productos (Categoría x Ubicación):\n");

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                String prod = matrizProductos[i][j];
                builder.append("[").append(prod != null ? prod : "Vacío").append("]\t");
            }
            builder.append("\n");
        }

        areaMatriz.setText(builder.toString());
    }

    /**
     * Guarda el contenido actual de la matriz en un archivo de texto.
     */
    private void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (int i = 0; i < FILAS; i++) {
                for (int j = 0; j < COLUMNAS; j++) {
                    String prod = matrizProductos[i][j];
                    writer.write(prod != null ? prod : "");  // Evita escribir "null"
                    if (j < COLUMNAS - 1) writer.write(",");  // Separador
                }
                writer.newLine();  // Nueva línea por cada fila
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Carga datos desde el archivo y los coloca en la matriz si existe el archivo.
     */
    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;  // Si no hay archivo, no hace nada

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int fila = 0;
            while ((linea = reader.readLine()) != null && fila < FILAS) {
                String[] valores = linea.split(",", -1);  // El -1 asegura que se mantengan campos vacíos
                for (int col = 0; col < Math.min(valores.length, COLUMNAS); col++) {
                    matrizProductos[fila][col] = valores[col].isEmpty() ? null : valores[col];
                }
                fila++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }
}
