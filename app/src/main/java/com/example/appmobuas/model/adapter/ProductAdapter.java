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
import com.example.appmobuas.model.product.DataItem;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<DataItem> productList;

    public ProductAdapter(Context context, List<DataItem> productList) {
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
        DataItem item = productList.get(position);
        holder.productName.setText(item.getProductName());
        holder.productPrice.setText(String.format(Locale.getDefault(), "Harga: Rp %,d", item.getProductPrice()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailProducts.class);
            context.startActivity(intent);
        });
        if (item.getProductImage() != null && !item.getProductImage().isEmpty()) {
            byte[] imageBytes = Base64.decode(item.getProductImage().substring(item.getProductImage().indexOf(",") + 1), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.productImage.setImageBitmap(decodedImage);
        } else {
            holder.productImage.setImageResource(R.drawable.baseline_error_24);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateData(List<DataItem> data) {
        productList.clear();
        productList.addAll(data);
        notifyDataSetChanged();
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
