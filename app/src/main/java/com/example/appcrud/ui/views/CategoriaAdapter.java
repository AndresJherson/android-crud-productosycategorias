package com.example.appcrud.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appcrud.models.Categoria;
import com.example.appcrud.R;
import com.example.appcrud.ui.viewmodels.CategoriaViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {
    private List<Categoria> categorias = new ArrayList<>();
    private CategoriaViewModel viewModel;

    public CategoriaAdapter(CategoriaViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.bind(categoria);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        notifyDataSetChanged();
    }

    class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        Button btnEditar, btnEliminar;

        CategoriaViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreCategoria);
            btnEditar = itemView.findViewById(R.id.btnEditarCategoria);
            btnEliminar = itemView.findViewById(R.id.btnEliminarCategoria);
        }

        void bind(Categoria categoria) {
            tvNombre.setText(categoria.getNombre());

            btnEditar.setOnClickListener(v -> {
                ((CategoriaActivity) itemView.getContext()).mostrarDialogoCategoria(categoria);
            });

            btnEliminar.setOnClickListener(v -> {
                viewModel.eliminarCategoria(categoria.getId());
            });
        }
    }
}
