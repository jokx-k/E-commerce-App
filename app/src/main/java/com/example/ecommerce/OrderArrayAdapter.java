package com.example.ecommerce;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderArrayAdapter extends  RecyclerView.Adapter<OrderArrayAdapter.ViewHolder>{

    ArrayList<Product> AllProducts_With_Image;
    public OrderArrayAdapter(ArrayList<Product> allProducts_With_Image) {
        AllProducts_With_Image = allProducts_With_Image;
    }

    @NonNull
    @Override
    public OrderArrayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order_recyclerviewitem,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderArrayAdapter.ViewHolder holder, int position) {
        Product p = AllProducts_With_Image.get(position);
        holder.cursom_order_productname.setText(p.ProductName);
        holder.custom_order_productprice.setText("Total Price : " + String.valueOf(p.ProductPrice));
        holder.custom_order_productquantity.setText("Total Quantity : " + String.valueOf(p.ProductQuantity));
        holder.custom_order_productimage.setImageResource(p.ProductImageSrc);
    }

    @Override
    public int getItemCount() {
        return AllProducts_With_Image.size();
    }

    public void removeAt(int position) {
        AllProducts_With_Image.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, AllProducts_With_Image.size());
    }

    public void updateAt(int position , Product product){
        AllProducts_With_Image.set(position , product);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        TextView cursom_order_productname , custom_order_productprice , custom_order_productquantity;
        ImageView custom_order_productimage;
        CardView ordercardview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cursom_order_productname = itemView.findViewById(R.id.cursom_order_productname);
            custom_order_productimage = itemView.findViewById(R.id.custom_order_productimage);
            custom_order_productprice = itemView.findViewById(R.id.custom_order_productprice);
            custom_order_productquantity = itemView.findViewById(R.id.custom_order_productquantity);
            ordercardview = itemView.findViewById(R.id.ordercardview);

            ordercardview.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Delete or Update");
            menu.add(this.getAdapterPosition() , 121 , 0 , "Delete Product");
            menu.add(this.getAdapterPosition() , 122 , 1 , "Edit Quantity");
        }

    }

}
