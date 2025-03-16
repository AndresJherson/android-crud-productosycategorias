package com.example.appcrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appcrud.ui.views.CategoriaActivity;
import com.example.appcrud.ui.views.ProductoActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnProductos, btnCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProductos = findViewById(R.id.btnProductos);
        btnCategorias = findViewById(R.id.btnCategorias);

        btnProductos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductoActivity.class);
            startActivity(intent);
        });

        btnCategorias.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
            startActivity(intent);
        });
    }
}
