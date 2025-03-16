package com.example.appcrud.ui.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appcrud.R;
import com.example.appcrud.models.Categoria;
import com.example.appcrud.models.Producto;
import com.example.appcrud.ui.viewmodels.ProductoViewModel;

public class ProductoActivity extends AppCompatActivity {
    private ProductoViewModel viewModel;
    private ProductoAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnAgregarProducto, btnIrCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        viewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        recyclerView = findViewById(R.id.recyclerProductos);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
        btnIrCategorias = findViewById(R.id.btnIrCategorias);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductoAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        viewModel.getProductos().observe(this, productos -> adapter.setProductos(productos));

        btnAgregarProducto.setOnClickListener(v -> mostrarDialogoProducto(null));

        btnIrCategorias.setOnClickListener(v -> {
            Intent intent = new Intent(ProductoActivity.this, CategoriaActivity.class);
            startActivity(intent);
        });
    }

    public void mostrarDialogoProducto(Producto producto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(producto == null ? "Agregar Producto" : "Editar Producto");

        View view = getLayoutInflater().inflate(R.layout.dialog_agregar_producto, null);
        EditText etNombre = view.findViewById(R.id.etNombreProducto);
        EditText etPrecio = view.findViewById(R.id.etPrecioProducto);
        EditText etStock = view.findViewById(R.id.etStockProducto);
        EditText etUrl = view.findViewById(R.id.etUrlProducto);
        Spinner spinnerCategoria = view.findViewById(R.id.spinnerCategoria);

        viewModel.getCategorias().observe(this, categorias -> {
            ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategoria.setAdapter(adapter);

            if (producto != null) {
                etNombre.setText(producto.getNombre());
                etPrecio.setText(String.valueOf(producto.getPrecio()));
                etStock.setText(String.valueOf(producto.getStock()));
                etUrl.setText(producto.getUrl());
                int pos = categorias.indexOf(producto.getCategoria());
                spinnerCategoria.setSelection(pos);
            }
        });

        builder.setView(view);
        builder.setPositiveButton("Guardar", (dialog, which) -> {

            String nombre = etNombre.getText().toString();
            double precio = Double.parseDouble(etPrecio.getText().toString());
            int stock = Integer.parseInt(etStock.getText().toString());
            String url = etUrl.getText().toString();
            Categoria categoriaSeleccionada = (Categoria) spinnerCategoria.getSelectedItem();

            if (producto == null) {
                Producto nuevoProducto = new Producto(0, nombre, categoriaSeleccionada, precio, stock, url);
                viewModel.agregarProducto(nuevoProducto);
            } else {
                Producto productoActualizado = new Producto(producto.getId(), nombre, categoriaSeleccionada, precio, stock, url);
                viewModel.actualizarProducto(productoActualizado);
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
