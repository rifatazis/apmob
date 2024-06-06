package com.example.appmobuas.model.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobuas.R;
import com.example.appmobuas.model.brands.BrandsData;

import java.util.List;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.BrandsViewHolder> {
    private Context context;
    private List<BrandsData> brandsList;
    private OnBrandClickListener listener;

    public BrandsAdapter(Context context, List<BrandsData> brandsList, OnBrandClickListener listener) {
        this.context = context;
        this.brandsList = brandsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BrandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brands_item, parent, false);
        return new BrandsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandsViewHolder holder, int position) {
        BrandsData brand = brandsList.get(position);

        byte[] imageBytes = Base64.decode(brand.getLogo().substring(brand.getLogo().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.logo.setImageBitmap(decodedImage);

        holder.itemView.setOnClickListener(v -> listener.onBrandClick(brand.getId()));
    }

    @Override
    public int getItemCount() {
        return brandsList.size();
    }

    public void updateBrands(List<BrandsData> data) {
        brandsList.clear();
        brandsList.addAll(data);
        notifyDataSetChanged();
    }

    static class BrandsViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;

        public BrandsViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
        }
    }

    public interface OnBrandClickListener {
        void onBrandClick(int brandId);
    }
}


