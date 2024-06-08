package com.example.appmobuas.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobuas.DetailProducts;
import com.example.appmobuas.R;
import com.example.appmobuas.model.products.ProductsData;

import java.util.List;
import java.util.Locale;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductsData> productList;

    public ProductsAdapter(Context context, List<ProductsData> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductsData products = productList.get(position);
        holder.productName.setText(products.getProductName());
        holder.productPrice.setText(String.format(Locale.getDefault(), "Rp %,d", products.getProductPrice()));

        if (products.getProductImage() != null && !products.getProductImage().isEmpty()) {
            byte[] imageBytes = Base64.decode(products.getProductImage(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.productImage.setImageBitmap(decodedImage);
        } else {
            holder.productImage.setImageResource(R.drawable.baseline_error_24);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailProducts.class);
            intent.putExtra("id_product", products.getIdProduct());
            intent.putExtra("product_name", products.getProductName());
            intent.putExtra("product_price", products.getProductPrice());
            intent.putExtra("product_details", products.getProductDetails());
            intent.putExtra("product_image", products.getProductImage());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;

        ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.tvProduct);
            productPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
