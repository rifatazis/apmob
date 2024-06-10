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

import com.example.appmobuas.ProductsActivity;
import com.example.appmobuas.R;
import com.example.appmobuas.model.sports.SportData;

import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportViewHolder> {

    private Context context;
    private List<SportData> sportList;

    public SportsAdapter(Context context, List<SportData> sportList){
        this.context = context;
        this.sportList = sportList;
    }
    @NonNull
    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sports_item,parent,false);
        return new SportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder holder, int position) {
        SportData sport = sportList.get(position);
        holder.sports.setText(sport.getNama());

        byte[] imageBytes = Base64.decode(sport.getImage().substring(sport.getImage().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.ivImage.setImageBitmap(decodedImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductsActivity.class);
            intent.putExtra("sport_id", sport.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

    public void updateData(List<SportData> data) {
        sportList.clear();
        sportList.addAll(data);
        notifyDataSetChanged();
    }

    static class SportViewHolder extends RecyclerView.ViewHolder {
        TextView sports;
        ImageView ivImage;

        SportViewHolder(View itemView) {
            super(itemView);
            sports = itemView.findViewById(R.id.sports);
            ivImage = itemView.findViewById(R.id.ivImage);

        }
    }
}
