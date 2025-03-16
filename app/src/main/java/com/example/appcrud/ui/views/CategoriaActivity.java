package com.example.appcrud.ui.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcrud.R;
import com.example.appcrud.models.Categoria;
import com.example.appcrud.ui.viewmodels.CategoriaViewModel;

public class CategoriaActivity extends AppCompatActivity {
    private CategoriaViewModel viewModel;
    private CategoriaAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnAgregarCategoria, btnIrProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        viewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);
        recyclerView = findViewById(R.id.recyclerCategorias);
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria);
        btnIrProductos = findViewById(R.id.btnIrProductos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoriaAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        // Observando la lista de categorías
        viewModel.getCategorias().observe(this, categorias -> adapter.setCategorias(categorias));

        btnAgregarCategoria.setOnClickListener(v -> mostrarDialogoCategoria(null));

        btnIrProductos.setOnClickListener(v -> {
            Intent intent = new Intent(CategoriaActivity.this, ProductoActivity.class);
            startActivity(intent);
        });
    }

    public void mostrarDialogoCategoria(Categoria categoria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(categoria == null ? "Agregar Categoría" : "Editar Categoría");

        View view = getLayoutInflater().inflate(R.layout.dialog_agregar_categoria, null);
        EditText etNombre = view.findViewById(R.id.etNombreCategoria);

        if (categoria != null) {
            etNombre.setText(categoria.getNombre());
        }

        builder.setView(view);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = etNombre.getText().toString();

            if (categoria == null) {
                Categoria nuevaCategoria = new Categoria(0, nombre);
                viewModel.agregarCategoria(nuevaCategoria);
            } else {
                Categoria categoriaActualizada = new Categoria(categoria.getId(), nombre);
                viewModel.actualizarCategoria(categoriaActualizada);
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
