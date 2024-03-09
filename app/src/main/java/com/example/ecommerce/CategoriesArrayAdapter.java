package com.example.ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesArrayAdapter extends RecyclerView.Adapter<CategoriesArrayAdapter.ViewHolder> {
    ArrayList<String> titles;
    ArrayList<Integer> images;
    OnRecyclerViewCategoryItemClickListener listener;
    public CategoriesArrayAdapter(ArrayList<String> titles, ArrayList<Integer> images , OnRecyclerViewCategoryItemClickListener listener) {
        this.titles = titles;
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_categories_recyclerview_item,null,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(titles.get(position));
        holder.imageview_img.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_title;
        ImageView imageview_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.custom_categories_recyclerview_textview);
            imageview_img = itemView.findViewById(R.id.custom_categories_recyclerview_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(tv_title.getText().toString());
                }
            });
        }
    }
}
