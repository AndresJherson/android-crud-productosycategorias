package com.example.appcrud.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcrud.models.Producto;
import com.example.appcrud.R;
import com.example.appcrud.ui.viewmodels.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private List<Producto> productos = new ArrayList<>();
    private ProductoViewModel viewModel;

    public ProductoAdapter(ProductoViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.bind(producto);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvCategoria, tvStock;
        ImageView imgProducto;
        Button btnEditar, btnEliminar;

        ProductoViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            tvStock = itemView.findViewById(R.id.tvStockProducto);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            tvCategoria = itemView.findViewById(R.id.tvCategoriaProducto);
            btnEditar = itemView.findViewById(R.id.btnEditarProducto);
            btnEliminar = itemView.findViewById(R.id.btnEliminarProducto);
        }

        void bind(Producto producto) {
            tvNombre.setText(producto.getNombre());
            tvPrecio.setText("Precio: $" + producto.getPrecio());
            tvStock.setText("Stock: " + producto.getStock());

            if (!producto.getUrl().isEmpty()) {
                Glide.with(itemView.getContext()).load(producto.getUrl()).into(imgProducto);
            }

            viewModel.getCategoriaPorId(producto.getCategoria().getId()).observe((LifecycleOwner) itemView.getContext(), categoria -> {
                if (categoria != null) {
                    tvCategoria.setText("Categoría: " + categoria.getNombre());
                }
            });

            btnEditar.setOnClickListener(v -> {
                ((ProductoActivity) itemView.getContext()).mostrarDialogoProducto(producto);
            });

            btnEliminar.setOnClickListener(v -> {
                viewModel.eliminarProducto(producto.getId());
            });
        }
    }
}
