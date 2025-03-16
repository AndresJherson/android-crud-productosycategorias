package com.example.appcrud.models;

public class Producto {
    private int id;
    private String nombre;
    private int categoriaId;
    private double precio;
    private int stock;
    private String url;

    public Producto(int id, String nombre, int categoriaId, double precio, int stock, String url) {
        this.id = id;
        this.nombre = nombre;
        this.categoriaId = categoriaId;
        this.precio = precio;
        this.stock = stock;
        this.url = url;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
