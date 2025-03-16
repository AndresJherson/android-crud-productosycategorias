package com.example.appcrud.repository;

import com.example.appcrud.models.Categoria;
import com.example.appcrud.models.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {
    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT producto.id AS producto_id, producto.nombre AS producto_nombre, producto.precio AS producto_precio, producto.stock AS producto_stock, producto.url as producto_url, " +
                "categoria.id AS categoria_id, categoria.nombre AS categoria_nombre " +
                "FROM producto " +
                "INNER JOIN categoria ON producto.categoria_id = categoria.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("categoria_id"),
                        rs.getString("categoria_nombre")
                );
                productos.add(new Producto(
                        rs.getInt("producto_id"),
                        rs.getString("producto_nombre"),
                        categoria,
                        rs.getDouble("producto_precio"),
                        rs.getInt("producto_stock"),
                        rs.getString("producto_url")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }


    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO producto (id, nombre, categoria_id, precio, stock, url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getId());
            stmt.setString(2, producto.getNombre());
            stmt.setInt(3, producto.getCategoria().getId());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getStock());
            stmt.setString(6, producto.getUrl());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE producto SET nombre=?, categoria_id=?, precio=?, stock=?, url=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getCategoria().getId());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setString(5, producto.getUrl());
            stmt.setInt(6, producto.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
