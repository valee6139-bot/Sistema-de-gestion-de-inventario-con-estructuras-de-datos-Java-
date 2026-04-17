package com.mycompany.proyectofinal;

import javax.swing.*;
import java.io.*;

/**
 * Esta clase representa una ventana gráfica que muestra un árbol binario con información de inventario.
 * Utiliza Swing para la interfaz y carga los datos desde un archivo de texto.
 */
public class ArbolBinarioGestion extends JFrame {
    // Creamos una instancia del árbol binario que almacenará los datos del inventario
    private ArbolBinario arbol = new ArbolBinario();

    /**
     * Constructor de la clase ArbolBinarioGestion.
     * Configura la ventana principal, carga los datos del archivo y muestra el árbol.
     */
    public ArbolBinarioGestion() {
        // Establecemos el título de la ventana
        setTitle("Árbol Binario - Stock");

        // Definimos el tamaño de la ventana (ancho x alto)
        setSize(900, 700);

        // Centramos la ventana en la pantalla del usuario
        setLocationRelativeTo(null);

        // Indicamos que al cerrar la ventana, solo se cierre esta y no toda la aplicación
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Llamamos al método que carga los datos desde el archivo al árbol binario
        cargarDatos();

        // Creamos un panel personalizado que mostrará gráficamente el árbol a partir de su raíz
        PanelArbol panel = new PanelArbol(arbol.getRaiz());

        // Añadimos el panel a la ventana dentro de un JScrollPane para permitir desplazamiento si es necesario
        add(new JScrollPane(panel));

        // Hacemos visible la ventana para el usuario
        setVisible(true);
    }

    /**
     * Método que se encarga de leer los datos desde el archivo "inventario.txt" y cargarlos en el árbol binario.
     * Cada línea del archivo debe tener el formato: nombre,cantidad
     */
    private void cargarDatos() {
        // Creamos un objeto File que representa el archivo de inventario
        File archivo = new File("inventario.txt");

        // Verificamos si el archivo existe antes de intentar leerlo
        if (archivo.exists()) {
            // Usamos try-with-resources para asegurarnos de cerrar el archivo después de leerlo
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;

                // Leemos el archivo línea por línea
                while ((linea = reader.readLine()) != null) {
                    // Dividimos la línea en partes separadas por comas
                    String[] partes = linea.split(",");

                    // Verificamos que la línea tenga exactamente dos partes: nombre y cantidad
                    if (partes.length == 2) {
                        // Extraemos y limpiamos el nombre del producto
                        String nombre = partes[0].trim();

                        // Convertimos la cantidad a un número entero
                        int cantidad = Integer.parseInt(partes[1].trim());

                        // Insertamos el nuevo nodo en el árbol binario con la cantidad y el nombre
                        arbol.insertar(cantidad, nombre);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                // Si ocurre un error al leer el archivo o al convertir la cantidad, mostramos un mensaje de error
                JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
            }
        }
    }
}
