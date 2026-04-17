package com.mycompany.proyectofinal;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class PilasMasVendidos extends JFrame {

    // Pila para almacenar los productos vendidos recientemente
    private Stack<String> pilaProductos = new Stack<>();

    // Mapa para llevar el conteo de las ventas por producto
    private Map<String, Integer> conteoVentas = new HashMap<>();

    // Área de texto donde se mostrarán los productos vendidos
    private JTextArea areaProductos;

    // Campo de texto para que el usuario ingrese el nombre del producto vendido
    private JTextField campoProducto;

    // Nombre del archivo donde se guardarán los productos vendidos
    private final String ARCHIVO = "productos.txt";

    // Constructor de la clase
    public PilasMasVendidos() {
        // Configuración básica de la ventana
        setTitle("Registro de Productos Más Vendidos");
        setSize(500, 300);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al salir

        // Inicialización de los componentes de la interfaz
        campoProducto = new JTextField(20); // Campo para ingresar el producto
        JButton botonAgregar = new JButton("Agregar Producto"); // Botón para agregar el producto
        areaProductos = new JTextArea(10, 40); // Área donde se mostrarán los productos
        areaProductos.setEditable(false); // El área de texto no es editable por el usuario

        // Acción que se ejecuta al presionar el botón "Agregar Producto"
        botonAgregar.addActionListener(e -> agregarProducto());

        // Panel superior que contiene la etiqueta, el campo de texto y el botón
        JPanel panel = new JPanel();
        panel.add(new JLabel("Producto vendido:"));
        panel.add(campoProducto);
        panel.add(botonAgregar);

        // Agrega el panel superior a la parte norte de la ventana
        add(panel, BorderLayout.NORTH);

        // Agrega el área de texto dentro de un scroll y lo coloca en el centro de la ventana
        add(new JScrollPane(areaProductos), BorderLayout.CENTER);

        // Carga los productos previamente guardados desde el archivo
        cargarDesdeArchivo();

        // Añade un listener para guardar los datos cuando se cierre la ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                guardarEnArchivo();
            }
        });

        // Hace visible la ventana
        setVisible(true);
    }

    // Método para agregar un producto a la pila y actualizar el conteo
    private void agregarProducto() {
        // Obtiene el texto ingresado por el usuario y elimina espacios al inicio y al final
        String producto = campoProducto.getText().trim();

        // Verifica que el campo no esté vacío
        if (!producto.isEmpty()) {
            // Agrega el producto a la pila
            pilaProductos.push(producto);

            // Actualiza el conteo de ventas para el producto
            int nuevoConteo = conteoVentas.getOrDefault(producto, 0) + 1;
            conteoVentas.put(producto, nuevoConteo);

            // Verifica si el producto actual es el más vendido
            boolean esMasVendido = true;
            for (Map.Entry<String, Integer> entry : conteoVentas.entrySet()) {
                if (!entry.getKey().equals(producto) && entry.getValue() > nuevoConteo) {
                    esMasVendido = false;
                    break;
                }
            }

            // Si es el más vendido, muestra un mensaje al usuario
            if (esMasVendido) {
                JOptionPane.showMessageDialog(this, "El producto '" + producto + "' es el más vendido recientemente.");
            }

            // Actualiza la lista de productos mostrada en el área de texto
            actualizarLista();

            // Limpia el campo de texto para una nueva entrada
            campoProducto.setText("");
        } else {
            // Si el campo está vacío, muestra un mensaje de advertencia
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un producto.");
        }
    }

    // Método para actualizar el área de texto con los productos en la pila
    private void actualizarLista() {
        StringBuilder builder = new StringBuilder();

        // Recorre la pila desde el último elemento agregado hasta el primero
        for (int i = pilaProductos.size() - 1; i >= 0; i--) {
            builder.append(pilaProductos.get(i)).append("\n");
        }

        // Establece el texto en el área de productos
        areaProductos.setText(builder.toString());
    }

    // Método para guardar los productos en un archivo al cerrar la ventana
    private void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            // Escribe cada producto en una nueva línea del archivo
            for (String producto : pilaProductos) {
                writer.write(producto);
                writer.newLine();
            }
        } catch (IOException e) {
            // Si ocurre un error al guardar, muestra un mensaje de error
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    // Método para cargar los productos desde el archivo al iniciar la aplicación
    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);

        // Verifica si el archivo existe
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;

                // Lee cada línea del archivo y la agrega a la pila
                while ((linea = reader.readLine()) != null) {
                    pilaProductos.push(linea);

                    // Actualiza el conteo de ventas para cada producto
                    conteoVentas.put(linea, conteoVentas.getOrDefault(linea, 0) + 1);
                }

                // Actualiza la lista de productos mostrada en el área de texto
                actualizarLista();
            } catch (IOException e) {
                // Si ocurre un error al cargar, muestra un mensaje de error
                JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
            }
        }
    }
}
