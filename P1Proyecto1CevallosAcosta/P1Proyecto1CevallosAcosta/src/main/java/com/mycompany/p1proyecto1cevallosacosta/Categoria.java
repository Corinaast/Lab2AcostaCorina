
package com.mycompany.p1proyecto1cevallosacosta;

import java.util.Scanner;

public class Categoria {
    private int codigo;
    private double precio;
    private String modelo;
    private String marca;
    Scanner dato = new Scanner(System.in);

    public Categoria(int codigo, String modelo, String marca, double precio) {
        this.codigo = codigo;
        this.precio = precio;
        this.modelo = modelo;
        this.marca = marca;
    }

    public int getCodigo() { 
        return codigo; 
    }
    public double getPrecio() {
        return precio;
    }
    public String getModelo() { 
        return modelo;
    }
    public String getMarca() {
        return marca; 
    }

    public void setCodigo() {
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print("Ingrese el codigo del producto: ");
                codigo = dato.nextInt();
                if (codigo < 100 || codigo > 999) {
                throw new CampoVacioException("El codigo debe tener exactamente 3 digitos y ser mayor a 0.");
            }
                valido = true;
            } catch (CampoVacioException e) {
                System.out.println("Error al validar codigo: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Entrada no valida. Intente nuevamente.");
                dato.nextLine();
            }
        }
    }

    public void setPrecio() {
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print("Ingrese el precio del producto: ");
                precio = dato.nextDouble();
                if (precio <= 0 || precio > 1000) throw new PrecioInvalidoException("El precio debe ser mayor a 0 y no mayor a 1000");
                this.precio = precio;
                valido = true;
            } catch (PrecioInvalidoException e) {
                System.out.println("Error al validar precio: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Entrada no valida. Intente nuevamente.");
                dato.nextLine();
            }
        }
    }

    public void setModelo(String modelo) {
        try {
            while (modelo == null || modelo.trim().isEmpty()) {
                System.out.println("El tipo no puede estar vacio. Ingrese nuevamente:");
                modelo = dato.nextLine();
            }
            this.modelo = modelo.trim();
        } catch (Exception e) {
            System.out.println("Error al asignar Modelo: " + e.getMessage());
        }
    }

    public void setMarca(String marca) {
        try {
            while (marca == null || marca.trim().isEmpty()) {
                System.out.println("La etiqueta no puede estar vacia. Ingrese nuevamente:");
                marca = dato.nextLine();
            }
            this.marca = marca.trim();
        } catch (Exception e) {
            System.out.println("Error al asignar Marca: " + e.getMessage());
        }
    }

    public void mostrarDatosCat() {
        System.out.println("Ingrese la Marca del articulo:");
        setMarca(dato.nextLine());
        setCodigo();
        dato.nextLine();
        System.out.println("Ingrese el Modelo de articulo:");
        setModelo(dato.nextLine());
        setPrecio();
        dato.nextLine();
    }
}