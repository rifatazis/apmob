package com.example.appmobuas.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobuas.MainActivity;
import com.example.appmobuas.R;
import com.example.appmobuas.model.category.DataCategory;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<DataCategory> categoryList;

    public CategoryAdapter(Context context, List<DataCategory> categoryList){
        this.context = context;
        this.categoryList = categoryList;
    }
    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        DataCategory category = categoryList.get(position);
        holder.category.setText(category.getCategory());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void updateData(List<DataCategory> data) {
        categoryList.clear();
        categoryList.addAll(data);
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView category;

        CategoryViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);

        }
    }
}
