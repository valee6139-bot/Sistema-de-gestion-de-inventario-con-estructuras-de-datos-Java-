package com.mycompany.proyectofinal;

import java.io.*;
import javax.swing.JOptionPane;

/**
 * Esta clase se encarga de gestionar un archivo de texto llamado "historial.txt".
 * Permite agregar nuevos registros al archivo y leer su contenido completo.
 */
public class ArchivoTXTGestion {

    // Nombre del archivo donde se almacenará el historial
    private final String nombreArchivo = "historial.txt";

    /**
     * Agrega un nuevo registro al archivo de historial.
     * Si el archivo no existe, se crea automáticamente.
     *
     * @param registro El texto que se desea agregar al historial.
     */
    public void agregarRegistro(String registro) {
        // Utilizamos try-with-resources para asegurar que el archivo se cierre correctamente
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            // Escribimos el registro en una nueva línea del archivo
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            // En caso de error, mostramos un mensaje al usuario
            JOptionPane.showMessageDialog(null, "Error guardando historial: " + e.getMessage());
        }
    }

    /**
     * Lee todo el contenido del archivo de historial y lo devuelve como una cadena de texto.
     *
     * @return Una cadena que contiene todo el historial, línea por línea.
     */
    public String leerHistorial() {
        // Utilizamos StringBuilder para construir eficientemente la cadena resultante
        StringBuilder contenido = new StringBuilder();
        // Utilizamos try-with-resources para asegurar que el archivo se cierre correctamente
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            // Leemos el archivo línea por línea
            while ((linea = reader.readLine()) != null) {
                // Agregamos cada línea al StringBuilder, seguida de un salto de línea
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            // En caso de error, mostramos un mensaje al usuario
            JOptionPane.showMessageDialog(null, "Error leyendo historial: " + e.getMessage());
        }
        // Devolvemos el contenido completo del archivo como una cadena
        return contenido.toString();
    }
}
