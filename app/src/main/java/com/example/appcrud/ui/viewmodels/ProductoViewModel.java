package com.example.appcrud.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcrud.models.Categoria;
import com.example.appcrud.models.Producto;
import com.example.appcrud.repository.CategoriaRepository;
import com.example.appcrud.repository.ProductoRepository;

import java.util.List;

public class ProductoViewModel extends ViewModel {
    private ProductoRepository repository;
    private CategoriaRepository categoriaRepository;
    private MutableLiveData<List<Producto>> productosLiveData;
    private MutableLiveData<List<Categoria>> categoriasLiveData;

    public ProductoViewModel() {
        repository = new ProductoRepository();
        categoriaRepository = new CategoriaRepository();
        productosLiveData = new MutableLiveData<>();
        categoriasLiveData = new MutableLiveData<>();
        cargarProductos();
        cargarCategorias();
    }

    public LiveData<List<Producto>> getProductos() {
        return productosLiveData;
    }

    public LiveData<List<Categoria>> getCategorias() {
        return categoriasLiveData;
    }

    public LiveData<Categoria> getCategoriaPorId(int categoriaId) {
        MutableLiveData<Categoria> categoriaLiveData = new MutableLiveData<>();
        new Thread(() -> {
            Categoria categoria = categoriaRepository.obtenerCategoriaPorId(categoriaId);
            categoriaLiveData.postValue(categoria);
        }).start();
        return categoriaLiveData;
    }

    public void cargarProductos() {
        new Thread(() -> {
            List<Producto> productos = repository.obtenerProductos();
            productosLiveData.postValue(productos);
        }).start();
    }

    public void cargarCategorias() {
        new Thread(() -> {
            List<Categoria> categorias = categoriaRepository.obtenerCategorias();
            categoriasLiveData.postValue(categorias);
        }).start();
    }

    public void agregarProducto(Producto producto) {
        new Thread(() -> {
            boolean exito = repository.agregarProducto(producto);
            if (exito) {
                cargarProductos();
            }
        }).start();
    }

    public void actualizarProducto(Producto producto) {
        new Thread(() -> {
            boolean exito = repository.actualizarProducto(producto);
            if (exito) {
                cargarProductos();
            }
        }).start();
    }

    public void eliminarProducto(int id) {
        new Thread(() -> {
            boolean exito = repository.eliminarProducto(id);
            if (exito) {
                cargarProductos();
            }
        }).start();
    }
}
