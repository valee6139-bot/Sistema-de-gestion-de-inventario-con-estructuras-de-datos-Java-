package com.mycompany.proyectofinal;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa una ventana para la búsqueda y visualización de productos en inventario.
 * Utiliza arreglos para almacenar los nombres de los productos y sus respectivas cantidades en stock.
 */
public class VectorGestion extends JFrame {

    // Arreglo para almacenar los nombres de hasta 100 productos
    private String[] productos = new String[100];

    // Arreglo para almacenar las cantidades en stock correspondientes a los productos
    private int[] stock = new int[100];

    // Contador que lleva la cuenta de cuántos productos se han agregado
    private int contador = 0;

    // Área de texto donde se mostrará la lista de productos y sus cantidades
    private JTextArea areaVector;

    // Campo de texto para ingresar el nombre del producto a buscar
    private JTextField campoNombre;

    /**
     * Constructor por defecto que inicializa la interfaz gráfica de la ventana.
     */
    public VectorGestion() {
        // Establece el título de la ventana
        setTitle("Búsqueda de productos");

        // Define el tamaño de la ventana
        setSize(500, 400);

        // Centra la ventana en la pantalla
        setLocationRelativeTo(null);

        // Define la operación por defecto al cerrar la ventana: solo se cierra esta ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicializa el campo de texto para el nombre del producto con un ancho de 10 columnas
        campoNombre = new JTextField(10);

        // Crea un botón con la etiqueta "Buscar"
        JButton botonBuscar = new JButton("Buscar");

        // Inicializa el área de texto donde se mostrará la lista de productos
        areaVector = new JTextArea(10, 40);

        // Hace que el área de texto no sea editable por el usuario
        areaVector.setEditable(false);

        // Crea un panel para organizar los componentes en la parte superior de la ventana
        JPanel panel = new JPanel();

        // Agrega una etiqueta al panel
        panel.add(new JLabel("Producto:"));

        // Agrega el campo de texto al panel
        panel.add(campoNombre);

        // Agrega el botón al panel
        panel.add(botonBuscar);

        // Agrega un ActionListener al botón para definir la acción al hacer clic
        botonBuscar.addActionListener(e -> buscarProducto());

        // Agrega el panel a la parte superior (NORTH) de la ventana
        add(panel, BorderLayout.NORTH);

        // Agrega el área de texto dentro de un JScrollPane al centro (CENTER) de la ventana
        add(new JScrollPane(areaVector), BorderLayout.CENTER);

        // Hace visible la ventana
        setVisible(true);
    }

    /**
     * Constructor que recibe una lista enlazada de productos y la carga en los arreglos.
     * @param cabeza Nodo inicial de la lista enlazada de productos.
     */
    public VectorGestion(ListasEnlazadas.Nodo cabeza) {
        // Llama al constructor por defecto para inicializar la interfaz
        this();

        // Carga los datos de la lista enlazada en los arreglos
        cargarDesdeLista(cabeza);
    }

    /**
     * Método que carga los datos de una lista enlazada en los arreglos de productos y stock.
     * @param cabeza Nodo inicial de la lista enlazada de productos.
     */
    private void cargarDesdeLista(ListasEnlazadas.Nodo cabeza) {
        // Reinicia el contador de productos
        contador = 0;

        // Variable auxiliar para recorrer la lista enlazada
        ListasEnlazadas.Nodo actual = cabeza;

        // Recorre la lista mientras haya nodos y no se haya alcanzado el límite del arreglo
        while (actual != null && contador < productos.length) {
            // Almacena el nombre del producto en el arreglo
            productos[contador] = actual.nombre;

            // Almacena la cantidad en stock del producto en el arreglo
            stock[contador] = actual.cantidad;

            // Incrementa el contador
            contador++;

            // Avanza al siguiente nodo de la lista
            actual = actual.siguiente;
        }

        // Actualiza la lista mostrada en el área de texto
        actualizarLista();
    }

    /**
     * Método que busca un producto por su nombre y muestra su cantidad en stock.
     */
    private void buscarProducto() {
        // Obtiene el nombre ingresado en el campo de texto y elimina espacios al inicio y al final
        String nombre = campoNombre.getText().trim();

        // Recorre los productos almacenados
        for (int i = 0; i < contador; i++) {
            // Verifica que el nombre del producto no sea nulo y que coincida (ignorando mayúsculas/minúsculas)
            if (productos[i] != null && productos[i].equalsIgnoreCase(nombre)) {
                // Muestra un mensaje con la cantidad en stock del producto encontrado
                JOptionPane.showMessageDialog(this, "Stock: " + stock[i]);
                return;
            }
        }

        // Si no se encuentra el producto, muestra un mensaje indicando que no fue encontrado
        JOptionPane.showMessageDialog(this, "Producto no encontrado.");
    }

    /**
     * Método que actualiza el área de texto con la lista de productos y sus cantidades en stock.
     */
    private void actualizarLista() {
        // Crea un StringBuilder para construir el texto a mostrar
        StringBuilder builder = new StringBuilder("Inventario actual:\n");

        // Recorre los productos almacenados
        for (int i = 0; i < contador; i++) {
            // Agrega el nombre del producto y su cantidad en stock al texto
            builder.append(productos[i]).append(": ").append(stock[i]).append("\n");
        }

        // Establece el texto construido en el área de texto
        areaVector.setText(builder.toString());
    }
}
