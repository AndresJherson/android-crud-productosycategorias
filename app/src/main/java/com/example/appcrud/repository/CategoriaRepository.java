package com.example.appcrud.repository;

import com.example.appcrud.models.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {
    public List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categoria");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public boolean agregarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categoria (id, nombre) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoria.getId());
            stmt.setString(2, categoria.getNombre());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCategoria(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCategoria(int id) {
        String sql = "DELETE FROM categoria WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Categoria obtenerCategoriaPorId(int id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(rs.getInt("id"), rs.getString("nombre"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
