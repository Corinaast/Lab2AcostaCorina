/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1proyecto1cevallosacosta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ceval
 */
public class CarritoCompras {
    private List<ItemInventario> carrito = new ArrayList<>();

    public void agregarAlCarrito(ItemInventario item, int cantidad) {
        Productos productoOriginal = item.getProducto();
        Productos productoCopia = new Productos(
            productoOriginal.getNumeroSerie(),
            productoOriginal.getColor(),
            productoOriginal.getCorreo(),
            cantidad
        );
        Categoria categoria = item.getCategoria();
        carrito.add(new ItemInventario(productoCopia, categoria));
        System.out.println("Producto agregado al carrito.");
    }

    public void mostrarCarrito() {
        if (carrito.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        System.out.println("\n=== Carrito de Compras ===");
        for (ItemInventario item : carrito) {
            item.mostrarDatosCompletos();
        }
    }

    public void guardarCarritoCSV(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("NumeroSerie,Color,Correo,Cantidad,Codigo,Marca,Modelo,Precio\n");
            for (ItemInventario item : carrito) {
                writer.write(item.toCSV() + "\n");
            }
            System.out.println("Carrito guardado en CSV correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el carrito: " + e.getMessage());
        }
    }

    public void cargarCarritoDesdeCSV(String nombreArchivo) {
        carrito.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            boolean primera = true;
            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false;
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length == 8) {
                    int numeroSerie = Integer.parseInt(partes[0].trim());
                    String color = partes[1].trim();
                    String correo = partes[2].trim();
                    int cantidad = Integer.parseInt(partes[3].trim());
                    int codigo = Integer.parseInt(partes[4].trim());
                    String marca = partes[5].trim();
                    String modelo = partes[6].trim();
                    double precio = Double.parseDouble(partes[7].trim());

                    Productos producto = new Productos(numeroSerie, color, correo, cantidad);
                    Categoria categoria = new Categoria(codigo, marca, modelo, precio);
                    carrito.add(new ItemInventario(producto, categoria));
                }
            }
            System.out.println("Carrito cargado correctamente desde CSV.");
        } catch (IOException e) {
            System.out.println("Error al leer el carrito: " + e.getMessage());
        }
    }
   public double calcularTotal() {
    double total = 0.0;
    for (ItemInventario item : carrito) {
        int cantidad = item.getProducto().getCantidad(); // Aquí obtienes la cantidad desde el objeto producto
        double precio = item.getCategoria().getPrecio();
        total += cantidad * precio;
    }
    return total;
}
   public boolean isEmpty() {
    return carrito.isEmpty();
}


}