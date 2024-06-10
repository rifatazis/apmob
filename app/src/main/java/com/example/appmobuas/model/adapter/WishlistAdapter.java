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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobuas.DetailProducts;
import com.example.appmobuas.R;
import com.example.appmobuas.model.wishlist.WishlistItem;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WishlistItem> wishlist;

    public WishlistAdapter(Context context, ArrayList<WishlistItem> wishlist) {
        this.context = context;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishlistItem item = wishlist.get(position);

        holder.tvProductName.setText(item.getProduct_name());
        holder.tvProductPrice.setText(String.format("Rp %,d", item.getProduct_price()));
        holder.removeWish.setOnClickListener(v->{
            wishlist.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, wishlist.size());
            Toast.makeText(context, "Item removed from wishlist", Toast.LENGTH_SHORT).show();
        });
        if (item.getProduct_image() != null && !item.getProduct_image().isEmpty()) {
            byte[] imageBytes = Base64.decode(item.getProduct_image(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.ivProductImage.setImageBitmap(decodedImage);
        } else {
            holder.ivProductImage.setImageResource(R.drawable.baseline_error_24);
        }
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailProducts.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice;
        ImageView ivProductImage, removeWish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductwish);
            tvProductPrice = itemView.findViewById(R.id.tvProductPriceWish);
            ivProductImage = itemView.findViewById(R.id.product_imageWish);
            removeWish = itemView.findViewById(R.id.btnRemoveWish);
        }
    }
}
