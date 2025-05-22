
package com.mycompany.p1proyecto1cevallosacosta;

class ItemInventario {
    private Productos producto;
    private Categoria categoria;

    public ItemInventario(Productos producto, Categoria categoria) {
        this.producto = producto;
        this.categoria = categoria;
    }

    public Productos getProducto() {
        return producto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void mostrarDatosCompletos() {
        System.out.println("Numero de Serie del Producto: " + producto.getNumeroSerie());
        System.out.println("Color Del Producto: " + producto.getColor());
        System.out.println("Correo: " + producto.getCorreo());
        System.out.println("Cantidad: "+producto.getCantidad());
        System.out.println("Codigo Categoria: " + categoria.getCodigo());
        System.out.println("Marca: " + categoria.getMarca());
        System.out.println("Modelo: " + categoria.getModelo());
        System.out.println("Precio: $" + categoria.getPrecio());
        System.out.println("-----------------------------");
    }

    public String toCSV() {
    return producto.getNumeroSerie() + "," +
           producto.getColor() + "," +
           producto.getCorreo() + "," +
           producto.getCantidad() + "," +
           categoria.getCodigo() + "," +
           categoria.getMarca() + "," +
           categoria.getModelo() + "," +
           categoria.getPrecio();
}

    public String toJSON() {
        return "{\n" +
            "  \"numeroSerie\": \"" + producto.getNumeroSerie() + "\",\n" +
            "  \"color\": \"" + producto.getColor() + "\",\n" +
            "  \"cantidad\": \"" + producto.getCantidad() + "\",\n" +
            "  \"correo\": \"" + producto.getCorreo() + "\",\n" +
            "  \"codigo\": " + categoria.getCodigo() + ",\n" +
            "  \"marca\": \"" + categoria.getMarca() + "\",\n" +
            "  \"modelo\": \"" + categoria.getModelo() + "\",\n" +
            "  \"precio\": " + categoria.getPrecio() + "\n" +
            "}";
    }
}
