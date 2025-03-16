package com.example.appcrud.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.appcrud.models.Producto;
import com.example.appcrud.repository.ProductoRepository;

import java.util.List;

public class ProductoViewModel extends ViewModel {
    private ProductoRepository repository;
    private MutableLiveData<List<Producto>> productosLiveData;

    public ProductoViewModel() {
        repository = new ProductoRepository();
        productosLiveData = new MutableLiveData<>();
        cargarProductos();
    }

    public LiveData<List<Producto>> getProductos() {
        return productosLiveData;
    }

    public void cargarProductos() {
        new Thread(() -> {
            List<Producto> productos = repository.obtenerProductos();
            productosLiveData.postValue(productos);
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
