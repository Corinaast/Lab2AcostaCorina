package com.mycompany.p1proyecto1cevallosacosta;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class P1Proyecto1CevallosAcosta {
    private static final String ARCHIVO_TRABAJADORES = "trabajadores.dat";

    // ✅ Expresión corregida: 1 mayúscula, 1 minúscula, 3 dígitos, 1 símbolo
    private static final Pattern PATRON_CONTRASENA =
        Pattern.compile("^(?=(?:.*\\d){3,})(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*]).{6,}$");

    private static final Pattern PATRON_USUARIO = Pattern.compile("^[a-zA-Z]+$");

    public static void main(String[] args) {
        GestorInventario gestor = new GestorInventario();
        CarritoCompras carrito = new CarritoCompras();
        Scanner sc = new Scanner(System.in);
        int opcionPrincipal;
        boolean esTrabajador = false;

        gestor.leerDesdeCSV("inventario.csv");

        System.out.println("===== SISTEMA DE INVENTARIO =====");
        System.out.println("1. Ingresar como Cliente");
        System.out.println("2. Registrarse/Ingresar como Trabajador");
        System.out.print("Seleccione una opcion: ");
        opcionPrincipal = sc.nextInt();
        sc.nextLine();

        if (opcionPrincipal == 2) {
            System.out.println("\n===== TRABAJADOR =====");
            System.out.println("1. Registrarse");
            System.out.println("2. Ingresar");
            System.out.print("Seleccione una opcion: ");
            int opcionTrabajador = sc.nextInt();
            sc.nextLine();

            if (opcionTrabajador == 1) {
                registrarTrabajador(sc);
                System.out.println("Registro exitoso! Ahora puede ingresar.");
            }

            esTrabajador = ingresarTrabajador(sc);

            if (esTrabajador) {
                System.out.println("\nBienvenido trabajador!");
            } else {
                System.out.println("\nIngresando como cliente.");
            }
        } else {
            System.out.println("\nBienvenido cliente!");
        }

        if (esTrabajador) {
            menuTrabajador(gestor, sc);
        } else {
            menuCliente(gestor, carrito, sc);
        }

        sc.close();
    }

    private static void registrarTrabajador(Scanner sc) {
        String usuario;
        boolean usuarioValido = false;

        do {
            System.out.print("\nIngrese nombre de usuario: ");
            usuario = sc.nextLine();

            if (!PATRON_USUARIO.matcher(usuario).matches()) {
                System.out.println("El nombre de usuario solo debe contener letras (A-Z, a-z)");
            } else if (usuarioExiste(usuario)) {
                System.out.println("Este usuario ya existe. Por favor elija otro.");
            } else if (usuario.trim().isEmpty()) {
                System.out.println("El usuario no puede estar vacio.");
            } else {
                usuarioValido = true;
            }
        } while (!usuarioValido);

        String contrasena;
        boolean contrasenaValida = false;

        do {
            System.out.print("Ingrese contrasena (1 mayuscula, minusculas, 3 numeros y un simbolo): ");
            contrasena = sc.nextLine();

            if (validarContrasena(contrasena)) {
                contrasenaValida = true;
            } else {
                System.out.println("Contraseña no valida. Debe contener:");
                System.out.println("- Al menos 1 letra mayuscula");
                System.out.println("- Letras minusculas");
                System.out.println("- Al menos 3 numeros");
                System.out.println("- Al menos 1 simbolo (!@#$%^&*)");
            }
        } while (!contrasenaValida);

        guardarCredenciales(usuario, contrasena);
        System.out.println("Trabajador registrado con exito!");
    }

    private static boolean ingresarTrabajador(Scanner sc) {
        System.out.print("\nIngrese nombre de usuario: ");
        String usuario = sc.nextLine();

        int intentos = 3;
        while (intentos > 0) {
            System.out.print("Ingrese contrasena (intentos restantes: " + intentos + "): ");
            String contrasena = sc.nextLine();

            if (validarCredenciales(usuario, contrasena)) {
                return true;
            }

            intentos--;
            if (intentos > 0) {
                System.out.println("Credenciales incorrectas. Intente nuevamente.");
            }
        }

        System.out.println("Demasiados intentos fallidos. Ingresando como cliente.");
        return false;
    }

    private static boolean usuarioExiste(String usuario) {
        File archivo = new File(ARCHIVO_TRABAJADORES);
        if (!archivo.exists()) return false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            List<Map<String, String>> credenciales = (List<Map<String, String>>) ois.readObject();
            for (Map<String, String> cred : credenciales) {
                if (cred.get("usuario").equalsIgnoreCase(usuario)) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Archivo vacío o corrupto
        }

        return false;
    }

    private static boolean validarContrasena(String contrasena) {
        return PATRON_CONTRASENA.matcher(contrasena).matches();
    }

    private static void guardarCredenciales(String usuario, String contrasena) {
        List<Map<String, String>> credenciales = new ArrayList<>();

        File archivo = new File(ARCHIVO_TRABAJADORES);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                credenciales = (List<Map<String, String>>) ois.readObject();
            } catch (Exception e) {
                // Archivo corrupto o vacío
            }
        }

        Map<String, String> nueva = new HashMap<>();
        nueva.put("usuario", usuario);
        nueva.put("contrasena", contrasena);
        credenciales.add(nueva);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(credenciales);
        } catch (IOException e) {
            System.out.println("Error al guardar credenciales. Intente nuevamente.");
        }
    }

    private static boolean validarCredenciales(String usuario, String contrasena) {
        File archivo = new File(ARCHIVO_TRABAJADORES);
        if (!archivo.exists()) return false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            List<Map<String, String>> credenciales = (List<Map<String, String>>) ois.readObject();
            for (Map<String, String> cred : credenciales) {
                if (cred.get("usuario").equalsIgnoreCase(usuario) &&
                    cred.get("contrasena").equals(contrasena)) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Error de lectura
        }

        return false;
    }

    private static void menuTrabajador(GestorInventario gestor, Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== MENU DE TRABAJADOR =====");
            System.out.println("1. Agregar producto al catalogo");
            System.out.println("2. Mostrar catalogo");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    gestor.leerDesdeCSV("inventario.csv");
                    gestor.agregarItem();
                    gestor.guardarEnCSV("inventario.csv");
                }
                case 2 -> {
                    gestor.leerDesdeCSV("inventario.csv");
                    gestor.mostrarInventario();
                }
                case 3 -> System.out.println("Saliendo del sistema de trabajador...");
                default -> System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

   private static void menuCliente(GestorInventario gestor, CarritoCompras carrito, Scanner sc) {
    int opcion;
    do {
        System.out.println("\n===== MENU DE CLIENTE =====");
        System.out.println("1. Mostrar catálogo");
        System.out.println("2. Ver contenido del carrito");
        System.out.println("3. Realizar la Compra");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");

        opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1 -> {
                // Forzar recarga del catálogo
                gestor.leerDesdeCSV("inventario.csv");
                gestor.mostrarInventario();

                System.out.print("¿Desea agregar productos al carrito? (s/n): ");
                String respuesta = sc.nextLine();

                if (respuesta.equalsIgnoreCase("s")) {
                    System.out.print("Ingrese el código del producto: ");
                    int codigo = sc.nextInt();
                    System.out.print("Ingrese la cantidad: ");
                    int cantidad = sc.nextInt();
                    sc.nextLine();

                    ItemInventario item = gestor.buscarItemPorCodigo(codigo);
                    if (item != null) {
                        if (cantidad <= item.getProducto().getCantidad()) {
                            carrito.agregarAlCarrito(item, cantidad);
                            carrito.guardarCarritoCSV("carrito.csv");
                            System.out.println("Producto agregado al carrito.");
                        } else {
                            System.out.println("No hay suficiente stock. Stock disponible: " + item.getProducto().getCantidad());
                        }
                    } else {
                        System.out.println("Producto no encontrado. Verifique el código.");
                    }
                }
            }
            // Resto del código del menú...
        }
    } while (opcion != 4);
}
}
