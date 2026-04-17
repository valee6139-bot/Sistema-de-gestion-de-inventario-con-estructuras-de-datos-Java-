package com.mycompany.proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Esta clase representa una ventana para gestionar la reposición de stock en un sistema de inventario.
 * Utiliza una cola (FIFO) para manejar los pedidos de reposición y permite agregar, procesar y visualizar estos pedidos.
 * Además, guarda y carga automáticamente los pedidos desde un archivo de texto para mantener la persistencia de los datos.
 */
public class ColasReposicionStock extends JFrame {

    // Cola que almacena los pedidos de reposición de productos
    private Queue<String> colaReposicion = new LinkedList<>();

    // Área de texto donde se muestran los pedidos actuales en la cola
    private JTextArea areaPedidos;

    // Campo de texto para ingresar nuevos pedidos
    private JTextField campoPedido;

    // Nombre del archivo donde se guardan los pedidos para persistencia
    private final String ARCHIVO = "reposicion.txt";

    /**
     * Constructor de la clase ColasReposicionStock.
     * Configura la interfaz gráfica, carga los pedidos desde el archivo y establece los eventos necesarios.
     */
    public ColasReposicionStock() {
        // Establece el título de la ventana
        setTitle("Gestión de Reposición de Stock");

        // Define el tamaño de la ventana (ancho x alto)
        setSize(500, 300);

        // Centra la ventana en la pantalla del usuario
        setLocationRelativeTo(null);

        // Define la operación por defecto al cerrar la ventana (solo cierra esta ventana)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicializa el campo de texto para ingresar pedidos
        campoPedido = new JTextField(20);

        // Botón para agregar un nuevo pedido a la cola
        JButton botonAgregar = new JButton("Agregar Pedido");

        // Botón para procesar (eliminar) el primer pedido de la cola
        JButton botonProcesar = new JButton("Procesar Pedido");

        // Área de texto donde se mostrarán los pedidos en cola
        areaPedidos = new JTextArea(10, 40);
        areaPedidos.setEditable(false); // El área de texto es de solo lectura

        // Asocia acciones a los botones utilizando expresiones lambda
        botonAgregar.addActionListener(e -> agregarPedido());
        botonProcesar.addActionListener(e -> procesarPedido());

        // Panel superior que contiene el campo de texto y los botones
        JPanel panel = new JPanel();
        panel.add(new JLabel("Producto a reponer:"));
        panel.add(campoPedido);
        panel.add(botonAgregar);
        panel.add(botonProcesar);

        // Agrega el panel superior a la parte superior de la ventana
        add(panel, BorderLayout.NORTH);

        // Agrega el área de texto dentro de un scroll pane al centro de la ventana
        add(new JScrollPane(areaPedidos), BorderLayout.CENTER);

        // Carga los pedidos previamente guardados desde el archivo
        cargarDesdeArchivo();

        // Agrega un listener para guardar los pedidos en el archivo al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                guardarEnArchivo();
            }
        });

        // Hace visible la ventana
        setVisible(true);
    }

    /**
     * Método para agregar un nuevo pedido a la cola.
     * Verifica que el campo de texto no esté vacío antes de agregar.
     */
    private void agregarPedido() {
        // Obtiene y limpia el texto ingresado por el usuario
        String pedido = campoPedido.getText().trim();

        // Verifica que el campo no esté vacío
        if (!pedido.isEmpty()) {
            // Agrega el pedido a la cola
            colaReposicion.add(pedido);

            // Actualiza la lista de pedidos mostrada en la interfaz
            actualizarLista();

            // Limpia el campo de texto para nuevos ingresos
            campoPedido.setText("");
        } else {
            // Muestra un mensaje de advertencia si el campo está vacío
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un producto.");
        }
    }

    /**
     * Método para procesar el primer pedido en la cola.
     * Elimina el pedido de la cola y notifica al usuario.
     */
    private void procesarPedido() {
        // Verifica si hay pedidos en la cola
        if (!colaReposicion.isEmpty()) {
            // Elimina y obtiene el primer pedido de la cola
            String procesado = colaReposicion.poll();

            // Muestra un mensaje indicando el pedido procesado
            JOptionPane.showMessageDialog(this, "Pedido procesado: " + procesado);

            // Actualiza la lista de pedidos mostrada en la interfaz
            actualizarLista();
        } else {
            // Muestra un mensaje si no hay pedidos en la cola
            JOptionPane.showMessageDialog(this, "No hay pedidos en cola.");
        }
    }

    /**
     * Método para actualizar el área de texto con los pedidos actuales en la cola.
     */
    private void actualizarLista() {
        // Utiliza StringBuilder para construir la cadena de pedidos
        StringBuilder builder = new StringBuilder();

        // Itera sobre cada pedido en la cola y lo agrega al StringBuilder
        for (String pedido : colaReposicion) {
            builder.append(pedido).append("\n");
        }

        // Establece el texto del área de pedidos con la lista actualizada
        areaPedidos.setText(builder.toString());
    }

    /**
     * Método para guardar los pedidos actuales en la cola al archivo de texto.
     * Se llama automáticamente al cerrar la ventana.
     */
    private void guardarEnArchivo() {
        // Utiliza try-with-resources para asegurar el cierre del archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            // Escribe cada pedido en una nueva línea del archivo
            for (String pedido : colaReposicion) {
                writer.write(pedido);
                writer.newLine();
            }
        } catch (IOException e) {
            // Muestra un mensaje de error si ocurre una excepción al guardar
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Método para cargar los pedidos desde el archivo de texto al iniciar la aplicación.
     * Si el archivo existe, lee cada línea y la agrega a la cola.
     */
    private void cargarDesdeArchivo() {
        // Crea un objeto File que representa el archivo de pedidos
        File archivo = new File(ARCHIVO);

        // Verifica si el archivo existe
        if (archivo.exists()) {
            // Utiliza try-with-resources para asegurar el cierre del archivo
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;

                // Lee cada línea del archivo y la agrega a la cola
                while ((linea = reader.readLine()) != null) {
                    colaReposicion.add(linea);
                }

                // Actualiza la lista de pedidos mostrada en la interfaz
                actualizarLista();
            } catch (IOException e) {
                // Muestra un mensaje de error si ocurre una excepción al leer
                JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
            }
        }
    }
}
