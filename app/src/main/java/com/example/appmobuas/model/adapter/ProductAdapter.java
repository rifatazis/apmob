package com.example.appmobuas.model.adapter;

import android.content.Context;
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

import com.example.appmobuas.R;
import com.example.appmobuas.model.product.DataItem;

import java.util.List;

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

    public static Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        DataItem item = productList.get(position);
        holder.productName.setText(item.getProductName());
        holder.productPrice.setText(String.format("Price: Rp %d", item.getProductPrice()));

        String imagePath = item.getProductImage();

        if (imagePath != null && !imagePath.isEmpty()) {
            Bitmap bitmap = decodeBase64ToBitmap(imagePath);
            holder.productImage.setImageBitmap(bitmap);
        }
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
