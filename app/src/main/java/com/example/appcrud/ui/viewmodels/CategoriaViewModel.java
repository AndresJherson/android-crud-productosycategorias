package com.example.appcrud.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.appcrud.models.Categoria;
import com.example.appcrud.repository.CategoriaRepository;
import java.util.List;

public class CategoriaViewModel extends ViewModel {
    private CategoriaRepository repository;
    private MutableLiveData<List<Categoria>> categoriasLiveData;

    public CategoriaViewModel() {
        repository = new CategoriaRepository();
        categoriasLiveData = new MutableLiveData<>();
        cargarCategorias();
    }

    public LiveData<List<Categoria>> getCategorias() {
        return categoriasLiveData;
    }

    public void cargarCategorias() {
        new Thread(() -> {
            List<Categoria> categorias = repository.obtenerCategorias();
            categoriasLiveData.postValue(categorias);
        }).start();
    }

    public void agregarCategoria(Categoria categoria) {
        new Thread(() -> {
            boolean exito = repository.agregarCategoria(categoria);
            if (exito) {
                cargarCategorias();
            }
        }).start();
    }

    public void actualizarCategoria(Categoria categoria) {
        new Thread(() -> {
            boolean exito = repository.actualizarCategoria(categoria);
            if (exito) {
                cargarCategorias();
            }
        }).start();
    }

    public void eliminarCategoria(int id) {
        new Thread(() -> {
            boolean exito = repository.eliminarCategoria(id);
            if (exito) {
                cargarCategorias();
            }
        }).start();
    }
}
